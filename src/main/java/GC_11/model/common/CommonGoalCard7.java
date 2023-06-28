package GC_11.model.common;

import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.util.ControlMatrix;
import GC_11.model.Player;
import GC_11.model.TileColor;

public class CommonGoalCard7 extends CommonGoalCard {

    private final String text = "Two groups each containing 4 tiles of the same type in a 2x2 square. " +
            "The tiles of one square can be different from those of the other square.";
    public int id = 7;

    public int getId() {
        return id;
    }

    ControlMatrix matrix = new ControlMatrix();

    @Override
    public void check(Player player) throws ColumnIndexOutOfBoundsException {

        matrix.reset();
        int counterTiles = 0;
        int counterGroups = 0;
        int counterForSquare = 0;
        for (int l = 0; l < 5; l++) {
            for (int c = 0; c < 4; c++) {
                if (!matrix.get(l, c) && !player.getShelf().getTile(l, c).getColor().equals(TileColor.EMPTY)) {
                    matrix.setTrue(l, c);
                    counterTiles = 1 + verify(player, l, c + 1, player.getShelf().getTile(l, c).getColor()) +
                            verify(player, l + 1, c, player.getShelf().getTile(l, c).getColor());
                    if (counterTiles == 4) {
                        counterForSquare = 1 + verifySquare(player, l, c + 1, player.getShelf().getTile(l, c).getColor()) +
                                verifySquare(player, l + 1, c, player.getShelf().getTile(l, c).getColor()) +
                                verifySquare(player, l + 1, c + 1, player.getShelf().getTile(l, c).getColor());
                        if (counterForSquare == 4) {
                            counterGroups++;
                        }

                    }
                }
            }
        }
        if (counterGroups >= 2) {
            givePoints(player);
        }

    }

    public int verifySquare(Player player, int l, int c, TileColor color) throws ColumnIndexOutOfBoundsException {

        if (l > 5 || c > 4) {
            return 0;
        } else {
            if (player.getShelf().getTile(l, c).getColor() == color) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    public int verify(Player player, int l, int c, TileColor color) throws ColumnIndexOutOfBoundsException {

        if (l > 5 || c > 4 || l < 0 || c < 0) {
            return 0;
        } else if (!matrix.get(l, c)) {
            if (player.getShelf().getTile(l, c).getColor() == color) {
                matrix.setTrue(l, c);
                return 1 + verify(player, l, c + 1, player.getShelf().getTile(l, c).getColor()) +
                        verify(player, l + 1, c, player.getShelf().getTile(l, c).getColor()) +
                        verify(player, l, c - 1, player.getShelf().getTile(l, c).getColor()) +
                        verify(player, l - 1, c, player.getShelf().getTile(l, c).getColor());
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public String getText() {
        return this.text;
    }
}
