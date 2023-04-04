//TODO la classe funziona nel dare i punti quando è giusto, verificare i casi limite

package GC_11.model.common;

import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.exceptions.NotEnoughFreeSpacesException;
import GC_11.model.Player;
import GC_11.model.Tile;
import GC_11.model.TileColor;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalCard5Test {

    CommonGoalCard5 carta = new CommonGoalCard5();
    Player player = new Player();
    Tile blue = new Tile(TileColor.BLUE);
    Tile cyan = new Tile(TileColor.CYAN);
    Tile green = new Tile(TileColor.GREEN);
    Tile orange = new Tile(TileColor.ORANGE);
    Tile purple = new Tile(TileColor.PURPLE);
    Tile white = new Tile(TileColor.WHITE);
    List<Tile> blues = Arrays.asList(blue,blue,blue);
    List<Tile> cyans = Arrays.asList(cyan,cyan,cyan);
    List<Tile> greens = Arrays.asList(green,green,green);
    List<Tile> oranges = Arrays.asList(orange,orange,orange);
    List<Tile> purples = Arrays.asList(purple,purple,purple);
    List<Tile> whites = Arrays.asList(white,white,white);


    @Test
    void checkTest() throws ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {


        player.getShelf().addTiles(blues,0);
        player.getShelf().addTiles(blues,0);
        player.getShelf().addTiles(cyans,1);
        player.getShelf().addTiles(cyans,1);
        player.getShelf().addTiles(greens,2);
        player.getShelf().addTiles(greens,2);
        player.getShelf().addTiles(oranges,3);
        player.getShelf().addTiles(oranges,3);
        player.getShelf().addTiles(blues,4);
        player.getShelf().addTiles(oranges,4);

        System.out.println(player.getPoints());
        assertEquals(player.getPoints(),0);
        carta.check(player);
        assertEquals(player.getPoints(),8);
        System.out.println(player.getPoints());

    }

    @Test
    void checkTestAllVoid() throws ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {

        /*
        player.getShelf().addTiles(blues,0);
        player.getShelf().addTiles(blues,0);
        player.getShelf().addTiles(cyans,1);
        player.getShelf().addTiles(cyans,1);
        player.getShelf().addTiles(greens,2);
        player.getShelf().addTiles(greens,2);
        player.getShelf().addTiles(oranges,3);
        player.getShelf().addTiles(oranges,3);
        player.getShelf().addTiles(blues,4);
        player.getShelf().addTiles(blues,4);
        */

        System.out.println("inizio test allVoid");
        System.out.println(player.getPoints());
        assertEquals(player.getPoints(),0);
        carta.check(player);
        assertEquals(player.getPoints(),0);
        System.out.println(player.getPoints());
        System.out.println("fine test allvoid");
    }

    @Test
    void checkTestWrong() throws ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {


        player.getShelf().addTiles(blues,0);
        player.getShelf().addTiles(blues,0);
        player.getShelf().addTiles(cyans,1);
        player.getShelf().addTiles(cyans,1);
        player.getShelf().addTiles(greens,2);
        player.getShelf().addTiles(greens,2);
        player.getShelf().addTiles(oranges,3);
        player.getShelf().addTiles(oranges,3);
        player.getShelf().addTiles(whites,4);
        player.getShelf().addTiles(whites,4);


        System.out.println("inizio test wrong");
        System.out.println(player.getPoints());
        assertEquals(player.getPoints(),0);
        carta.check(player);
        assertEquals(player.getPoints(),0);
        System.out.println(player.getPoints());
        System.out.println("fine test wrong");
    }
}