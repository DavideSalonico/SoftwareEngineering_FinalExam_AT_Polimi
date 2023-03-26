package GC_11.model.common;

import GC_11.exceptions.columnIndexOutOfBoundsException;
import GC_11.model.Player;

public class CommonGoalCard7 extends CommonGoalCard{

    private final String text = "Two groups each containing 4 tiles of the same type in a 2x2 square. " +
            "The tiles of one square can be different from those of the other square.";

    @Override
    public void check(Player player) throws columnIndexOutOfBoundsException {

    }
}
