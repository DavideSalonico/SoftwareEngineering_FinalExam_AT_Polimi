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

class CommonGoalCard2Test {

    CommonGoalCard2 carta = new CommonGoalCard2();
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

    @Test
    void checkTestWrong() throws ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {


        player.insertTiles(blues,0);
        player.insertTiles(blues,0);
        player.insertTiles(cyans,1);
        player.insertTiles(cyans,1);
        player.insertTiles(greens,2);
        player.insertTiles(greens,2);
        player.insertTiles(oranges,3);
        player.insertTiles(oranges,3);
        player.insertTiles(whites,4);
        player.insertTiles(whites,4);


        System.out.println("inizio test wrong");
        System.out.println(player.getPoints());
        assertEquals(player.getPoints(),0);
        carta.check(player);
        assertEquals(player.getPoints(),0);
        System.out.println(player.getPoints());
        System.out.println("fine test wrong");
    }

    @Test
    void checkTestAllVoid() throws ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {

        /*
        player.insertTiles(blues,0);
        player.insertTiles(blues,0);
        player.insertTiles(cyans,1);
        player.insertTiles(cyans,1);
        player.insertTiles(greens,2);
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
    void checkTestDiagonaleSinistraAlta() throws ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {

        player.getShelf().addTiles(blues,0);
        player.getShelf().addTiles(oranges,0);
        player.getShelf().addTiles(cyans,1);
        player.getShelf().addTiles(oranges,1);
        player.getShelf().addTiles(greens,2);
        player.getShelf().addTiles(oranges,2);
        player.getShelf().addTiles(oranges,3);
        player.getShelf().addTiles(oranges,3);
        player.getShelf().addTiles(oranges,4);
        player.getShelf().addTiles(blues,4);


        System.out.println("inizio test diagomale alta sinistra");
                System.out.println(player.getPoints());
                assertEquals(player.getPoints(),0);
                carta.check(player);
                assertEquals(player.getPoints(),8);
                System.out.println(player.getPoints());
                System.out.println("fine test diagonale alta sinistra");

    }

    @Test
    void checkTestDiagonaleSinistraBassa() throws ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {

        player.getShelf().addTiles(blues,0);
        player.getShelf().addTiles(oranges,0);
        player.getShelf().addTiles(cyans,1);
        player.getShelf().addTiles(oranges,1);
        player.getShelf().addTiles(oranges,2);
        player.getShelf().addTiles(greens,2);
        player.getShelf().addTiles(oranges,3);
        player.getShelf().addTiles(oranges,3);
        player.getShelf().addTiles(oranges,4);
        player.getShelf().addTiles(blues,4);


        System.out.println("inizio test diagomale bassa sinistra");
        System.out.println(player.getPoints());
        assertEquals(player.getPoints(),0);
        carta.check(player);
        assertEquals(player.getPoints(),8);
        System.out.println(player.getPoints());
        System.out.println("fine test diagonale bassa sinistra");

    }

    @Test
    void checkTestDiagonaleDestraAlta() throws ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {

        player.getShelf().addTiles(blues,0);
        player.getShelf().addTiles(oranges,0);
        player.getShelf().addTiles(blues,1);
        player.getShelf().addTiles(oranges,1);
        player.getShelf().addTiles(purples,2);
        player.getShelf().addTiles(blues,2);
        player.getShelf().addTiles(oranges,3);
        player.getShelf().addTiles(blues,3);
        player.getShelf().addTiles(oranges,4);
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
    void checkTestDiagonaleDestraBassa() throws ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {

        player.getShelf().addTiles(blues,0);
        player.getShelf().addTiles(oranges,0);
        player.getShelf().addTiles(blues,1);
        player.getShelf().addTiles(oranges,1);
        player.getShelf().addTiles(blues,2);
        player.getShelf().addTiles(purples,2);
        player.getShelf().addTiles(oranges,3);
        player.getShelf().addTiles(blues,3);
        player.getShelf().addTiles(oranges,4);
        player.getShelf().addTiles(blues,4);


        System.out.println("inizio test diagomale bassa destra");
        System.out.println(player.getPoints());
        assertEquals(player.getPoints(),0);
        carta.check(player);
        assertEquals(player.getPoints(),8);
        System.out.println(player.getPoints());
        System.out.println("fine test diagonale bassa destra");

    }


}

