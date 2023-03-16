package GC_11;

public class Tile {

    // Attributes
    private TileColor color;

    public Tile(){
        this.color = TileColor.randomColor();
    }

    public TileColor getColor() {
        return color;
    }
}
