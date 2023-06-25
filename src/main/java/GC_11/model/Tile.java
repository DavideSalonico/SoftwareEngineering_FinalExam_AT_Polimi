package GC_11.model;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Random;

/**
 * Tile's class, serializable except for Random attribute
 */
public class Tile implements Serializable {

    // Attributes
    private TileColor color;
    private int id;

    private static Random RANDOM = new Random();

    /**
     * Duplicate Tile constructor
     *
     * @param t is the original Tile
     */
    public Tile(Tile t) {
        this.color = t.getColor();
        this.id = t.getId();
    }

    public Tile(TileColor color, int id) {
        this.color = color;
        this.id = id;
    }

    /*public Tile(TileColor color) {
        this.color = color;
    }

     */

    public TileColor getColor() {
        return this.color;
    }

    public int getId() {
        return this.id;
    }

    public boolean equals(@NotNull Tile t) {
        return (t.getColor() == this.color);
    }

    public boolean equals(TileColor tc) {
        return this.color == tc;
    }
}
