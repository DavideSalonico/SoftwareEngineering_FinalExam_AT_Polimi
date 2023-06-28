package GC_11.exceptions;

/**
 * PlayerNotInListException is an exception that is thrown when a player cannot be removed from a list because they are not in the list.
 */
public class PlayerNotInListException extends Exception {

    /**
     * Constructs a PlayerNotInListException with a custom error message indicating that the specified player cannot be removed because they are not in the list.
     *
     * @param playerName  the name of the player that cannot be removed.
     */
    public PlayerNotInListException(String playerName) {
        super(playerName + "cannot be removed because it is not in the list.");
    }
}
