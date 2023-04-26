package GC_11;

import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.exceptions.NotEnoughFreeSpacesException;
import GC_11.model.Shelf;
import GC_11.model.Tile;
import GC_11.model.TileColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.*;

public class ShelfTest {

    private Shelf shelf;

    @BeforeEach
    public void createShelf(){
        shelf = new Shelf();
    }

    @DisplayName("startEmpty test")
    @Test
    public void startEmpty() throws ColumnIndexOutOfBoundsException {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(shelf.getTile(i, j).getColor(), TileColor.EMPTY);
            }
        }
    }

    @DisplayName("NotEnoughFreeSpacesException test")
    @Test
    public void throwsNotEnoughFreeSpacesException() {
        List<Tile> tiles = new ArrayList<Tile>();
        for (int i = 0; i < 3; i++) {
            tiles.add(new Tile());
        }

        for (int i = 0; i < 2; i++) {
            try {
                shelf.addTiles(tiles, 0);
            } catch (NotEnoughFreeSpacesException e) {
                throw new RuntimeException(e);
            } catch (ColumnIndexOutOfBoundsException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            shelf.addTiles(tiles, 0);
            fail("addTiles didn't throw Exception when it was supposed to");
        } catch (NotEnoughFreeSpacesException expectedException) {
        } catch (ColumnIndexOutOfBoundsException e){
            fail("addTiles threw up thw wrong exception");
        }
    }

    @DisplayName("columnIndexOutOfBoundException test")
    @Test
    public void throwsColumnOutOfIndexException(){
        List<Tile> tiles = new ArrayList<Tile>();
        tiles.add(new Tile());

        try{
            shelf.addTiles(tiles, -1);
            fail("addTiles didn't throw Exception when it was supposed to");
        } catch (NotEnoughFreeSpacesException e) {
            fail("addTiles threw up thw wrong exception");
        } catch (ColumnIndexOutOfBoundsException e) {
        }
    }

    @DisplayName("isFull test")
    @Test
    public void fillAll() throws NotEnoughFreeSpacesException, ColumnIndexOutOfBoundsException {
        for(int i=0; i < 6; i++){
            for(int j=0; j < 5; j++){
                System.out.println("i = " + i + " y = " + j);
                List<Tile> tmpTile = new ArrayList<Tile>();
                tmpTile.add(new Tile());
                shelf.addTiles(tmpTile, j);
                shelf.print();
                System.out.println();
                if(!(i == 5 && j == 4)) assertFalse(shelf.isFull());
            }
        }
        assertTrue(shelf.isFull());
    }
}