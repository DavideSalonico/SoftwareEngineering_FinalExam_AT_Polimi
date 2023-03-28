package GC_11;

import GC_11.model.Lobby;
import GC_11.model.*;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.*;

public class LobbyTest {
    //TODO: To be implemented by Mattia

    @Test
    public void testHasPlayer(){
        Lobby lobby = new Lobby(2);
        //PersonalGoalCard pgc = new PersonalGoalCard();
        Player p1 = new Player("Name1");
        Player p2 = new Player("Name2");
        Player p3 = new Player("Name3");
        lobby.addPlayer(p1);
        lobby.addPlayer(p2);

        assertEquals(lobby.getPlayers().contains(p1),lobby.hasPlayer(p1));
        assertEquals(lobby.getPlayers().contains(p3),lobby.hasPlayer(p3));

    }

    @Test
    public void testNumOfPlayers(){
        Lobby lobby = new Lobby(2);
        Player p1 = new Player("Name1");
        Player p2 = new Player("Name2");
        Player p3 = new Player("Name3");
        lobby.addPlayer(p1);
        lobby.addPlayer(p2);
        assertEquals(lobby.getPlayers().size(), lobby.getMaxPlayers());
    }

    @Test
    public void noDuplicateInPlayerList(){
        Lobby lobby = new Lobby(2);
        Player p1 = new Player("Name1");
        lobby.addPlayer(p1);
        lobby.addPlayer(p1);
        assertEquals(1,lobby.getPlayers().size());
    }

    @Test
    public void checkIsFull(){
        Lobby lobby = new Lobby(2);
        Player p1 = new Player("Name1");
        Player p2 = new Player("Name2");
        lobby.addPlayer(p1);
        assertEquals(lobby.getMaxPlayers()==lobby.getPlayers().size(), lobby.isFull());
        lobby.addPlayer(p2);
        assertEquals(lobby.getMaxPlayers()==lobby.getPlayers().size(), lobby.isFull());
    }

}
