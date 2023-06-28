package GC_11.model.common;

import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.model.Player;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * CommonGoalCard is an abstract class representing a common goal card in the game.
 * It provides common functionality and methods for specific common goal cards.
 */
public abstract class CommonGoalCard implements Serializable {

    private List<Player> winningPlayers = new ArrayList<>();
    //TODO: to change based on CommonGoalCard number
    protected String text = "sample common goal card text";

    protected int id;
    private PropertyChangeListener listener;

    /**
     * Returns the ID of the common goal card.
     *
     * @return the ID value
     */
    public int getId(){
        return id;
    }

    /**
     * Gives points to the specified player and updates the winning players list.
     *
     * @param player the player to give points to
     */
    protected void givePoints(Player player) {
        List<Player> oldWinning = this.winningPlayers;
        winningPlayers.add(player);
        int point = 10 - 2 * winningPlayers.size();
        player.addPointsCommonGoals(point);

        /*PropertyChangeEvent evt = new PropertyChangeEvent(
                this,
                "CHANGED_WINNING_PLAYERS",
                oldWinning,
                this.winningPlayers);
        this.listener.propertyChange(evt);*/
    }

    /**
     * Checks the specified player against the conditions of the common goal card.
     *If the conditions are met, the player is given points.
     *
     * @param player the player to check
     * @throws ColumnIndexOutOfBoundsException when the column index is out of bounds
     */
    public abstract void check(Player player) throws ColumnIndexOutOfBoundsException;

    /**
     * Returns the list of winning players who achieved the common goal.
     *
     * @return the list of winning players
     */
    public List<Player> getWinningPlayers() {
        return winningPlayers;
    }

    /**
     * Returns the text description of the common goal card.
     *
     * @return the text description
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the property change listener for the common goal card.
     *
     * @param listener the property change listener to set
     */
    public void setListener(PropertyChangeListener listener) {
        this.listener = listener;
    }
}

