package GC_11.distributed.socket;

import GC_11.exceptions.ExceededNumberOfPlayersException;
import GC_11.exceptions.NameAlreadyTakenException;
import GC_11.network.MessageView;
import GC_11.util.choices.Choice;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


/**
 * The ServerClientHandler class handles the communication with a single client on the server side.
 * It implements the Runnable interface, allowing it to be executed as a separate thread.
 */
public class ServerClientHandler implements Runnable {

    private final ServerSock server;
    private final Socket clientSocket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    private String nickname;

    /**
     * Constructs a ServerClientHandler object with the specified client socket and server instance.
     *
     * @param socket The client socket.
     * @param server The server instance.
     */
    public ServerClientHandler(Socket socket, ServerSock server) {
        this.clientSocket = socket;
        this.server = server;
    }

    /**
     * Receives a lobby message from the client.
     *
     * @return The received lobby message.
     * @throws IOException            If an I/O error occurs.
     * @throws ClassNotFoundException If the class of the serialized object cannot be found.
     */

    public String receiveLobbyMessageFromClient() throws IOException, ClassNotFoundException {
        String clientMessage = null;
        try {
            clientMessage = (String) inputStream.readObject();
            System.out.println("Received message from client: " + clientMessage);
        } catch (IOException e) {
            System.out.println("Error during receiving message from client");
            closeConnection();
            server.notifyDisconnectionAllSockets(this.clientSocket, this);
            throw new IOException();
        } catch (ClassNotFoundException e) {
            System.out.println("Error during deserialization of message from client");
            closeConnection();
            server.notifyDisconnectionAllSockets(this.clientSocket, this);
            throw new ClassNotFoundException();
        } finally {
            return clientMessage;
        }
    }

    /**
     * Receives a message from the client.
     *
     * @return The received message.
     * @throws IOException            If an I/O error occurs.
     * @throws ClassNotFoundException If the class of the serialized object cannot be found.
     */

    public String receiveMessageFromClient() throws IOException, ClassNotFoundException {
        String clientChoice = null;
        try {
            clientChoice = (String) inputStream.readObject();
            System.out.println("Received choice from client: " + clientChoice);
            server.notifyAllClients(clientChoice, this);
        } catch (IOException e) {
            System.out.println("Error during receiving message from client");
            closeConnection();
            server.notifyDisconnectionAllSockets(this.clientSocket, this);
            throw new IOException();
        } catch (ClassNotFoundException e) {
            System.out.println("Error during deserialization of message from client");
            closeConnection();
            server.notifyDisconnectionAllSockets(this.clientSocket, this);
            throw new ClassNotFoundException();
        } finally {
            return clientChoice;
        }
    }


    /**
     * Receives a choice from the client.
     */
    public void receiveChoiceFromClient() {
        try {
            Choice clientChoice = (Choice) inputStream.readObject();
            System.out.println("Received choice from: " + clientChoice.getPlayer() + ":" + clientChoice.getType() + clientChoice.getParams());
            //this.controller.propertyChange(new PropertyChangeEvent(this, "choice", null, clientChoice));
        } catch (IOException e) {
            System.out.println("Error during receiving message from client");
            closeConnection();
            server.notifyDisconnectionAllSockets(this.clientSocket, this);
        } catch (ClassNotFoundException e) {
            System.out.println("Error during deserialization of message from client");
            closeConnection();
            server.notifyDisconnectionAllSockets(this.clientSocket, this);
        }
    }

    /**
     * Sends a message to the client.
     *
     * @param s The message to send.
     */
    public void sendMessageToClient(String s) {
        try {
            outputStream.writeObject(s);
            outputStream.flush();
            outputStream.reset();
        } catch (IOException e) {
            System.out.println("Error during sending message to client");
            closeConnection();

        }
    }

    /**
     * Sends a MessageView object to the client.
     *
     * @param messageView The MessageView object to send.
     */
    public void sendMessageViewToClient(MessageView messageView) {
        try {
            outputStream.writeObject(messageView);
            outputStream.flush();
            outputStream.reset();
        } catch (IOException e) {
            System.out.println("Error during sending gameView to client");
            closeConnection();
        }
    }


