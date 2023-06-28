package GC_11.network.choices;

import GC_11.controller.Controller;
import GC_11.distributed.ServerMain;
import GC_11.exceptions.*;
import GC_11.model.Player;
import GC_11.view.View;

import java.beans.PropertyChangeEvent;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Choice is an abstract class representing a player's choice in the game.
 */
public abstract class Choice implements Serializable {
    protected ChoiceType type;
    protected List<String> params;
    protected Player player;

    /**
            * Constructs a Choice object.
            *
            * @param player the player making the choice.
            * @param params the parameters associated with the choice.
     * @param type   the type of the choice.
            * @throws IllegalMoveException if the choice is an illegal move.
            */
    public Choice(Player player, List<String> params, ChoiceType type) throws IllegalMoveException {
        this.type = type;
        this.player = player;
        this.params = params;
    }

    /**
     * Executes the choice on the server.
     *
     * @param controller the game controller.
     * @throws RemoteException                    if a remote error occurs.
     * @throws ExceededNumberOfPlayersException    if the number of players exceeds the limit.
     * @throws NameAlreadyTakenException           if a player name is already taken.
     */
    public abstract void executeOnServer(Controller controller) throws RemoteException, ExceededNumberOfPlayersException, NameAlreadyTakenException;

    /**
     * Returns the parameters associated with the choice.
     *
     * @return the parameters.
     */
    public List<String> getParams() {
        return params;
    }

    /**
     * Returns the player making the choice.
     *
     * @return the player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns the type of the choice.
     *
     * @return the choice type.
     */
    public ChoiceType getType() {
        return type;
    }

    /**
     * Returns a string representation of the choice.
     *
     * @return the string representation.
     */
    public String toString(){
        String choiceStringified = type.toString();
        for (String p : params){
            choiceStringified += " "+p;
        }
        return choiceStringified;
    }
}
