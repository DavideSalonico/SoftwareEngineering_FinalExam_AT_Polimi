package GC_11.model;

import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.exceptions.NotEnoughFreeSpacesException;

import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.List;

/**
 * Shelf's class, it is composed by a matrix
 * it's a Serializable class except for the listener attribute
 */
public class Shelf implements Serializable {
    private Tile[][] myShelf;
    //Player must register
    public transient PropertyChangeListener listener;

    /**
     * Constructs a new Shelf object.
     * Initializes the myShelf matrix with empty tiles.
     */
    public Shelf() {
        myShelf = new Tile[6][5];
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 5; c++) {
                myShelf[r][c] = new Tile(TileColor.EMPTY, 0);
            }
        }
    }

    /**
     * Constructs a new Shelf object by duplicating another Shelf instance.
     *
     * @param shelf The original Shelf instance to duplicate.
     */
    public Shelf(Shelf shelf) {
        this.myShelf = new Tile[6][5];
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 5; c++) {
                this.myShelf[r][c] = new Tile(shelf.getMyShelf()[r][c]);
            }
        }
    }

    /**
     * Returns the myShelf matrix.
     *
     * @return The matrix representing the shelf.
     */
    private Tile[][] getMyShelf() {
        return this.myShelf;
    }

    /**
     * Calculates the number of free spaces in the selected column.
     *
     * @param column The column for which to count the number of free spaces.
     * @return The number of free spaces in the column.
     */
    private int freeSpaces(int column) {
        int free = 0;
        for (int i = 0; i < 6; i++) {
            if (myShelf[i][column].getColor() == TileColor.EMPTY) {
                free++;
            }
        }
        return free;
    }

    /**
     * Inserts a list of tiles into the desired column of the shelf.
     *
     * @param tileList The list of tiles to be inserted.
     * @param column   The column number (starting from 0) where the tiles should be inserted.
     * @return True if the shelf is full after the tiles are inserted, false otherwise.
     * @throws NotEnoughFreeSpacesException    If there is not enough space in the selected column to insert the tiles.
     * @throws ColumnIndexOutOfBoundsException If the column index is out of bounds.
     */
    public boolean addTiles(List<Tile> tileList, int column) throws NotEnoughFreeSpacesException, ColumnIndexOutOfBoundsException {
        if (column < 0 || column >= 5) {
            throw new ColumnIndexOutOfBoundsException(column);
        } else {
            if (freeSpaces(column) < tileList.size()) {
                throw new NotEnoughFreeSpacesException(column, tileList.size());
            } else {
                int firstFreeSpace = freeSpaces(column) - 1;
                for (Tile t : tileList) {
                    myShelf[firstFreeSpace][column] = t;
                    firstFreeSpace--;
                }
            }
        }
        if(this.isFull()){
            return true;
        }
        return false;
    }

    /**
     * Returns the tile at the specified position in the shelf.
     *
     * @param line   The row index of the tile.
     * @param column The column index of the tile.
     * @return The tile at the specified position.
     * @throws ColumnIndexOutOfBoundsException If the specified position is outside the shelf matrix bounds.
     */
    public Tile getTile(int line, int column) throws ColumnIndexOutOfBoundsException {
        if (line < 0 || line >= 6 || column < 0 || column >= 5) {
            throw new ColumnIndexOutOfBoundsException(-1);
        } else {
            return myShelf[line][column];
        }
    }

    /**
     * Checks if the shelf is completely filled with tiles.
     *
     * @return True if all the tiles in the shelf are occupied, false otherwise.
     */
    public boolean isFull() {
        for (int i = 0; i < 5; i++) {
            if (myShelf[0][i].getColor().equals(TileColor.EMPTY)) return false;
        }
        return true;
    }

    /**
     * Calculates the maximum number of free vertical spaces in the shelf.
     *
     * @return The maximum number of free vertical spaces in any column of the shelf.
     */
    public int maxFreeVerticalSpaces() {
        int max = 0;
        for (int j = 0; j < 5; j++) {
            int column_max = 0;
            for (int i = 5; i >= 0; i--) {
                if (myShelf[i][j].getColor().equals(TileColor.EMPTY)) column_max++;
            }
            max = Math.max(max, column_max);
        }
        return max;
    }

    /**
     * Prints the contents of the shelf.
     */
    public void print() {
        for (int i = 0; i < 6; i++) {
            System.out.print("|");
            for (int j = 0; j < 5; j++) {
                if (myShelf[i][j].getColor().equals(TileColor.EMPTY))
                    System.out.print("  |");
                else
                    System.out.print(TileColor.ColorToString(myShelf[i][j].getColor()) + "|");
            }
            System.out.println();
        }
        System.out.println(" 0  1  2  3  4 ");
    }

    /**
     * Sets the tile at the specified position in the shelf.
     *
     * @param line   The row index of the position.
     * @param column The column index of the position.
     * @param tile   The tile to set at the specified position.
     * @throws ColumnIndexOutOfBoundsException If the specified position is outside the shelf matrix bounds.
     */
    public void setTile(int line, int column, Tile tile) throws ColumnIndexOutOfBoundsException {
        if (line < 0 || line >= 6 || column < 0 || column >= 5) {
            throw new ColumnIndexOutOfBoundsException(-1);
        } else {
            myShelf[line][column] = tile;
        }
    }

    /**
     * Sets the listener for the shelf.
     *
     * @param listener The PropertyChangeListener to set as the listener for the shelf.
     */
    public void setListener(PropertyChangeListener listener) {
        this.listener = listener;
    }

}
