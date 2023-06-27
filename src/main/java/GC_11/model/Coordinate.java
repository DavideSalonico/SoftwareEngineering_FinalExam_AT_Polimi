package GC_11.model;

import java.io.Serializable;

public class Coordinate implements Serializable {

    private int row;
    private int column;

    /**
     * Constructor for the class Coordinate
     * @param row the row of the coordinate
     * @param col the column of the coordinate
     */
    public Coordinate(int row, int col) {
        this.row = row;
        this.column = col;
    }

    /**
     * Getter for the row of the coordinate
     * @return the row of the coordinate
     */
    public int getRow() {
        return row;
    }

    /**
     * Getter for the column of the coordinate
     * @return the column of the coordinate
     */
    public int getColumn() {
        return column;
    }

    /**
     * Check if the coordinate is equals to another coordinate
     * @param coordinate the coordinate to compare
     * @return true if the coordinates are equals, false otherwise
     */
    public boolean isEquals(Coordinate coordinate) {
        return this.row == coordinate.getRow() && this.column == coordinate.getColumn();
    }

}
