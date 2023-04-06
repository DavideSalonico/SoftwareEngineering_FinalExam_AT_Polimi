package GC_11;

import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.exceptions.NotEnoughFreeSpacesException;
import GC_11.model.Player;
import GC_11.model.Tile;
import GC_11.model.TileColor;
import GC_11.model.common.CommonGoalCard2;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {
    //TODO: To be implemented by Rei

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
    void calculateAdjacencyPointTestGood() throws ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {


        player.getShelf().addTiles(blues2,0);
        player.getShelf().addTiles(whites,0);
        player.getShelf().addTiles(blues2,1);
        player.getShelf().addTiles(whites,1);
        player.getShelf().addTiles(oranges2,3);
        player.getShelf().addTiles(oranges2,4);




        System.out.println("inizio test good");
        System.out.println(player.getPointsAdjacency());
        assertEquals(player.getPointsAdjacency(),0);
        player.calculateAdjacencyPoint();
        assertEquals(player.getPointsAdjacency(),14);
        System.out.println(player.getPointsAdjacency());
        System.out.println("fine test good");

    }


}
