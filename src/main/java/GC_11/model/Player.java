package GC_11.model;

import GC_11.exceptions.ColumnIndexOutOfBoundsException;

import java.util.List;

public class Player {



    private String nickname;
    private int points;
    private List<Tile> tiles;
    private PersonalGoalCard personalGoal;
    private List<Integer> ListCommonGoals;
    private Shelf shelf;

    /**
     * Main constructor of Player
     * @param nickname of the Player, received by Controller
     * @param personalCard generated in random way and given by JsonReader
     */
    public Player(String nickname, PersonalGoalCard personalCard){
        this.nickname = nickname;
        this.points=0;
        this.shelf = new Shelf();
        this.personalGoal = personalCard;
    }

    //Solo per test, da cancellare
    public Player(String nickname){
        this.nickname = nickname;
        this.points=0;
        this.shelf=new Shelf();
    }

    //Solo per test, da cancellare
    public Player(){
        this.nickname = "nickname";
        this.points = 0;
        this.shelf = new Shelf();
        this.personalGoal = new PersonalGoalCard();
    }

    /**
     * Get 'nickname' method
     * @return The nickname of the player
     */

    public String getNickname(){
        return nickname;
    }

    /**
     * Get 'point' method
     * @return The points of the player
     */
    public int getPoints(){
        return points;
    }

    /**
     * This method add Points to a specif Player
     * @param n is the number of points that is summed to the current number of points
     */

    public void addPoints(int n){
        this.points+=n;
    }

    /**
     * This method returns the List of Tiles that the Player drawn in his Turn, it needs to be empty at the end of the Turn
     * @return the tiles the player is holding from the board and will be put in the shelf
     */
    public List<Tile> getTiles() {
        return tiles;
    }

    /**
     * Player draws Tiles from Board
     * @param tilesDrawn is the list of tiles that the player drawn from the board
     */

    public void setTiles(List<Tile> tilesDrawn) {
        this.tiles = tilesDrawn;
    }

    /**
     *  This method insert the tiles in the column and by the order chosen by the player
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

    private int calculatePersonalPoints() throws ColumnIndexOutOfBoundsException {
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
     * Get 'Shelf' method
     * @return the shelf of the player
     */
    public Shelf getShelf() {
        return shelf;
    }

    public boolean equals(Player currentPlayer) {
        return (this.nickname.equals(currentPlayer.getNickname()) && this.points == currentPlayer.getPoints());
    }
}
