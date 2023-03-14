package GC_11;

import java.awt.image.TileObserver;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PersonalGoalCard {


    // Attributes
    private List<Triplet> goalList;

    // Methods

    public PersonalGoalCard(){
        Random RANDOM = new Random();

        goalList = new ArrayList<Triplet>();

        for (int i=0; i<6;i++){
            int randomRow = RANDOM.nextInt(6); // Generate a random row (0 to 5 rows)
            int randomColumn = RANDOM.nextInt(5); // Generate a random column (0 to 4 columns)
            TileColor randColor = TileColor.randomColor(); // Generate a random color
            goalList.add(new Triplet (randomRow,randomColumn,randColor)); // Add to the list a random triplet with row = randY, column = i (0 to 5) and random color
        }
    }

    public List<Triplet> getGoalList() {
        return goalList;
    }
}
