package GC_11;

import GC_11.exceptions.IllegalMoveException;
import GC_11.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class BoardTest {
  private Board board;
  private ListenerVoid listener=new ListenerVoid();

  @BeforeEach
  void setUp() {
    board = new Board(2);
    board.setListener(listener);
  }

  @Test
  void testSetTile() {
    Tile tile = new Tile(TileColor.PURPLE, 5);
    board.setTile(0, 0, tile);
    Assertions.assertEquals(tile, board.getTile(0, 0));
  }

  @Test
  void testDrawTile() throws IllegalMoveException {
    Tile tile = board.drawTile(1, 4);
    Assertions.assertEquals(TileColor.EMPTY, board.getTile(1, 4).getColor());
  }

  @Test
  void testSelectTile() throws IllegalMoveException {
    board.selectTile(1, 4);
    Assertions.assertEquals(1, board.getSelectedTiles().size());
    Assertions.assertTrue((new Coordinate(1, 4)).isEquals(board.getSelectedTiles().get(0)));
    board.selectTile(1, 3);
    Assertions.assertEquals(2, board.getSelectedTiles().size());
    Assertions.assertTrue((new Coordinate(1, 3)).isEquals(board.getSelectedTiles().get(1)));
    Assertions.assertThrows(IllegalMoveException.class, () -> board.selectTile(1, 5));
    Assertions.assertEquals(2, board.getSelectedTiles().size());
  }

  @Test
  void testChangeOrder() throws IllegalMoveException {
    board.selectTile(1, 4);
    board.selectTile(1, 3);
    List<Integer> positions = new ArrayList<>();
    positions.add(1);
    positions.add(0);
    board.changeOrder(positions);
    List<Coordinate> selectedTiles = board.getSelectedTiles();
    Assertions.assertTrue((new Coordinate(1, 3)).isEquals(board.getSelectedTiles().get(0)));
    Assertions.assertTrue((new Coordinate(1, 4)).isEquals(board.getSelectedTiles().get(1)));
  }

  @Test
  void testRefillBoard() {
    board.refillBoard();
    Assertions.assertNotNull(board.getTile(0, 0));
    Assertions.assertNotNull(board.getTile(1, 1));
    Assertions.assertNotNull(board.getTile(2, 2));
    // ...
  }

  @Test
  void testDeselectTile() throws IllegalMoveException {
    board.selectTile(1, 4);
    board.selectTile(1, 3);
    board.deselectTile();
    Assertions.assertEquals(1, board.getSelectedTiles().size());
    Assertions.assertTrue((new Coordinate(1, 4)).isEquals(board.getSelectedTiles().get(0)));
    board.deselectTile();
    Assertions.assertEquals(0, board.getSelectedTiles().size());
    Assertions.assertThrows(IllegalMoveException.class, board::deselectTile);
  }

  @Test
  void testGetBag() {
    Bag bag = board.getBag();
    Assertions.assertNotNull(bag);
  }

  @Test
  void testPrint() {
    board.print();
    // Check console output
  }

  @Test
  void testResetTurn() throws IllegalMoveException {
    board.selectTile(1, 3);
    board.resetTurn();
    Assertions.assertEquals(0, board.getSelectedTiles().size());
  }

}

