package GC_11.model;

import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.exceptions.NotEnoughFreeSpacesException;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.List;

/**
 * Player's class, all serializable attributes except for ControlMatrix and the Listener of the class
 */
public class Player implements PropertyChangeListener, Serializable {
    private String nickname;
    private int pointsCommonGoals;
    private int pointsPersonalGoal;
    private int pointsAdjacency;
    private PersonalGoalCard personalGoal;
    private Shelf shelf;

    //ControlMatrix and Listener don't need to be Serialized
    private transient ControlMatrix matrix = new ControlMatrix();

    //Game must register
    public transient PropertyChangeListener listener;


    public void setListener(PropertyChangeListener listener) {
        this.listener = listener;
    }

    /**
     * Main constructor of Player
     *
     * @param nickname     of the Player, received by Controller
     * @param personalCard generated in random way and given by JsonReader
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

    public Player(Player p){
        this.nickname = p.getNickname();
        this.shelf = new Shelf(p.getShelf());
        this.shelf.setListener(this);
        this.personalGoal = new PersonalGoalCard(p.getPersonalGoal());
        this.pointsCommonGoals = p.getPointsCommonGoals();
        this.pointsPersonalGoal = p.getPointsPersonalGoal();
        this.pointsAdjacency = p.getPointsAdjacency();
    }

    //Solo per test, da cancellare. OCCHIO viene usato dal ServerSocket
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
     * Get 'point' method
     *
     * @return The points of the player
     */
    public int getPoints() {
        return this.pointsAdjacency + this.pointsPersonalGoal + this.pointsCommonGoals;
    }

    /**
     * Get 'pointsAdjacency' method
     *
     * @return The pointsAdjacency of the player
     */
    public int getPointsAdjacency() {
        return this.pointsAdjacency;
    }

    /**
     * This method add Points to a specif Player
     *
     * @param n is the number of points that is summed to the current number of points
     */


    public void addPointsCommonGoals(int n) {
        this.pointsCommonGoals += n;
    }

    /**
     * This method insert the tiles in the column and by the order chosen by the player
     *
     * @param tilesOrder is the list of the tiles in the order that the player want to insert them into the shelf
     * @param column     is the columns in which the player want to insert the insert
     */
    public void insertTiles(List<Tile> tilesOrder, int column) throws ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {
        if (column < 0 || column > 5) {
            throw new IndexOutOfBoundsException("Wrong column index received");
        }
        shelf.addTiles(tilesOrder, column);
    }

    /**
     * This method calculate the personal point checking how many personal goal are right
     *
     * @return the number of points according to the right number of tiles
     * @throws ColumnIndexOutOfBoundsException
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

    public boolean equals(Player currentPlayer) {
        return (this.nickname.equals(currentPlayer.getNickname()) && (this.pointsAdjacency + this.pointsPersonalGoal + this.pointsCommonGoals) == currentPlayer.getPoints());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.listener.propertyChange(evt);
    }

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

    public void setPersonalGoal(PersonalGoalCard p) {
        this.personalGoal = p;
    }

    public int getPointsCommonGoals() {
        return this.pointsCommonGoals;
    }

    public int getPointsPersonalGoal() {
        return this.pointsPersonalGoal;
    }
}


