package GC_11.model;

import GC_11.controller.JsonReader;
import GC_11.exceptions.IllegalMoveException;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.*;

import static java.lang.Math.abs;

public class Board implements PropertyChangeListener, Serializable {

    private Tile[][] chessBoard;
    private List<Coordinate> selectedTiles = new ArrayList<>();
    private Bag bag;

    public void setListener(PropertyChangeListener listener) {
        this.listener = listener;
    }

    transient PropertyChangeListener listener;

    /**
     * Duplicate method
     *
     * @return
     */
    private Tile[][] getChessBoard() {
        return chessBoard;
    }

    public Board() {
        this.bag = new Bag();
        this.bag.setListener(this);
        this.chessBoard = new Tile[9][9];
    }


    /**
     * Builder
     *
     * @param num is the number of players
     */
    public Board(int num) {
        this.bag = new Bag();
        this.bag.setListener(this);
        this.chessBoard = new Tile[9][9];
        List<Coordinate> coordinateList = new ArrayList<Coordinate>();
        JsonReader json = new JsonReader();
        coordinateList = json.readCoordinate(num);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                chessBoard[i][j] = new Tile(TileColor.EMPTY);
            }
        }
        for (Coordinate c : coordinateList) {
            this.chessBoard[c.getRow()][c.getColumn()] = new Tile(TileColor.PROHIBITED);
        }

        setBoard();
    }


    /**
     * It sets empty cells of chessboard into some random Tile picked from the bag (it uses bag's methods)
     */
    private void setBoard() {
        int randomNum;
        List<Tile> tiles = bag.drawOutTiles();
        for (int line = 0; line < 9; line++) {
            for (int column = 0; column < 9; column++) {
                if (chessBoard[line][column].equals(TileColor.EMPTY)) {
                    randomNum = new Random().nextInt(tiles.size());
                    this.chessBoard[line][column] = tiles.get(randomNum);
                    tiles.remove(randomNum);
                }
            }
        }
        this.bag.updateBag(tiles);
    }

    /**
     * Return Tile at line 'r' and column 'c'
     *
     * @param l = line
     * @param c = column
     * @return Tile
     */
    public Tile getTile(int l, int c) {
        return chessBoard[l][c];
    }

    public void setTile(int x, int y, Tile t) {
        chessBoard[x][y] = t;
    }

    /**
     * Return picked Tile from Board, it creates new Tile with TileColor.EMPTY (Immutable object),
     * the controller firstly call checkLegalMove method and then the drawTile method
     *
     * @param l = line
     * @param c = column
     * @return picked Tile
     */
    public Tile drawTile(int l, int c) throws IllegalMoveException {
        if (chessBoard[l][c].getColor().equals(TileColor.PROHIBITED) || chessBoard[l][c].getColor().equals(TileColor.EMPTY))
            throw new IllegalMoveException("You can't pick this Tile!");
        Tile picked = new Tile(chessBoard[l][c]);
        chessBoard[l][c] = new Tile(TileColor.EMPTY);
        PropertyChangeEvent evt = new PropertyChangeEvent(
                this,
                "TILE_PICKED",
                picked,
                new Tile(TileColor.EMPTY));
        this.listener.propertyChange(evt);
        return picked;
    }

    public void selectTile(int l, int c) throws IllegalMoveException {
        if (!this.isPlayable(l, c) || this.freeSides(l, c) == 0 || this.selectedTiles.size() == 3)
            throw new IllegalMoveException("You can't pick this Tile!");
        if (this.selectedTiles.size() == 0) {
            this.selectedTiles.add(new Coordinate(l, c));
            PropertyChangeEvent evt = new PropertyChangeEvent(
                    this,
                    "TILE_SELECTED",
                    null,
                    new Coordinate(l, c));
            this.listener.propertyChange(evt);
        } else {
            if (isSelected(new Coordinate(l, c)))
                throw new IllegalMoveException("You have already selected this Tile!");
            if (this.selectedTiles.size() == 1) {
                if ((l == this.selectedTiles.get(0).getRow() && abs(c - this.selectedTiles.get(0).getColumn()) == 1) ||
                        (c == this.selectedTiles.get(0).getColumn() && abs(l - this.selectedTiles.get(0).getRow()) == 1)) {
                    this.selectedTiles.add(new Coordinate(l, c));
                    PropertyChangeEvent evt = new PropertyChangeEvent(
                            this,
                            "TILE_SELECTED",
                            null,
                            new Coordinate(l, c));
                    this.listener.propertyChange(evt);
                } else {
                    throw new IllegalMoveException("You can't pick this Tile!");
                }
            } else {
                if (this.selectedTiles.get(0).getRow() == this.selectedTiles.get(1).getRow()) {
                    int max = this.selectedTiles.stream().mapToInt(Coordinate::getColumn).max().orElseThrow(NoSuchElementException::new);
                    int min = this.selectedTiles.stream().mapToInt(Coordinate::getColumn).min().orElseThrow(NoSuchElementException::new);
                    if (c == min - 1 || c == max + 1) {
                        this.selectedTiles.add(new Coordinate(l, c));
                        PropertyChangeEvent evt = new PropertyChangeEvent(
                                this,
                                "TILE_SELECTED",
                                null,
                                new Coordinate(l, c));
                        this.listener.propertyChange(evt);
                    } else
                        throw new IllegalMoveException("You can't pick this Tile!");
                } else {
                    int max = this.selectedTiles.stream().mapToInt(Coordinate::getRow).max().orElseThrow(NoSuchElementException::new);
                    int min = this.selectedTiles.stream().mapToInt(Coordinate::getRow).min().orElseThrow(NoSuchElementException::new);
                    if (l == min - 1 || l == max + 1) {
                        this.selectedTiles.add(new Coordinate(l, c));
                        PropertyChangeEvent evt = new PropertyChangeEvent(
                                this,
                                "TILE_SELECTED",
                                null,
                                new Coordinate(l, c));
                        this.listener.propertyChange(evt);
                    } else
                        throw new IllegalMoveException("You can't pick this Tile!");

                }

            }
        }
    }

    private int freeSides(int l, int c) {
        int counter = 0;
        if (l == 0 || !this.isPlayable(l - 1, c)) {
            counter++;
        }
        if (l == 8 || !this.isPlayable(l + 1, c)) {
            counter++;
        }
        if (c == 0 || !this.isPlayable(l, c - 1)) {
            counter++;
        }
        if (c == 8 || !this.isPlayable(l, c + 1)) {
            counter++;
        }
        return counter;
    }

    public boolean isPlayable(int l, int c) {
        boolean empty = !chessBoard[l][c].getColor().equals(TileColor.EMPTY);
        boolean prohibited = !chessBoard[l][c].getColor().equals(TileColor.PROHIBITED);
        return empty && prohibited;
    }

    /**
     * It checks if there are only isolated Tiles on the Board so the Board needs a refill of Tiles
     *
     * @return number of isolated Tiles if the board needs a refill, else it returns 0
     */
    private int checkDraw() {
        int counter = 0;
        for (int l = 0; l < 9; l++) {
            for (int c = 0; c < 9; c++) {
                if (!chessBoard[l][c].getColor().equals(TileColor.PROHIBITED) &&
                        !chessBoard[l][c].getColor().equals(TileColor.EMPTY)) {
                    if (l != 0 && !chessBoard[l - 1][c].getColor().equals(TileColor.PROHIBITED) &&
                            !chessBoard[l - 1][c].getColor().equals(TileColor.EMPTY))
                        return 0;
                    if (c != 0 && !chessBoard[l][c - 1].getColor().equals(TileColor.PROHIBITED) &&
                            !chessBoard[l][c - 1].getColor().equals(TileColor.EMPTY))
                        return 0;
                    if (l != 8 && !chessBoard[l + 1][c].getColor().equals(TileColor.PROHIBITED) &&
                            !chessBoard[l + 1][c].getColor().equals(TileColor.EMPTY))
                        return 0;
                    if (c != 8 && !chessBoard[l][c + 1].getColor().equals(TileColor.PROHIBITED) &&
                            !chessBoard[l][c + 1].getColor().equals(TileColor.EMPTY))
                        return 0;

                    counter++;
                }
            }
        }
        //probabilmente non ci interessa sapere quante Tiles isolate ci sono, ma lascio per future necessità
        return counter;
    }

    public void changeOrder(List<Integer> positions) throws IllegalArgumentException {
        if (positions.size() != this.selectedTiles.size())
            throw new InvalidParameterException("Position numbers are not the same of selected tiles!");

        Coordinate[] tmp_selected = new Coordinate[selectedTiles.size()];

        for (Coordinate c : selectedTiles) {
            tmp_selected[positions.get(selectedTiles.indexOf(c))] = c;
        }

        //Controlli finali, non dovrebbero mai essere sollevati
        for (Coordinate tmp_c : tmp_selected) {
            if (tmp_c == null)
                throw new IllegalArgumentException("Something went wrong!");
        }

        this.selectedTiles = Arrays.stream(tmp_selected).toList();

        PropertyChangeEvent evt = new PropertyChangeEvent(
                this,
                "CHANGED_ORDER",
                null,
                this.chessBoard);
        this.listener.propertyChange(evt);
    }

    /**
     * Checks (using checkDraw method) if the Board needs a Tiles' refill, if true, it will clean the board from remaining
     * Tiles inserting them into the Bag and after that it will set again all the Board's cells with random tiles using setBoard() method
     */
    public void refillBoard() {
        if (checkDraw() > 0) {
            for (int line = 0; line < 9; line++) {
                for (int column = 0; column < 9; column++) {
                    if (!chessBoard[line][column].getColor().equals(TileColor.PROHIBITED) &&
                            !chessBoard[line][column].getColor().equals(TileColor.EMPTY)) {
                        this.bag.insertTile(chessBoard[line][column]);
                        chessBoard[line][column] = new Tile(TileColor.EMPTY);
                    }
                }
            }

            setBoard();
            /* Can't manage to give right OldValue e NewValue */
            PropertyChangeEvent evt = new PropertyChangeEvent(
                    this,
                    "BOARD_REFILLED",
                    null,
                    this.chessBoard);
            this.listener.propertyChange(evt);

        }
    }

    public void deselectTile() throws IllegalMoveException {
        int l = this.selectedTiles.size();
        if (l == 0)
            throw new IllegalMoveException("You haven't selected any tile!");
        this.selectedTiles.remove(l - 1);

        PropertyChangeEvent evt = new PropertyChangeEvent(
                this,
                "DESELECTED_TILE",
                null,
                this.chessBoard);
        this.listener.propertyChange(evt);
    }


    /**
     * Getter method of bag
     *
     * @return bag of remaining Tiles
     */
    public Bag getBag() {
        return this.bag;
    }

    public void print() {
        String[][] tmpboard = buildBoardPrint();

        for (int i = -1; i < 9; i++) {
            if (i == -1) {
                System.out.print("\t");
                for (int k = 0; k < 9; k++) {
                    System.out.print("\t" + k);
                }
                System.out.println();
            } else {
                for (int j = -1; j < 9; j++) {
                    if (j == -1) {
                        System.out.print("\t" + i);
                    } else {
                        System.out.print("\t" + tmpboard[i][j]);
                    }
                }
                System.out.println("\n");
            }
        }


        System.out.println("Board selected tiles:");
        for (Coordinate c : selectedTiles) {
            System.out.println(c.getRow() + ", " + c.getColumn() + ", " +
                    TileColor.ColorToString(this.getTile(c.getRow(), c.getColumn()).getColor()));
        }
    }

    private String[][] buildBoardPrint() {
        String[][] tmpboard = new String[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                TileColor color = getTile(i, j).getColor();
                if (color != TileColor.PROHIBITED) {
                    tmpboard[i][j] = TileColor.ColorToString(color);
                }
                //System.out.print("\t" + color.toString().charAt(0));
                else {
                    tmpboard[i][j] = "▧";
                    //System.out.print("\t" + "X");
                }
            }
            //System.out.println();
        }
        return tmpboard;
    }

    public List<Coordinate> getSelectedTiles() {
        return selectedTiles;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.listener.propertyChange(evt);
    }

    public void resetSelectedTiles() {
        this.selectedTiles = new ArrayList<Coordinate>();
    }

    public void resetTurn(){
        resetSelectedTiles();
        PropertyChangeEvent evt = new PropertyChangeEvent(
                this,
                "RESET_TURN",
                null,
                null);
        this.listener.propertyChange(evt);
    }

    private boolean isSelected(Coordinate c) {
        for (Coordinate tmp_c : selectedTiles) {
            if (tmp_c.isEquals(c))
                return true;
        }
        return false;
    }
}