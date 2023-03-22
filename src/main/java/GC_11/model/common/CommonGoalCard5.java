package GC_11.model.common;

import GC_11.exceptions.columnIndexOutOfBoundsException;
import GC_11.model.Player;
import GC_11.model.TileColor;

public class CommonGoalCard5 extends CommonGoalCard{

    private final String text = "Four tiles of the same type in the four corners of the bookshelf.";

    @Override
    public void check(Player player) throws columnIndexOutOfBoundsException {
        TileColor color;
        color = player.getShelf().getTile(0,0).getColor();
        if(color != TileColor.EMPTY &&
                player.getShelf().getTile(4,0).getColor() == color &&
                player.getShelf().getTile(0,5).getColor() == color &&
                player.getShelf().getTile(4,5).getColor() == color) {
            givePoints(player);
        }
    }
}
