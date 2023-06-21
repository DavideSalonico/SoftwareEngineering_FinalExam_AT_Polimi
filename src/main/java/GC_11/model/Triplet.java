package GC_11.model;

import java.io.Serializable;

/**
 * Triplet is a side class which give the possibility to save a combination of row,column and color of a Tile
 */
public class Triplet implements Serializable {

    private int row;
    private int col;
    private TileColor color;

    public Triplet(int r, int c, TileColor color) {
        this.row = r;
        this.col = c;
        this.color = color;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public TileColor getColor() {
        return color;
    }
}
