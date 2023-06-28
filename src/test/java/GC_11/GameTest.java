package GC_11;

import GC_11.controller.JsonReader;
import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.exceptions.NotEnoughFreeSpacesException;
import GC_11.model.*;
import GC_11.model.common.*;
import GC_11.network.message.GameViewMessage;
import GC_11.util.CircularList;
import org.junit.Before;
import org.junit.Test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {

    private Game game;
    private List<String> playerNames;

    Tile blue = new Tile(TileColor.BLUE, 0);
    Tile cyan = new Tile(TileColor.CYAN, 0);
    Tile green = new Tile(TileColor.GREEN, 0);
    Tile yellow = new Tile(TileColor.YELLOW, 0);
    Tile purple = new Tile(TileColor.PURPLE, 0);
    Tile white = new Tile(TileColor.WHITE, 0);

    @Before
    public void setUp() {
        playerNames = Arrays.asList("Player1", "Player2", "Player3");
        game = new Game(playerNames, new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                // Do nothing
            }
        });
    }

    @Test
    public void testGameInitialization() {
        assertNotNull(game.getPlayers());
        assertEquals(3, game.getPlayers().size());

        for (Player player : game.getPlayers()) {
            assertNotNull(player.getNickname());
            assertNotNull(player.getPersonalGoal());
        }

        assertNotNull(game.getCurrentPlayer());
        assertEquals(game.getPlayers().get(0), game.getCurrentPlayer());

        assertFalse(game.isEndGame());
        assertFalse(game.setNextCurrent());
        assertNotNull(game.getBoard());
        assertNotNull(game.getChat());
    }

    @Test
    public void testGetCommonGoal() {
        assertNotNull(game.getCommonGoal());
        assertEquals(2, game.getCommonGoal().size());
    }

    @Test
    public void testGetCurrentPlayer() {
        assertEquals(game.getPlayers().get(0), game.getCurrentPlayer());
        game.setNextCurrent();
        assertEquals(game.getPlayers().get(1), game.getCurrentPlayer());
    }

    @Test
    public void testSetNextCurrent() {
        assertFalse(game.getCurrentPlayer().equals(game.getPlayers().get(1)));
        game.setNextCurrent();
        assertEquals(game.getPlayers().get(1), game.getCurrentPlayer());
        assertTrue(game.getCurrentPlayer().equals(game.getPlayers().get(1)));
        game.setNextCurrent();
        assertEquals(game.getPlayers().get(2), game.getCurrentPlayer());
        game.setNextCurrent();
        assertEquals(game.getPlayers().get(0), game.getCurrentPlayer());
    }

    @Test
    public void testIsEndGame() {
        assertFalse(game.isEndGame());
        game.setEndGame(true);
        assertTrue(game.isEndGame());
    }

    @Test
    public void testGetEndPlayer() {
        assertNull(game.getEndPlayer());
        game.setEndPlayer("Player1");
        assertEquals("Player1", game.getEndPlayer());
    }

    @Test
    public void testGetPlayer() {
        for (String playerName : playerNames) {
            Player player = game.getPlayer(playerName);
            assertNotNull(player);
            assertEquals(playerName, player.getNickname());
        }

        assertNull(game.getPlayer("NonExistingPlayer"));
    }

    @Test
    public void testCalculateCommonPoints() throws ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {
        game.getCommonGoal().clear();
        game.getCommonGoal().add(new CommonGoalCard1());
        game.getCommonGoal().add(new CommonGoalCard2());
        game.getPlayers().get(0).insertTiles(Arrays.asList(green,green,green,green), 0);
        game.getPlayers().get(0).insertTiles(Arrays.asList(blue,blue,blue,blue), 1);
        game.getPlayers().get(0).insertTiles(Arrays.asList(white,white,white,white), 2);
        game.getPlayers().get(0).insertTiles(Arrays.asList(green,green,green,green), 3);
        System.out.println(game.getCurrentPlayer().getNickname());
        game.calculateCommonPoints();

        assertEquals(8,game.getPlayers().get(0).getPointsCommonGoals());

        for (CommonGoalCard commonGoalCard : game.getCommonGoal()) {
            assertTrue(commonGoalCard.getWinningPlayers().isEmpty());
        }

        // Set a winning player for the first common goal
        CommonGoalCard commonGoal1 = new CommonGoalCard1();
        commonGoal1.getWinningPlayers().add(game.getPlayers().get(0));
        game.setCommonGoal(0, commonGoal1);

        game.calculateCommonPoints();

        assertTrue(game.getCommonGoal(0).getWinningPlayers().contains(game.getPlayers().get(0)));
        assertFalse(game.getCommonGoal(1).getWinningPlayers().contains(game.getPlayers().get(0)));

        // Set a winning player for the second common goal
        CommonGoalCard commonGoal2 = new CommonGoalCard2();
        commonGoal2.getWinningPlayers().add(game.getPlayers().get(1));
        game.setCommonGoal(1, commonGoal2);

        game.calculateCommonPoints();

        assertTrue(game.getCommonGoal(0).getWinningPlayers().contains(game.getPlayers().get(0)));
        assertTrue(game.getCommonGoal(1).getWinningPlayers().contains(game.getPlayers().get(1)));
    }

    @Test
    public void testTriggerException() {
        Exception exception = new ColumnIndexOutOfBoundsException(0);
        game.triggerException(exception);

        // No assertion needed, just test that no exceptions occur
    }

    @Test
    public void testGetChat() {
        assertNotNull(game.getChat());
    }

    @Test
    public void testTriggerEnd() {
        game.triggerEnd();

        // No assertion needed, just test that no exceptions occur
    }
}
