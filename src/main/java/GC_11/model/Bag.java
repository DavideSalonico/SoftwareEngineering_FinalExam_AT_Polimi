package GC_11.model;

import java.util.ArrayList;
import java.util.List;

public class Bag {

    private List<Tile> tiles; // 132 tiles, 22 x 6

    /**
     * Initialization of the Bag (new Game)
     */
    public Bag(){
        this.tiles = new ArrayList<Tile>();
        for (int i=0; i< 22;i++){
            tiles.add(new Tile(TileColor.WHITE));
            tiles.add(new Tile(TileColor.BLUE));
            tiles.add(new Tile(TileColor.GREEN));
            tiles.add(new Tile(TileColor.CYAN));
            tiles.add(new Tile(TileColor.PURPLE));
            tiles.add(new Tile(TileColor.ORANGE));
        }
    }

    /**
     * Get method
     * @return the whole Array-list of tiles in the bag
     */
    public List<Tile> getTiles() {
        return tiles;
    }

    /**
     * Used to insert tiles, one by one
     */
    public void insertTile(Tile t){
        tiles.add(t);
    }

    /**
     * Appends all the tiles, which was previously in the board before the refill, in the array-List at the end of "tiles"
     * @param t
     */
    public void insertAllTile(List<Tile>t){
        tiles.addAll(t);
    }

    // DA SISTEMARE : Si può fare semplicemente con il metodo remove(tile)
    public void removeTile(Tile tile){
        boolean removed = false;
        for (int i = 0; i<tiles.size() && !removed; i++)
            if (tiles.get(i).getColor() == tile.getColor()) {
                tiles.remove(i);
                removed = true;
            }
    }

    /**
     * Counter Method
     * @param tc
     * @return Number of tiles in 'tc' coloration
     */
    private int countTiles(TileColor tc){
        int count = 0;
        for (Tile t : tiles)
            if (t.getColor() == tc)
                count++;
        return count;
    }

    /**
     * Counter method
     * @return size of "tiles"
     */
    public int countNumOfTiles(){
        return tiles.size();
    }

    /**
     * Draw out all the Tiles in the bag, even they are more than requested, after computing
     * by the Board, it will use the method update(list) (use retainAll(Collection x), an ArrayList method) which Retains
     * only the elements in this list that are contained in the specified collection.
     * @return tiles Array-List
     */
    public List<Tile> drawOutTiles(){
        return tiles;
    }

    /**
     * After EVERY Draw-Out, the controller put in the bag only the remaining Tiles with this method
     * @param list
     */
    public void updateBag(List<Tile> list){
        tiles.retainAll(list);
    }
}
