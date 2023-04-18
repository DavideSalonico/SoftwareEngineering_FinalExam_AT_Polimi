package GC_11;

import GC_11.model.Game;
import GC_11.model.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.*;

public class GameTest {
    private Game game;
    private List<String> players = new ArrayList<String>();
    @BeforeEach
    public void createGame(){
        String name1 = "Lorenzo";
        String name2 = "Davide";
        String name3 = "Mattia";
        String name4 = "Jaskaran";

        players.add(name1);
        players.add(name2);
        players.add(name3);
        players.add(name4);

        game = new Game(players);
    }

    @DisplayName("Right players order")
    @Test
    public void rightOrder(){
        assertEquals(players.size(), game.getPlayers().size());

        for(int i=0; i < players.size(); i++){
            assertEquals(players.get(i), game.getPlayers().get(i).getNickname());
        }
    }

    @DisplayName("Right current player")
    @Test
    public void rightCurrentPlayer(){
        assertEquals(game.getCurrentPlayer(), game.getPlayers().get(0));
        //TODO: expand when we add turn logic to controller
    }

    @DisplayName("No null pointers")
    @Test
    public void noNull(){
        assertNotNull(game.getBoard());
        assertNotNull(game.getCommonGoal(0));
        assertNotNull(game.getCommonGoal(1));
    }

    @DisplayName("Test Constructor")
    @Test
    public void testConstructor() {
        List<String> playerNames = Arrays.asList("Player 1", "Player 2");
        Game game = new Game(playerNames);

        assertEquals(2, game.getPlayers().size());
        assertEquals("Player 1", game.getPlayers().get(0).getNickname());
        assertEquals("Player 2", game.getPlayers().get(1).getNickname());
        assertNotNull(game.getCurrentPlayer());
        assertFalse(game.isEndGame());
        assertNotNull(game.getCommonGoal(0));
        assertNotNull(game.getCommonGoal(1));
        assertNotNull(game.getBoard());
    }
    @DisplayName("Test CurrentPlayer")
    @Test
    public void testSetCurrentPlayer() {
        List<String> playerNames = Arrays.asList("Player 1", "Player 2");
        Game game = new Game(playerNames);

        Player newCurrentPlayer = new Player("New Player");
        game.setCurrentPlayer(newCurrentPlayer);

        assertEquals(newCurrentPlayer, game.getCurrentPlayer());
    }

    @DisplayName("Set EndPlayer")
    @Test
    public void testSetEndPlayer() {
        List<String> playerNames = Arrays.asList("Player 1", "Player 2");
        Game game = new Game(playerNames);

        Player endPlayer = new Player("End Player");
        game.setEndPlayer(endPlayer);

        assertEquals(endPlayer, game.getEndPlayer());
    }


    @DisplayName("Test if Game is correctly serializable")
    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        // Create a new Game object to be serialized
        List<String> playerNames = Arrays.asList("Player 1", "Player 2");
        Game game = new Game(playerNames);
        //game.setEndPlayer(game.getPlayers(0));
        // ... initialize the game object with required data

        // Serialize the game object to a byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(game);
        oos.close();

        // Deserialize the byte array back to a new Game object
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        Game deserializedGame = (Game) ois.readObject();
        ois.close();

        // Check if the deserialized Game object is not null
        assertNotNull(deserializedGame);

        // Check if all non-transient attributes are equal to the original game object
        assertEquals(game.getPlayers(0).getNickname(), deserializedGame.getPlayers(0).getNickname());
        assertEquals(game.getPlayers(1).getNickname(), deserializedGame.getPlayers(1).getNickname());
        assertEquals(game.getCommonGoal(0).getText(), deserializedGame.getCommonGoal(0).getText());
        assertEquals(game.getCommonGoal(1).getText(), deserializedGame.getCommonGoal(1).getText());
        assertEquals(game.getCurrentPlayer().getNickname(), deserializedGame.getCurrentPlayer().getNickname());
        assertEquals(game.isEndGame(), deserializedGame.isEndGame());

        //assertEquals(game.getEndPlayer().getNickname(), deserializedGame.getEndPlayer().getNickname());
        assertEquals(game.getBoard().getBag().countNumOfTiles(), deserializedGame.getBoard().getBag().countNumOfTiles());
        assertEquals(game.getChanged(), deserializedGame.getChanged());

        // Check if the transient attribute is null in the deserialized Game object
        assertNull(deserializedGame.listener);
    }

    /*
    //QUESTO TEST NON FUNZIONA PER COLPA DEL LISTNER RIMASTO A NULL
    @DisplayName("Set EndGame Token")
    @Test
    public void testSetEndGame() {
        List<String> playerNames = Arrays.asList("Player 1", "Player 2");
        Game game = new Game(playerNames);
        PropertyChangeListener prova;
        game.setListener(prova);
        assertFalse(game.isEndGame());

        game.setEndGame(true);

        assertTrue(game.isEndGame());
    }*/

}
