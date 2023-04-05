package GC_11.model;

import GC_11.model.common.*;
import GC_11.util.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends Observable {

    private List<Player> players;
    private List<CommonGoalCard> commonGoals;
    private Player currentPlayer;
    private boolean endGame;
    private Player endPlayer;
    private Board board;

    public Game(List<String> playerNames){

        this.players = new ArrayList<Player>();
        for(int i=0; i<playerNames.size(); i++){
            this.players.add(new Player(playerNames.get(i)));
        }
        this.currentPlayer = this.players.get(0);
        this.endGame = false;
        this.commonGoals = new ArrayList<CommonGoalCard>();
        this.board = new Board();
        Random random = new Random();
        int tmp1 = random.nextInt(0, 11);
        int tmp2 = random.nextInt(0, 11);
        while(tmp1 == tmp2) {
            tmp2 = random.nextInt(0, 11);
        }
        this.commonGoals.add(loadCommon(tmp1));
        this.commonGoals.add(loadCommon(tmp2));
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

    public boolean isGameEnded() {
        return endGame;
    }

    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
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
