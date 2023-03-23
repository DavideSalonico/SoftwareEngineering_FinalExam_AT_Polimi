package GC_11;

import GC_11.model.PersonalGoalCard;
import GC_11.model.Triplet;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static junit.framework.Assert.assertEquals;

public class PersonalGoalCardTest {
    //TODO: To be implemented by Davide

    @DisplayName("Exactly six tiles")
    @Test
    public void exactlySixGoals(PersonalGoalCard p){
        assertEquals(p.getGoalList().size(), 6);
    }

    @DisplayName("All different colors")
    @Test
    public void allSixColors(PersonalGoalCard p){
        List<Triplet> goals = p.getGoalList();

        for(int i = 0; i < 6; i++){
            for(int j = i + 1; j < 6; j++){
                assert(!goals.get(i).getColor().equals(goals.get(j).getColor()));
            }
        }
    }
}
