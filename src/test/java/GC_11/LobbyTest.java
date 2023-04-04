package GC_11;


import GC_11.Network.Lobby;
import GC_11.exceptions.ExceededNumberOfPlayersException;
import GC_11.exceptions.NameAlreadyTakenException;
import GC_11.exceptions.PlayerNotInListException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.*;

public class LobbyTest {
    private String p1;
    private String p2;
    private String p3;
    private String p4;
    //TODO: To be implemented by Mattia

    @BeforeEach
    public void setup(){
        p1 = "Mattia";
        p2 = "Jaskaran";
        p3 = "Davide";
        p4 = "Lorenzo";
    }

    @Test
    public void testRemovePlayer() throws ExceededNumberOfPlayersException, NameAlreadyTakenException, PlayerNotInListException {
        Lobby lobby=new Lobby();
        lobby.addPlayer(p1);
        lobby.addPlayer(p2);
        lobby.addPlayer(p3);

        lobby.removePlayer(p1);
        assertFalse( lobby.hasPlayer(p1));
    }


    @Test
    public void testHasPlayer() throws ExceededNumberOfPlayersException, NameAlreadyTakenException {
        Lobby lobby = new Lobby();
        lobby.addPlayer(p1);
        lobby.addPlayer(p2);
        assertEquals(lobby.getPlayers().contains(p1),lobby.hasPlayer(p1));
        assertEquals(lobby.getPlayers().contains(p3),lobby.hasPlayer(p3));

    }

    @Test
    public void testNumOfPlayers() throws ExceededNumberOfPlayersException, NameAlreadyTakenException {
        Lobby lobby = new Lobby();
        String p1 = "Marco";
        lobby.addPlayer(p1);
        lobby.addPlayer(p2);
        assertEquals(2, lobby.getPlayers().size());
    }

    @Test
    public void testNoDuplicateInPlayerList() throws ExceededNumberOfPlayersException, NameAlreadyTakenException {
        Lobby lobby = new Lobby();
        lobby.addPlayer(p1);
        lobby.addPlayer(p1);
        assertEquals(1,lobby.getPlayers().size());
    }

    @Test void testGetLobbyNumber(){
        Lobby lobby0 = new Lobby();
        assertEquals(0,lobby0.getLobbyID());
        Lobby lobby1 = new Lobby();
        assertEquals(1,lobby1.getLobbyID());

    }

    @Test
    public void checkIsFull() throws ExceededNumberOfPlayersException, NameAlreadyTakenException {
        Lobby lobby = new Lobby();
        lobby.addPlayer(p1);
        assertEquals(lobby.getMaxPlayers()==lobby.getPlayers().size(), lobby.isFull());
        lobby.addPlayer(p2);
        assertEquals(lobby.getMaxPlayers()==lobby.getPlayers().size(), lobby.isFull());
    }

    @Test
    public void testNameAlreadyTaken() throws ExceededNumberOfPlayersException, NameAlreadyTakenException {
        Lobby lobby = new Lobby();
        lobby.addPlayer(p1);
        lobby.addPlayer(p4);
        assertTrue(lobby.nameAlreadyTaken(p4));
    }

    public void testCreateNewGame(){

    }

}
