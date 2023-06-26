package GC_11.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * We will create an instance of PersonalGoalCard in the "Player" class, but the actor who will call his constructor is going to be a Controller's class,
 * it's a Serializable class
 */
public class PersonalGoalCard implements Serializable {

    private int id;
    private List<Triplet> goalList;

    public PersonalGoalCard(int id, List<Triplet> goalList) {
        this.id = id;
        this.goalList = new ArrayList<Triplet>(goalList);
    }

    public PersonalGoalCard(PersonalGoalCard p){
        this.id = p.getId();
        this.goalList = new ArrayList<Triplet>(p.getGoalList());
    }

    public PersonalGoalCard() {
        this.id = -1;
        this.goalList = null;
    }

    public List<Triplet> getGoalList() {
        return goalList;
    }

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

    public int getId() {
        return id;
    }
}
