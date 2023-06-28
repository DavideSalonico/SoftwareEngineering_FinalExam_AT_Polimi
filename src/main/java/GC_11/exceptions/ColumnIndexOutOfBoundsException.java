package GC_11.exceptions;

/**
 * ColumnIndexOutOfBoundsException is an exception that is thrown when an invalid column index is provided.
 */
public class ColumnIndexOutOfBoundsException extends Exception {

    /**
     * Constructs a ColumnIndexOutOfBoundsException with the specified column index.
     *
     * @param columnIndex the invalid column index
     */
    public ColumnIndexOutOfBoundsException(int columnIndex) {
        super("Selected a wrong column index. Provided: " + columnIndex + " expected from 0 to 4");
    }

}
