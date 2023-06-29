package GC_11.distributed;

import GC_11.controller.Controller;

import GC_11.distributed.rmi.ServerRMIImpl;
import GC_11.distributed.socket.ServerSock;

import GC_11.network.message.*;
import GC_11.network.choices.Choice;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.util.ConcurrentModificationException;
import java.util.HashMap;

import java.util.Iterator;
import java.util.Map;


/**
 * The main server class for the multiplayer game server.
 * Implements the PropertyChangeListener interface to listen for property changes in the model.
 */

public class ServerMain implements PropertyChangeListener {

    private ServerRMIImpl serverRMI;
    private ServerSock serverSocket;
    private Controller controller = new Controller(this);
    private Map<String, Server> clientMap = new HashMap<String, Server>(); // <nickname, Server >

    // Runnable threads for starting the server sockets
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

    // Runnable threads for starting the server RMI
    Thread serverRMIThread = new Thread(new Runnable() {
        @Override
        public void run() {
            serverRMI.setup();
        }

    });

    /**
     * Constructs a new ServerMain object with the specified port number.
     * Initializes the RMI and socket servers.
     *
     * @param port The port number to listen on.
     */

    public ServerMain(int port) {
        try {
            this.serverRMI = new ServerRMIImpl(this);
            this.serverSocket = new ServerSock(port, this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Starts the server by starting the server socket and RMI threads.
     */
    public void startServer() {
        serverSocketThread.start();
        serverRMIThread.start();
        pinger.start();
    }

    /**
     * This method is called by the serverSocket or serverRMI when a new connection is established and save the connection in a map
     *
     * @param clientNickname The nickname of the client
     */
    public synchronized void addConnection(String clientNickname, Server server) {
        clientMap.put(clientNickname, server);

        System.out.println("ADDED CONNECTION: " + clientNickname); //TODO fare un metodo getConnectionType


        if (clientMap.size() == 1) {
            // Ask the first player to choose the max number of players
            try {
                server.askMaxNumber();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        notifyClients(new LobbyViewMessage(this.controller.getLobby()));
        if (clientMap.size() > 1 && clientMap.size() == this.controller.getLobby().getMaxPlayers()) {
            // Start the game
            this.getController().startGame();
        }

    }

    /**
     * Asks the first player for the maximum number of players in the lobby.
     * Updates the lobby's maximum players value.
     */

    public void askMaxPlayers() {

    }


    public void makeAMove(Choice choice) { //TODO: rename
        try {
            this.controller.update(choice);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        //TODO far costruire i MessageView giusti
        MessageView msg = (MessageView) evt.getNewValue();
        notifyClients(msg);
    }

    public void notifyClients(MessageView msg) {
        synchronized (this.clientMap) {
            try {
                for (Map.Entry<String, Server> entry : clientMap.entrySet()) {
                    MessageView msgCopy = msg.sanitize(entry.getKey());
                    try {
                        entry.getValue().sendMessage(msgCopy, entry.getKey());
                    } catch (RemoteException e) {
                        removeConnection(entry.getKey());
                    }
                }
            }catch(ConcurrentModificationException ignored){

            }
        }
    }

    public void removeConnection(String nickname) {
        if (this.clientMap.get(nickname) != null) {
            System.out.println("REMOVED CONNECTION: " + nickname);
            if (this.controller.getGame() != null){
                this.controller.getGame().setEndGame(true);
            }
            synchronized (this.clientMap) {
                this.clientMap.remove(nickname);
            }
            notifyClients(new DisconnectionMessage());
        } else {
            System.out.println("Unable to remove connection because nickname is unknown");
        }

    }

    public Controller getController() {
        return this.controller;
    }

    public Map getClientsMap() {
        return this.clientMap;
    }

    public void isAlive(String s) {
        //TODO
    }

    public void notifyClient(MessageView messageView, String s) {
        try {
            this.clientMap.get(s).sendMessage(messageView, s);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    Thread pinger = new Thread() {
        @Override
        public void run() {
            while (true) {
                Iterator<Map.Entry<String, Server>> iterator = clientMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    try{
                        Map.Entry<String, Server> entry = iterator.next();
                        try {
                            entry.getValue().sendMessage(new PingMessage(), entry.getKey());
                        } catch (RemoteException e) {
                            removeConnection(entry.getKey());
                        }
                    }
                    catch(ConcurrentModificationException ignored){
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

}
