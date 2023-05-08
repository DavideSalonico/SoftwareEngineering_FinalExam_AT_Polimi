package GC_11.model.common;

import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.model.Player;
import GC_11.model.TileColor;

import java.util.HashSet;
import java.util.Set;

public class CommonGoalCard6 extends CommonGoalCard{

    private final String text = "Two columns each formed by 6 different types of tiles.";

    /**
     * This method check if the common goal of the card has been achieved and in this case adds points to the player
     * @param player is the player to which you want to control the shelf
     * @throws ColumnIndexOutOfBoundsException when trying to control a position outside the matrix
     */
    @Override
    public void check(Player player) throws ColumnIndexOutOfBoundsException {

        int correctColumns=0;
        for (int c=0; c<5; c++){
            Set<TileColor> colors = new HashSet<TileColor>();
            for (int l=0; l<6; l++){
                if(!player.getShelf().getTile(l,c).getColor().equals(TileColor.EMPTY)){
                    colors.add(player.getShelf().getTile(l,c).getColor());
                }
            }
            if (colors.size()==6){
                correctColumns++;
            }
        }
        if (correctColumns >= 2){
            givePoints(player);
        }

    }
    public String getText() {
        return this.text;
    }
}
