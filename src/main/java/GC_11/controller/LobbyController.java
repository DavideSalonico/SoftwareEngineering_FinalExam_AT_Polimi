package GC_11.controller;

import GC_11.exceptions.ExceededNumberOfPlayersException;
import GC_11.exceptions.NameAlreadyTakenException;
import GC_11.model.Game;
import GC_11.network.Lobby;
import GC_11.util.Choice;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.security.InvalidParameterException;
import java.util.List;

public class LobbyController implements PropertyChangeListener {

    public Choice choice;
    private Lobby lobbyModel;
    public LobbyController(Lobby lobby) {
        this.lobbyModel=lobby;
    }

   /* private void findMatch(List<String> params) {
        //if(player.equals(lobbyModel.getBoss()))) We should check that only the main player can start the game
        lobbyModel.setGameModel(new Game(lobbyModel.getPlayers()));
        //lobbyModel.startGame(this.gameModel);
    }*/

    private void login(List<String> params) {
        //TODO
        System.out.println("Player "+ params.get(0) + " logged successfully");
    }

    private void insertName(List<String> params) throws ExceededNumberOfPlayersException, NameAlreadyTakenException {
        //TODO
        if(params.size() != 1) throw new IllegalArgumentException();
        if(params.get(0).length() >= 64) throw new InvalidParameterException();
        lobbyModel.addPlayer(params.get(0));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //TODO
    }

    public void addPlayerName(String playerName) throws ExceededNumberOfPlayersException, NameAlreadyTakenException {
        this.lobbyModel.addPlayer(playerName);
    }
}
