package GC_11.model.common;

import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.model.Player;
import GC_11.model.TileColor;

public class CommonGoalCard7 extends CommonGoalCard{

    private final String text = "Two groups each containing 4 tiles of the same type in a 2x2 square. " +
            "The tiles of one square can be different from those of the other square.";

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
        int counterForSquare=0;
        for(int l = 0; l<5; l++){
            for(int c=0; c<4; c++) {
                if(!getControlMatrix(l,c)) {
                    setControlMatrixTrue(l,c);
                    counterTiles = 1 + verify(player, l, c+1, player.getShelf().getTile(l, c).getColor())+
                            verify(player,l+1,c,player.getShelf().getTile(l,c).getColor());
                    if(counterTiles==4){
                        counterForSquare = 1+ verifySquare(player, l, c+1, player.getShelf().getTile(l, c).getColor())+
                                verifySquare(player,l+1,c,player.getShelf().getTile(l,c).getColor())+
                                verifySquare(player,l+1,c+1,player.getShelf().getTile(l,c).getColor());
                        if (counterForSquare==4) {
                            counterGroups++;
                        }

                    }
                }
            }
        }
        if(counterGroups>=2){
            givePoints(player);
        }

    }

    public int verifySquare (Player player, int l, int c, TileColor color) throws ColumnIndexOutOfBoundsException {

        if(l>5 || c>4){
            return 0;
        }
        else if(!getControlMatrix(l,c)){
            if(player.getShelf().getTile(l,c).getColor()==color){
                return  1;
            }else {return 0;}
        }
        else {return 0;}
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
