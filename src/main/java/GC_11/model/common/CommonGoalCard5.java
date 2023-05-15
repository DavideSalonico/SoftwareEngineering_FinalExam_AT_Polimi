package GC_11.model.common;

import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.model.Player;
import GC_11.model.TileColor;

public class CommonGoalCard5 extends CommonGoalCard{

    private final String text = "Four tiles of the same type in the four corners of the bookshelf.";
    public int id = 5;

    public int getId() {
        return id;
    }

    @Override
    public void check(Player player) throws ColumnIndexOutOfBoundsException {
        TileColor color;
        color = player.getShelf().getTile(5,0).getColor();
        if(color != TileColor.EMPTY &&
                player.getShelf().getTile(0,0).getColor() == color &&
                player.getShelf().getTile(0,4).getColor() == color &&
                player.getShelf().getTile(5,4).getColor() == color) {
            givePoints(player);
        }
    }
    public String getText() {
        return this.text;
    }
}
