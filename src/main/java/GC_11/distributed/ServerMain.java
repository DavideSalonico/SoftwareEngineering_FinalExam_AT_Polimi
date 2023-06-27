package GC_11.distributed;

import GC_11.controller.Controller;

import GC_11.distributed.RMI.ServerRMIImpl;
import GC_11.distributed.socket.ServerSock;

import GC_11.network.message.GameViewMessage;
import GC_11.network.message.LobbyViewMessage;
import GC_11.network.choices.Choice;

import GC_11.network.message.MessageView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.util.HashMap;

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
    }

    /**
     * This method is called by the serverSocket or serverRMI when a new connection is established and save the connection in a map
     *
     * @param clientNickname The nickname of the client
     */
    public synchronized void addConnection(String clientNickname, Server server) {
        clientMap.put(clientNickname, server);

        System.out.println("ADDED CONNECTION: " + clientNickname); //TODO fare un metodo getConnectionType


        if(clientMap.size() == 1){
            // Ask the first player to choose the max number of players
            try {
                server.askMaxNumber();
                notifyClients(new LobbyViewMessage(this.controller.getLobby()));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        else if(clientMap.size() > 1 && clientMap.size() == this.controller.getLobby().getMaxPlayers()){
            // Start the game
            this.getController().startGame();
            notifyClients(new GameViewMessage(this.controller.getGame(), null));
        }else{
            // Notify all clients that a new player has joined the lobby
            notifyClients(new LobbyViewMessage(this.controller.getLobby()));
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

    private void notifyClients(MessageView msg) {
        for(Map.Entry<String,Server> entry : clientMap.entrySet()){

            MessageView msgCopy = msg.sanitize(entry.getKey());

            try {
                entry.getValue().sendMessage(msgCopy,entry.getKey());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        //JsonWriter.saveGame(new GameViewMessage(this.controller.getGame(), null, null)); //TODO: adapt parameters and uncomment
    }

    public void removeConnection(String nickname) {
        if (this.clientMap.get(nickname) != null){
            System.out.println("REMOVED CONNECTION: " + nickname + " " + this.clientMap.get(nickname));
            this.clientMap.remove(nickname);
            this.controller.getGame().setEndGame(true);
            GameViewMessage msg = new GameViewMessage(this.controller.getGame(), new Exception("Player " + nickname + " disconnected"));
        }
        else{
            System.out.println("Unable to remove connection because nickname is unknown");
        }

    }

    public Controller getController(){
        return this.controller;
    }

    public Map getClientsMap(){
        return this.clientMap;
    }

    public void isAlive(String s) {
        //TODO
    }
}
