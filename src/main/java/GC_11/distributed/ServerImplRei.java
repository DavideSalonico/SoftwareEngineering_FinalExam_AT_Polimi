package GC_11.distributed;

import GC_11.controller.Controller;
import GC_11.controller.LobbyController;
import GC_11.exceptions.ExceededNumberOfPlayersException;
import GC_11.exceptions.NameAlreadyTakenException;
import GC_11.model.Game;
import GC_11.model.GameViewMessage;
import GC_11.network.Lobby;
import GC_11.network.LobbyViewMessage;
import GC_11.util.Choice;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServerImplRei extends UnicastRemoteObject implements ServerRei {

    private Controller gameController;

    private Game gameModel;
    private LobbyController lobbyController;

    private Lobby lobbyModel;

    int maxPlayer;
    List<ClientRei> clients = new ArrayList<>();

    public ServerImplRei() throws RemoteException {
        super();
    }
/*
    protected ServerImplRei(int port) throws RemoteException {
        super(port);
    }

    protected ServerImplRei(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
    }
*/
    @Override
    public synchronized void register (ClientRei client) throws ExceededNumberOfPlayersException, NameAlreadyTakenException, RemoteException {
        if(clients.size()==0){
            maxPlayer = client.askMaxNumber();
            lobbyModel = new Lobby(maxPlayer);
            lobbyController = new LobbyController(lobbyModel);
        }
        clients.add(client);
        System.out.println(client.getNickname() + " connected to the server");
        lobbyController.addPlayerName(client.getNickname());
        System.out.println(client.getNickname() + " aggiunto alla lobby");
        for( ClientRei c : clients){
            try {
                c.updateViewLobby(new LobbyViewMessage(lobbyModel));
                System.out.println(c.getNickname() + " aggiornato");
            } catch (RemoteException e){
                System.out.println("Error while updating the client: " + e.getMessage() +  ". Skipping the update...");
            }
        }
        if (clients.size() == maxPlayer) {
            System.out.println("\n ##### Starting a game #####\n");
            this.gameModel=new Game(lobbyModel.getPlayers());
            this.gameController=new Controller(this.gameModel);
            for (ClientRei c : clients) {
                try {
                    new Thread(() -> {
                        try {
                            c.updateViewGame(new GameViewMessage(gameModel, null));
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }).start();
                    System.out.println(c.getNickname() + " aggiornato GAME");
                } catch (RemoteException e) {
                    System.out.println("Error while notify the client " + e.getMessage());
                }
            }
        }


        //if(clients.size()==maxPlayer){

    }

    @Override
    public void updateGame(ClientRei client, Choice choice) throws RemoteException {
        PropertyChangeEvent evt = new PropertyChangeEvent(client, "choice made", null, choice);
        this.gameController.propertyChange(evt);
    }

    @Override
    public void updateLobby(ClientRei client, Choice choice) throws RemoteException {
        PropertyChangeEvent evt = new PropertyChangeEvent(client, "choice made", null, choice);
        this.lobbyController.propertyChange(evt);
    }

}
