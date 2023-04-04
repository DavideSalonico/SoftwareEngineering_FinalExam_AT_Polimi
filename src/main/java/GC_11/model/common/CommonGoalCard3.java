package GC_11.model.common;

import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.model.Player;
import GC_11.model.TileColor;


public class CommonGoalCard3 extends CommonGoalCard{

    private final String text = "Four groups each containing at least" +
            "4 tiles of the same type (not necessarily in the depicted shape)." +
            "The tiles of one group can be different from those of another group.";

    boolean[][] controlMatrix = new boolean[6][5];


    private boolean getControlMatrix(int l, int c){

        return controlMatrix [l][c];

    }

    private void setControlMatrixTrue(int l, int c){

        controlMatrix [l][c]= true;

    }

    private void resetControlMatrix(){

        controlMatrix = new boolean[6][5];

    }
    @Override
    public void check(Player player) throws ColumnIndexOutOfBoundsException {

        resetControlMatrix();
        int counterTiles = 0;
        int counterGroups=0;
        for(int l = 0; l<6; l++){
            for(int c=0; c<5; c++) {
                if(!getControlMatrix(l,c)) {
                    setControlMatrixTrue(l,c);
                    counterTiles = 1+ verify(player, l, c+1, player.getShelf().getTile(l, c).getColor())+
                            verify(player,l+1,c,player.getShelf().getTile(l,c).getColor());
                    if(counterTiles>=4){
                        counterGroups++;
                    }
                }
            }
        }
        if(counterGroups>=4){
            givePoints(player);
        }
    }

    public int verify (Player player, int l, int c, TileColor color) throws ColumnIndexOutOfBoundsException {

        if(l>5 || c>4){
            return 0;
        }
        else if(!getControlMatrix(l,c)){
            if(player.getShelf().getTile(l,c).getColor()==color){
                setControlMatrixTrue(l,c);
                return  1 + verify(player, l, c+1, player.getShelf().getTile(l, c).getColor())+
                        verify(player,l+1,c,player.getShelf().getTile(l,c).getColor())+
                        verify(player,l,c-1,player.getShelf().getTile(l,c).getColor())+
                        verify(player,l-1,c,player.getShelf().getTile(l,c).getColor());
            }else {return 0;}
        }
        else {return 0;}
    }
}
