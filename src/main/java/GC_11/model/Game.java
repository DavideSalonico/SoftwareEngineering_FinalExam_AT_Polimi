package GC_11.model;

import GC_11.model.common.*;
import GC_11.util.CircularList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Game's class, it is the center of all the game model, it contains references with all the object and can also
 * interact with them to settle the changes sent by controller
 * it's a Serializable class, so the GameView can serialize a copy of its status every time something changes
 */
public class Game implements PropertyChangeListener, Serializable {

    private CircularList<Player> players;
    private List<CommonGoalCard> commonGoals;
    private Player currentPlayer;
    private boolean endGame;
    private Player endPlayer;
    private Board board;
    private boolean changed = false;

    //It's not necessary to serialize the listener (attribute transient)
    public transient PropertyChangeListener  listener;

    //Need a constructor which allows the deserialization of the class
    //public Game(Game game){
    //    this.players = game.getPlayers();
    //    this.commonGoals = game.getCommonGoal();
    //    this.currentPlayer = game.getCurrentPlayer();
    //    this.endGame = game.getEndGame();
    //    this.endPlayer = game.getEndPlayer();
    //    this.board = game.getBoard();
    //    this.changed = game.getChanged();
    //}


    public Game(List<String> playerNames){

        this.players = new CircularList<>();
        for(int i=0; i<playerNames.size(); i++){
            this.players.add(new Player(playerNames.get(i)));
            players.get(i).setListener(this);
        }
        this.currentPlayer = this.players.get(0);
        this.endGame = false;
        this.commonGoals = new ArrayList<CommonGoalCard>();
        this.board = new Board(players.size());
        this.board.setListener(this);
        Random random = new Random();
        int tmp1 = random.nextInt(0, 11);
        int tmp2 = random.nextInt(0, 11);
        while(tmp1 == tmp2) {
            tmp2 = random.nextInt(0, 11);
        }
        this.commonGoals.add(loadCommon(tmp1));
        this.commonGoals.add(loadCommon(tmp2));
        this.commonGoals.get(0).setListener(this);
        this.commonGoals.get(1).setListener(this);
    }

    private CommonGoalCard loadCommon(int i){
        CommonGoalCard tmp = null;
        switch (i){
            case 0: tmp = new CommonGoalCard1();
            case 1: tmp = new CommonGoalCard2();
            case 2: tmp = new CommonGoalCard3();
            case 3: tmp = new CommonGoalCard4();
            case 4: tmp = new CommonGoalCard5();
            case 5: tmp = new CommonGoalCard6();
            case 6: tmp = new CommonGoalCard7();
            case 7: tmp = new CommonGoalCard8();
            case 8: tmp = new CommonGoalCard9();
            case 9: tmp = new CommonGoalCard10();
            case 10: tmp = new CommonGoalCard11();
            case 11: tmp = new CommonGoalCard12();
        }
        return tmp;
    }

    public CircularList<Player> getPlayers() {
        return players;
    }

    /**
     *
     * @param i is an integer equals to 0 or 1
     * @return the corresponding CommonGoalCard
     */
    public CommonGoalCard getCommonGoal(int i){
        return commonGoals.get(i);
    }

    public List<CommonGoalCard> getCommonGoal(){
        return commonGoals;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getPlayers(int i){
        return players.get(i);
    }

    /**
     * @param currentPlayer the player that is wanted to be set as current
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
        this.changed = true;
        PropertyChangeEvent evt = new PropertyChangeEvent(
                this,
                "CHANGED_CURRENT_PLAYER",
                null,
                new GameView(this));
        this.listener.propertyChange(evt);
    }

    public void setNextCurrent(){
        this.currentPlayer = this.players.get(this.players.indexOf(this.currentPlayer) + 1);
        this.changed = true;
        PropertyChangeEvent evt = new PropertyChangeEvent(
                this,
                "CHANGED_CURRENT_PLAYER(NEXT)",
                null,
                new GameView(this));
        this.listener.propertyChange(evt);
    }

    public boolean isEndGame() {
        return this.endGame;
    }

    /**
     * Notify a property change in 'Game' to Game Listener
     * @param endGame
     */
    public void setEndGame(boolean endGame) {
        PropertyChangeEvent evt = new PropertyChangeEvent(
                this,
                "END_GAME_SET",
                this.endGame,
                endGame);
        this.endGame = endGame;

        this.listener.propertyChange(evt);
    }

    public Player getEndPlayer() {
        return endPlayer;
    }

    public void setEndPlayer(Player endPlayer) {
        this.endPlayer = endPlayer;
        this.changed = true;
        PropertyChangeEvent evt = new PropertyChangeEvent(
                this,
                "END_GAME_SET",
                null,
                new GameView(this));
        this.listener.propertyChange(evt);
    }

    public Board getBoard() {
        return board;
    }

    public boolean getChanged() {
        return this.changed;
    }

    /**
     * Game launches this method every time the currentPlayer is about to end his Turn, if the player haven't already
     * completed the Common Goal it invokes commonGoalCard.givePoints() method
     */
    private void calculateCommonPoints(){
        if(!commonGoals.get(0).getWinningPlayers().contains(currentPlayer))
            commonGoals.get(0).givePoints(currentPlayer);

        if(!commonGoals.get(1).getWinningPlayers().contains(currentPlayer))
            commonGoals.get(1).givePoints(currentPlayer);
    }

    public void setListener(PropertyChangeListener listener) {
        this.listener = listener;
    }


    // TODO: Queste due metodi sottostanti dovranno generare un oggetto GameView
    /**
     * Notify a property change in one of object connected to 'Game' to Game Listener
     * @param evt A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.changed = true;
        PropertyChangeEvent move = new PropertyChangeEvent(
                this,
                evt.getPropertyName(),
                null,
                new GameView(this));
        this.listener.propertyChange(move);
    }
}
