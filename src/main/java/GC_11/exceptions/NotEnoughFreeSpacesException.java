package GC_11.exceptions;

/**
 * NotEnoughFreeSpacesException is an exception that is thrown when there are not enough free spaces in a column to insert a certain number of tiles.
 */
public class NotEnoughFreeSpacesException extends Exception {

    /**
     * Constructs a NotEnoughFreeSpacesException with a custom error message indicating that the specified column does not have enough free spaces to insert the given number of tiles.
     *
     * @param column      the column index where the tiles are being inserted.
     * @param totalTiles  the total number of tiles that are being inserted.
     */
    public NotEnoughFreeSpacesException(int column, int totalTiles) {
        super("Column + " + column + " has not enough free spaces to insert " + totalTiles + " tiles");
    }
}

