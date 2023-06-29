package GC_11;

import GC_11.exceptions.ExceededNumberOfPlayersException;
import GC_11.exceptions.NameAlreadyTakenException;
import GC_11.exceptions.PlayerNotInListException;
import GC_11.model.Lobby;
import GC_11.network.message.LobbyViewMessage;
import org.junit.Before;
import org.junit.Test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import static org.junit.Assert.*;

public class LobbyTest {

    private Lobby lobby;
    private MockPropertyChangeListener listener;

    @Before
    public void setUp() {
        lobby = new Lobby();
        listener = new MockPropertyChangeListener();
        lobby.setListener(listener);
    }

    @Test
    public void testInitialMaxPlayers() {
        assertEquals(1, lobby.getMaxPlayers());
    }

    @Test
    public void testSetMaxPlayers() {
        lobby.setMaxPlayers(4);
        assertEquals(4, lobby.getMaxPlayers());
    }

    @Test
    public void testAddPlayer() throws ExceededNumberOfPlayersException, NameAlreadyTakenException {
        lobby.addPlayer("Player1");
        assertTrue(lobby.getPlayers().contains("Player1"));
    }

    @Test(expected = ExceededNumberOfPlayersException.class)
    public void testAddPlayerExceededNumberOfPlayers() throws ExceededNumberOfPlayersException, NameAlreadyTakenException {
        lobby.setMaxPlayers(2);
        lobby.addPlayer("Player1");
        lobby.addPlayer("Player2");
        lobby.addPlayer("Player3");  // This should throw an ExceededNumberOfPlayersException
    }

    @Test(expected = NameAlreadyTakenException.class)
    public void testAddPlayerNameAlreadyTaken() throws ExceededNumberOfPlayersException, NameAlreadyTakenException {
        lobby.addPlayer("Player1");
        lobby.setMaxPlayers(3);
        lobby.addPlayer("Player1");  // This should throw a NameAlreadyTakenException
    }

    @Test
    public void testRemovePlayer() throws PlayerNotInListException, ExceededNumberOfPlayersException, NameAlreadyTakenException {
        lobby.addPlayer("Player1");
        lobby.removePlayer("Player1");
        assertFalse(lobby.getPlayers().contains("Player1"));
    }

    @Test(expected = PlayerNotInListException.class)
    public void testRemovePlayerNotInList() throws PlayerNotInListException {
        lobby.removePlayer("Player1");  // This should throw a PlayerNotInListException
    }

    @Test
    public void testIsFull() throws ExceededNumberOfPlayersException, NameAlreadyTakenException {
        assertFalse(lobby.isFull());

        lobby.addPlayer("Player1");
        assertTrue(lobby.isFull());

        lobby.setMaxPlayers(2);
        assertFalse(lobby.isFull());

        lobby.addPlayer("Player2");
        assertTrue(lobby.isFull());
    }

   /* @Test
    public void testHasPlayer() throws ExceededNumberOfPlayersException, NameAlreadyTakenException {
        assertFalse(lobby.hasPlayer("Player1"));

        lobby.addPlayer("Player1");
        assertTrue(lobby.hasPlayer("Player1"));
    }*/

    @Test
    public void testTriggerException() {
        Exception exception = new Exception("Test Exception");
        lobby.triggerException(exception);
        assertEquals(exception, listener.getEvent().getNewValue());
    }

    private static class MockPropertyChangeListener implements PropertyChangeListener {
        private PropertyChangeEvent event;

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            event = evt;
        }

        public PropertyChangeEvent getEvent() {
            return event;
        }
    }
}
