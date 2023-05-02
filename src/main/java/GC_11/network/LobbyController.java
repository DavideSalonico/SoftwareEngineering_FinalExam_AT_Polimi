package GC_11.network;

import GC_11.exceptions.ExceededNumberOfPlayersException;
import GC_11.exceptions.NameAlreadyTakenException;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LobbyController implements PropertyChangeListener {

    private Lobby lobbyModel;

    public LobbyController(){
        this.lobbyModel=new Lobby();
    }

    public void lobbySetup(){
        if(this.lobbyModel.getPlayers().size()==0){
            // Lobby setup
        }
        else {
            // Lobby add player
        }
    }

    public void askPlayerNumber(){

    }

    public void addPlayerName(String playerName) throws ExceededNumberOfPlayersException, NameAlreadyTakenException {
        this.lobbyModel.addPlayer(playerName);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
