package GC_11.distributed.socket;

import GC_11.controller.Controller;
import GC_11.controller.LobbyController;
import GC_11.distributed.ServerImplRMI;
import GC_11.model.Game;
import GC_11.network.Lobby;

import java.rmi.RemoteException;
import java.util.Map;

public class ServerMain {

    private ServerImplRMI serverRMI;
    private ServerSock serverSocket;
    private Game gameModel;
    private Lobby lobbyModel;
    private Controller gameController;
    private LobbyController lobbyController;
    private Map<Client, String> clientMap;

    private int maxPlayers;

    public ServerMain(int port) {
        try {
            this.serverRMI = new ServerImplRMI();
            this.serverSocket = new ServerSock(port);
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

    public void notifyClients() {
        //serverSocket.notifyAllClients();
        //serverRMI.notifyClients();

        /*

        for (client : clientMap){
            // Filtraggio personal Goal card e chat
            if client.connection.equals("RMI"){
                serverRMI.notifyClient(messageView, client);
            }
            else if (client.connection.equals("SOCKET")){
                serverSocket.notifyClient(messageView, client);
            }
            else{
                throw new RuntimeException("Connection type not supported");
            }
        }
         */
    }
}
