package GC_11;

import java.util.List;
import java.util.Set;

public class Game {

    private List<Player> players;
    //private List<CommonGoalCard> commonGoals;
    private Player currentPlayer;
    private boolean endGame;
    private Player endPlayer;
    private Board board;
    private Lobby lobby;



    public Game(Set<Player> p){

    }

    public void run(){

    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

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
