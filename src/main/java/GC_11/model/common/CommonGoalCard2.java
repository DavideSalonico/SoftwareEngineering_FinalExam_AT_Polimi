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
    /**
     * This method check if the common goal of the card has been achieved and in this case adds points to the player
     * @param player is the player to which you want to control the shelf
     * @throws columnIndexOutOfBoundsException when trying to control a position outside the matrix
     */
    @Override
    public void check(Player player) throws columnIndexOutOfBoundsException {
        for(int a=1; a>=0; a--){
            int counter1=0;
            int counter2=0;
            for (int l=2-a; l<=5-a; l++){
               // for (int c=1; c<=4; c++){

                    System.out.println("conreollo nella poszione " + l+ " e " + (l-(1-a))+ " con " + player.getShelf().getTile(l,l-(1-a)).getColor() + " e " );
                    System.out.println(player.getShelf().getTile(a,0).getColor());
                    if (player.getShelf().getTile(l,l-(1-a)).getColor() == player.getShelf().getTile(1-a,0).getColor() && !player.getShelf().getTile(1-a,0).getColor().equals( TileColor.EMPTY )){
                        counter1++;
                    }
                System.out.println("conreollo nella poszione " + l+ " e " + (4-(l-(1-a))));
                    if (player.getShelf().getTile(l,4-(l-(1-a))).getColor() == player.getShelf().getTile(1-a,4).getColor() && !player.getShelf().getTile(1-a,4).getColor().equals(TileColor.EMPTY)){
                        counter2++;
                    }
               // }
            }
            if (counter1==4 || counter2==4){
                givePoints(player);
                return;
            }
        }
    }



}
