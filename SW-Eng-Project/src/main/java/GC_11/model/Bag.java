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

    // Used to insert tiles which was previously in the board before the refill
    public void insertTile(Tile t){

    }

    public void removeTile(Tile t){


    }
}
