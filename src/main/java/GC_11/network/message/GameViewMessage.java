package GC_11.network.message;


import GC_11.ClientApp;
import GC_11.distributed.Client;
import GC_11.model.Board;
import GC_11.model.Game;
import GC_11.model.Message;
import GC_11.model.Player;
import GC_11.model.common.CommonGoalCard;
import GC_11.util.CircularList;

import java.util.*;

/**
 * Represents a message containing all the information about the game, specialized for a particular player.
 * It contains various attributes representing the game state, such as players, common goals, current player, etc.
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
    private Player winner = null;




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
        Player playerMaxPoints = model.getPlayers().get(0);
        for (Player p : model.getPlayers()) {
            this.players.add(new Player(p));
            playerMaxPoints = p.getPoints() > playerMaxPoints.getPoints() ? p : playerMaxPoints;
        }
        this.winner = playerMaxPoints;
        this.commonGoals = new ArrayList<>(model.getCommonGoal());
        this.currentPlayer = model.getCurrentPlayer().getNickname();
        this.endGame = model.isEndGame();
        if(model.getEndPlayer() != null) this.endPlayer = model.getEndPlayer();
        this.board = new Board(model.getBoard());
        if (model.getChat() != null) {
            this.mainChat = new ArrayList<>(model.getChat().getMainChat());
            this.privateChats = new HashMap<>(model.getChat().getPvtChats());
        }
        else{
            this.mainChat = new ArrayList<>();
            this.privateChats = new HashMap<>();
        }
        // TODO VEDERE SE COSI VA BENE

    }

    /**
     * send a message to the client.
     *
     * @param message the message to sent.
     */
    public GameViewMessage(String message){
        this.message=message;
    }

    /**
     * Constructs a GameViewMessage object based on another GameViewMessage object.
     *
     * @param gameViewMessage The GameViewMessage object to copy.
     */
    public GameViewMessage(GameViewMessage gameViewMessage) {
        super();
        this.error = gameViewMessage.error;
        this.exceptionMessage = gameViewMessage.exceptionMessage;
        this.exception = new Exception(gameViewMessage.exception);
        Player playerMaxPoints = gameViewMessage.getPlayers().get(0);
        for (Player p : gameViewMessage.getPlayers()) {
            this.players.add(new Player(p));
            playerMaxPoints = p.getPoints() > playerMaxPoints.getPoints() ? p : playerMaxPoints;
        }
        this.winner = playerMaxPoints;
        this.commonGoals = gameViewMessage.getCommonGoalCards();
        this.currentPlayer = gameViewMessage.getCurrentPlayer();
        this.endGame = gameViewMessage.isEndGame();
        if(gameViewMessage.getEndPlayer() != null)
            this.endPlayer = gameViewMessage.getEndPlayer();
        this.endGame = gameViewMessage.isEndGame();
        this.board = new Board(gameViewMessage.getBoard());
        this.mainChat = new ArrayList<>(gameViewMessage.getMainChat());
        this.privateChats = new HashMap<>(gameViewMessage.getPrivateChats());
        this.filteredPvtChats = new HashMap<>();
    }

    /**
     * Returns the board of the game.
     *
     * @return The game board.
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * Returns the list of players in the game.
     *
     * @return The list of players.
     */
    public CircularList<Player> getPlayers() {
        return this.players;
    }

    /**
     * Returns the nickname of the current player.
     *
     * @return The nickname of the current player.
     */
    public String getCurrentPlayer() { return this.currentPlayer; }

    /**
     * Returns the list of common goal cards in the game.
     *
     * @return The list of common goal cards.
     */
    public List<CommonGoalCard> getCommonGoalCards() {
        return this.commonGoals;
    }

    /**
     * CommonGoalCard Getter method.
     *
     * @param index or 0 or 1.
     * @return CommonGoalCard at position 'index'.
     */
    public CommonGoalCard getCommonGoalCard(int index) {
        return this.commonGoals.get(index);
    }

    /**
     * Checks if there was an error in the game.
     *
     * @return True if there was an error, false otherwise.
     */
    public boolean isError() {
        return this.error;
    }

    /**
     * Returns the winner of the game.
     *
     * @return The player who won the game.
     */
    public Player getWinner() {
    	return this.winner;
    }

    /**
     * Exception's message setter from object Exception.
     *
     * @param e generic exception caught.
     */
    public void setExceptionMessage(Exception e) {
        this.exceptionMessage = e.getMessage();
    }

    /**
     * Exception's message setter directly from String.
     *
     * @param mess Error message string.
     */
    public void setExceptionMessage(String mess) {
        this.exceptionMessage = mess;
    }


    /**
     * Exception message getter.
     *
     * @return error message string.
     */
    public String getExceptionMessage() {
        return this.exceptionMessage;
    }

    /**
     * Returns a player object based on the provided client nickname.
     *
     * @param clientNickName The client nickname.
     * @return The player object corresponding to the client nickname.
     */
    public Player getPlayer(String clientNickName) {
        for (Player p : this.getPlayers()) {
            if (p.getNickname().equals(clientNickName)) return new Player(p);
        }
        return null;   //ATTENZIONE A QUESTO NULL non gestito
    }

    /**
     * Returns the exception caught during the game.
     *
     * @return The exception object.
     */
    public Exception getException() {
        return exception;
    }

    /**
     * Returns the message associated with the game view.
     *
     * @return The message string.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message associated with the game view.
     *
     * @param message The message string.
     */
    public void setMessage(String message) {
    	this.message = message;
    }

    /**
     * Sets the exception caught during the game.
     *
     * @param exception The exception object.
     */
    public void setException(Exception exception) {
        this.exception = exception;
    }

    /**
     * Returns the main chat messages of the game.
     *
     * @return The list of main chat messages.
     */
    public List<Message> getMainChat() {
        return mainChat;
    }

    /**
     * Returns the private chat messages of the game.
     *
     * @return The map of private chat messages.
     */
    public Map<Set<String>, List<Message>> getPrivateChats() {
        return privateChats;
    }

    /**
     * Returns the filtered private chat messages of the game.
     *
     * @return The map of filtered private chat messages.
     */
    public Map<String, List<Message>> getFilteredPvtChats() {
        return filteredPvtChats;
    }

    /**
     * Sets the filtered private chat messages of the game.
     *
     * @param filteredPvtChats The map of filtered private chat messages.
     */
    public void setFilteredPvtChats(Map<String, List<Message>> filteredPvtChats) {
        this.filteredPvtChats = filteredPvtChats;
    }

    /**
     * Sets the private chat messages of the game.
     *
     * @param privateChats The map of private chat messages.
     */
    public void setPrivateChats(Map<Set<String>, List<Message>> privateChats) {
        this.privateChats = privateChats;
    }

    /**
     * Sets the main chat messages of the game.
     *
     * @param mainChat The list of main chat messages.
     */
    public void setMainChat(List<Message> mainChat) {
        this.mainChat = mainChat;
    }

    /**
     * Executes the game view update on the client.
     *
     * @param client The client to update with the game view.
     */
    @Override
    public void executeOnClient(Client client){
        ClientApp.view.update(this);
    }

    /**
     * Sanitizes the game view message for a specific receiver.
     * It removes sensitive information from the game view that should not be visible to other players.
     *
     * @param receiver The nickname of the receiver.
     * @return A sanitized copy of the game view message.
     */
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

    /**
     * Checks if the game has ended.
     *
     * @return True if the game has ended, false otherwise.
     */
    public boolean isEndGame() {
        return endGame;
    }

    /**
     * Returns the nickname of the player who caused the game to end.
     *
     * @return The nickname of the player who caused the game to end.
     */
    public String getEndPlayer() {
    	return endPlayer;
    }
}