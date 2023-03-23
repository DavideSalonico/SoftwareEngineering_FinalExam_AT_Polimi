package GC_11.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * We will create an instance of PersonalGoalCard in the "Player" class, but the actor who will call his constructor is going to be a Controller's class
 */
public class PersonalGoalCard {


    // Attributes
    private int id;
    private List<Triplet> goalList;

    // Methods

    /**
     * There is only one way to construct a Personal Card, it needs triplets of Tiles
     */
    public PersonalGoalCard(List <Triplet> read){

        //final int randomNumber = new Random().nextInt(13);
        goalList = new ArrayList<Triplet>();
        this.goalList=read;

        // Read from a file one of the 12 personalGoalCards
    }


    public List<Triplet> getGoalList() {
        return goalList;
    }
}
