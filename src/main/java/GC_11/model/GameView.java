package GC_11.model;


import GC_11.model.common.CommonGoalCard;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.List;

/**
 * This class give all the information about the Game specialized for a particular Player given by parameter
 * Use marker 'transiet' if we don't want to serialize the attribute
 */
public class GameView implements Serializable {

    //The serialization process at runtime associates an id with each Serializable class which is known as SerialVersionUID.
    //The sender and receiver must have the same SerialVersionUID, otherwise, InvalidClassException will be thrown when you deserialize the object.
    private static final long serialVersionUID = 2L;

    /** NON so ancora se servono
    public String playerNickname;
    public String PersonalGoalText;
    public String[] CommonGoalText;

    //From the first player in order
    public int[] playersPoints;
     */

    private final Game model;
    //TODO:[RAM] remove model reference and copy every significant attribute

    public GameView(Game model){
        if (model == null){
            throw new IllegalArgumentException();
        }
        this.model = model;
    }

    public Board getBoard() { return model.getBoard(); }

    public List<Player> getPlayers(){ return model.getPlayers(); }

    public Player getCurrentPlayer(){ return model.getCurrentPlayer(); }

    public void setPersonalNull(Player player){
        for(Player p : this.getPlayers()){
            if(!p.equals(player)) p.setPersonalGoal(null);
        }
    }
}
