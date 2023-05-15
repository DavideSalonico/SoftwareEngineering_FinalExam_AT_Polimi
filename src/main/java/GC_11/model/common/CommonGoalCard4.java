package GC_11.model.common;

import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.model.Player;
import GC_11.model.TileColor;

import java.util.HashSet;
import java.util.Set;

public class CommonGoalCard4 extends CommonGoalCard{

    private  final String text = "Four lines each formed by 5 tiles of maximum three different types. " +
            "One line can show the same or a different combination of another line.";
    public int id = 4;


    public int getId() {
        return id;
    }

    //this code verifies if there are at least 4 line with 5 tiles and maximum 3 different tile types.
    // it uses a set to count the number of different
    //types and a counter to verify if the line has 5 tiles
    /**
     * This method check if the common goal of the card has been achieved and in this case adds points to the player
     * @param player is the player to which you want to control the shelf
     * @throws ColumnIndexOutOfBoundsException when trying to control a position outside the matrix
     */
    @Override
    public void check(Player player) throws ColumnIndexOutOfBoundsException {

        int correctLines=0;
        for (int l=0; l<6; l++){
            int counter = 0;
            Set<TileColor> colors = new HashSet<TileColor>();
            for (int c=0; c<5; c++){
                if(player.getShelf().getTile(l,c).getColor() != TileColor.EMPTY){
                    counter++;
                    colors.add(player.getShelf().getTile(l,c).getColor());
                }
            }
            if (counter==5 && colors.size()<4){
                correctLines++;
            }
        }
        if (correctLines >= 4){
            givePoints(player);
        }

    }
    public String getText() {
        return this.text;
    }
}
