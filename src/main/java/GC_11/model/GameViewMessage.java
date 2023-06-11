package GC_11.model;


import GC_11.model.common.CommonGoalCard;
import GC_11.network.MessageView;

import java.io.Serializable;
import java.util.List;

/**
 * This class give all the information about the Game specialized for a particular Player given by parameter
 * Use marker 'transiet' if we don't want to serialize the attribute
 */
public class GameViewMessage extends MessageView {

    //The serialization process at runtime associates an id with each Serializable class which is known as SerialVersionUID.
    //The sender and receiver must have the same SerialVersionUID, otherwise, InvalidClassException will be thrown when you deserialize the object.
    private static final long serialVersionUID = 2L;
    private boolean error;
    private String exceptionMessage;
    private final Game model;

    /**
     * Game creates instance of GameViewMessage through listener, if there is an exception it only initializes attributes 'exceptionMessage'
     *  and 'error', the model remains null because the Player doesn't need a view refresh, but just the exception Error,
     *  so the network don't send heavier packets
     * @param model Game model
     * @param exception caught during the game
     */
    public GameViewMessage(Game model, Exception exception){
        //if (model == null){
        //    throw new IllegalArgumentException();
        //}
        if(exception != null){
            this.error = true;
            this.exceptionMessage = exception.getMessage();
            this.model = null;
        }else {
            this.model = model;
            this.error = false;
            this.exceptionMessage  = null;
        }
    }

    public Board getBoard() { return model.getBoard(); }
    public Chat getChat() { return model.getChat(); }

    public List<Player> getPlayers(){ return model.getPlayers(); }

    public Player getCurrentPlayer(){ return model.getCurrentPlayer(); }

    public List<CommonGoalCard> getCommonGoalCards(){
        return model.getCommonGoal();
    }

    /**
     * This method needs to be called in ServerClientHandler only on a copy of the original view,
     * otherwise it will set Null the remaining PersonalCards at first call, so it become useless for the others players
     * @param player
     */
    public void setPersonalNull(Player player){
        for(Player p : this.getPlayers()){
            if(!p.equals(player)) p.setPersonalGoal(null);
        }
    }

    /**
     * CommonGoalCard Getter method
     * @param index or 0 or 1
     * @return CommonGoalCard at position 'index'
     */
    public CommonGoalCard getCommonGoalCard(int index) {
        return this.model.getCommonGoal(index);
    }

    public boolean isError() {
        return this.error;
    }

    /**
     * Exception's message setter from object Exception
     * @param e generic exception caught
     */
    public void setExceptionMessage(Exception e){
        this.exceptionMessage = e.getMessage();
    }

    /**
     * Exception's message setter directly from String
     * @param mess Error message string
     */
    public void setExceptionMessage(String mess){
        this.exceptionMessage = mess;
    }


    /**
     * Exception message getter
     * @return error message string
     */
    public String getExceptionMessage(){
        return this.exceptionMessage;
    }

    public Player getPlayer(String clientNickName) {
        for(Player p : this.getPlayers()){
            if(p.getNickname().equals(clientNickName)) return p;
        }
        return null;   //ATTENZIONE A QUESTO NULL non gestito
    }
}