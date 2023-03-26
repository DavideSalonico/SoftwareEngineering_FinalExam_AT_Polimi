package GC_11.model;

import java.util.ArrayList;
import java.util.List;

public class Bag {

    private List<Tile> tiles; // 132 tiles, 22 x 6


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
    public void removeTile(Tile tile){
        boolean removed = false;
        for (int i = 0; i<tiles.size() && !removed; i++)
        {
            if(tiles.get(i).getColor()==tile.getColor()){
                tiles.remove(i);
                removed= true;
            }
        }
    }

    private int countTiles(TileColor tc){
        int count = 0;
        for (Tile t : tiles){
            if (t.getColor() == tc)
            {
                count++;
            }
        }
        return count;
    }

    /**
     * Counter method
     * @return size of "tiles"
     */
    public int countNumOfTiles(){
        return tiles.size();
    }
}
