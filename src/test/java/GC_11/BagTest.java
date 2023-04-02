
package GC_11;

import GC_11.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static junit.framework.Assert.*;

//import static org.junit.jupiter.api.Assertions.assertEquals;

public class BagTest {
    //TODO: To be implemented by Mattia

    @Test
    public void rightNumberOfEachTile(){
        Bag bag = new Bag();
        ArrayList<Tile> tiles = (ArrayList<Tile>) bag.drawOutTiles();
        for (TileColor tc : TileColor.values()){
            if(tc != TileColor.PROHIBITED && tc!= TileColor.EMPTY)
                assertEquals(22,countTiles(tiles,tc));
        }
    }



    @Test
    public void rightNumberOfTiles(){
        Bag bag = new Bag();
        assertEquals(132, bag.drawOutTiles().size());
    }


    @Test
    public void checkRemove(){
        Bag bag = new Bag();
        Tile t = new Tile();
        int tilesNumberBefore = countTiles(bag.drawOutTiles(),t.getColor());
        bag.removeTile(t);
        assertEquals(tilesNumberBefore-1,countTiles(bag.drawOutTiles(),t.getColor()));
    }

    @Test
    public void checkIsBagEmpty(){
        Bag bag = new Bag();
        assertEquals(bag.countNumOfTiles()==0,bag.isBagEmpty());

        bag.removeListOfTile(bag.drawOutTiles());
        assertEquals(bag.countNumOfTiles()==0,bag.isBagEmpty());

    }
    @Test

    public void checkAddAllTiles(){
        Bag bag = new Bag();
        bag.removeListOfTile(bag.drawOutTiles());

        List<Tile> tileList = new ArrayList<Tile>();
        for (int i=0; i<20;i++){
            tileList.add(new Tile(TileColor.randomColor()));
        }
        for(Tile t : tileList){
            assertEquals(false,bag.drawOutTiles().contains(t));
        }

        bag.insertAllTile(tileList);
        for(Tile t : tileList){
            assertEquals(true,bag.drawOutTiles().contains(t));
        }
    }

    private int countTiles(List<Tile> list,TileColor color)
    {
        int count = 0;
        for(Tile t : list){
            if(t.getColor() == color){
                count++;
            }
        }
        return count;
    }



}
