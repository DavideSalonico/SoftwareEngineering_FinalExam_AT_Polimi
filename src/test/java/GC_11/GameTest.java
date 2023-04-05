package GC_11;

import GC_11.model.Game;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

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
}
