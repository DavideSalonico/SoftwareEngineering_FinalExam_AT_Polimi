package GC_11;

import GC_11.controller.JsonReader;
import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.exceptions.NotEnoughFreeSpacesException;
import GC_11.model.*;
import GC_11.model.common.CommonGoalCard2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {


    Player player = new Player();
    private PersonalGoalCard personalGoalCard;

    @BeforeEach
    void setUp() {
        personalGoalCard = JsonReader.readPersonalGoalCard(2);

        player = new Player("Alice", personalGoalCard);
    }
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
    List<Tile> yellows = Arrays.asList(yellow,yellow,yellow);

    List<Tile> blues2 = Arrays.asList(blue,blue);
    List<Tile> cyans2 = Arrays.asList(cyan,cyan);
    List<Tile> greens2 = Arrays.asList(green,green);
    List<Tile> purples2 = Arrays.asList(purple,purple);
    List<Tile> whites2 = Arrays.asList(white,white);

    @Test
    void testConstructor() {
        assertEquals("Alice", player.getNickname());
        assertEquals(personalGoalCard, player.getPersonalGoal());
        assertEquals(0, player.getPointsCommonGoals());
        assertEquals(0, player.getPointsPersonalGoal());
        assertEquals(0, player.getPointsAdjacency());
        assertNotNull(player.getShelf());
    }

    @Test
    void testCopyConstructor() throws ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {
        player.addPointsCommonGoals(5);
        player.getShelf().addTiles(createTileList(), 0);

        Player copy = new Player(player);

        assertEquals(player.getNickname(), copy.getNickname());
        assertEquals(player.getPointsCommonGoals(), copy.getPointsCommonGoals());
        assertEquals(player.getPointsPersonalGoal(), copy.getPointsPersonalGoal());
        assertEquals(player.getPointsAdjacency(), copy.getPointsAdjacency());
        assertEquals(player.getPersonalGoal().getGoalList(), copy.getPersonalGoal().getGoalList());
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++)
                assertEquals(player.getShelf().getTile(i, j).getColor(), copy.getShelf().getTile(i, j).getColor());
        }
    }

    @Test
    void testSetListener() {
        PropertyChangeListener listener = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                // Do something
            }
        };

        player.setListener(listener);

        assertEquals(listener, player.listener);
    }

    @Test
    void testGetPoints() {

    }

    @Test
    void testAddPointsCommonGoals() {
        player.addPointsCommonGoals(5);
        assertEquals(5, player.getPointsCommonGoals());

        player.addPointsCommonGoals(3);
        assertEquals(8, player.getPointsCommonGoals());
    }

    @Test
    void testInsertTiles() {

    }

    @Test
    void testInsertTiles_ColumnIndexOutOfBoundsException() {
        List<Tile> tiles = createTileList();

        assertThrows(ColumnIndexOutOfBoundsException.class, () -> player.insertTiles(tiles, -1));
        assertThrows(ColumnIndexOutOfBoundsException.class, () -> player.insertTiles(tiles, 6));
    }

    @Test
    void testInsertTiles_NotEnoughFreeSpacesException() throws ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {
        List<Tile> tiles = createTileList();
        player.insertTiles(tiles,0);
        assertThrows(NotEnoughFreeSpacesException.class, () -> player.insertTiles(tiles, 0));
    }

    @Test
    void testUpdatesPointsPersonalGoal() throws ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {
        player.getPersonalGoal().print();
        player.insertTiles(whites,0);
        assertDoesNotThrow(player::updatesPointsPersonalGoal);
        assertEquals(1, player.getPointsPersonalGoal());
        player.insertTiles(blues,0);
        assertDoesNotThrow(player::updatesPointsPersonalGoal);
        assertEquals(2, player.getPointsPersonalGoal());
        player.insertTiles(greens,1);
        assertDoesNotThrow(player::updatesPointsPersonalGoal);
        assertEquals(4, player.getPointsPersonalGoal());
        player.insertTiles(cyans,4);
        assertDoesNotThrow(player::updatesPointsPersonalGoal);
        assertEquals(6, player.getPointsPersonalGoal());
        player.insertTiles(purples,2);
        player.insertTiles(purples2,2);
        assertDoesNotThrow(player::updatesPointsPersonalGoal);
        assertEquals(9, player.getPointsPersonalGoal());
        player.insertTiles(yellows,3);
        player.insertTiles(yellows,3);
        assertDoesNotThrow(player::updatesPointsPersonalGoal);
        player.getShelf().print();
        assertDoesNotThrow(player::updatesPointsPersonalGoal);
        assertEquals(12, player.getPointsPersonalGoal());
    }

    @Test
    void testUpdatesPointsPersonalGoal_ColumnIndexOutOfBoundsException() throws ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {

    }

    @Test
    void testEquals() {
        Player player2 = new Player("Alice", personalGoalCard);

        assertTrue(player.equals(player2));

        player2.addPointsCommonGoals(3);
        assertFalse(player.equals(player2));
    }

    @Test
    void testCalculateAndGiveAdjacencyPoint() throws ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {
        player.getShelf().addTiles(greens, 0);
        assertDoesNotThrow(player::calculateAndGiveAdjacencyPoint);
        assertEquals(2, player.getPointsAdjacency());
        player.getShelf().addTiles(Arrays.asList(green), 0);
        assertDoesNotThrow(player::calculateAndGiveAdjacencyPoint);
        assertEquals(3, player.getPointsAdjacency());
        player.getShelf().addTiles(Arrays.asList(green), 0);
        assertDoesNotThrow(player::calculateAndGiveAdjacencyPoint);
        assertEquals(5, player.getPointsAdjacency());
        player.getShelf().addTiles(Arrays.asList(green), 0);
        assertDoesNotThrow(player::calculateAndGiveAdjacencyPoint);
        assertEquals(8, player.getPointsAdjacency());

        player.getShelf().addTiles(blues, 1);
        assertDoesNotThrow(player::calculateAndGiveAdjacencyPoint);
        assertEquals(10, player.getPointsAdjacency());
        player.getShelf().addTiles(Arrays.asList(blue), 1);
        assertDoesNotThrow(player::calculateAndGiveAdjacencyPoint);
        assertEquals(11, player.getPointsAdjacency());
        player.getShelf().addTiles(Arrays.asList(blue), 1);
        assertDoesNotThrow(player::calculateAndGiveAdjacencyPoint);
        assertEquals(13, player.getPointsAdjacency());
        player.getShelf().addTiles(Arrays.asList(blue), 1);
        assertDoesNotThrow(player::calculateAndGiveAdjacencyPoint);
        assertEquals(16, player.getPointsAdjacency());
    }


    @Test
    void testSetPersonalGoal() {
        PersonalGoalCard2 personalGoalCard2 = new PersonalGoalCard2();

        player.setPersonalGoal(personalGoalCard2);

        assertEquals(personalGoalCard2, player.getPersonalGoal());
    }

    private List<Tile> createTileList() {
        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(TileColor.PURPLE,0));
        tiles.add(new Tile(TileColor.BLUE,0));
        tiles.add(new Tile(TileColor.YELLOW,1));
        tiles.add(new Tile(TileColor.GREEN,1));
        tiles.add(new Tile(TileColor.PURPLE,1));
        return tiles;
    }
}

class PersonalGoalCard2 extends PersonalGoalCard {
    // Additional implementation for testing purposes

}
