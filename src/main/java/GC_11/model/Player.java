package GC_11.model;

import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.exceptions.NotEnoughFreeSpacesException;
import GC_11.util.ControlMatrix;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.List;

/**
 * The Player class represents a player in the game. It contains various attributes related to the player, including
 * their nickname, points for common goals, points for personal goals, points for adjacency, personal goal card,
 * and shelf. The class implements the PropertyChangeListener interface and provides methods for accessing and modifying
 * player-related information.
 */
public class Player implements PropertyChangeListener, Serializable {
    private String nickname;
    private int pointsCommonGoals;
    private int pointsPersonalGoal;
    private int pointsAdjacency;
    private PersonalGoalCard personalGoal;
    private Shelf shelf;

    private ControlMatrix matrix = new ControlMatrix();

    //Game must register
    public transient PropertyChangeListener listener;




    /**
     * Constructs a Player object with the specified nickname and personal goal card.
     *
     * @param nickname     The nickname of the player.
     * @param personalCard The personal goal card assigned to the player.
     */
    public Player(String nickname, PersonalGoalCard personalCard) {
        this.nickname = nickname;
        this.shelf = new Shelf();
        this.shelf.setListener(this);
        this.personalGoal = personalCard;
        this.pointsCommonGoals = 0;
        this.pointsPersonalGoal = 0;
        this.pointsAdjacency = 0;
    }

    /**
     * Constructs a copy of the Player object.
     *
     * @param p The Player instance to be copied.
     */
    public Player(Player p){
        this.nickname = p.getNickname();
        this.shelf = new Shelf(p.getShelf());
        this.shelf.setListener(this);
        this.personalGoal = new PersonalGoalCard(p.getPersonalGoal());
        this.pointsCommonGoals = p.getPointsCommonGoals();
        this.pointsPersonalGoal = p.getPointsPersonalGoal();
        this.pointsAdjacency = p.getPointsAdjacency();
    }

    /**
     * Constructs a Player object with the specified nickname and default shelf.
     *
     * @param nickname The nickname of the player.
     */
    public Player(String nickname) {
        this.nickname = nickname;
        this.shelf = new Shelf();
        this.shelf.setListener(this);
    }

    //Solo per test, da cancellare
    public Player() {
        this.nickname = "nickname";
        this.shelf = new Shelf();
        this.shelf.setListener(this);
        this.personalGoal = new PersonalGoalCard();
    }

    /**
     * set the listener of the player
     *
     * @param listener the listener to set
     */
    public void setListener(PropertyChangeListener listener) {
        this.listener = listener;
    }

    /**
     * Returns the personal goal card assigned to the player.
     *
     * @return The personal goal card.
     */
    public PersonalGoalCard getPersonalGoal() {
        return this.personalGoal;
    }

    /**
     * Get 'nickname' method
     *
     * @return The nickname of the player
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Returns the total points of the player, including points for common goals, personal goals, and adjacency.
     *
     * @return The total points of the player.
     */
    public int getPoints() {
        return this.pointsAdjacency + this.pointsPersonalGoal + this.pointsCommonGoals;
    }

    /**
     * Returns the points for adjacency of the player.
     *
     * @return The points for adjacency.
     */
    public int getPointsAdjacency() {
        return this.pointsAdjacency;
    }

    /**
     * Adds points for common goals to the player's common goal points.
     *
     * @param n The number of points to add.
     */
    public void addPointsCommonGoals(int n) {
        this.pointsCommonGoals += n;
    }

    /**
     * Inserts tiles into the player's shelf in the specified column and order.
     *
     * @param tilesOrder The list of tiles in the desired order of insertion.
     * @param column     The column in which to insert the tiles.
     * @throws ColumnIndexOutOfBoundsException  If the column index is out of bounds.
     * @throws NotEnoughFreeSpacesException     If there are not enough free spaces in the shelf.
     */
    public void insertTiles(List<Tile> tilesOrder, int column) throws ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {
        if (column < 0 || column > 5) {
            throw new ColumnIndexOutOfBoundsException(column);
        }
        shelf.addTiles(tilesOrder, column);
    }

