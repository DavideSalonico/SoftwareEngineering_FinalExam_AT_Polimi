package GC_11.distributed;

import GC_11.controller.Controller;
import GC_11.controller.LobbyController;
import GC_11.exceptions.ExceededNumberOfPlayersException;
import GC_11.exceptions.NameAlreadyTakenException;
import GC_11.model.Game;
import GC_11.model.Player;
import GC_11.network.Lobby;
import GC_11.network.LobbyViewMessage;
import GC_11.util.Choice;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServerImplRei extends UnicastRemoteObject implements ServerRei{

    private Controller gameController;

    private Game model;
    private LobbyController lobbyController;

    private Lobby lobby;

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
    public void register(ClientRei client) throws ExceededNumberOfPlayersException, NameAlreadyTakenException, RemoteException {
        if(clients.size()==0){
            maxPlayer = client.askMaxNumber();
            lobby = new Lobby(maxPlayer);
            lobbyController = new LobbyController(lobby);
        }
        clients.add(client);
        lobbyController.addPlayerName(client.getNickname());
        for( ClientRei c : clients){
            try {
                c.updateViewLobby(new LobbyViewMessage(lobby));
            } catch (RemoteException e){
                System.out.println("Errore whhile updating the client: " + e.getMessage() +  ". Skipping the update...");
            }
        }


        //if(clients.size()==maxPlayer){

    }

    public void ciao(){
        System.out.println("ciao soco acceso");
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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Game game = (Game) evt.getNewValue();
        this.gameController = new Controller(game);
        //this.gameController.start();
    }
}
