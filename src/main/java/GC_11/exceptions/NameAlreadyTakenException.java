package GC_11.exceptions;

public class NameAlreadyTakenException extends Exception{

    public NameAlreadyTakenException(String playerName){
        super(playerName + "is already taken. Please select another nickname");
    }
}
