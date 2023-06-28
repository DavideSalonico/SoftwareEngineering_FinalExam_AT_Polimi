package GC_11.model;

import java.io.Serializable;

/**
 * Triplet is a helper class that represents a combination of row, column, and color of a Tile.
 * It is used to store and retrieve information about a specific position and color on the board.
 */
public class Triplet implements Serializable {

    private int row;
    private int col;
    private TileColor color;

    /**
     * Constructs a Triplet object with the specified row, column, and color.
     *
     * @param row   the row of the position
     * @param col   the column of the position
     * @param color the color of the Tile
     */
    public Triplet(int row, int col, TileColor color) {
        this.row = row;
        this.col = col;
        this.color = color;
    }

    /**
     * Returns the column of the position represented by this Triplet.
     *
     * @return the column value
     */
    public int getCol() {
        return col;
    }

    /**
     * Returns the row of the position represented by this Triplet.
     *
     * @return the row value
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the color of the Tile represented by this Triplet.
     *
     * @return the TileColor value
     */
    public TileColor getColor() {
        return color;
    }
}
