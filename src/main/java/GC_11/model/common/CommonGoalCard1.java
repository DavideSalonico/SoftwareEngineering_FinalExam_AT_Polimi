package GC_11.model.common;

import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.model.ControlMatrix;
import GC_11.model.Player;
import GC_11.model.TileColor;


public class CommonGoalCard1 extends CommonGoalCard{

    private final String text = "Four groups each containing at least" +
            "4 tiles of the same type (not necessarily in the depicted shape)." +
            "The tiles of one group can be different from those of another group.";

  ControlMatrix matrix = new ControlMatrix();
    @Override
    public void check(Player player) throws ColumnIndexOutOfBoundsException {

        matrix.reset();
        int counterTiles = 0;
        int counterGroups=0;
        for(int l = 0; l<6; l++){
            for(int c=0; c<5; c++) {
                if(!matrix.get(l,c) && !player.getShelf().getTile(l, c).getColor().equals(TileColor.EMPTY)) {
                    matrix.setTrue(l,c);
                    counterTiles = 1+ verify(player, l, c+1, player.getShelf().getTile(l, c).getColor())+
                            verify(player,l+1,c,player.getShelf().getTile(l,c).getColor());
                    if(counterTiles>=2){
                        counterGroups++;
                    }
                }
            }
        }
        if(counterGroups>=6){
            givePoints(player);
        }
    }

    public int verify (Player player, int l, int c, TileColor color) throws ColumnIndexOutOfBoundsException {

        if(l>5 || c>4 || l<0 || c<0){
            return 0;
        }
        else if(!matrix.get(l, c) && !player.getShelf().getTile(l, c).getColor().equals(TileColor.EMPTY)){
            if(player.getShelf().getTile(l,c).getColor()==color){
                matrix.setTrue(l,c);
                return  1 + verify(player, l, c+1, player.getShelf().getTile(l, c).getColor())+
                        verify(player,l+1,c,player.getShelf().getTile(l,c).getColor())+
                        verify(player,l,c-1,player.getShelf().getTile(l,c).getColor())+
                        verify(player,l-1,c,player.getShelf().getTile(l,c).getColor());
            }else {return 0;}
        }
        else {return 0;}
    }
}