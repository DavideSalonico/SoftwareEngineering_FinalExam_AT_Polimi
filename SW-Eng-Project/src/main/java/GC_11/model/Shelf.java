package GC_11.model;

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
        if(column <0 || column >4){
            // exception
        }
        else
        {
            // Controllare se la colonna ha abbastanza spazi, altrimenti generare eccezione

            // Aggiungere le tessere nell'ordine scelto dall'utente
        }

    }

    public Tile getTile(int x, int y){
        return myShelf[x][y];
    }
}
