package GC_11;

import GC_11.model.Tile;
import GC_11.model.TileColor;
import org.junit.jupiter.api.Test;
import junit.framework.Assert;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TileTest {

    Tile t1 = new Tile(TileColor.CYAN, 0);
    Tile t2 = new Tile(TileColor.CYAN, 0);
    Tile t3 = new Tile(TileColor.BLUE, 0);

    @Test
    public void checkEquals(){
        assertEquals(true, t1.equals(t2));
    }

    @Test
    public void checkNotEquals()
    {
        assertEquals(false, t1.equals(t3));
    }



    @Test
    public void checkGetColors(){
        for(TileColor t : TileColor.getColors()){
            assertEquals(true ,t!=TileColor.PROHIBITED && t!= TileColor.EMPTY);
        }
    }


    @Test
    public void checkCreateOnlyColor(){
        for(int i=0; i<100;i++){
            Tile t = new Tile(TileColor.randomColor(), 0);
            assertEquals(true,t.getColor()!=TileColor.EMPTY && t.getColor()!=TileColor.PROHIBITED);
        }
    }
}
