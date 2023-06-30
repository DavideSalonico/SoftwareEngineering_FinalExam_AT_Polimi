package GC_11.distributed.socket;

import GC_11.exceptions.ExceededNumberOfPlayersException;
import GC_11.exceptions.IllegalMoveException;
import GC_11.exceptions.NameAlreadyTakenException;
import GC_11.network.message.*;
import GC_11.network.choices.Choice;

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
        if (connected) {
            readThread.start();
        }

    }

    private void connectionSetup() {

        if (connected) {
            Choice reply = null;
            boolean ok = false;
            while (!ok) {
                NicknameMessage msg = new NicknameMessage();
                sendMessageViewToClient(msg);
                try {
                    reply = (Choice) inputStream.readObject();
                    reply.executeOnServer(this.server.getServerMain().getController());
                    ok = true;

                } catch (IOException | ClassNotFoundException e) {
                    System.err.println("Client Disconnected. Unable to read nickname");
                    closeConnection();
                    return;
                }
            }
            this.nickname = reply.getParams().get(0);
            this.server.getSocketMap().put(this.nickname, this);
            this.server.getServerMain().addConnection(this.nickname, this.server);
        }

    }

    private Thread readThread = new Thread(new Runnable() {


        @Override
        public void run() {
            System.out.println("Thread read started");
            while (connected)
                try {
                    receiveChoiceFromClient();
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
     * Method that receives a Choice object from the client and sends it to the main server.
     *
     * @throws IOException            if the connection is closed
     * @throws ClassNotFoundException if the class of the object received is not found
     * @throws IllegalMoveException   if the move is not valid
     */
    public void receiveChoiceFromClient() throws IOException, ClassNotFoundException, IllegalMoveException {
        Choice clientChoice;
        if (connected) {
            try {
                clientChoice = (Choice) inputStream.readObject();
                this.server.receiveMessage(clientChoice);
            } catch (IOException e) {
                closeConnection();
                throw new IOException();
            } catch (ClassNotFoundException e) {
                closeConnection();
                throw new ClassNotFoundException();
            }
        }
    }


    /**
     * Sends a MessageView object to the client.
     *
     * @param messageView The MessageView object to send.
     */
    public void sendMessageViewToClient(MessageView messageView) {
        if (connected) {
            try {
                outputStream.writeObject(messageView);
                outputStream.flush();
                outputStream.reset();
            } catch (IOException e) {
                closeConnection();
            }
        }

    }

    private void closeConnection() {
        if (connected) {
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
        }
        this.server.getServerMain().removeConnection(this.nickname);
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

    /**
     * Method that asks the client to choose a number of players sending a MaxNumberMessage.
     * Number of players is sent only to the first client that connects to the server.
     */
    public void askMaxNumber() {
        sendMessageViewToClient(new MaxNumberMessage());
        try {
            receiveChoiceFromClient();
        } catch (IOException | IllegalMoveException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

