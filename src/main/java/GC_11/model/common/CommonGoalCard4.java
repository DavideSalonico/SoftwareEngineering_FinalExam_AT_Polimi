package GC_11.model.common;

import GC_11.exceptions.columnIndexOutOfBoundsException;
import GC_11.model.Player;
import GC_11.model.TileColor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommonGoalCard4 extends CommonGoalCard{
    private List<Player> winningPlayers;

    @Override
    public int calculatePoints(Player player) {
        return 0;

    }


//this code verifies if there are at least 4 line with 5 tiles and maximum 3 different tile types. it uses a set to count the number of different
//types and a counter to verify if the line has 5 tiles
    @Override
    public void check(Player player) throws columnIndexOutOfBoundsException {

        int correctLines=0;
        for (int l=0; l<6; l++){
            int counter = 0;
            Set<TileColor> colors = new HashSet<TileColor>();
            for (int c=0; c<5; c++){
                if(player.getShelf().getTile(c,l).getColor() != TileColor.EMPTY){
                    counter++;
                    colors.add(player.getShelf().getTile(c,l).getColor());
                }
            }
            if (counter==5 && colors.size()<4){
                correctLines++;
            }
        }
        if (correctLines >= 4){
            calculatePoints(player);
            winningPlayers.add(player);
        }

    }
}
