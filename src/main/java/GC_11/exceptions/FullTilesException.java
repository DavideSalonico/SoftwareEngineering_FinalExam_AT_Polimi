package GC_11.exceptions;

/**
 * FullTilesException is an exception that is thrown when there are no available slots in a column to insert tiles.
 */
public class FullTilesException extends Exception {

    /**
     * Constructs a FullTilesException.
     */
    public FullTilesException() {
        super("Full Tiles. Choose a column where to insert them");
    }
}
