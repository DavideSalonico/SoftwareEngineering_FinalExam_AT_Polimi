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

    /**
     * There is only one way to construct a Personal Card, it needs triplets of Tiles
     */
    public PersonalGoalCard(List <Triplet> read){

        //final int randomNumber = new Random().nextInt(13);
        goalList = new ArrayList<Triplet>();
        this.goalList=read;

        // Read from a file one of the 12 personalGoalCards
    }

    public PersonalGoalCard() {
        this.id = -1;
        this.goalList = null;
    }

    public List<Triplet> getGoalList() {
        return goalList;
    }

    public void print() {
        System.out.println("Personal Goal Card:");
        for(Triplet goal : this.getGoalList()){
            System.out.println(goal.getRow() + goal.getCol() + goal.getCol());
        }
    }

    public int getId() {
        return id;
    }
}
