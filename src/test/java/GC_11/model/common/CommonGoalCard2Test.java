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
    void checkTestWrong() throws columnIndexOutOfBoundsException, notEnoughFreeSpacesException {


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
        carta.check(player);
        System.out.println(player.getPoints());
        System.out.println("fine test wrong");
    }

    @Test
    void checkTestAllVoid() throws columnIndexOutOfBoundsException, notEnoughFreeSpacesException {

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
        carta.check(player);
        System.out.println(player.getPoints());
        System.out.println("fine test allvoid");
    }

    @Test
    void checkTestDiagonaleSinistraAlta() throws columnIndexOutOfBoundsException, notEnoughFreeSpacesException {

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
                carta.check(player);
                System.out.println(player.getPoints());
                System.out.println("fine test diagonale alta sinistra");

    }

    @Test
    void checkTestDiagonaleSinistraBassa() throws columnIndexOutOfBoundsException, notEnoughFreeSpacesException {

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
        carta.check(player);
        System.out.println(player.getPoints());
        System.out.println("fine test diagonale bassa sinistra");

    }
}