package GC_11.model;

public class Tile {

    // Attributes
    private TileColor color;
    public Tile(){
        this.color = TileColor.randomColor();
    }
    public Tile(TileColor color){
        this.color=color;
    }

    public TileColor getColor() {
        return color;
    }


    public boolean equals(Tile t) {
        return (t.getColor() == this.color);
    }
}
