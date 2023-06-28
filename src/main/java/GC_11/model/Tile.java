package GC_11.model;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Random;

/**
 * Tile's class, it is composed by a color and an id
 * it's a Serializable class
 */
public class Tile implements Serializable {

    // Attributes
    private TileColor color;
    private int id;

    private static Random RANDOM = new Random();

    /**
     * Constructs a new Tile object by duplicating another Tile instance.
     *
     * @param t The original Tile instance to duplicate.
     */
    public Tile(Tile t) {
        this.color = t.getColor();
        this.id = t.getId();
    }

    /**
     * Constructs a new Tile object with the given color and ID.
     *
     * @param color The color of the tile.
     * @param id    The ID of the tile.
     */
    public Tile(TileColor color, int id) {
        this.color = color;
        this.id = id;
    }

    /**
     * Returns the color of the tile.
     *
     * @return The color of the tile.
     */
    public TileColor getColor() {
        return this.color;
    }

    /**
     * Returns the ID of the tile.
     *
     * @return The ID of the tile.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Checks if the current tile is equal to the specified tile.
     *
     * @param t The tile to compare.
     * @return True if the tiles are equal (have the same color), false otherwise.
     */
    public boolean equals(@NotNull Tile t) {
        return (t.getColor() == this.color);
    }

    /**
     * Checks if the current tile is equal to the specified color.
     *
     * @param tc The color to compare.
     * @return True if the tile color is equal to the specified color, false otherwise.
     */
    public boolean equals(TileColor tc) {
        return this.color == tc;
    }
}
