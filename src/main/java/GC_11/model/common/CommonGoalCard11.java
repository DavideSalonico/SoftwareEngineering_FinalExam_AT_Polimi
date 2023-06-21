package GC_11.model.common;

import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.model.Player;
import GC_11.model.TileColor;

import java.util.ArrayList;
import java.util.List;

public class CommonGoalCard11 extends CommonGoalCard {

    private final String text = "Eight tiles of the same type. " +
            "There’s no restriction about the position of these tiles.";
    public int id = 11;

    public int getId() {
        return id;
    }

    /**
     * This method check if the common goal of the card is has been achieved and in this case adds points to the player
     *
     * @param player is the player to which you want to control the shelf
     * @throws ColumnIndexOutOfBoundsException when trying to control a position outside the matrix
     */
    @Override
    public void check(Player player) throws ColumnIndexOutOfBoundsException {

        List<TileColor> colors = new ArrayList<TileColor>();

        for (int l = 0; l < 6; l++) {
            for (int c = 0; c < 5; c++) {
                colors.add(player.getShelf().getTile(l, c).getColor());
            }
        }

        if (colors.stream().filter(TileColor.BLUE::equals).count() >= 8 ||
                colors.stream().filter(TileColor.GREEN::equals).count() >= 8 ||
                colors.stream().filter(TileColor.YELLOW::equals).count() >= 8 ||
                colors.stream().filter(TileColor.WHITE::equals).count() >= 8 ||
                colors.stream().filter(TileColor.PURPLE::equals).count() >= 8 ||
                colors.stream().filter(TileColor.CYAN::equals).count() >= 8) {

            givePoints(player);

        }

    }

    public String getText() {
        return this.text;
    }
}