    private Thread readThread = new Thread(new Runnable() {
        boolean connected = true;

        @Override
        public void run() {
            System.out.println("Thread read started");
            while (connected)
                try {
                    receiveMessageFromClient();
                } catch (IOException | ClassNotFoundException e) {
                    connected = false;
                    try {
                        clientSocket.close();
                    } catch (IOException ex) {
                        System.err.println("Unable to close socket");
                    }
                }
        }
    });


    /**
     * The run method that will be executed when the ServerClientHandler is started as a separate thread.
     */

    @Override
    public void run() {

        // Init phase
        try {
            this.inputStream = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            System.err.println("Unable to get input stream");
        }
        try {
            this.outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Unable to get output stream");
        }
        connectionSetup();
        /*try {
            lobbySetup();
        } catch (IOException e) {
            System.err.println("Unable to setup lobby");
        } catch (ClassNotFoundException e) {
            System.err.println("Unable to setup lobby");
        }*/
        readThread.start();

    }

    private void connectionSetup() {
        sendMessageToClient("Hi! Welcome to the game! Please, insert your nickname:");
        String reply = null;
        try {
            reply = (String) inputStream.readObject();
        } catch (IOException e) {
            System.err.println("Unable to read nickname");
        } catch (ClassNotFoundException e) {
            System.err.println("Unable to read nickname");
        }
        this.nickname = reply;
        this.server.getSocketMap().put(this.nickname, this);
        this.server.getServerMain().addConnection(this.nickname, "SOCKET");
    }

