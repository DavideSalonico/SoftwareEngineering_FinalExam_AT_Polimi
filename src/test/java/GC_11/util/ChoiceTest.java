/*package GC_11.util;

import GC_11.model.Player;
import GC_11.util.choices.Choice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.*;

public class ChoiceTest {

    @DisplayName("IllegalMoveException")
    @Test
    public void throwsIllegalMoveException(){
        Player player = new Player("TestPlayer");
        try{
            Choice choice = new Choice(player, "CIAO 7");
            fail("Choice builder didn't throw the IllegalMoveException when supposed to");
        } catch (IllegalMoveException e){}
    }
}
*/