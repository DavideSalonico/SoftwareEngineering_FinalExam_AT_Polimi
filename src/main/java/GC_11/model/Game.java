package GC_11.model;

import GC_11.controller.JsonReader;
import GC_11.controller.JsonWriter;
import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.model.common.*;
import GC_11.network.message.GameViewMessage;
import GC_11.util.CircularList;
import org.jetbrains.annotations.NotNull;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.*;

/**
 * Game's class, it is the center of all the game model, it contains references with all the object and can also
 * interact with them to settle the changes sent by controller
 * it's a Serializable class, so the GameViewMessage can serialize a copy of its status every time something changes
 */
public class Game implements PropertyChangeListener, Serializable {

    private CircularList<Player> players;
    private List<CommonGoalCard> commonGoals;
    private Player currentPlayer;
    private boolean endGame;
    private boolean lastTurn;
    private String endPlayer;
    private Board board;
    private Chat chat;
    public transient PropertyChangeListener listener;

    public Game(@NotNull List<String> playerNames, PropertyChangeListener listener) {
        Random random = new Random();
        this.players = new CircularList<>();
        int[] idArray = generateSetOfRandomNumber(playerNames.size(), 0, 11);
        for (int i = 0; i < playerNames.size(); i++) {
            System.out.println("int i = " + i);
            System.out.println("ID della carta personale del giocatore: " + idArray[i]);
            PersonalGoalCard card = JsonReader.readPersonalGoalCard(idArray[i]);

            this.players.add(new Player(playerNames.get(i), card));
            System.out.println("Carta data al giocatore " + playerNames.get(i));
            card.print();
            System.out.println();
            players.get(i).setListener(this);
            System.out.println("listener settato");
        }
        this.currentPlayer = this.players.get(0);
        this.endGame = false;
        this.lastTurn = false;
        this.commonGoals = new ArrayList<CommonGoalCard>();
        this.board = new Board(players.size());
        this.board.setListener(this);
        this.chat = new Chat();
        this.chat.setListener(this);
        int tmp1 = random.nextInt(0, 11);
        int tmp2 = random.nextInt(0, 11);
        while (tmp1 == tmp2) {
            tmp2 = random.nextInt(0, 11);
        }
        this.commonGoals.add(loadCommon(tmp1));
        this.commonGoals.add(loadCommon(tmp2));
        this.commonGoals.get(0).setListener(this);
        this.commonGoals.get(1).setListener(this);
        this.listener = listener;
    }


    /**
     * THIS BUILDER IS USED ONLY TO CREATE A GAME WITH ATTRIBUTES READ FROM THE JSON FILE
     *
     * @param players is the list of players loaded from the json file
     * @param board   is the board loaded from the json file
     */
    public Game(List<Player> players, Board board, int[] commonGoals,
                List<Player> winningPlayers1, List<Player> winningPlayers2, String currentPlayer) {
        this.players = new CircularList<>();
        for (Player p : players) {
            this.players.add(p);
            p.setListener(this);
        }
        this.currentPlayer = getCurrentPlayer(currentPlayer);
        this.board = board;
        this.commonGoals = new ArrayList<CommonGoalCard>();
        this.commonGoals.add(loadCommon(commonGoals[0]-1));
        this.commonGoals.add(loadCommon(commonGoals[1]-1));
        this.commonGoals.get(0).getWinningPlayers().addAll(winningPlayers1);
        this.commonGoals.get(1).getWinningPlayers().addAll(winningPlayers2);
        this.commonGoals.get(0).setListener(this);
        this.commonGoals.get(1).setListener(this);
        this.chat = new Chat();
        this.chat.setListener(this);

    }

    /**
     * Return the current player from a given nickname
     * @param nickname the nickname of the current player
     * @return the player with the given nickname
     */
    private Player getCurrentPlayer(String nickname){
        for(Player p : players){
            if(p.getNickname().equals(nickname))
                return p;
        }
        return null;
    }

    /**
     * Method to generate the common goals cards
     * @param i the index of the common goal card
     * @return the corresponding common goal card
     */
    private CommonGoalCard loadCommon(int i) {
        switch (i) {
            case 0:
                return new CommonGoalCard1();
            case 1:
                return new CommonGoalCard2();
            case 2:
                return new CommonGoalCard3();
            case 3:
                return new CommonGoalCard4();
            case 4:
                return new CommonGoalCard5();
            case 5:
                return new CommonGoalCard6();
            case 6:
                return new CommonGoalCard7();
            case 7:
                return new CommonGoalCard8();
            case 8:
                return new CommonGoalCard9();
            case 9:
                return new CommonGoalCard10();
            case 10:
                return new CommonGoalCard11();
            case 11:
                return new CommonGoalCard12();
            default:
                throw new RuntimeException("Common goal non selezionata correttamente");
        }

    }

