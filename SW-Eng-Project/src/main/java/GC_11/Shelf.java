package GC_11;

import java.util.List;

public class Shelf {


    // Attributes
    private Tile[][] myShelf;


    // Methods
    public Shelf(){
        myShelf= new Tile[5][6];
    }

    private void addTile(Tile t, int column){

    }

    public void addTiles(List<Tile> list, int column){

    }

    public Tile getTile(int x, int y){
        return myShelf[x][y];
    }
}
