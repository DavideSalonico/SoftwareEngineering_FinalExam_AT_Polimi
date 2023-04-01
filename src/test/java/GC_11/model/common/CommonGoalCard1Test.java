
package GC_11.model.common;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import GC_11.exceptions.columnIndexOutOfBoundsException;
import GC_11.exceptions.notEnoughFreeSpacesException;
import GC_11.model.Player;
import GC_11.model.Tile;
import GC_11.model.TileColor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalCard1Test {

    CommonGoalCard1 carta = new CommonGoalCard1();
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
    void checkTest() throws columnIndexOutOfBoundsException, notEnoughFreeSpacesException {


        player.getShelf().addTiles(blues,0);
        player.getShelf().addTiles(blues,0);
        player.getShelf().addTiles(cyans,1);
        player.getShelf().addTiles(cyans,1);
        player.getShelf().addTiles(greens,2);
        player.getShelf().addTiles(greens,2);
        player.getShelf().addTiles(oranges,3);
        player.getShelf().addTiles(oranges,3);
        player.getShelf().addTiles(purples,4);
        player.getShelf().addTiles(whites,4);

        System.out.println(player.getPoints());
        carta.check(player);
        System.out.println(player.getPoints());

    }

    @Test
    void verifyTest() {
    }
}