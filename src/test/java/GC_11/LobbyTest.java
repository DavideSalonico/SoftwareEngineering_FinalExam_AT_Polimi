package GC_11;


import GC_11.model.Lobby;
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
    public void testRemovePlayer(){
        Lobby lobby=new Lobby(3);
        lobby.addPlayer(p1);
        lobby.addPlayer(p2);
        lobby.addPlayer(p3);

        lobby.removePlayer(p1);
        assertFalse( lobby.hasPlayer(p1));
    }


    @Test
    public void testHasPlayer(){
        Lobby lobby = new Lobby(2);
        lobby.addPlayer(p1);
        lobby.addPlayer(p2);
        assertEquals(lobby.getPlayers().contains(p1),lobby.hasPlayer(p1));
        assertEquals(lobby.getPlayers().contains(p3),lobby.hasPlayer(p3));

    }

    @Test
    public void testNumOfPlayers(){
        Lobby lobby = new Lobby(2);
        String p1 = "Marco";
        lobby.addPlayer(p1);
        lobby.addPlayer(p2);
        assertEquals(lobby.getPlayers().size(), lobby.getMaxPlayers());
    }

    @Test
    public void testNoDuplicateInPlayerList(){
        Lobby lobby = new Lobby(2);
        lobby.addPlayer(p1);
        lobby.addPlayer(p1);
        assertEquals(1,lobby.getPlayers().size());
    }

    @Test void testGetLobbyNumber(){
        Lobby lobby0 = new Lobby(2);
        assertEquals(0,lobby0.getLobbyID());
        Lobby lobby1 = new Lobby(2);
        assertEquals(1,lobby1.getLobbyID());

    }

    @Test
    public void checkIsFull(){
        Lobby lobby = new Lobby(2);
        lobby.addPlayer(p1);
        assertEquals(lobby.getMaxPlayers()==lobby.getPlayers().size(), lobby.isFull());
        lobby.addPlayer(p2);
        assertEquals(lobby.getMaxPlayers()==lobby.getPlayers().size(), lobby.isFull());
    }

    @Test
    public void testNameAlreadyTaken(){
        Lobby lobby = new Lobby(2);
        lobby.addPlayer(p1);
        lobby.addPlayer(p4);
        assertTrue(lobby.nameAlreadyTaken(p4));
    }

    public void testCreateNewGame(){

    }

}
