package GC_11.model.common;

import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.model.Player;
import GC_11.model.TileColor;

public class CommonGoalCard10 extends CommonGoalCard {

    public CommonGoalCard10() {
        super();
        this.id = 10;
        this.text = "Five tiles of the same type forming an X.";
    }

    /**
     * This method check if the common goal of the card has been achieved and in this case adds points to the player
     *
     * @param player is the player to which you want to control the shelf
     * @throws ColumnIndexOutOfBoundsException when trying to control a position outside the matrix
     */
    @Override
    public void check(Player player) throws ColumnIndexOutOfBoundsException {

        for (int l = 0; l < 4; l++) {
            for (int c = 0; c < 3; c++) {

                if (player.getShelf().getTile(l, c).getColor().equals(player.getShelf().getTile(l + 1, c + 1).getColor()) &&
                        player.getShelf().getTile(l, c).getColor().equals(player.getShelf().getTile(l + 2, c + 2).getColor()) &&
                        player.getShelf().getTile(l, c).getColor().equals(player.getShelf().getTile(l, c + 2).getColor()) &&
                        player.getShelf().getTile(l, c).getColor().equals(player.getShelf().getTile(l + 2, c).getColor()) &&
                        !player.getShelf().getTile(l, c).getColor().equals(TileColor.EMPTY)) {

                    givePoints(player);
                    return;
                }

            }
        }

    }

}
