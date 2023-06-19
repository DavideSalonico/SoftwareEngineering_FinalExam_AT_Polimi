package GC_11.network;

import GC_11.exceptions.ExceededNumberOfPlayersException;
import GC_11.exceptions.NameAlreadyTakenException;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LobbyController implements PropertyChangeListener {

    private Lobby lobby;


    public LobbyController(Lobby lobby){
        this.lobby= lobby;
    }

    public void lobbySetup(){
        String messageFromClient;
        String messageToClient;
        LobbyViewMessage lvm = new LobbyViewMessage();
        if(this.lobby==null){
            // Lobby setup
            lvm.setMessage("Non c'è ancora nessun giocatore nella lobby. Vuoi crearne una?\n[S] Sì\n[N] no");

        }
        else {
            // Lobby add player
        }
    }

    public void askPlayerNumber(){

    }

    public void addPlayerName(String playerName) throws ExceededNumberOfPlayersException, NameAlreadyTakenException {
        this.lobby.addPlayer(playerName);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
