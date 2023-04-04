package GC_11.exceptions;

public class ExceededNumberOfPlayers extends Exception{
    public ExceededNumberOfPlayers(){
        super("Lobby is full. Cannot add another player.");
    }
}