    /**
     * Updates the points for the player's personal goal based on the correct number of tiles.
     *
     * @throws ColumnIndexOutOfBoundsException If a column index is out of bounds.
     */
    public void updatesPointsPersonalGoal() throws ColumnIndexOutOfBoundsException {
        this.pointsPersonalGoal = 0;
        int totalRight = 0;
        // For every goal in the personal goal card check if matches with the personal shelf
        for (Triplet t : personalGoal.getGoalList()) {
            if (shelf.getTile(t.getRow(), t.getCol()).getColor() == t.getColor()) {
                totalRight++;
            }
        }
        switch (totalRight) {
            case 1:
                this.pointsPersonalGoal = 1;
                break;
            case 2:
                this.pointsPersonalGoal = 2;
                break;
            case 3:
                this.pointsPersonalGoal = 4;
                break;
            case 4:
                this.pointsPersonalGoal = 6;
                break;
            case 5:
                this.pointsPersonalGoal = 9;
                break;
            case 6:
                this.pointsPersonalGoal = 12;
                break;
            default:
                this.pointsPersonalGoal = 0;
        }
    }

    /**
     * Get 'Shelf' method
     *
     * @return the shelf of the player
     */
    public Shelf getShelf() {
        return shelf;
    }

    /**
     * Checks if the current player is equal to the given player.
     *
     * @param currentPlayer The player to compare with.
     * @return True if the players are equal, false otherwise.
     */
    public boolean equals(Player currentPlayer) {
        return (this.nickname.equals(currentPlayer.getNickname()) && (this.pointsAdjacency + this.pointsPersonalGoal + this.pointsCommonGoals) == currentPlayer.getPoints());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.listener.propertyChange(evt);
    }

    /**
     * Calculates and assigns points for adjacency to the player.
     *
     * @throws ColumnIndexOutOfBoundsException If a column index is out of bounds.
     */
    public void calculateAndGiveAdjacencyPoint() throws ColumnIndexOutOfBoundsException {
        this.pointsAdjacency = 0;
        matrix.reset();
        int counterTiles = 0;
        for (int l = 0; l < 6; l++) {
            for (int c = 0; c < 5; c++) {
                if (!matrix.get(l, c)) {
                    matrix.setTrue(l, c);
                    if (!this.getShelf().getTile(l, c).getColor().equals(TileColor.EMPTY)) {
                        counterTiles = 1 + verify(l, c + 1, this.getShelf().getTile(l, c).getColor()) +
                                verify(l + 1, c, this.getShelf().getTile(l, c).getColor());
                        switch (counterTiles) {
                            case 1:
                            case 2:
                                break;
                            case 3:
                                this.pointsAdjacency = this.pointsAdjacency + 2;
                                break;
                            case 4:
                                this.pointsAdjacency = this.pointsAdjacency + 3;
                                break;
                            case 5:
                                this.pointsAdjacency = this.pointsAdjacency + 5;
                                break;
                            default:
                                this.pointsAdjacency = this.pointsAdjacency + 8;
                                break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Recursive helper method used to calculate the number of adjacent tiles of the same color starting from a given position.
     *
     * @param l     The row index of the current position.
     * @param c     The column index of the current position.
     * @param color The color of the tiles being checked for adjacency.
     * @return The number of adjacent tiles of the same color, including the current tile.
     * @throws ColumnIndexOutOfBoundsException If the given row or column index is out of bounds.
     */
    private int verify(int l, int c, TileColor color) throws ColumnIndexOutOfBoundsException {

        if (l > 5 || c > 4 || l < 0 || c < 0) {
            return 0;
        } else if (!matrix.get(l, c)) {
            if (this.getShelf().getTile(l, c).getColor() == color) {
                matrix.setTrue(l, c);
                return 1 + verify(l, c + 1, this.getShelf().getTile(l, c).getColor()) +
                        verify(l + 1, c, this.getShelf().getTile(l, c).getColor()) +
                        verify(l, c - 1, this.getShelf().getTile(l, c).getColor()) +
                        verify(l - 1, c, this.getShelf().getTile(l, c).getColor());
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    /**
     * Sets the personal goal card for the player.
     *
     * @param p The personal goal card to set.
     */
    public void setPersonalGoal(PersonalGoalCard p) {
        this.personalGoal = p;
    }

    /**
     * Returns the points for common goals of the player.
     *
     * @return The points for common goals.
     */
    public int getPointsCommonGoals() {
        return this.pointsCommonGoals;
    }

    /**
     * Returns the points for personal goals of the player.
     *
     * @return The points for personal goals.
     */
    public int getPointsPersonalGoal() {
        return this.pointsPersonalGoal;
    }
}


