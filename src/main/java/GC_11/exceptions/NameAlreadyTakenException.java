package GC_11.exceptions;

/**
 * NameAlreadyTakenException is an exception that is thrown when a player tries to use a nickname that is already taken.
 */
public class NameAlreadyTakenException extends Exception {

    /**
     * Constructs a NameAlreadyTakenException with a custom error message indicating that the nickname is already taken.
     *
     * @param playerName the nickname that is already taken.
     */
    public NameAlreadyTakenException(String playerName) {
        super(playerName + "is already taken. Please select another nickname");
    }
}
