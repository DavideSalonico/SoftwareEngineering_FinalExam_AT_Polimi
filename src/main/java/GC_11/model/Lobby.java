package GC_11.model;

import GC_11.exceptions.ExceededNumberOfPlayersException;
import GC_11.exceptions.NameAlreadyTakenException;
import GC_11.exceptions.PlayerNotInListException;
import GC_11.model.Game;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Lobby implements PropertyChangeListener {
    private int maxPlayers = 1;
    private List<String> playersNames = new ArrayList<String>();
    private PropertyChangeListener listener;

    public Lobby(){}

    public synchronized boolean nameAlreadyTaken(String playerName) {
        return playersNames.contains(playerName);
    }

    public synchronized void addPlayer(String playerName) throws ExceededNumberOfPlayersException, NameAlreadyTakenException {
        if (playersNames.size() < maxPlayers && !playersNames.contains(playerName)) {
            playersNames.add(playerName);
            if(playersNames.size() == 1){
                PropertyChangeEvent evt = new PropertyChangeEvent(
                        this,
                        "FIRST PLAYER",
                        null,
                        playerName);
                this.listener.propertyChange(evt);
            }
            if(this.isFull()){
                PropertyChangeEvent evt = new PropertyChangeEvent(
                        this,
                        "LAST PLAYER",
                        null,
                        playerName);
                this.listener.propertyChange(evt);
            }
        } else if (this.isFull()) {
            throw new ExceededNumberOfPlayersException();
        } else if (this.nameAlreadyTaken(playerName)) {
            throw new NameAlreadyTakenException(playerName);
        }
    }

    public synchronized void removePlayer(String playerName) throws PlayerNotInListException {
        if (playersNames.contains(playerName)) {
            playersNames.remove(playerName);
        } else {
            throw new PlayerNotInListException(playerName);
        }
    }

    public synchronized boolean isFull() {
        return (playersNames.size() == maxPlayers);
    }

    private synchronized boolean hasPlayer(String playerName) {
        return (playersNames.contains(playerName));
    }

    public synchronized List<String> getPlayers() {
        return playersNames;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public void triggerException(Exception e){
        PropertyChangeEvent evt = new PropertyChangeEvent(
                this,
                "EXCEPTION",
                null,
                e);
        this.listener.propertyChange(evt);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.listener.propertyChange(new PropertyChangeEvent(this,
                "lobbyModel",
                null,
                this));
    }

    public void setListener(PropertyChangeListener listener) {
        this.listener = listener;
    }
}
