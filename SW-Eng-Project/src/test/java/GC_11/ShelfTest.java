package GC_11;

import GC_11.exceptions.columnIndexOutOfBoundsException;
import GC_11.exceptions.notEnoughFreeSpacesException;
import GC_11.model.Shelf;
import GC_11.model.Tile;
import GC_11.model.TileColor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.*;

public class ShelfTest {

    @Test
    public void startEmpty() throws columnIndexOutOfBoundsException {
        Shelf shelf = new Shelf();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(shelf.getTile(i, j), TileColor.EMPTY);
            }
        }
    }

    @Test
    public void throwsNotEnoughFreeSpacesException() {
        Shelf shelf = new Shelf();
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
            fail( "addTiles didn't throw Exception when I expected it to" );
        } catch (notEnoughFreeSpacesException expectedException) {
        } catch (columnIndexOutOfBoundsException e){};
    }
}