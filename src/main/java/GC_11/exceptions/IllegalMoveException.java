package GC_11.exceptions;

public class IllegalMoveException extends Exception {
    public IllegalMoveException() {
        super("Mossa non valida");
    }

    public IllegalMoveException(String message) {
        super(message);
    }
}
