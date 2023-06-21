
package GC_11.model.common;

import java.util.Arrays;
import java.util.List;

import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.exceptions.NotEnoughFreeSpacesException;
import GC_11.model.Player;
import GC_11.model.Tile;
import GC_11.model.TileColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommonGoalCard1Test {

    CommonGoalCard1 carta = new CommonGoalCard1();
    Player player = new Player();
    Tile blue = new Tile(TileColor.BLUE);
    Tile cyan = new Tile(TileColor.CYAN);
    Tile green = new Tile(TileColor.GREEN);
    Tile yellow = new Tile(TileColor.YELLOW);
    Tile purple = new Tile(TileColor.PURPLE);
    Tile white = new Tile(TileColor.WHITE);
    List<Tile> blues = Arrays.asList(blue,blue,blue);
    List<Tile> cyans = Arrays.asList(cyan,cyan,cyan);
    List<Tile> greens = Arrays.asList(green,green,green);
    List<Tile> yellows = Arrays.asList(yellow,yellow,yellow);
    List<Tile> purples = Arrays.asList(purple,purple,purple);
    List<Tile> whites = Arrays.asList(white,white,white);


    @Test
    void checkTestGood() throws ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {


        player.insertTiles(blues,0);
        player.getShelf().addTiles(blues,0);
        player.getShelf().addTiles(cyans,1);
        player.getShelf().addTiles(cyans,1);
        player.getShelf().addTiles(greens,2);
        player.getShelf().addTiles(greens,2);
        player.getShelf().addTiles(yellows,3);
        player.getShelf().addTiles(yellows,3);
        player.getShelf().addTiles(purples,4);
        player.getShelf().addTiles(whites,4);

        System.out.println("inizio test good");
        System.out.println(player.getPoints());
        assertEquals(player.getPoints(),0);
        carta.check(player);
        assertEquals(player.getPoints(),8);
        System.out.println(player.getPoints());
        System.out.println("fine test good");

    }

    @Test
    void checkTestAllVoid() throws ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {


        // player.getShelf().addTiles(blues,0);
        // player.getShelf().addTiles(blues,0);
        // player.getShelf().addTiles(cyans,1);
        // player.getShelf().addTiles(cyans,1);
        // player.getShelf().addTiles(greens,2);
        // player.getShelf().addTiles(greens,2);
        //  player.getShelf().addTiles(yellows,3);
        //  player.getShelf().addTiles(yellows,3);
        //  player.getShelf().addTiles(whites,4);
        //  player.getShelf().addTiles(whites,4);


        System.out.println("inizio test all void");
        System.out.println(player.getPoints());
        assertEquals(player.getPoints(),0);
        carta.check(player);
        assertEquals(player.getPoints(),0);
        System.out.println(player.getPoints());
        System.out.println("fine test all void");
    }

    @Test
    void checkTestSameColor() throws ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {


        player.getShelf().addTiles(blues,0);
        player.getShelf().addTiles(blues,0);
        player.getShelf().addTiles(blues,1);
        player.getShelf().addTiles(blues,1);
        player.getShelf().addTiles(blues,2);
        player.getShelf().addTiles(blues,2);
        player.getShelf().addTiles(blues,3);
        player.getShelf().addTiles(blues,3);
        player.getShelf().addTiles(blues,4);
        player.getShelf().addTiles(blues,4);


        System.out.println("inizio test same color");
        System.out.println(player.getPoints());
        assertEquals(player.getPoints(),0);
        carta.check(player);
        assertEquals(player.getPoints(),0);
        System.out.println(player.getPoints());
        System.out.println("fine test all same color");
    }



}