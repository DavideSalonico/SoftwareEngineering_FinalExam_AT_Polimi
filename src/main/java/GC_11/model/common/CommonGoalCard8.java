package GC_11.model.common;

import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.model.Player;
import GC_11.model.TileColor;

import java.util.HashSet;
import java.util.Set;

public class CommonGoalCard8 extends CommonGoalCard{

    private final String text = "Two lines each formed by 5 different types of tiles." +
            " One line can show the same or a different combination of the other line.";
    public int id = 8;


    /**
     * This method check if the common goal of the card has been achieved and in this case adds points to the player
     * @param player is the player to which you want to control the shelf
     * @throws ColumnIndexOutOfBoundsException when trying to control a position outside the matrix
     */
    @Override
    public void check(Player player) throws ColumnIndexOutOfBoundsException {

        int correctLines=0;
        for (int l=0; l<6; l++){
            Set<TileColor> colors = new HashSet<TileColor>();
            for (int c=0; c<5; c++){
                if(!player.getShelf().getTile(l,c).getColor().equals(TileColor.EMPTY)){
                    colors.add(player.getShelf().getTile(l,c).getColor());
                }
            }
            if (colors.size()==5){
                correctLines++;
            }
        }
        if (correctLines >= 2){
            givePoints(player);
        }

    }
    public String getText() {
        return this.text;
    }
}
