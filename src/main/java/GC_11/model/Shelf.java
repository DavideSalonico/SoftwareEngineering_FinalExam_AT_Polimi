package GC_11.model;

import java.util.List;
import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.exceptions.NotEnoughFreeSpacesException;

public class Shelf {

    private Tile[][] myShelf;

    public Shelf() {
        myShelf = new Tile[6][5];
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 5; c++) {
                myShelf[r][c] = new Tile(TileColor.EMPTY);
            }
        }
    }

    /**
     * @param column indicates the column of which I want to know the number of fre spaces
     * @return an integer indicating how namy more Tiles can be added in the selected column
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
     * This method permit to insert a list of Tiles in the desired column
     * @param tileList is the list of Tiles to be inserted
     * @param column   is the number of the column (starting from 0)
     * @throws NotEnoughFreeSpacesException    when there is not enough space in the selected column to insert the tiles
     * @throws ColumnIndexOutOfBoundsException when the column index is out of bound
     */
    public void addTiles(List<Tile> tileList, int column) throws NotEnoughFreeSpacesException, ColumnIndexOutOfBoundsException {
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
    }

    /**
     * @param line   indicates the line to check
     * @param column indicates the column ti check
     * @return the tile in the position required
     * @throws ColumnIndexOutOfBoundsException when the position required
     *                                         is outside the matrix of the shelf
     */
    public Tile getTile(int line, int column) throws ColumnIndexOutOfBoundsException {
        if (line < 0 || line >= 6 || column < 0 || column >= 5) {
            throw new ColumnIndexOutOfBoundsException(-1);
        } else {
            return myShelf[line][column];
        }
    }

    /**
     * this method is used to check if a shelf is complete
     * @return a boolean which indicates if all the Tiles in the Shelf are occupied
     */
    public boolean isFull() {
        for (int i = 0; i < 5; i++) {
            if (myShelf[0][i].getColor().equals(TileColor.EMPTY)) return false;
        }
        return true;
    }
    //Il test non passa

    public void print() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(myShelf[i][j].getColor() + "\t");
            }
            System.out.println();
        }
    }
}
