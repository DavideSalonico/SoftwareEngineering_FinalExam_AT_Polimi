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

class CommonGoalCard12Test {

    CommonGoalCard11 carta = new CommonGoalCard11();
    Player player = new Player();
    Tile blue = new Tile(TileColor.BLUE, 0);
    Tile cyan = new Tile(TileColor.CYAN, 0);
    Tile green = new Tile(TileColor.GREEN, 0);
    Tile yellow = new Tile(TileColor.YELLOW, 0);
    Tile purple = new Tile(TileColor.PURPLE, 0);
    Tile white = new Tile(TileColor.WHITE, 0);

    List<Tile> blues = Arrays.asList(blue,blue,blue);
    List<Tile> cyans = Arrays.asList(cyan,cyan,cyan);
    List<Tile> greens = Arrays.asList(green,green,green);
    List<Tile> yellows = Arrays.asList(yellow,yellow,yellow);
    List<Tile> purples = Arrays.asList(purple,purple,purple);
    List<Tile> whites = Arrays.asList(white,white,white);

    List<Tile> blues2 = Arrays.asList(blue,blue);
    List<Tile> cyans2 = Arrays.asList(cyan,cyan);
    List<Tile> greens2 = Arrays.asList(green,green);
    List<Tile> yellows2 = Arrays.asList(yellow,yellow);
    List<Tile> purples2 = Arrays.asList(purple,purple);
    List<Tile> whites2 = Arrays.asList(white,white);

    List<Tile> blues1 = Arrays.asList(blue);
    List<Tile> cyans1 = Arrays.asList(cyan);
    List<Tile> greens1 = Arrays.asList(green);
    List<Tile> yellows1 = Arrays.asList(yellow);
    List<Tile> purples1 = Arrays.asList(purple);
    List<Tile> whites1 = Arrays.asList(white);

    @Test
    void checkTestAllVoid() throws ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {


        // player.insertTiles(blues,0);
        // player.insertTiles(blues,0);
        // player.insertTiles(cyans,1);
        // player.insertTiles(cyans,1);
        // player.insertTiles(greens,2);
        // player.insertTiles(greens,2);
        //  player.insertTiles(yellows,3);
        //  player.insertTiles(yellows,3);
        //  player.insertTiles(whites,4);
        //  player.insertTiles(whites,4);


        System.out.println("inizio test all void");
        System.out.println(player.getPoints());
        assertEquals(player.getPoints(),0);
        carta.check(player);
        assertEquals(player.getPoints(),0);
        System.out.println(player.getPoints());
        System.out.println("fine test all void");
    }

    @Test
    void checkTestWrong() throws ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {


        player.insertTiles(blues,0);
        player.insertTiles(blues,0);
        player.insertTiles(cyans,1);
        player.insertTiles(cyans,1);
        player.insertTiles(greens,2);
        player.insertTiles(greens,2);
        player.insertTiles(yellows,3);
        player.insertTiles(yellows,3);
        player.insertTiles(whites,4);
        player.getShelf().addTiles(whites,4);


        System.out.println("inizio test wrong");
        System.out.println(player.getPoints());
        assertEquals(player.getPoints(),0);
        carta.check(player);
        assertEquals(player.getPoints(),0);
        System.out.println(player.getPoints());
        System.out.println("fine test wrong");
    }

    @Test
    void checkTestDiagonaleSinistraAlta() throws ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {

        player.getShelf().addTiles(blues,0);
        player.getShelf().addTiles(yellows,0);
        player.getShelf().addTiles(cyans,1);
        player.getShelf().addTiles(yellows2,1);
        player.getShelf().addTiles(greens,2);
        player.getShelf().addTiles(yellows1,2);
        player.getShelf().addTiles(yellows,3);
        player.getShelf().addTiles(yellows2,4);


        System.out.println("inizio test diagomale alta sinistra");
        System.out.println(player.getPoints());
        assertEquals(player.getPoints(),0);
        carta.check(player);
        assertEquals(player.getPoints(),8);
        System.out.println(player.getPoints());
        System.out.println("fine test diagonale alta sinistra");

    }

    @Test
    void checkTestDiagonaleDestraAlta() throws ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {

        player.getShelf().addTiles(blues2,0);
        player.getShelf().addTiles(blues,1);
        player.getShelf().addTiles(purples,2);
        player.getShelf().addTiles(blues2,2);
        player.getShelf().addTiles(yellows,3);
        player.getShelf().addTiles(blues2,3);
        player.getShelf().addTiles(yellows,4);
        player.getShelf().addTiles(blues,4);


        System.out.println("inizio test diagomale alta destra");
        System.out.println(player.getPoints());
        assertEquals(player.getPoints(),0);
        carta.check(player);
        assertEquals(player.getPoints(),8);
        System.out.println(player.getPoints());
        System.out.println("fine test diagonale alta destra");

    }

    @Test
    void checkTestDiagonaleSinistraBassa() throws ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {

        player.getShelf().addTiles(blues,0);
        player.getShelf().addTiles(yellows2,0);
        player.getShelf().addTiles(cyans,1);
        player.getShelf().addTiles(yellows1,1);
        player.getShelf().addTiles(yellows,2);
        player.getShelf().addTiles(yellows2,3);
        player.getShelf().addTiles(yellows1,4);


        System.out.println("inizio test diagomale bassa sinistra");
        System.out.println(player.getPoints());
        assertEquals(player.getPoints(),0);
        carta.check(player);
        assertEquals(player.getPoints(),8);
        System.out.println(player.getPoints());
        System.out.println("fine test diagonale bassa sinistra");

    }

    @Test
    void checkTestDiagonaleDestraBassa() throws ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {

        player.getShelf().addTiles(blues1,0);
        player.getShelf().addTiles(blues2,1);
        player.getShelf().addTiles(blues,2);
        player.getShelf().addTiles(yellows,3);
        player.getShelf().addTiles(blues2,3);
        player.getShelf().addTiles(yellows,4);
        player.getShelf().addTiles(blues2,4);


        System.out.println("inizio test diagomale bassa destra");
        System.out.println(player.getPoints());
        assertEquals(player.getPoints(),0);
        carta.check(player);
        assertEquals(player.getPoints(),8);
        System.out.println(player.getPoints());
        System.out.println("fine test diagonale bassa destra");

    }

}