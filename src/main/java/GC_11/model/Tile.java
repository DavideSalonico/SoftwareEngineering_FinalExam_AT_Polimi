package GC_11.model;

import java.util.Random;

public class Tile {

    // Attributes
    private TileColor color;
    private int id;

    //private static Random RANDOM = new Random(3);
    public Tile(){
        this.color = TileColor.randomColor();
    }
    public Tile(TileColor color){
        this.color=color;
    }

    public TileColor getColor() {
        return color;
    }

    public int getId(){
        return this.id;
    }


    public boolean equals(Tile t) {
        return (t.getColor() == this.color);
    }
}
