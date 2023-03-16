package GC_11.exceptions;

public class notEnoughFreeSpacesException extends Exception{
    public notEnoughFreeSpacesException(int column, int totalTiles){
        super("Column + " + column + " has not enough free spaces to insert " + totalTiles + " tiles");
    }
}

