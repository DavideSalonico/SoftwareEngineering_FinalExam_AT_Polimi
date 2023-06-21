package GC_11.exceptions;

public class ExceededNumberOfPlayersException extends Exception {
    public ExceededNumberOfPlayersException() {
        super("Lobby is full. Cannot add another player.");
    }
}
