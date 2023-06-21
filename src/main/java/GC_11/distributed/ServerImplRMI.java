package GC_11.distributed;

import GC_11.controller.Controller;
import GC_11.controller.LobbyController;
import GC_11.exceptions.ExceededNumberOfPlayersException;
import GC_11.exceptions.NameAlreadyTakenException;
import GC_11.model.Game;
import GC_11.model.GameViewMessage;
import GC_11.model.Lobby;
import GC_11.util.choices.Choice;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServerImplRMI extends UnicastRemoteObject implements ServerRMI {

    private Controller gameController;

    private Game gameModel;
    private LobbyController lobbyController;

    private Lobby lobbyModel;

    private int maxPlayer;
    private List<ClientRMI> clients = new ArrayList<>();

    private ServerMain serverMain;

    public ServerImplRMI(ServerMain serverMain) throws RemoteException {
        super();
        this.serverMain = serverMain;
    }

    public void setup() {
        try {

            //System.out.println("***** Constructing server implementation *****\n");
            //System.out.println("***** Getting the registry *****\n");
            Registry registry = LocateRegistry.createRegistry(1099);
            //System.out.println("***** Binding server implementation to registry *****\n");
            registry.rebind("server", this);
            //System.out.println("***** Waiting for clients *****\n");
            System.out.println("SERVER RMI RUNNING");
        } catch (Exception e) {
            System.err.println("server error: " + e.getMessage());
        }
    }

    @Override
    public synchronized void register(ClientRMI client) throws ExceededNumberOfPlayersException, NameAlreadyTakenException, RemoteException {
        serverMain.addConnection(client.getNickname(), "RMI");
        /*if (clients.size() == 0) {
            maxPlayer = client.askMaxNumber();
            lobbyModel = new Lobby(maxPlayer);
            lobbyController = new LobbyController(lobbyModel);
        }
        clients.add(client);
        System.out.println(client.getNickname() + " connected to the server");
        lobbyController.addPlayerName(client.getNickname());
        System.out.println(client.getNickname() + " aggiunto alla lobby");
        for (ClientRMI c : clients) {
            try {
                new Thread(() -> {
                    try {
                        c.updateViewLobby(new LobbyViewMessage(lobbyModel));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }).start();
                System.out.println(c.getNickname() + " aggiornato");
            } catch (RemoteException e) {
                System.out.println("Error while updating the client: " + e.getMessage() + ". Skipping the update...");
            }
        }
        System.out.println("\n");
        if (clients.size() == maxPlayer) {
            System.out.println("\n ##### Starting a game #####\n");
            this.gameModel = new Game(lobbyModel.getPlayers(), this);
            this.gameController = new Controller(this.gameModel);
            for (ClientRMI c : clients) {
                try {
                    new Thread(() -> {
                        try {
                            c.updateStartGame(new GameViewMessage(gameModel, null));
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }).start();
                    System.out.println(c.getNickname() + " aggiornato GAME");
                } catch (RemoteException e) {
                    System.out.println("Error while notify the client " + e.getMessage());
                }
            }
            System.out.println("\n");
        }
*/
    }

    @Override
    public void updateGame(ClientRMI client, Choice choice) throws RemoteException {
        PropertyChangeEvent evt = new PropertyChangeEvent(client, "choice made", null, choice);
        System.out.println(client.getNickname() + ": " + choice.getType());
        this.gameController.propertyChange(evt);
    }

    @Override
    public void updateLobby(ClientRMI client, Choice choice) throws RemoteException {
        PropertyChangeEvent evt = new PropertyChangeEvent(client, "choice made", null, choice);
        this.lobbyController.propertyChange(evt);
    }

    public synchronized void notifyClients() {
        for (ClientRMI c : clients) {
            new Thread(() -> {
                try {
                    c.updateViewGame(new GameViewMessage(gameModel, null));
                    System.out.println(c.getNickname() + " aggiornato GAME correctly");
                } catch (RemoteException e) {
                    System.out.println(e.getMessage());
                }
            }).start();

        }
        System.out.println("\n");
    }

    public synchronized void notifyClient(String nickname, GameViewMessage gameViewMessage) throws RemoteException {
        for (ClientRMI c : clients) {
            if (c.getNickname().equals(nickname)) {
                new Thread(() -> {
                    try {
                        c.updateViewGame(gameViewMessage);
                        System.out.println(c.getNickname() + " aggiornato GAME correctly");
                    } catch (RemoteException e) {
                        System.out.println(e.getMessage());
                    }
                }).start();
                System.out.println("\n");
            }
        }
    }
}

