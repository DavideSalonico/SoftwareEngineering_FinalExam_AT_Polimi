package GC_11.distributed;

import GC_11.controller.Controller;
import GC_11.controller.LobbyController;
import GC_11.distributed.socket.ServerSock;
import GC_11.model.Game;
import GC_11.model.GameViewMessage;
import GC_11.network.Lobby;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public class ServerMain {

    private ServerImplRMI serverRMI;
    private ServerSock serverSocket;
    private Game gameModel;
    private Lobby lobbyModel;
    private Controller gameController;
    private LobbyController lobbyController;
    private Map<String, String> clientMap = new HashMap<String, String>(); // <nickname, connectionType>

    private int maxPlayers;

    public ServerMain(int port) {
        try {
            this.serverRMI = new ServerImplRMI(this);
            this.serverSocket = new ServerSock(port, this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    Thread serverSocketThread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                serverSocket.startServer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });

    Thread serverRMIThread = new Thread(new Runnable() {

        @Override
        public void run() {
            serverRMI.setup();
        }

    });

    public void startServer() throws Exception {

        serverSocketThread.start();
        serverRMIThread.start();

    }

    public synchronized void addConnection(String clientNickname, String connectionType) {
        clientMap.put(clientNickname, connectionType);
        System.out.println("ADDED CONNECTION: " + clientNickname + " " + connectionType);
    }


    public void notifyClients(GameViewMessage messageView) {

        for (Map.Entry<String, String> client : clientMap.entrySet()) {
            if (client.getValue().equals("RMI")) {
                try {
                    serverRMI.notifyClient(client.getKey(), messageView);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else if (client.getValue().equals("SOCKET")) {
                serverSocket.notifyClient(client.getKey(), messageView);
            } else {
                throw new RuntimeException("Connection type not supported");
            }
        }

    }
}
