package GC_11.exceptions;

public class ColumnIndexOutOfBoundsException extends Exception {

    public ColumnIndexOutOfBoundsException(int columnIndex){
        super("Selected a wrong column index. Provided: "+ columnIndex + " expected from 0 to 4");
    }

}
