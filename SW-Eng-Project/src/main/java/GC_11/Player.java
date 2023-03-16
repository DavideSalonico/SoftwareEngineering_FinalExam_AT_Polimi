package GC_11;

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
        shelf.addTiles(tilesOrder,column);

    }

    private void caluclatePersonalPoints(){

    }

    private void calculateCommonPoints(){

    }

    public Shelf getShelf() {
        return shelf;
    }
}
