package GC_11.model;

import GC_11.exceptions.ExceededNumberOfPlayersException;
import GC_11.exceptions.NameAlreadyTakenException;
import GC_11.exceptions.PlayerNotInListException;
import GC_11.model.Game;
import GC_11.network.message.LobbyViewMessage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Lobby {
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
            PropertyChangeEvent evt = new PropertyChangeEvent(
                    this,
                    "EXCEPTION",
                    null,
                    new LobbyViewMessage(this, null));
            //this.listener.propertyChange(evt);
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
                new LobbyViewMessage(this, e));
        this.listener.propertyChange(evt);
    }

    public void setListener(PropertyChangeListener listener) {
        this.listener = listener;
    }
}
