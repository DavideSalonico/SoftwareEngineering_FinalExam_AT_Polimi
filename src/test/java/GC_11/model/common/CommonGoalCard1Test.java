package GC_11.model.common;

import GC_11.Controller.JsonReader;
import GC_11.model.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalCard1Test {

    private Player player;
    @BeforeAll
            static void setup(){
            Player player= new Player("NomeGiocatore", JsonReader.readPersonalGoalCard(2));
    }


    @Test
    void checkTest() {
    }

    @Test
    void verifyTest() {
    }
}