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
import java.util.ArrayList;
import java.util.List;

public class ServerImplRei implements ServerRei{

    private Controller controller;

    Game model;
    private LobbyController lobbyController;

    private Lobby lobby;

    int maxPlayer;
    List<ClientRei> clients = new ArrayList<>();
    @Override
    public void register(ClientImplRei client) throws ExceededNumberOfPlayersException, NameAlreadyTakenException {
        if(clients.size()==0){
            maxPlayer = client.askMaxNumber();
            lobby = new Lobby(maxPlayer);
            lobbyController = new LobbyController(lobby);
        }
        clients.add(client);
        lobbyController.addPlayerName(client.getNickname());
        for( ClientRei c : clients){
            c.updateViewLobby(new LobbyViewMessage(lobby));
        }


        //if(clients.size()==maxPlayer){

    }

    @Override
    public void updateGame(ClientRei client, Choice choice) {
        PropertyChangeEvent evt = new PropertyChangeEvent(client, "choice made", null, choice);
        this.controller.propertyChange(evt);
    }

    @Override
    public void updateLobby(ClientRei client, Choice choice) {
        PropertyChangeEvent evt = new PropertyChangeEvent(client, "choice made", null, choice);
        this.lobbyController.propertyChange(evt);
    }
}
