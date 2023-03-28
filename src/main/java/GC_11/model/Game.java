package GC_11.model;

import GC_11.Controller.JsonReader;

import java.util.List;

public class Game {

    private List<Player> players;
    //private List<CommonGoalCard> commonGoals;
    private Player currentPlayer;
    private boolean endGame;
    private Player endPlayer;
    private Board board;
    private Lobby lobby;

    //TODO: CHANGE FACTORY METHOD IN STRATEGY (for CommonGoalCards)



    // Passare le coordinate proibite come parametro a game invece che far usare il reader a board
    public Game(List<Player> players, Lobby lobby){
        this.players=players;
        this.board = new Board(JsonReader.readCoordinate(players.size()));
        this.lobby=lobby;
        this.endGame=false;

    }

    public void run(){

    }

    /**
     *
     * @return
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     *
     * @param currentPlayer
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public List<Player> getPlayers() {
        return players;
    }

    //public commonGoalCard getCommonGoal(int i)


    public Player getEndPlayer() {
        return endPlayer;
    }

    public void setEndPlayer(Player endPlayer) {
        this.endPlayer = endPlayer;
    }

    public Board getBoard() {
        return board;
    }

    private void calculateCommonPoints(Player p){

    }

    public void givePoints(Player p){

    }

    public Lobby getLobby(){
        return lobby;
    }
}
