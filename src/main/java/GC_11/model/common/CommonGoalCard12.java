package GC_11.model.common;

import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.model.Player;
import GC_11.model.TileColor;

public class CommonGoalCard12 extends CommonGoalCard {

    private final String text = "Five columns of increasing or decreasing height. " +
            "Starting from the first column on the left or on the right, " +
            "each next column must be made of exactly one more tile. Tiles can be of any type.";
    public int id = 12;


    /**
     * This method check if the common goal of the card is has been achieved and in this case adds points to the player
     * @param player is the player to which you want to control the shelf
     * @throws ColumnIndexOutOfBoundsException when trying to control a position outside the matrix
     */
    @Override
    public void check(Player player) throws ColumnIndexOutOfBoundsException {

        for (int a = 0; a <= 1; a++) {
            int counter1 = 0;
            int counter2 = 0;
            for (int l = a + 1; l < 5+a; l++) {
                    if((a==0 && !player.getShelf().getTile(a, 0).getColor().equals(TileColor.EMPTY)) ||
                            (a==1 && !player.getShelf().getTile(a, 0).getColor().equals(TileColor.EMPTY) &&
                                    player.getShelf().getTile(0, 0).getColor().equals(TileColor.EMPTY))) {
                        if (!player.getShelf().getTile(l, l+a).getColor().equals(TileColor.EMPTY) &&
                                player.getShelf().getTile(l-1, l+a).getColor().equals(TileColor.EMPTY   )) {
                            counter1++;
                        }
                    }

                    if((a==0 && !player.getShelf().getTile(a, 4).getColor().equals(TileColor.EMPTY)) ||
                            (a==1 && !player.getShelf().getTile(a, 4).getColor().equals(TileColor.EMPTY) &&
                                    player.getShelf().getTile(0, 4).getColor().equals(TileColor.EMPTY))) {
                        if (!player.getShelf().getTile(l, 4-(l+a)).getColor().equals(TileColor.EMPTY) &&
                                player.getShelf().getTile(l-1, 4-(l+a)).getColor().equals(TileColor.EMPTY)) {
                            counter2++;
                        }
                    }
            }
            if (counter1 == 4 || counter2 == 4) {
                givePoints(player);
            }

        }
    }
    public String getText() {
        return this.text;
    }
}
