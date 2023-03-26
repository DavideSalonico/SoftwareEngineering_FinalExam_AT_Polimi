package GC_11.model.common;

import GC_11.exceptions.columnIndexOutOfBoundsException;
import GC_11.model.Player;

public class CommonGoalCard3 extends CommonGoalCard{

    private final String text = "Four groups each containing at least" +
            "4 tiles of the same type (not necessarily in the depicted shape)." +
            "The tiles of one group can be different from those of another group.";


    @Override
    public void check(Player player) throws columnIndexOutOfBoundsException {

    }
}
