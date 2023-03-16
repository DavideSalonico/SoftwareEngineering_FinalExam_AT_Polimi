package GC_11.model;

public class Triplet {


    // Attributes
    private int row;
    private int col;
    private TileColor color;


    // Methods
    public Triplet (int r, int c, TileColor color)
    {
        this.row = r;
        this.col=c;
        this.color=color;
    }

    public int getCol() {
        return col;
    }

    public int getRow() { return row; }

    public TileColor getColor() {
        return color;
    }
}
