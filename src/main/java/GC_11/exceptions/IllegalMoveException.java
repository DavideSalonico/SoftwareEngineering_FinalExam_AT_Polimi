package GC_11.exceptions;

/**
 * IllegalMoveException is an exception that is thrown when an invalid move is made in the game.
 */
public class IllegalMoveException extends Exception {

    /**
     * Constructs an IllegalMoveException with a default error message.
     */
    public IllegalMoveException() {
        super("Mossa non valida");
    }

    /**
     * Constructs an IllegalMoveException with a custom error message.
     *
     * @param message the custom error message describing the reason for the exception.
     */
    public IllegalMoveException(String message) {
        super(message);
    }
}