    /**
     * Method to get the list of players
     * @return the list of players
     */
    public CircularList<Player> getPlayers() {
        return players;
    }

    /**
     * @param i is an integer equals to 0 or 1
     * @return the corresponding CommonGoalCard
     */
    public CommonGoalCard getCommonGoal(int i) {
        return commonGoals.get(i);
    }

    /**
     * Method to get the list of common goals
     * @return the list of common goals
     */
    public List<CommonGoalCard> getCommonGoal() {
        return commonGoals;
    }

    public void setCommonGoal(int index, CommonGoalCard c) {
        commonGoals.set(index, c);
    }

    /**
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }


    public Player getPlayers(int i) {
        return players.get(i);
    }

    /**
     * Set the next player as current player
     * @return true if the last turn is started, false otherwise
     */
    public boolean setNextCurrent() {
        this.currentPlayer = this.players.get(this.players.indexOf(this.currentPlayer) + 1);
        if(this.currentPlayer.getNickname().equals(this.players.get(0).getNickname()) && this.lastTurn){
            return true;
        }
        PropertyChangeEvent evt = new PropertyChangeEvent(
                this,
                "CHANGED_CURRENT_PLAYER",
                null,
                new GameViewMessage(this, null));
        this.listener.propertyChange(evt);
        JsonWriter.saveGame(this);
        System.out.println("Set next current player: " + this.currentPlayer.getNickname());
        return false;
    }

    /**
     * Method to get the last Turn
     */

    public boolean isEndGame() {
        return this.endGame;
    }

    /**
     * Notify a property change in 'Game' to Game Listener
     *
     * @param endGame
     */
    public void setEndGame(boolean endGame){
        this.endGame = endGame;
        System.out.println("Set end game");

    }

    /**
     * Method to get the endPlayer
     */
    public String getEndPlayer() {
        return endPlayer;
    }

    public void setEndPlayer(String endPlayer){
        this.endPlayer = endPlayer;
        System.out.println("Set end player");
    }

    public Player getPlayer(String nickname) {
        for (Player p : players) {
            if (p.getNickname().equals(nickname))
                return p;
        }
        return null;
    }

    public Board getBoard() {
        return board;
    }

    /**
     * Game launches this method every time the currentPlayer is about to end his Turn, if the player haven't already
     * completed the Common Goal it invokes commonGoalCard.givePoints() method
     */
    public void calculateCommonPoints() throws ColumnIndexOutOfBoundsException {
        if (!commonGoals.get(0).getWinningPlayers().contains(currentPlayer))
            commonGoals.get(0).check(currentPlayer);

        if (!commonGoals.get(1).getWinningPlayers().contains(currentPlayer))
            commonGoals.get(1).check(currentPlayer);
    }

    public void setListener(PropertyChangeListener listener) {
        this.listener = listener;
    }


    /**
     * Notify a property change in one of object connected to 'Game' to Game Listener
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        PropertyChangeEvent e = new PropertyChangeEvent(
                evt.getSource(),
                evt.getPropertyName(),
                null,
                new GameViewMessage(this, null));
        listener.propertyChange(e);
        JsonWriter.saveGame(this);
    }

    public void triggerException(Exception e) {
        PropertyChangeEvent evt = new PropertyChangeEvent(
                this,
                "EXCEPTION TRIGGERED",
                null,
                new GameViewMessage(this, e));
        this.listener.propertyChange(evt);
        System.out.println("Triggered exception\n" + e.getMessage());
    }

    /**
     * Method to generate a set of random numbers
     * @param size the size of the set
     * @param min the minimum value of the set
     * @param max the maximum value of the set
     * @return
     */
    private int[] generateSetOfRandomNumber(int size, int min, int max) {
        Random random = new Random();
        Set<Integer> generatedNumbers = new HashSet<>();

        while (generatedNumbers.size() < size) {
            int randomNumber = random.nextInt(max - min + 1) + min;
            generatedNumbers.add(randomNumber);
        }

        for (int number : generatedNumbers) {
            System.out.println(number);
        }

        return generatedNumbers.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * Method to get the chat
     * @return the chat
     */
    public Chat getChat() {
        return chat;
    }

    public void triggerEnd() {
        PropertyChangeEvent evt = new PropertyChangeEvent(
                this,
                "END GAME",
                null,
                new GameViewMessage(this, null));
        this.listener.propertyChange(evt);
    }

    /**
     * Method to set the last turn
     * @param b true if it's the last turn, false otherwise
     */
    public void setLastTurn(boolean b) {
        this.lastTurn = b;
    }

    public Boolean getLastTurn() {
        return this.lastTurn;
    }
}
