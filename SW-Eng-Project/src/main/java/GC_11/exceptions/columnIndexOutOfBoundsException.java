package GC_11.exceptions;

public class columnIndexOutOfBoundsException extends Exception {

    public columnIndexOutOfBoundsException(int columnIndex){
        super("Selected a wrong column index. Provided: "+ columnIndex + " expected from 0 to 4");
    }

}
