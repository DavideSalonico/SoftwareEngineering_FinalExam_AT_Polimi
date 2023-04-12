package GC_11.model;

import GC_11.exceptions.ColumnIndexOutOfBoundsException;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class Player implements PropertyChangeListener{

    private String nickname;
    private int points;
    private List<Tile> tiles;
    private PersonalGoalCard personalGoal;
    private List<Integer> ListCommonGoals;
    private Shelf shelf;

    public void setListener(PropertyChangeListener listener) {
        this.listener = listener;
    }

    //Game must register
    PropertyChangeListener listener;


    public Player(String nickname, PersonalGoalCard personalCard){
        this.nickname = nickname;
        this.points=0;
        this.shelf = new Shelf();
        this.shelf.setListener(this);
        this.personalGoal = personalCard;
    }

    public Player(String nickname){
        this.nickname = nickname;
        this.points=0;
        this.shelf=new Shelf();
        this.shelf.setListener(this);
    }

    public Player(){
        this.nickname = "nickname";
        this.points = 0;
        this.shelf = new Shelf();
        this.shelf.setListener(this);
        this.personalGoal = new PersonalGoalCard();
    }

    /**
     *
     * @return The nickname of the player
     */

    public String getNickname(){
        return nickname;
    }

    /**
     *
     * @return The points of the player
     */
    public int getPoints(){
        return points;
    }

    /**
     *
     * @param n is the number of points that is summed to the current number of points
     */

    public void addPoints(int n){
        this.points+=n;
    }

    /**
     *
     * @return the tiles the player is holding from the board and will be put in the shelf
     */
    public List<Tile> getTiles() {
        return tiles;
    }

    /**
     *
     * @param tilesDrawn is the list of tiles that the player drawns from the board
     */

    public void setTiles(List<Tile> tilesDrawn) {
        this.tiles = tilesDrawn;
    }

    /**
     *  This method insert the tiles in the column and by the order choosen by the player
     * @param tilesOrder is the list of the tiles in the order that the player want to insert them into the shelf
     * @param column is the columns in which the player want to insert the insert
     */

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

    /**
     * This method calculate the personal point checking how many personal goal are right
     * @return the number of points according to the right number of tiles
     * @throws ColumnIndexOutOfBoundsException
     */

    private int caluclatePersonalPoints() throws ColumnIndexOutOfBoundsException {
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

    /**
     *
     * @return the shelf of the player
     */
    public Shelf getShelf() {
        return shelf;
    }

    public void pickTile(Tile t){
        this.tiles.add(t);
    }

    public boolean equals(Player currentPlayer) {
        return (this.nickname.equals(currentPlayer.getNickname()) && this.points == currentPlayer.getPoints());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.listener.propertyChange(evt);
    }
}
