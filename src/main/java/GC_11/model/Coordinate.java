package GC_11.model;

import java.io.Serializable;

public class Coordinate implements Serializable {

    private int row;
    private int column;

    public Coordinate(int row, int col){
        this.row = row;
        this.column = col;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isEquals(Coordinate coordinate){
        return this.row == coordinate.getRow() && this.column == coordinate.getColumn();
    }

}
