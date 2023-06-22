package GC_11.distributed.socket;


import GC_11.distributed.ServerMain;
import GC_11.model.Lobby;
import GC_11.network.LobbyController;
import GC_11.network.MessageView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * The class responsible for handling socket connections in the server.
 * Implements the PropertyChangeListener interface to listen for property changes.
 */
public class ServerSock implements PropertyChangeListener {

    Lobby lobby;
    LobbyController lobbyController;
    private final int port;
    private ServerSocket serverSocket;
    private ServerMain serverMain;
    private final ExecutorService executor = Executors.newFixedThreadPool(32);

    private List<ServerClientHandler> serverClientHandlerList;

    private LinkedHashMap<String, ServerClientHandler> socketMap = new LinkedHashMap<String, ServerClientHandler>(); // <socket, nickname>


    /**
     * Constructs a ServerSock object with the specified port number and serverMain instance.
     *
     * @param port       The port number to listen on.
     * @param serverMain The ServerMain instance.
     */

    public ServerSock(int port, ServerMain serverMain) {

        // Opening TCP port
        this.port = port;
        this.serverMain = serverMain;
    }

    public void lobbySetup(int maxPlayers) {
        this.lobby = new Lobby();
    }


    /**
     * Starts the server by initializing the server socket and accepting client connections.
     *
     * @throws IOException            If an I/O error occurs while initializing the server socket.
     * @throws ClassNotFoundException If a class required for client communication is not found.
     */
    public void startServer() throws IOException, ClassNotFoundException {

        // Init phase

        try {
            this.serverSocket = new ServerSocket(this.port);
            //System.out.println("---Server---");
            //System.out.println("Server ready on port: " + port);

        } catch (IOException e) {
            System.out.println("Error during initialization phase...");
            System.out.println(e.getMessage());
        }
        this.serverClientHandlerList = new ArrayList<>();
        //this.lobby=new Lobby();

        System.out.println("SERVER SOCKET RUNNING");
        // Waiting for connection
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                //System.out.println("Received client connection");
                ServerClientHandler sch = new ServerClientHandler(socket, this);
                this.serverClientHandlerList.add(sch);
                executor.submit(sch);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }


    /**
     * Notifies all clients except the sourceHandler with the given message.
     *
     * @param message       The message to send to clients.
     * @param sourceHandler The source client handler that triggered the notification.
     */

    public void notifyAllClients(String message, ServerClientHandler sourceHandler) {
        for (ServerClientHandler sch : serverClientHandlerList) {
            if (sch != sourceHandler) {
                sch.sendMessageToClient(message);
            }
        }
    }

    /**
     * Notifies all clients except the sourceHandler with the given message view.
     *
     * @param message       The message view to send to clients.
     * @param sourceHandler The source client handler that triggered the notification.
     */

    public void notifyAllClients(MessageView message, ServerClientHandler sourceHandler) {
        for (ServerClientHandler sch : serverClientHandlerList) {
            if (sch != sourceHandler) {
                sch.sendMessageViewToClient(message);
            }
        }
    }

    /**
     * Notifies a specific client with the given message view.
     *
     * @param messageView The message view to send to the client.
     * @param client      The nickname of the client to notify.
     */
    public void notifyClient(MessageView messageView, String client) {
        socketMap.get(client).sendMessageViewToClient(messageView);
    }

    /**
     * Notifies all client sockets about a disconnection and removes the sourceHandler from the serverClientHandlerList.
     *
     * @param socket        The socket that got disconnected.
     * @param sourceHandler The source client handler that triggered the disconnection.
     */

    public void notifyDisconnectionAllSockets(Socket socket, ServerClientHandler sourceHandler) {
        Socket disconnectedSocket = socket;
        serverClientHandlerList.remove(sourceHandler);
        for (ServerClientHandler sch : serverClientHandlerList) {
            sch.notifyDisconnection(disconnectedSocket);
        }
    }

    // TODO: CHECK IF LOBBY IS NECESSARY
    public Lobby getLobby() {
        return lobby;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName() == "newGameView") {

        }
    }

    /**
     * Retrieves the socketMap containing the client sockets and corresponding client handlers.
     *
     * @return The socketMap containing the client sockets and client handlers.
     */
    public Map<String, ServerClientHandler> getSocketMap() {
        return socketMap;
    }

    /**
     * Retrieves the ServerMain instance.
     *
     * @return The ServerMain instance.
     */
    public ServerMain getServerMain() {
        return serverMain;
    }

    /**
     * Notifies a specific client with the given message view.
     *
     * @param clientNickname The nickname of the client to notify.
     * @param messageView    The message view to send to the client.
     */
    public void notifyClient(String clientNickname, MessageView messageView) {
        socketMap.get(clientNickname).sendMessageViewToClient(messageView);
    }

    public int askMaxNumber() {

        String clientNickname =this.socketMap.entrySet().iterator().next().getKey();

        return socketMap.get(clientNickname).askMaxNumber();
    }
}

