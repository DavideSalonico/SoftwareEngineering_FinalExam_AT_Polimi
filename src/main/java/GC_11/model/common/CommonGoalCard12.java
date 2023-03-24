package GC_11.model.common;

import GC_11.exceptions.columnIndexOutOfBoundsException;
import GC_11.model.Player;

public class CommonGoalCard12 extends CommonGoalCard {

    private final String text = "Five columns of increasing or decreasing height. " +
            "Starting from the first column on the left or on the right, " +
            "each next column must be made of exactly one more tile. Tiles can be of any type.";

    @Override
    public void check(Player player) throws columnIndexOutOfBoundsException {



    }
}
