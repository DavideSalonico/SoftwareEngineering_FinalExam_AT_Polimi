package GC_11.exceptions;

public class PlayerNotInListException extends Exception{
    public PlayerNotInListException(String playerName){
        super(playerName +"cannot be removed because it is not in the list.");
    }
}
