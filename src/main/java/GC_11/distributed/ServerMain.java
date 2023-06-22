package GC_11.distributed;

import GC_11.controller.Controller;
import GC_11.distributed.socket.ServerSock;
import GC_11.model.Game;
import GC_11.model.Lobby;

import GC_11.model.GameViewMessage;
import GC_11.model.Player;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public class ServerMain implements PropertyChangeListener {

    private ServerImplRMI serverRMI;
    private ServerSock serverSocket;
    private Game gameModel;
    private Lobby lobby = new Lobby();
    private Controller controller;
    private Map<String, String> clientMap = new HashMap<String, String>(); // <nickname, connectionType>

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

    public void startServer() {
        serverSocketThread.start();
        serverRMIThread.start();
    }

    public synchronized void addConnection(String clientNickname, String connectionType) {
        clientMap.put(clientNickname, connectionType);
        System.out.println("ADDED CONNECTION: " + clientNickname + " " + connectionType);
    }



    public void notifyClients(GameViewMessage messageView) {
        for (Map.Entry<String, String> client : clientMap.entrySet()) {

            // Just before sending the message, we remove the personal goal from the other players
            for(Player p : messageView.getPlayers()){
                if(!p.getNickname().equals(client.getKey())){
                    p.setPersonalGoal(null);
                }
            }

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

    public void askMaxPlayers() {
        //TODO: ask max players to the first player
        this.controller.getLobby().setMaxPlayers(4); //To change
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getSource() instanceof Lobby){
            if(evt.getPropertyName().equals("FIRST PLAYER")){
                this.askMaxPlayers();
            }
            if(evt.getPropertyName().equals("LAST PLAYER")){
                this.controller.startGame();
            }
        }
        this.notifyClients((GameViewMessage) evt.getNewValue());
    }
}
