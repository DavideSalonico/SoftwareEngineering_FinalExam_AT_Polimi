package GC_11.model.common;

import GC_11.exceptions.columnIndexOutOfBoundsException;
import GC_11.exceptions.notEnoughFreeSpacesException;
import GC_11.model.Player;
import GC_11.model.Tile;
import GC_11.model.TileColor;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalCard6Test {

    CommonGoalCard6 carta = new CommonGoalCard6();
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

    List<Tile> blues2 = Arrays.asList(blue,blue);
    List<Tile> cyans2 = Arrays.asList(cyan,cyan);
    List<Tile> greens2 = Arrays.asList(green,green);
    List<Tile> oranges2 = Arrays.asList(orange,orange);
    List<Tile> purples2 = Arrays.asList(purple,purple);
    List<Tile> whites2 = Arrays.asList(white,white);

    List<Tile> blues1 = Arrays.asList(blue);
    List<Tile> cyans1 = Arrays.asList(cyan);
    List<Tile> greens1 = Arrays.asList(green);
    List<Tile> oranges1 = Arrays.asList(orange);
    List<Tile> purples1 = Arrays.asList(purple);
    List<Tile> whites1 = Arrays.asList(white);

    @Test
    void checkTestAllVoid() throws columnIndexOutOfBoundsException, notEnoughFreeSpacesException {


        // player.getShelf().addTiles(blues,0);
        // player.getShelf().addTiles(blues,0);
        // player.getShelf().addTiles(cyans,1);
        // player.getShelf().addTiles(cyans,1);
        // player.getShelf().addTiles(greens,2);
        // player.getShelf().addTiles(greens,2);
        //  player.getShelf().addTiles(oranges,3);
        //  player.getShelf().addTiles(oranges,3);
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
    void checkTestGood() throws columnIndexOutOfBoundsException, notEnoughFreeSpacesException {


        player.getShelf().addTiles(blues1,0);
        player.getShelf().addTiles(cyans1,0);
        player.getShelf().addTiles(purples1,0);
        player.getShelf().addTiles(greens1,0);
        player.getShelf().addTiles(whites1,0);
        player.getShelf().addTiles(oranges1,0);
        player.getShelf().addTiles(blues1,1);
        player.getShelf().addTiles(oranges1,1);
        player.getShelf().addTiles(whites1,1);
        player.getShelf().addTiles(greens1,1);
        player.getShelf().addTiles(purples1,1);
        player.getShelf().addTiles(cyans1,1);
        player.getShelf().addTiles(greens,2);
        player.getShelf().addTiles(greens,2);
        player.getShelf().addTiles(blues,3);
        player.getShelf().addTiles(blues,3);
        player.getShelf().addTiles(blues1,4);
        player.getShelf().addTiles(cyans1,4);
        player.getShelf().addTiles(purples1,4);
        player.getShelf().addTiles(greens1,4);
        player.getShelf().addTiles(whites1,4);
        player.getShelf().addTiles(oranges1,4);


        System.out.println("inizio test good");
        System.out.println(player.getPoints());
        assertEquals(player.getPoints(),0);
        carta.check(player);
        assertEquals(player.getPoints(),8);
        System.out.println(player.getPoints());
        System.out.println("fine test good");
    }

    @Test
    void checkTestFewColors() throws columnIndexOutOfBoundsException, notEnoughFreeSpacesException {


        player.getShelf().addTiles(blues1,0);
        player.getShelf().addTiles(cyans1,0);
        player.getShelf().addTiles(purples1,0);
        player.getShelf().addTiles(greens,0);
        player.getShelf().addTiles(blues1,1);
        player.getShelf().addTiles(oranges1,1);
        player.getShelf().addTiles(whites1,1);
        player.getShelf().addTiles(greens1,1);
        player.getShelf().addTiles(purples1,1);
        player.getShelf().addTiles(cyans1,1);
        player.getShelf().addTiles(greens,2);
        player.getShelf().addTiles(greens,2);
        player.getShelf().addTiles(blues,3);
        player.getShelf().addTiles(blues,3);
        player.getShelf().addTiles(greens,4);
        player.getShelf().addTiles(greens,4);


        System.out.println("inizio test few colors");
        System.out.println(player.getPoints());
        assertEquals(player.getPoints(),0);
        carta.check(player);
        assertEquals(player.getPoints(),0);
        System.out.println(player.getPoints());
        System.out.println("fine test few colors");
    }


}