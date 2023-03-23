package GC_11.model.common;

import GC_11.exceptions.columnIndexOutOfBoundsException;
import GC_11.model.Player;
import GC_11.model.TileColor;



public class CommonGoalCard2 extends CommonGoalCard{


    private final String text = "Five tiles of the same type forming a diagonal.";


// this code verifies if there are 5 tiles of the same type forming a diagonal. The check is made with 2 counters that
// counts how many tiles on a diagonal have the same color of the tile on the top left of the diagonal, for counter1,
// and the one on top right, for counter2. counter1 checks the diagonals that go from top-left to bottom-right,
// counter2 checks the diagonals that go from top-right to bottom-left
    @Override
    public void check(Player player) throws columnIndexOutOfBoundsException {
        for(int a=5; a>=4; a--){
            int counter1=0;
            int counter2=0;
            for (int l=a-1; l>=a-4; l--){
                for (int c=1; c<=4; c++){
                    if (player.getShelf().getTile(l,c).getColor() == player.getShelf().getTile(a,0).getColor() && !player.getShelf().getTile(a,0).getColor().equals( TileColor.EMPTY )){
                        counter1++;
                    }
                    if (player.getShelf().getTile(l,4-c).getColor() == player.getShelf().getTile(a,4).getColor() && !player.getShelf().getTile(a,4).getColor().equals(TileColor.EMPTY)){
                        counter2++;
                    }
                }
            }
            if (counter1==4 || counter2==4){
                givePoints(player);
            }
        }
    }



}
