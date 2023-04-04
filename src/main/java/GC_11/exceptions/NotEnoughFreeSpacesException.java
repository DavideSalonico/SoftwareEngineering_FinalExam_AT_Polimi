package GC_11.exceptions;

public class NotEnoughFreeSpacesException extends Exception{
    public NotEnoughFreeSpacesException(int column, int totalTiles){
        super("Column + " + column + " has not enough free spaces to insert " + totalTiles + " tiles");
    }
}

