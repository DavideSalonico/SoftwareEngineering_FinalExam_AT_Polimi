package GC_11.model;

import GC_11.Controller.JsonReader;
import GC_11.model.common.CommonGoalCard;
import GC_11.util.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Game extends Observable {

    private List<Player> players;
    private List<CommonGoalCard> commonGoals;
    private Player currentPlayer;
    private boolean endGame;
    private Player endPlayer;
    private Board board;

    public boolean isEndGame() {
        return endGame;
    }

    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }

    // Passare le coordinate proibite come parametro a game invece che far usare il reader a board
    public Game(List<Player> players){
        this.board = new Board(JsonReader.readCoordinate(players.size()));
        this.endGame=false;

    }

    public Game(){
        int n = 4;
        this.players = new ArrayList<Player>();
        for(int i=0; i < 4; i++){
            this.players.add(new Player());
        }

        this.currentPlayer = players.get(0);
        this.endGame = false;
        this.board = new Board();
        this.endGame = false;
        Random random = new Random();
        int tmp1 = random.nextInt(0, 11);
        int tmp2 = random.nextInt(0, 11);
        while(tmp1 == tmp2) {
            tmp2 = random.nextInt(0, 11);
        }
    }

    public Game(Set<String> playersNames){
        // Create the players with the corresponding name

        for(String s : playersNames){
            players.add(new Player(s));
        }
    }

    public Game(List<String> playerNames){

        this.players = new ArrayList<Player>();
        for(int i=0; i<playerNames.size(); i++){
            this.players.add(new Player(playerNames.get(i)));
        }
        this.currentPlayer = this.players.get(0);
        this.endGame = false;
        this.commonGoals = new ArrayList<CommonGoalCard>();
        this.board = new Board();

    }

    public void run(){

    }


    /**
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * @param currentPlayer the player that is wanted to be set as current
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public List<Player> getPlayers() {
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


    public Player getEndPlayer() {
        return endPlayer;
    }

    public void setEndPlayer(Player endPlayer) {
        this.endPlayer = endPlayer;
    }

    public Board getBoard() {
        return board;
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

}
