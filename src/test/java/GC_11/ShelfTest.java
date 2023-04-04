package GC_11;

import GC_11.exceptions.columnIndexOutOfBoundsException;
import GC_11.exceptions.notEnoughFreeSpacesException;
import GC_11.model.Shelf;
import GC_11.model.Tile;
import GC_11.model.TileColor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.*;

//TODO: To be implemented by Davide


public class ShelfTest {

    private Shelf shelf;

    @BeforeEach
    public void createShelf(){
        shelf = new Shelf();
    }

    @DisplayName("startEmpty test")
    @Test
    public void startEmpty() throws columnIndexOutOfBoundsException {
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
            } catch (notEnoughFreeSpacesException e) {
                throw new RuntimeException(e);
            } catch (columnIndexOutOfBoundsException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            shelf.addTiles(tiles, 0);
            fail("addTiles didn't throw Exception when it was supposed to");
        } catch (notEnoughFreeSpacesException expectedException) {
        } catch (columnIndexOutOfBoundsException e){
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
        } catch (notEnoughFreeSpacesException e) {
            fail("addTiles threw up thw wrong exception");
        } catch (columnIndexOutOfBoundsException e) {
        }
    }

    @DisplayName("isFull test")
    @Test
    public void fillAll() throws notEnoughFreeSpacesException, columnIndexOutOfBoundsException {
        for(int i=0; i < 5; i++){
            for(int j=0; j < 6; j++){
                List<Tile> tmpTile = new ArrayList<Tile>();
                tmpTile.add(new Tile());
                shelf.addTiles(tmpTile, i);
                assertFalse(shelf.isFull());
            }
        }
        assertTrue(shelf.isFull());
    }

    public void tryPrint(){
        shelf.print();
    }
}