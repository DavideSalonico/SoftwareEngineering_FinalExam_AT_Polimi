package GC_11.model.common;

import GC_11.exceptions.columnIndexOutOfBoundsException;
import GC_11.model.Player;
import GC_11.model.TileColor;

public class CommonGoalCard12 extends CommonGoalCard {

    private final String text = "Five columns of increasing or decreasing height. " +
            "Starting from the first column on the left or on the right, " +
            "each next column must be made of exactly one more tile. Tiles can be of any type.";

    /**
     * This method check if the common goal of the card is has been achieved and in this case adds points to the player
     * @param player is the player to which you want to control the shelf
     * @throws columnIndexOutOfBoundsException when trying to control a position outside the matrix
     */
    @Override
    public void check(Player player) throws columnIndexOutOfBoundsException {

        for (int a = 0; a <= 1; a++) {
            int counter1 = 0;
            int counter2 = 0;
            for (int l = a + 1; l < 6; l++) {
                for (int c = 1; c <= 4; c++) {
                    if((a==0 && !player.getShelf().getTile(a, 0).getColor().equals(TileColor.EMPTY)) ||
                            (a==1 && !player.getShelf().getTile(a, 0).getColor().equals(TileColor.EMPTY) &&
                                    player.getShelf().getTile(0, 0).getColor().equals(TileColor.EMPTY))) {
                        if (!player.getShelf().getTile(l, c).getColor().equals(TileColor.EMPTY) &&
                                player.getShelf().getTile(l-1, c).getColor().equals(TileColor.EMPTY   )) {
                            counter1++;
                        }
                    }

                    if((a==0 && !player.getShelf().getTile(a, 4).getColor().equals(TileColor.EMPTY)) ||
                            (a==1 && !player.getShelf().getTile(a, 4).getColor().equals(TileColor.EMPTY) &&
                                    player.getShelf().getTile(0, 4).getColor().equals(TileColor.EMPTY))) {
                        if (!player.getShelf().getTile(l, 4 - c).getColor().equals(TileColor.EMPTY) &&
                                player.getShelf().getTile(l-1, 4 - c).getColor().equals(TileColor.EMPTY)) {
                            counter2++;
                        }
                    }
                }
            }
            if (counter1 == 4 || counter2 == 4) {
                givePoints(player);
            }

        }
    }
}
