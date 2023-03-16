package GC_11.model;

import java.util.List;

public class Player {



    private String nickname;
    private int points;
    private List<Tile> tiles;
    private PersonalGoalCard personalGoal;
    private List<Integer> ListCommonGoals;
    private Shelf shelf;


    public Player(String s){
        this.nickname = s;
        this.points=0;
        this.shelf = new Shelf();
        this.personalGoal = new PersonalGoalCard();
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

    public void insertTiles(List<Tile> tilesOrder, int column){
        if (column <0 || column > 5){
            // Exception
        }
        try {
            shelf.addTiles(tilesOrder,column);
        }
        catch(Exception e){
            // Handle the exception that signals if the player is trying to insert the tiles in a column which has not enough free spaces
            // or the exception if column number is less than 0 or greater than 5
            e.getMessage();
        }

    }

    private void caluclatePersonalPoints(){

    }

    private void calculateCommonPoints(){

    }

    public Shelf getShelf() {
        return shelf;
    }
}
