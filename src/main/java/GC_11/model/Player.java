package GC_11.model;

import GC_11.exceptions.columnIndexOutOfBoundsException;

import java.util.List;

public class Player {



    private String nickname;
    private int points;
    private List<Tile> tiles;
    private PersonalGoalCard personalGoal;
    private List<Integer> ListCommonGoals;
    private Shelf shelf;


    public Player(String nickname, PersonalGoalCard personalCard){
        this.nickname = nickname;
        this.points=0;
        this.shelf = new Shelf();
        this.personalGoal = personalCard;
    }

    public Player(){
        this.nickname = "nickname";
        this.points = 0;
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

    public void insertTiles(List<Tile> tilesOrder, int column) {
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

    private int caluclatePersonalPoints() throws columnIndexOutOfBoundsException {
        int totalRight = 0;
        // For every goal in the personal goal card check if matches with the personal shelf
        for (Triplet t : personalGoal.getGoalList()){
            if (shelf.getTile(t.getCol(),t.getRow()).getColor() == t.getColor()){
                totalRight++;
            }
        }
        switch (totalRight){
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 4;
            case 4:
                return 6;
            case 5:
                return 9;
            case 6:
                return 12;
            default:
                return 0;

        }
    }

    private void calculateCommonPoints(){

    }

    public Shelf getShelf() {
        return shelf;
    }

    public boolean equals(Player currentPlayer) {
        return (this.nickname.equals(currentPlayer.getNickname()) && this.points == currentPlayer.getPoints());
    }
}
