package GC_11.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PersonalGoalCard {


    // Attributes
    private List<Triplet> goalList;

    // Methods

    public PersonalGoalCard(){

        final int randomNumber = new Random().nextInt(13);
        goalList = new ArrayList<Triplet>();

        // Read from a file one of the 12 personalGoalCards


    }

    public List<Triplet> getGoalList() {
        return goalList;
    }
}
