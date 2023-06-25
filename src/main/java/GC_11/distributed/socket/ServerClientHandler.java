package GC_11.distributed.socket;

import GC_11.exceptions.IllegalMoveException;
import GC_11.model.Player;
import GC_11.network.GameViewMessage;
import GC_11.network.LobbyViewMessage;
import GC_11.network.choices.Choice;
import GC_11.network.choices.ChoiceFactory;

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

    boolean connected;

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
        this.connected = true;
        connectionSetup();
        if (connected)
            readThread.start();

    }

    private void connectionSetup() {

        if (connected) {
            GameViewMessage msg = new GameViewMessage("Hi! Welcome to the game! Please, insert your nickname:");
            sendMessageViewToClient(msg);
            String reply = null;
            try {
                reply = (String) inputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Unable to read nickname");
                closeConnection();
                return;
            }
            this.nickname = reply;
            this.server.getSocketMap().put(this.nickname, this);
            this.server.getServerMain().addConnection(this.nickname, "SOCKET");
        }

    }

    private Thread readThread = new Thread(new Runnable() {


        @Override
        public void run() {
            System.out.println("Thread read started");
            while (connected)
                try {
                    receiveMessageFromClient();
                    //receiveChoiceFromClient();
                } catch (IOException | ClassNotFoundException | IllegalMoveException e) {

                    closeConnection();
                    try {
                        clientSocket.close();
                    } catch (IOException ex) {
                        System.err.println("Unable to close socket");
                    }
                }
        }
    });


    /**
     * Receives a lobby message from the client.
     *
     * @return The received lobby message.
     * @throws IOException            If an I/O error occurs.
     * @throws ClassNotFoundException If the class of the serialized object cannot be found.
     */

    public void sendLobbyViewMessage(LobbyViewMessage msg) {
        try {
            outputStream.writeObject(msg);
            outputStream.flush();
            outputStream.reset();
        } catch (IOException e) {
            System.err.println("Unable to send lobby message");
        }
    }


    /**
     * Receives a message from the client.
     * It always gets a String from the client. If the name is set and there is more than 1 client in the map
     * it creates a Choice and notify it to the main server
     *
     * @return The received message.
     * @throws IOException            If an I/O error occurs.
     * @throws ClassNotFoundException If the class of the serialized object cannot be found.
     */

    public String receiveMessageFromClient() throws IOException, ClassNotFoundException, IllegalMoveException {
        String clientMessage = null;
        Choice clientChoice;
        if (connected) {
            try {
                clientMessage = (String) inputStream.readObject();
                if (this.nickname != null && !this.nickname.isEmpty() && this.server.getServerMain().getClientsMap().size() > 1) {
                    clientChoice = ChoiceFactory.createChoice(new Player(this.nickname), clientMessage);
                    this.server.getServerMain().makeAMove(clientChoice);
                }
            } catch (IOException e) {
                System.out.println("Error during receiving message from client");
                closeConnection();
                throw new IOException();
            } catch (ClassNotFoundException e) {
                System.out.println("Error during deserialization of message from client");
                closeConnection();
                throw new ClassNotFoundException();
            } catch (IllegalMoveException e) {
                System.out.println("Error during reading message from client");
                closeConnection();
                throw new IllegalMoveException();
            } finally {
                return clientMessage;
            }
        }
        return clientMessage;
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
    public void sendMessageViewToClient(GameViewMessage messageView) {
        if (connected) {
            try {
                outputStream.writeObject(messageView);
                outputStream.flush();
                outputStream.reset();
            } catch (IOException e) {
                System.out.println("Error during sending gameView to client");
                closeConnection();
            }
        }

    }


    /**
     * This method is called when an error occurs during the communication with the client.
     * Closes the connection with the client. It also notifies the server of the disconnection.
     * The server will then notify all the other clients of the disconnection.
     */
    private void closeConnection() {
        this.connected = false;
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
        this.readThread.interrupt();
        //this.server.notifyDisconnectionAllSockets(this.clientSocket, this);
        this.server.getServerMain().removeConnection(this.nickname);

    }


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

    public int askMaxNumber() {

        int maxPlayers = -1;
        GameViewMessage msg = new GameViewMessage("Inserire il numero massimo di giocatori");
        sendMessageViewToClient(msg);
        try {
            maxPlayers = Integer.parseInt(receiveMessageFromClient());
            while (maxPlayers <= 1 || maxPlayers >= 5) {
                msg.setMessage("Il numero di giocatori deve essere compreso tra 2 e 4");
                sendMessageViewToClient(msg);
                maxPlayers = Integer.parseInt(receiveMessageFromClient());
            }
        } catch (NumberFormatException e) {
            msg.setMessage("Inserire un numero");
            sendMessageViewToClient(msg);
        } catch (IOException | ClassNotFoundException | IllegalMoveException e) {
            System.err.println("Unable to receive message from client");
            this.connected = false;
        }

        return maxPlayers;
    }

    public boolean askLoading() {
        boolean loading = false;
        GameViewMessage msg = new GameViewMessage("Vuoi caricare una partita precedente?");
        sendMessageViewToClient(msg);
        try {
            String answer = receiveMessageFromClient();
            while (!answer.equalsIgnoreCase("si") && !answer.equalsIgnoreCase("no")) {
                msg.setMessage("Rispondere con si o no");
                sendMessageViewToClient(msg);
                answer = receiveMessageFromClient();
            }
            if (answer.equalsIgnoreCase("si")) {
                loading = true;
            }
        } catch (IOException | ClassNotFoundException | IllegalMoveException e) {
            System.err.println("Unable to receive message from client");
            this.connected = false;
        }
        return loading;
    }
}

