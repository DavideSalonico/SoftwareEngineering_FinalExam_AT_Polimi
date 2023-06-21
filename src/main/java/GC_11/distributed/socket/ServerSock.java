package GC_11.distributed.socket;


import GC_11.distributed.ServerMain;
import GC_11.network.Lobby;
import GC_11.network.LobbyController;
import GC_11.network.MessageView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerSock implements PropertyChangeListener {

    Lobby lobby;
    LobbyController lobbyController;
    private final int port;
    private ServerSocket serverSocket;
    private ServerMain serverMain;
    private final ExecutorService executor = Executors.newCachedThreadPool();

    private List<ServerClientHandler> serverClientHandlerList;

    private Map<Socket, String> socketMap = new LinkedHashMap<Socket,String>(); // <socket, nickname>


    public ServerSock(int port, ServerMain serverMain) {

        // Opening TCP port
        this.port = port;
        this.serverMain = serverMain;
    }

    public void lobbySetup(int maxPlayers) {
        this.lobby = new Lobby(maxPlayers);
    }

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

    public void notifyAllClients(String message, ServerClientHandler sourceHandler) {
        for (ServerClientHandler sch : serverClientHandlerList) {
            if (sch != sourceHandler) {
                sch.sendMessageToClient(message);
            }
        }
    }

    public void notifyAllClients(MessageView message, ServerClientHandler sourceHandler) {
        for (ServerClientHandler sch : serverClientHandlerList) {
            if (sch != sourceHandler) {
                sch.sendMessageViewToClient(message);
            }
        }
    }

    public void notifyDisconnectionAllSockets(Socket socket, ServerClientHandler sourceHandler) {
        Socket disconnectedSocket = socket;
        serverClientHandlerList.remove(sourceHandler);
        for (ServerClientHandler sch : serverClientHandlerList) {
            sch.notifyDisconnection(disconnectedSocket);
        }
    }

    public Lobby getLobby() {
        return lobby;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName() == "newGameView") {

        }
    }

    public Map<Socket, String> getSocketMap() {
        return socketMap;
    }

    public ServerMain getServerMain() {
        return serverMain;
    }
}

