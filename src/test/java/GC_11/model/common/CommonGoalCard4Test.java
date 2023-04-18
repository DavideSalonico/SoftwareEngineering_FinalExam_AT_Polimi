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

class CommonGoalCard4Test {

    CommonGoalCard4 carta = new CommonGoalCard4();
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
    void checkTest2ColonneVuote() throws ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {


        player.insertTiles(blues,0);
        player.insertTiles(blues,0);
        player.insertTiles(cyans,1);
        player.insertTiles(cyans,1);
        player.insertTiles(greens,2);
        player.insertTiles(greens,2);
      //  player.insertTiles(oranges,3);
      //  player.insertTiles(oranges,3);
      //  player.insertTiles(whites,4);
      //  player.insertTiles(whites,4);


        System.out.println("inizio test 2 Colonne vuote");
        System.out.println(player.getPoints());
        assertEquals(player.getPoints(),0);
        carta.check(player);
        assertEquals(player.getPoints(),0);
        System.out.println(player.getPoints());
        System.out.println("fine test 2 colonne vuote");
    }

    @Test
    void checkTestAllVoid() throws ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {


       // player.insertTiles(blues,0);
       // player.insertTiles(blues,0);
       // player.insertTiles(cyans,1);
       // player.insertTiles(cyans,1);
       // player.insertTiles(greens,2);
       // player.insertTiles(greens,2);
        //  player.insertTiles(oranges,3);
        //  player.insertTiles(oranges,3);
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
    void checkTestGood() throws ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {


        player.getShelf().addTiles(blues,0);
        player.getShelf().addTiles(blues,0);
        player.getShelf().addTiles(cyans,1);
        player.getShelf().addTiles(cyans,1);
        player.getShelf().addTiles(greens,2);
        player.getShelf().addTiles(greens,2);
        player.getShelf().addTiles(blues,3);
        player.getShelf().addTiles(blues,3);
        player.getShelf().addTiles(greens,4);
        player.getShelf().addTiles(greens,4);


        System.out.println("inizio test good");
        System.out.println(player.getPoints());
        assertEquals(player.getPoints(),0);
        carta.check(player);
        assertEquals(player.getPoints(),8);
        System.out.println(player.getPoints());
        System.out.println("fine test good");
    }

    @Test
    void checkTestOnly3Line() throws ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {


        player.getShelf().addTiles(blues,0);
      //  player.getShelf().addTiles(blues,0);
        player.getShelf().addTiles(cyans,1);
       // player.getShelf().addTiles(cyans,1);
        player.getShelf().addTiles(greens,2);
       // player.getShelf().addTiles(greens,2);
        player.getShelf().addTiles(blues,3);
      //  player.getShelf().addTiles(blues,3);
        player.getShelf().addTiles(greens,4);
      //  player.getShelf().addTiles(greens,4);


        System.out.println("inizio test only3line");
        System.out.println(player.getPoints());
        assertEquals(player.getPoints(),0);
        carta.check(player);
        assertEquals(player.getPoints(),0);
        System.out.println(player.getPoints());
        System.out.println("fine test inly3line");
    }

    @Test
    void checkTestExactly4Line() throws ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {


        player.getShelf().addTiles(blues2,0);
        player.getShelf().addTiles(blues2,0);
        player.getShelf().addTiles(cyans2,1);
        player.getShelf().addTiles(cyans2,1);
        player.getShelf().addTiles(greens2,2);
        player.getShelf().addTiles(greens2,2);
        player.getShelf().addTiles(blues2,3);
        player.getShelf().addTiles(blues2,3);
        player.getShelf().addTiles(greens2,4);
        player.getShelf().addTiles(greens2,4);


        System.out.println("inizio test exactly4line");
        System.out.println(player.getPoints());
        assertEquals(player.getPoints(),0);
        carta.check(player);
        assertEquals(player.getPoints(),8);
        System.out.println(player.getPoints());
        System.out.println("fine test exactly4line");
    }

    @Test
    void checkTest5Color() throws ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {


        player.getShelf().addTiles(blues,0);
        player.getShelf().addTiles(blues,0);
        player.getShelf().addTiles(cyans,1);
        player.getShelf().addTiles(cyans,1);
        player.getShelf().addTiles(greens,2);
        player.getShelf().addTiles(greens,2);
        player.getShelf().addTiles(purples,3);
        player.getShelf().addTiles(purples,3);
        player.getShelf().addTiles(whites,4);
        player.getShelf().addTiles(whites,4);


        System.out.println("inizio test 5 colors");
        System.out.println(player.getPoints());
        assertEquals(player.getPoints(),0);
        carta.check(player);
        assertEquals(player.getPoints(),0);
        System.out.println(player.getPoints());
        System.out.println("fine test 5 colors");
    }

}