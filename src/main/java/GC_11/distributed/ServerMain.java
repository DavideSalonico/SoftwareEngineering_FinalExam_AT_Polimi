package GC_11.distributed;

import GC_11.controller.Controller;
import GC_11.distributed.socket.ServerSock;
import GC_11.exceptions.ExceededNumberOfPlayersException;
import GC_11.exceptions.NameAlreadyTakenException;
import GC_11.model.Game;
import GC_11.model.Lobby;
import GC_11.model.Player;
import GC_11.network.GameViewMessage;
import GC_11.network.LobbyViewMessage;

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

    private ServerImplRMI serverRMI;
    private ServerSock serverSocket;
    private Game gameModel;
    private Controller controller = new Controller(this);
    private Map<String, String> clientMap = new HashMap<String, String>(); // <nickname, connectionType>

    /**
     * Constructs a new ServerMain object with the specified port number.
     * Initializes the RMI and socket servers.
     *
     * @param port The port number to listen on.
     */

    public ServerMain(int port) {
        try {
            this.serverRMI = new ServerImplRMI(this);
            this.serverSocket = new ServerSock(port, this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

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
     * @param connectionType The type of connection (RMI or SOCKET)
     */
    public synchronized void addConnection(String clientNickname, String connectionType) {
        clientMap.put(clientNickname, connectionType);
        try {
            this.controller.getLobby().addPlayer(clientNickname);
        } catch (ExceededNumberOfPlayersException e) {
            throw new RuntimeException(e); //TODO: handle exception
        } catch (NameAlreadyTakenException e) {
            throw new RuntimeException(e); //TODO: handle exception
        }
        System.out.println("ADDED CONNECTION: " + clientNickname + " " + connectionType);
    }


    /**
     * Notifies all clients with the given gameViewMessage.
     * Adjusts the message for each client and sends it through the corresponding server type.
     */

    public void notifyClientsLobby() {
        for (Map.Entry<String, String> client : clientMap.entrySet()) {
            if (client.getValue().equals("RMI")) {
                serverRMI.notifyClientsLobby(new LobbyViewMessage(this.controller.getLobby()));
            } else if (client.getValue().equals("SOCKET")) {
                //TODO aggiornare la lobby tramite socket
            } else {
                System.out.println("Unable to notify " + client.getKey() + " because connection type is unknown");
            }
        }
    }

    public void notifyClientsGame() {
        for (Map.Entry<String, String> client : clientMap.entrySet()) {

            // Make a copy of the messageView for every player and keeps the original messageView intact
            GameViewMessage messageViewCopy = new GameViewMessage(this.controller.getGame(), null);

            // Just before sending the message, we remove the personal goal from the other players
            for (Player p : messageViewCopy.getPlayers()) {
                if (!p.getNickname().equals(client.getKey())) {
                    p.setPersonalGoal(null);
                }
            }

            // For every client we check the connection type and notify the corresponding server
            if (client.getValue().equals("RMI")) {
                try {
                    serverRMI.notifyClient(client.getKey(), messageViewCopy);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else if (client.getValue().equals("SOCKET")) {
                serverSocket.notifyClient(client.getKey(), messageViewCopy);
            } else {
                System.out.println("Unable to notify " + client.getKey() + " because connection type is unknown");
            }
        }

    }

    /**
     * Asks the first player for the maximum number of players in the lobby.
     * Updates the lobby's maximum players value.
     */

    public void askMaxPlayers() {
        boolean ok = false;
        while (!ok) {
            if (this.clientMap.get(this.controller.getLobby().getPlayers().get(0)).equals("RMI")) {
                try {
                    int max = this.serverRMI.getClients().get(0).askMaxNumber();
                    this.controller.setMaxPlayers(max);
                    ok = true;
                } catch (RemoteException e) {
                    System.out.println("Unable to ask max players because of RemoteException");
                }
            } else if (this.clientMap.get(this.controller.getLobby().getPlayers().get(0)).equals("SOCKET")) {
                int max = this.serverSocket.askMaxNumber();
                this.controller.setMaxPlayers(max);
                ok = true;
            } else {
                System.out.println("Unable to ask max players because connection type is unknown");
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() instanceof Lobby) {
            if (evt.getPropertyName().equals("FIRST PLAYER")) {
                this.askMaxPlayers();
            }
            if (evt.getPropertyName().equals("LAST PLAYER")) {
                this.notifyClientsLobby();
                this.controller.startGame();
                this.notifyClientsGame();
            } else
                this.notifyClientsLobby();
        } else {
            this.notifyClientsGame();
        }
    }
}
