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

    /**
     * Constructs a Lobby object.
     */
    public Lobby(){}


    /**
     *  Check if a name is already taken by a player in the lobby.
      * @param playerName The name to check.
     * @return True if the name is already taken, false otherwise.
     */
    public synchronized boolean nameAlreadyTaken(String playerName) {
        return playersNames.contains(playerName);
    }

    /**
     * Adds a player to the lobby.
     * @param playerName The name of the player to add.
     * @throws ExceededNumberOfPlayersException If the lobby is full.
     * @throws NameAlreadyTakenException If the name is already taken.
     */
    public synchronized void addPlayer(String playerName) throws ExceededNumberOfPlayersException, NameAlreadyTakenException {
        if (playersNames.size() < maxPlayers && !playersNames.contains(playerName)) {
            playersNames.add(playerName);
        } else if (this.isFull()) {
            throw new ExceededNumberOfPlayersException();
        } else if (this.nameAlreadyTaken(playerName)) {
            throw new NameAlreadyTakenException(playerName);
        }
    }

    /**
     * Removes a player from the lobby.
     * @param playerName The name of the player to remove.
     * @throws PlayerNotInListException If the player is not in the lobby.
     */
    public synchronized void removePlayer(String playerName) throws PlayerNotInListException {
        if (playersNames.contains(playerName)) {
            playersNames.remove(playerName);
        } else {
            throw new PlayerNotInListException(playerName);
        }
    }

    /**
     * Checks if the lobby is full.
     * @return True if the lobby is full, false otherwise.
     */
    public synchronized boolean isFull() {
        return (playersNames.size() == maxPlayers);
    }

    /**
     * Gets the list of players in the lobby.
     * @return The list of players in the lobby.
     */

    public synchronized List<String> getPlayers() {
        return playersNames;
    }

    /**
     * Gets the maximum number of players in the lobby.
     * @return The maximum number of players in the lobby.
     */
    public int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     * Sets the maximum number of players in the lobby.
     * @param maxPlayers The maximum number of players in the lobby.
     */
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
