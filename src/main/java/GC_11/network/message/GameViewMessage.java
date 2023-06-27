package GC_11.network.message;


import GC_11.distributed.Client;
import GC_11.model.*;
import GC_11.model.common.CommonGoalCard;
import GC_11.util.CircularList;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.util.*;

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
    private List<Message> mainChat = new ArrayList<>();
    private Map<Set<String>, List<Message>> privateChats = new HashMap<>();
    private Map<String, List<Message>> filteredPvtChats = new HashMap<>();
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
        this.commonGoals = new ArrayList<>(model.getCommonGoal());
        this.currentPlayer = model.getCurrentPlayer().getNickname();
        this.endGame = model.isEndGame();
        if(model.getEndPlayer() != null) this.endPlayer = model.getEndPlayer();
        this.board = new Board(model.getBoard());
        this.mainChat = new ArrayList<>(model.getChat().getMainChat());
        this.privateChats = new HashMap<>(model.getChat().getPvtChats());
    }

    // Solo per inviare messaggi testuali da server al client
    public GameViewMessage(String message){
        this.message=message;
    }

    public GameViewMessage(GameViewMessage gameViewMessage) {
        super();
        this.error = gameViewMessage.error;
        this.exceptionMessage = gameViewMessage.exceptionMessage;
        this.exception = new Exception(gameViewMessage.exception);
        for (Player p : gameViewMessage.getPlayers()) {
            this.players.add(new Player(p));
        }
        this.commonGoals = gameViewMessage.getCommonGoalCards();

        this.currentPlayer = gameViewMessage.getCurrentPlayer();
        this.endGame = gameViewMessage.isEndGame();
        if(gameViewMessage.getEndPlayer() != null)
            this.endPlayer = gameViewMessage.getEndPlayer();
        this.board = new Board(gameViewMessage.getBoard());
        this.mainChat = new ArrayList<>(gameViewMessage.getMainChat());
        this.privateChats = new HashMap<>(gameViewMessage.getPrivateChats());
        this.filteredPvtChats = new HashMap<>();
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

    public List<Message> getMainChat() {
        return mainChat;
    }
    public Map<Set<String>, List<Message>> getPrivateChats() {
        return privateChats;
    }

    public Map<String, List<Message>> getFilteredPvtChats() {
        return filteredPvtChats;
    }

    public void setFilteredPvtChats(Map<String, List<Message>> filteredPvtChats) {
        this.filteredPvtChats = filteredPvtChats;
    }

    public void setPrivateChats(Map<Set<String>, List<Message>> privateChats) {
        this.privateChats = privateChats;
    }

    public void setMainChat(List<Message> mainChat) {
        this.mainChat = mainChat;
    }

    @Override
    public void executeOnClient(Client client){
        try {
            client.getView().update(this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MessageView sanitize(String receiver) {
        GameViewMessage copy = new GameViewMessage(this);

        for (Player p : copy.getPlayers()) {
            if (!p.getNickname().equals(receiver))
                p.setPersonalGoal(null);
        }
        for(Map.Entry<Set<String>, List<Message>> entry : copy.getPrivateChats().entrySet()){
            if(!entry.getKey().contains(receiver)){
                copy.getPrivateChats().remove(entry.getKey());
            }
            else{
                for(String str : entry.getKey()){
                    if(!str.equals(receiver)){
                        copy.getFilteredPvtChats().put(str, entry.getValue());
                    }
                }
            }
        }

        return copy;
    }

    public boolean isEndGame() {
        return endGame;
    }

    public String getEndPlayer() {
    	return endPlayer;
    }
}