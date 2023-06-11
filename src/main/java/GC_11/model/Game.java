package GC_11.model;

import GC_11.distributed.ServerRei;
import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.model.common.*;
import GC_11.util.CircularList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private Player endPlayer;
    private Board board;
    private Chat chat;
    private boolean changed = false;
    private ServerRei server;

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


    public Game(List<String> playerNames, ServerRei server){

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
        this.chat = new Chat();
        this.chat.setListener(this);
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
        this.server = server;
    }

    private CommonGoalCard loadCommon(int i){
        switch (i){
            case 0: return new CommonGoalCard1();
            case 1: return new CommonGoalCard2();
            case 2: return new CommonGoalCard3();
            case 3: return new CommonGoalCard4();
            case 4: return new CommonGoalCard5();
            case 5: return new CommonGoalCard6();
            case 6: return new CommonGoalCard7();
            case 7: return new CommonGoalCard8();
            case 8: return new CommonGoalCard9();
            case 9: return new CommonGoalCard10();
            case 10: return new CommonGoalCard11();
            case 11: return new CommonGoalCard12();
            default : throw new RuntimeException("Common goal non selezionata correttamente");
        }

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

    public void setCommonGoal(int index, CommonGoalCard c){
        commonGoals.set(index, c);
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
    public void setCurrentPlayer(Player currentPlayer) throws RemoteException {
        this.currentPlayer = currentPlayer;
        this.changed = true;
        /*PropertyChangeEvent evt = new PropertyChangeEvent(
                this,
                "CHANGED_CURRENT_PLAYER",
                null,
                new GameViewMessage(this, null));
        this.listener.propertyChange(evt);*/
        server.notifyClients();
    }

    public void setNextCurrent() throws RemoteException {
        this.currentPlayer = this.players.get(this.players.indexOf(this.currentPlayer) + 1);
        this.changed = true;
        /*PropertyChangeEvent evt = new PropertyChangeEvent(
                this,
                "CHANGED_CURRENT_PLAYER(NEXT)",
                null,
                new GameViewMessage(this, null));
        this.listener.propertyChange(evt);*/
        server.notifyClients();
        System.out.println("Set next current player: " + this.currentPlayer.getNickname());
    }

    public boolean isEndGame() {
        return this.endGame;
    }

    /**
     * Notify a property change in 'Game' to Game Listener
     * @param endGame
     */
    public void setEndGame(boolean endGame) throws RemoteException {
        this.endGame = endGame;
        /*PropertyChangeEvent evt = new PropertyChangeEvent(
                this,
                "END_GAME_SET",
                this.endGame,
                endGame);
        this.listener.propertyChange(evt);*/
        //server.notifyClients();
        System.out.println("Set end game");

    }

    public Player getEndPlayer() {
        return endPlayer;
    }

    public void setEndPlayer(Player endPlayer) throws RemoteException {
        this.endPlayer = endPlayer;
        this.changed = true;
        /*PropertyChangeEvent evt = new PropertyChangeEvent(
                this,
                "END_GAME_SET",
                null,
                new GameViewMessage(this, null));
        this.listener.propertyChange(evt);*/
        //server.notifyClients();
        System.out.println("Set end player");
    }

    public Player getPlayer(String nickname){
        for(Player p : players){
            if(p.getNickname().equals(nickname))
                return p;
        }
        return null;
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
    public void calculateCommonPoints() throws ColumnIndexOutOfBoundsException {
        if(!commonGoals.get(0).getWinningPlayers().contains(currentPlayer))
            commonGoals.get(0).check(currentPlayer);

        if(!commonGoals.get(1).getWinningPlayers().contains(currentPlayer))
            commonGoals.get(1).check(currentPlayer);
    }

    public void setListener(PropertyChangeListener listener) {
        this.listener = listener;
    }


    /**
     * Notify a property change in one of object connected to 'Game' to Game Listener
     * @param evt A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.changed = true;
        /*PropertyChangeEvent move = new PropertyChangeEvent(
                this,
                evt.getPropertyName(),
                null,
                new GameViewMessage(this, null));*/
        /*try {
            server.notifyClients();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }*/
    }

    public void triggerException(Exception e) throws RemoteException {
        /*PropertyChangeEvent exception = new PropertyChangeEvent(
                this,
                e.getMessage(),
                null,
                new GameViewMessage(null, e));
        this.listener.propertyChange(exception);*/
        server.notifyClients();
        System.out.println("Trigger exception\n" + e.getMessage());
    }

    public Chat getChat() {
        return chat;
    }
}
