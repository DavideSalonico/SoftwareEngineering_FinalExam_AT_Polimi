package GC_11.network;


import GC_11.model.Board;
import GC_11.model.Chat;
import GC_11.model.Game;
import GC_11.model.Player;
import GC_11.model.common.CommonGoalCard;
import GC_11.network.MessageView;
import GC_11.util.CircularList;

import java.util.List;

/**
 * This class give all the information about the Game specialized for a particular Player given by parameter
 * Use marker 'transiet' if we don't want to serialize the attribute
 */
public class GameViewMessage extends MessageView {

    //The serialization process at runtime associates an id with each Serializable class which is known as SerialVersionUID.
    //The sender and receiver must have the same SerialVersionUID, otherwise, InvalidClassException will be thrown when you deserialize the object.
    private static final long serialVersionUID = 2L;
    private boolean error = false;
    private String exceptionMessage = null;
    private Exception exception = null;

    private CircularList<Player> players = new CircularList<>();
    private List<CommonGoalCard> commonGoals = null;
    private String currentPlayer = null;
    private boolean endGame = false;
    private String endPlayer = null;
    private Board board = null;
    private Chat chat = null;
    private String message;




    /**
     * Game creates instance of GameViewMessage through listener, if there is an exception it only initializes attributes 'exceptionMessage'
     * and 'error', the model remains null because the Player doesn't need a view refresh, but just the exception Error,
     * so the network don't send heavier packets
     *
     * @param model     Game model
     * @param exception caught during the game
     */
    public GameViewMessage(Game model, Exception exception) {
        if (exception != null) {
            this.error = true;
            this.exceptionMessage = exception.getMessage();
            this.exception = exception;
        }
        for (Player p : model.getPlayers()) {
            this.players.add(new Player(p));
        }
        this.commonGoals = model.getCommonGoal(); //TODO passarle per valore e non copiare l'oggetto
        this.currentPlayer = model.getCurrentPlayer().getNickname();
        this.endGame = model.isEndGame();
        if(model.getEndPlayer() != null) this.endPlayer = model.getEndPlayer().getNickname();
        this.board = new Board(model.getBoard());
    }

    // Solo per inviare messaggi testuali da server al client
    public GameViewMessage(String message){
        this.message=message;
    }

    public Board getBoard() {
        return this.board;
    }


    public CircularList<Player> getPlayers() {
        return this.players;
    }

    public String getCurrentPlayer() { return this.currentPlayer; }

    public List<CommonGoalCard> getCommonGoalCards() {
        return this.commonGoals;
    }

    /**
     * CommonGoalCard Getter method
     *
     * @param index or 0 or 1
     * @return CommonGoalCard at position 'index'
     */
    public CommonGoalCard getCommonGoalCard(int index) {
        return this.commonGoals.get(index);
    }

    public boolean isError() {
        return this.error;
    }

    /**
     * Exception's message setter from object Exception
     *
     * @param e generic exception caught
     */
    public void setExceptionMessage(Exception e) {
        this.exceptionMessage = e.getMessage();
    }

    /**
     * Exception's message setter directly from String
     *
     * @param mess Error message string
     */
    public void setExceptionMessage(String mess) {
        this.exceptionMessage = mess;
    }


    /**
     * Exception message getter
     *
     * @return error message string
     */
    public String getExceptionMessage() {
        return this.exceptionMessage;
    }

    public Player getPlayer(String clientNickName) {
        for (Player p : this.getPlayers()) {
            if (p.getNickname().equals(clientNickName)) return new Player(p);
        }
        return null;   //ATTENZIONE A QUESTO NULL non gestito
    }

    public Exception getException() {
        return exception;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
    	this.message = message;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}