package GC_11.model.common;

import GC_11.exceptions.columnIndexOutOfBoundsException;
import GC_11.model.Player;
import GC_11.model.TileColor;

import java.util.HashSet;
import java.util.Set;

public class CommonGoalCard9 extends CommonGoalCard{

    private final String text = "Three columns each formed by 6 tiles of maximum three different types." +
            " One column can show the same or a different combination of another column.";


    /**
     * This method check if the common goal of the card has been achieved and in this case adds points to the player
     * @param player is the player to which you want to control the shelf
     * @throws columnIndexOutOfBoundsException when trying to control a position outside the matrix
     */
    @Override
    public void check(Player player) throws columnIndexOutOfBoundsException {

        int correctColumns=0;
        for (int l=0; l<6; l++){
            int counter = 0;
            Set<TileColor> colors = new HashSet<TileColor>();
            for (int c=0; c<5; c++){
                if(player.getShelf().getTile(l,c).getColor() != TileColor.EMPTY){
                    counter++;
                    colors.add(player.getShelf().getTile(l,c).getColor());
                }
            }
            if (counter==6 && colors.size()<4){
                correctColumns++;
            }
        }
        if (correctColumns >= 4){
            givePoints(player);
        }

    }
}
