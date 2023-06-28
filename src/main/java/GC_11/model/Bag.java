package GC_11.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Bag class. It contains all the tiles of the game. It is used to draw out tiles
 * and to refill the board.
 *
 * @see Tile
 * @see Board
 */
public class Bag implements Serializable {

    private List<Tile> tiles; // 132 tiles, 22 x 6

    private transient PropertyChangeListener listener;

    /**
     * Setter of the listener
     *
     * @param listener is the listener
     */
    public void setListener(PropertyChangeListener listener) {
        this.listener = listener;
    }

    /**
     * Copy constructor. Create a new Bag with the same tiles of the bag passed as parameter
     *
     * @param bag is the bag to copy
     */
    public Bag(Bag bag) {
        this.tiles = bag.getTiles();
        this.listener = bag.listener;
    }

    /**
     * Getter of the tiles
     *
     * @return all the tiles in the bag
     */
    public List<Tile> getTiles() {
        return this.tiles;
    }

    /**
     * Initialization of the Bag (new Game)
     */
    public Bag() {
        this.tiles = new ArrayList<Tile>();
        for (int i = 0; i < 22; i++) {
            for(TileColor color : TileColor.values()) {
                if(color != TileColor.EMPTY && color != TileColor.PROHIBITED) {
                    this.tiles.add(new Tile(color, i % 3));
                }
            }
        }
    }

    /**
     * Used to insert tiles, one by one
     */
    public void insertTile(Tile t) {
        tiles.add(t);
    }

    /**
     * Appends all the tiles, which was previously in the board before the refill, in the array-List at the end of "tiles"
     *
     * @param tiles is the list of tiles to insert
     */
    public void insertAllTile(List<Tile> tiles) {
        PropertyChangeEvent evt = new PropertyChangeEvent(
                this,
                "INSERTED_TILES",
                this.tiles,
                this.tiles.addAll(tiles));

        this.listener.propertyChange(evt);
    }

    /**
     * Check if the bag is empty
     *
     * @return true if this list contains no elements.
     */
    public boolean isBagEmpty() {
        return tiles.isEmpty();
    }

    // DA SISTEMARE: Si può fare semplicemente con il metodo remove(tile)
    public void removeTile(Tile tile) {
        boolean removed = false;
        for (int i = 0; i < tiles.size() && !removed; i++)
            if (tiles.get(i).getColor() == tile.getColor()) {
                tiles.remove(i);
                removed = true;
            }
    }

    /**
     * Remove out a list of tiles from the bag
     * Notify the listener that the game is ended
     *
     */
    public void removeListOfTile(List<Tile> t) {

        this.tiles.removeAll(t);

        PropertyChangeEvent evt = new PropertyChangeEvent(
                this,
                "END_GAME_SET",
                null,
                this.tiles);

        this.listener.propertyChange(evt);
    }

    /**
     * Counter Method
     *
     * @param tc is the color of the tiles that I want to count
     * @return Number of tiles in 'tc' coloration
     */
    public int countTiles(TileColor tc) {
        int count = 0;
        for (Tile t : tiles)
            if (t.getColor() == tc)
                count++;
        return count;
    }

    /**
     * Counter method
     *
     * @return size of "tiles"
     */
    public int countNumOfTiles() {
        return tiles.size();
    }

    /**
     * Draw out all the Tiles in the bag, even they are more than requested, after computing
     * by the Board, it will use the method updateBag(list) (use retainAll(Collection x), an ArrayList method) which Retains
     * only the elements in this list that are contained in the specified collection.
     * The random selection of Tiles is NOT managed here
     *
     * @return tiles Array-List
     */
    public List<Tile> drawOutTiles() {
        return this.tiles;
    }

    /**
     * After EVERY Draw-Out, the controller keep in the bag only the remaining Tiles,
     * contained in 'list', and it deletes all the used ones from "tiles"
     *
     */
    public void updateBag(List<Tile> list) {
        tiles.retainAll(list);
    }

    //Duplicate of removeListOfTiles???

}
