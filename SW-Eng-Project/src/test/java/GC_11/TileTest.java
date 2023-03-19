package GC_11;

import GC_11.model.Tile;
import GC_11.model.TileColor;
import org.junit.jupiter.api.Test;
import junit.framework.Assert;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TileTest {

    Tile t1 = new Tile(TileColor.CYAN);
    Tile t2 = new Tile(TileColor.CYAN);
    Tile t3 = new Tile(TileColor.BLUE);

    @Test
    public void checkEquals(){
        assertEquals(true, t1.equals(t2));
    }

    @Test
    public void checkNotEquals()
    {
        assertEquals(false, t1.equals(t3));

    }
}
