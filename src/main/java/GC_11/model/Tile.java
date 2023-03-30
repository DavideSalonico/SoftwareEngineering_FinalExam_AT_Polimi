package GC_11.model;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class Tile {

    // Attributes
    private TileColor color;
    private int id;

    //private static Random RANDOM = new Random(3);
    public Tile(){
        this.color = TileColor.randomColor();
    }

    /**
     * Duplicate Tile constructor
     * @param t is the original Tile
     */
    public Tile(Tile t){
        this.color = t.color;
        this.id = t.id;
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


    public boolean equals(@NotNull Tile t) {
        return (t.getColor() == this.color);
    }

    public boolean equals(TileColor tc){
        return this.color == tc;
    };
}
