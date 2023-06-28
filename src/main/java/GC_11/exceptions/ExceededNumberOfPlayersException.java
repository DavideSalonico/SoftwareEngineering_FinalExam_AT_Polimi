package GC_11.exceptions;

/**
 * ExceededNumberOfPlayersException is an exception that is thrown when the lobby is full and another player cannot be added.
 */
public class ExceededNumberOfPlayersException extends Exception {

    /**
     * Constructs an ExceededNumberOfPlayersException.
     */
    public ExceededNumberOfPlayersException() {
        super("Lobby is full. Cannot add another player.");
    }
}
