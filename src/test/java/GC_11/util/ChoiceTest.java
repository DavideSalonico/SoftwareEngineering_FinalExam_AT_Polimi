package GC_11.util;

import GC_11.model.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.*;

public class ChoiceTest {

    @DisplayName("IllegalArgumentException")
    @Test
    public void throwsIllegalArgumentException(){
        Player player = new Player("TestPlayer");
        try{
            Choice choice = new Choice(player, "CIAO 7");
            fail("Choice builder didn't throw the InvalidArgumentException when supposed to");
        } catch (IllegalArgumentException e){}
    }
}
