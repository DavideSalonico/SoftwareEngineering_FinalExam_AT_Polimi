package GC_11.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * The PersonalGoalCard class represents a personal goal card in a game. It contains an ID and a list of goal triplets.
 * It provides methods for accessing and manipulating the goal list, printing the card, and getting the ID.
 */
public class PersonalGoalCard implements Serializable {

    private int id;
    private List<Triplet> goalList;

    /**
     * Constructs a PersonalGoalCard object with the specified ID and goal list.
     *
     * @param id       The ID of the personal goal card.
     * @param goalList The list of goal triplets for the card.
     *
     * @see Triplet
     */
    public PersonalGoalCard(int id, List<Triplet> goalList) {
        this.id = id;
        this.goalList = new ArrayList<Triplet>(goalList);
    }

    /**
     * Constructs a copy of the PersonalGoalCard object.
     *
     * @param p The PersonalGoalCard instance to be copied.
     */
    public PersonalGoalCard(PersonalGoalCard p){
        this.id = p.getId();
        this.goalList = new ArrayList<Triplet>(p.getGoalList());
    }

    /**
     * Constructs a default PersonalGoalCard object with ID -1 and a null goal list.
     */
    public PersonalGoalCard() {
        this.id = -1;
        this.goalList = null;
    }

    /**
     * Returns the list of goal triplets for the personal goal card.
     *
     * @return The list of goal triplets.
     */
    public List<Triplet> getGoalList() {
        return goalList;
    }

    /**
     * Prints the personal goal card as a matrix, representing the colored tiles for each goal.
     */
    public void print() {
        String[][] matrix = new String[6][5];
        for (Triplet goal : this.getGoalList()) {
            matrix[goal.getRow()][goal.getCol()] = TileColor.ColorToString(goal.getColor());
        }
        for (int row = 0; row < 6; row++) {
            System.out.print("|");
            for (int col = 0; col < 5; col++) {
                if (matrix[row][col] == null) {
                    System.out.print("  |");
                } else {
                    System.out.print(matrix[row][col] + "|");
                }
            }
            System.out.println();
        }
        System.out.println(" 0  1  2  3  4 ");
    }

    /**
     * Returns the ID of the personal goal card.
     *
     * @return The ID of the personal goal card.
     */
    public int getId() {
        return id;
    }
}
