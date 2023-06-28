
package GC_11;

import GC_11.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

//import static org.junit.jupiter.api.Assertions.assertEquals;

public class BagTest {

   Bag bag;
   ListenerVoid listener;
   //Board board = new Board();

    @BeforeEach
    public void setUp() {
        bag = new Bag();
        listener= new ListenerVoid();
        bag.setListener(listener);
    }

    @Test
    public void rightNumberOfEachTile(){
        Bag bag = new Bag();
        ArrayList<Tile> tiles = (ArrayList<Tile>) bag.drawOutTiles();
        for (TileColor tc : TileColor.values()){
            if(tc != TileColor.PROHIBITED && tc!= TileColor.EMPTY)
                Assertions.assertEquals(22, countTiles(tiles,tc));
        }
    }



    @Test
    public void rightNumberOfTiles(){
        Bag bag = new Bag();
        Assertions.assertEquals(132, bag.drawOutTiles().size());
    }


    @Test
    public void checkIsBagEmpty(){
        Assertions.assertEquals(bag.countNumOfTiles()==0,bag.isBagEmpty());

        bag.removeListOfTile(bag.drawOutTiles());
        Assertions.assertEquals(bag.countNumOfTiles()==0,bag.isBagEmpty());

    }
    @Test
/*
    public void checkAddAllTiles(){
        Bag bag = new Bag();
        bag.removeListOfTile(bag.drawOutTiles());

        List<Tile> tileList = new ArrayList<Tile>();
        for (int i=0; i<20;i++){
            tileList.add(new Tile(TileColor.randomColor()));
        }
        for(Tile t : tileList){
            Assertions.assertEquals(false,bag.drawOutTiles().contains(t));
        }

        bag.insertAllTile(tileList);
        for(Tile t : tileList){
            Assertions.assertEquals(true,bag.drawOutTiles().contains(t));
        }
    }

 */

    public int countTiles(List<Tile> list,TileColor color)
    {
        int count = 0;
        for(Tile t : list){
            if(t.getColor() == color){
                count++;
            }
        }
        return count;
    }

    //////////////////////////////////////////////
    @Test
    public void testBagInitialization() {
        // Check if the bag contains the correct number of tiles
        Assertions.assertEquals(132, bag.countNumOfTiles());
    }

    @Test
    public void testInsertTile() {
        Tile tile = new Tile(TileColor.BLUE, 1);
        bag.insertTile(tile);

        // Check if the tile is inserted into the bag
        Assertions.assertEquals(133, bag.countNumOfTiles());
    }

    @Test
    public void testInsertAllTile() {
        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(TileColor.BLUE, 1));
        tiles.add(new Tile(TileColor.PURPLE, 2));

        bag.insertAllTile(tiles);

        // Check if the tiles are inserted into the bag
        Assertions.assertEquals(134, bag.countNumOfTiles());
    }

    @Test
    public void testIsBagEmpty() {
        Assertions.assertFalse(bag.isBagEmpty());
        bag.removeListOfTile(bag.drawOutTiles());
        Assertions.assertTrue(bag.isBagEmpty());
    }

    @Test
    public void testRemoveTile() {
        Tile tile = new Tile(TileColor.BLUE, 1);
        bag.removeTile(tile);

        // Check if the tile is removed from the bag
        Assertions.assertEquals(131, bag.countNumOfTiles());
    }

    @Test
    public void testRemoveListOfTile() {
        List<Tile> tiles = new ArrayList<>();
        tiles.add(bag.getTiles().get(0));
        tiles.add(bag.getTiles().get(1));
        bag.setListener(listener);
        bag.removeListOfTile(tiles);

        // Check if the tiles are removed from the bag
        Assertions.assertEquals(130, bag.countNumOfTiles());
    }

    @Test
    public void testCountTiles() {
        Assertions.assertEquals(22, bag.countTiles(TileColor.BLUE));
        Assertions.assertEquals(22, bag.countTiles(TileColor.PURPLE));
        Assertions.assertEquals(22, bag.countTiles(TileColor.YELLOW));
        Assertions.assertEquals(22, bag.countTiles(TileColor.WHITE));
        Assertions.assertEquals(22, bag.countTiles(TileColor.CYAN));
        Assertions.assertEquals(22, bag.countTiles(TileColor.GREEN));
        Assertions.assertEquals(0, bag.countTiles(TileColor.EMPTY));
        Assertions.assertEquals(0, bag.countTiles(TileColor.PROHIBITED));
    }


    public void testCountNumOfTiles() {
        Assertions.assertEquals(132, bag.countNumOfTiles());
        bag.removeListOfTile(bag.drawOutTiles());
        Assertions.assertEquals(0, bag.countNumOfTiles());
    }

    @Test
    public void testDrawOutTiles() {
        List<Tile> tiles = bag.drawOutTiles();

        // Check if the correct number of tiles is drawn out
        Assertions.assertEquals(132, tiles.size());
    }

    @Test
    public void testUpdateBag() {
        List<Tile> tiles = new ArrayList<>();
        tiles.add(bag.getTiles().get(0));
        tiles.add(bag.getTiles().get(1));

        bag.updateBag(tiles);

        // Check if the bag contains only the specified tiles
        Assertions.assertEquals(2, bag.countNumOfTiles());
        Assertions.assertTrue(bag.getTiles().containsAll(tiles));
    }
}
