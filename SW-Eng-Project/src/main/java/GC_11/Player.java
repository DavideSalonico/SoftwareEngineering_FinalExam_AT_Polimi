package GC_11;

import java.util.List;

public class Player {


    // Attributes
    private String nickname;
    private int points;
    private List<Tile> tiles;
    private PersonalGoalCard personalGoal;
    private List<Integer> ListCommonGoals;
    private Shelf shelf;

    //Methodss
    public Player(){


    }

    public String getNickname(){
        return nickname;
    }

    public int getPoints(){
        return points;
    }

    public void addPoints(int n){
        this.points+=n;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void draw(){

    }

    public void insertTile(){

    }

    private void caluclatePersonalPoints(){

    }

    private void calculateCommonPoints(){

    }
}
