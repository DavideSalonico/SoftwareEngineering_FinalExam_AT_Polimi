package GC_11;

import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.exceptions.NotEnoughFreeSpacesException;
import GC_11.model.Player;
import GC_11.model.Tile;
import GC_11.model.TileColor;
import GC_11.model.common.CommonGoalCard2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {


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
    List<Tile> purples = Arrays.asList(purple,purple,purple);
    List<Tile> whites = Arrays.asList(white,white,white);

    List<Tile> blues2 = Arrays.asList(blue,blue);
    List<Tile> cyans2 = Arrays.asList(cyan,cyan);
    List<Tile> greens2 = Arrays.asList(green,green);
    List<Tile> purples2 = Arrays.asList(purple,purple);
    List<Tile> whites2 = Arrays.asList(white,white);

    @Test
    void calculateAdjacencyPointTestGood() throws ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {


        player.getShelf().addTiles(blues2,0);
        player.getShelf().addTiles(whites,0);
        player.getShelf().addTiles(blues2,1);
        player.getShelf().addTiles(whites,1);




        System.out.println("inizio test good");
        System.out.println(player.getPointsAdjacency());
        assertEquals(player.getPointsAdjacency(),0);
        player.calculateAndGiveAdjacencyPoint();
        assertEquals(player.getPointsAdjacency(),14);
        System.out.println(player.getPointsAdjacency());
        System.out.println("fine test good");

    }
}
