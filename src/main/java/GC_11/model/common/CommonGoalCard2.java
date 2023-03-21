package GC_11.model.common;

import GC_11.exceptions.columnIndexOutOfBoundsException;
import GC_11.model.Player;

import java.util.List;

//TODO verfificare perchè dice che la condizione è sempre falsa


public class CommonGoalCard2 extends CommonGoalCard{

    private List<Player> winningPlayers;

    @Override
    public int calculatePoints(Player player) {
        return 0;
    }

    @Override
    public void check(Player player) throws columnIndexOutOfBoundsException {
        for(int a=5; a>=4; a= a-1){
            int counter1=0;
            int counter2=0;
            for (int l=4; l>=0; l--){
                for (int c=1; c<=4; c++){
                    if (player.getShelf().getTile(c,l).getColor() == player.getShelf().getTile(0,a).getColor() && !player.getShelf().getTile(0,a).getColor().equals( "EMPTY" )){
                        counter1++;
                    }
                    if (player.getShelf().getTile(4-c,l).getColor() == player.getShelf().getTile(4,a).getColor() && !player.getShelf().getTile(4,a).getColor().equals("EMPTY")){
                        counter2++;
                    }
                }
            }
            if (counter1==4 || counter2==4){
                calculatePoints(player);
                winningPlayers.add(player);
            }
        }
    }



}