    private void closeConnection() {
        System.out.println("Closing socket: " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
        try {
            inputStream.close();
        } catch (IOException e) {
            System.err.println("Unable to close input stream");
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            System.err.println("Unable to close output stream");
        }
        try {
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Unable to close socket");
        }
        this.server.notifyDisconnectionAllSockets(this.clientSocket, this);

    }


    /*
    private void lobbySetup() throws IOException, ClassNotFoundException {
        System.out.println("Lobby setup...");
        if(this.server.getLobby().getPlayers().size()==0) {
            System.out.println("Lobby is empty");
            String reply;
            outputStream.writeObject("Non c'è ancora nessun giocatore nella lobby. Vuoi crearne una?\n[S] Sì\n[N] no");
            outputStream.flush();
            reply = (String) inputStream.readObject();
            System.out.println("Risposta: "+reply);
            if (reply.equals("S") || reply.equals("Si") || reply.equals("Sì") || reply.equals("Y") || reply.equals("Yes") || reply.equals("YES") || reply.equals("s") || reply.equals("si") || reply.equals("sì")) {
                System.out.println("Creazione della lobby");
                int maxPlayers=-1;
                while (maxPlayers <= 1 || maxPlayers >= 5){
                    System.out.println("Inserire numero di giocatori");
                    outputStream.writeObject("Inserire il numero massimo di giocatori");
                    outputStream.flush();
                    reply = (String) inputStream.readObject();
                    maxPlayers = Integer.parseInt(reply);
                    System.out.println("Ricevuto:" + maxPlayers);
                    if (maxPlayers <= 1 || maxPlayers >= 5){
                        outputStream.writeObject("Il numero di giocatori deve essere compreso tra 2 e 4");
                        outputStream.flush();
                    }
                }
                outputStream.writeObject("OK");
                outputStream.flush();
                this.server.getLobby().setMaxPlayers(maxPlayers);
                outputStream.writeObject("Inserisci il tuo nome");
                outputStream.flush();
                String playerName = (String) inputStream.readObject();
                try{
                    this.server.getLobby().addPlayer(playerName);
                    this.server.getLobby().setFisrtPlayer(playerName);
                    System.out.println("Aggiunto " + playerName + " alla lobby");
                    outputStream.writeObject("Sei stato aggiunto alla lobby!");
                    outputStream.flush();
                } catch (ExceededNumberOfPlayersException e) {
                    outputStream.writeObject("Raggiunto il numero massimo di giocatori");
                } catch (NameAlreadyTakenException e) {
                    throw new RuntimeException(e);
                }
                String message = "Lobby creata!\nNumero massimo di giocatori: "+this.server.getLobby().getMaxPlayers() + "\n";
                message = message + "Giocatori nella lobby: \n";
                for (int i=0; i<this.server.getLobby().getPlayers().size();i++){
                    message=message+this.server.getLobby().getPlayers().get(i);
                }

                outputStream.writeObject(message);
                outputStream.flush();
            }
        }
        else{
            System.out.println("Lobby has" + this.server.getLobby().getPlayers().size() + "players");

        }

    }
*/

    /**
     * Notifies the client of disconnection.
     *
     * @param socket The disconnected socket.
     */
    public void notifyDisconnection(Socket socket) {
        String alert = "Socket " + socket.getInetAddress() + ":" + socket.getPort() + " has disconnected";
        try {
            outputStream.writeObject(alert);
            outputStream.reset();
            outputStream.flush();
        } catch (IOException e) {
            System.err.println("Unable to notify socket disconnection");
        }
    }

    public void lobbySetup() throws IOException, ClassNotFoundException {
        if (this.server.getLobby() == null || this.server.getLobby().getPlayers().size() == 0) {
            sendMessageToClient("Non c'è ancora nessun giocatore nella lobby. Vuoi crearne una?\n[S] Sì\n[N] no");
            String reply = receiveLobbyMessageFromClient();
            reply = reply.toUpperCase();
            if (reply.equals("S") || reply.equals("Y") || reply.equals("SI") || reply.equals("YES")) {
                sendMessageToClient("Inserire il numero massimo di giocatori");
                int maxPlayers = Integer.parseInt(receiveLobbyMessageFromClient());
                while (maxPlayers <= 1 || maxPlayers >= 5) {
                    sendMessageToClient("Il numero di giocatori deve essere compreso tra 2 e 4");
                    maxPlayers = Integer.parseInt(receiveLobbyMessageFromClient());
                }
                this.server.lobbySetup(maxPlayers);
                sendMessageToClient("Inserisci il tuo nome");
                String playerName = receiveLobbyMessageFromClient();
                boolean setName = false;
                while (!setName) {
                    try {
                        this.server.getLobby().addPlayer(playerName);
                        setName = true;
                    } catch (ExceededNumberOfPlayersException e) {
                        sendMessageToClient("Raggiunto il numero massimo di giocatori");
                    } catch (NameAlreadyTakenException e) {
                        sendMessageToClient("Nome già in uso. Sceglierne un altro");
                    }
                }
            } else {
                sendMessageToClient("Sei sicuro?");
            }
        } else {
            sendMessageToClient("Esiste già una lobby. Vuoi entrare?\n[S] Sì\n[N] no");
            String reply = receiveMessageFromClient();
            reply = reply.toUpperCase();
            if (reply.equals("S") || reply.equals("Y") || reply.equals("SI") || reply.equals("YES")) {
                sendMessageToClient("Inserisci il tuo nome");
                String playerName = receiveMessageFromClient();
                boolean setName = false;
                while (!setName) {
                    try {
                        this.server.getLobby().addPlayer(playerName);
                        setName = true;
                    } catch (ExceededNumberOfPlayersException e) {
                        sendMessageToClient("Raggiunto il numero massimo di giocatori");
                    } catch (NameAlreadyTakenException e) {
                        sendMessageToClient("Nome già in uso. Sceglierne un altro");
                    }
                }
            } else {
                sendMessageToClient("Sei sicuro?");
            }
        }
        sendMessageToClient("Pronto per giocare");
    }

    /**
     * Sets the nickname for the client.
     *
     * @param nickname The nickname to set.
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Gets the nickname of the client.
     *
     * @return The nickname of the client.
     */
    public String getNickname() {
        return this.nickname;
    }
}

