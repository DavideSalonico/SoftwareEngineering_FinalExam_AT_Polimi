package GC_11.controller;

import GC_11.ListenerVoid;
import GC_11.distributed.ServerMain;
import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.exceptions.IllegalMoveException;
import GC_11.model.Game;
import GC_11.model.Lobby;
import GC_11.model.Player;
import GC_11.model.TileColor;
import GC_11.network.choices.Choice;
import GC_11.network.choices.ChoiceType;
import GC_11.network.choices.PickColumnChoice;
import GC_11.network.choices.SelectTileChoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    private Controller controller;

    @BeforeEach
    void setUp() {
        ServerMain serverMain= new ServerMain(4321);
        controller = new Controller(serverMain);
        controller.setGame(new Game(new ArrayList<>(List.of("Player 1", "Player 2", "Player 3")), new ListenerVoid()));
    }

    @Test
    void getGame() {
        assertEquals(controller.getGame().getClass(), Game.class);
    }

    @Test
    void getLobby() {
        assertEquals(controller.getLobby().getClass(), Lobby.class);
    }

    @Test
    void setChoice() throws IllegalMoveException {
        controller.setChoice(new PickColumnChoice(new Player(), Collections.singletonList("0"), ChoiceType.PICK_COLUMN));
        assertEquals(controller.getChoice().getType(), ChoiceType.PICK_COLUMN);
    }


    @Test
    void checkTurn() throws IllegalMoveException {
        controller.setChoice(new PickColumnChoice(controller.getGame().getCurrentPlayer(), Collections.singletonList("0"), ChoiceType.PICK_COLUMN));
        assertTrue(controller.checkTurn());
    }

    @Test
    void update() throws IllegalMoveException, RemoteException {
        controller.update(new SelectTileChoice(controller.getGame().getCurrentPlayer(), new ArrayList<>(List.of("1", "3")), ChoiceType.SELECT_TILE));

    }

    @Test
    void resetTurn() throws IllegalMoveException, RemoteException {
        controller.getGame().getBoard().selectTile(1, 3);
        assertEquals(1, controller.getGame().getBoard().getSelectedTiles().size());
        controller.resetTurn(new ArrayList<>(List.of()));
        assertEquals(0, controller.getGame().getBoard().getSelectedTiles().size());
    }

    @Test
    void deselectTile() throws IllegalMoveException, RemoteException {
        controller.getGame().getBoard().selectTile(1, 3);
        controller.getGame().getBoard().selectTile(1, 4);
        assertEquals(2, controller.getGame().getBoard().getSelectedTiles().size());
        controller.deselectTile(new ArrayList<>(List.of()));
        assertEquals(1, controller.getGame().getBoard().getSelectedTiles().size());

    }

    @Test
    void selectTile() throws IllegalMoveException {
        controller.getGame().getBoard().selectTile(1, 3);
        controller.getGame().getBoard().selectTile(1, 4);
        assertEquals(2, controller.getGame().getBoard().getSelectedTiles().size());
    }

    @Test
    void pickColumn() throws IllegalMoveException, RemoteException, ColumnIndexOutOfBoundsException {
        controller.getGame().getBoard().selectTile(1, 3);
        controller.getGame().getBoard().selectTile(1, 4);
        assertEquals(2, controller.getGame().getBoard().getSelectedTiles().size());
        controller.pickColumn(new ArrayList<>(List.of("0")));
        assertEquals(0, controller.getGame().getBoard().getSelectedTiles().size());
        controller.getGame().getPlayers(0).getShelf().print();
        assertNotSame(controller.getGame().getPlayers(0).getShelf().getTile(5, 0).getColor(), TileColor.EMPTY);
    }

    @Test
    void chooseOrder() throws IllegalMoveException, RemoteException {
        controller.getGame().getBoard().selectTile(1, 3);
        controller.getGame().getBoard().selectTile(1, 4);
        controller.chooseOrder(new ArrayList<>(List.of("1", "0")));
        assertEquals(4, controller.getGame().getBoard().getSelectedTiles().get(0).getColumn());
    }

    @Test
    void propertyChange() throws IllegalMoveException, RemoteException{
        controller.propertyChange(new PropertyChangeEvent(this, "CHOICE", null, new SelectTileChoice(controller.getGame().getCurrentPlayer(), new ArrayList<>(List.of("1", "3")), ChoiceType.SELECT_TILE)));
    }

    @Test
    void sendMessage() throws RemoteException {
        controller.sendMessage(controller.getGame().getPlayers(0), new ArrayList<>(List.of("Everyone", "ciao a tutti")));
        assertEquals("Player 1", controller.getGame().getChat().getMainChat().get(0).getSender());
        assertEquals("ciao a tutti", controller.getGame().getChat().getMainChat().get(0).getText());
    }

    @Test
    void setMaxPlayers() {
        controller.setMaxPlayers(3);
        assertEquals(3, controller.getLobby().getMaxPlayers());
    }


    @Test
    void getServer() {
        assertEquals(controller.getServer().getClass(), ServerMain.class);
    }

    @Test
    void setOldGameAvailable() {
    }

    @Test
    void selectLoadGame() {
    }
}