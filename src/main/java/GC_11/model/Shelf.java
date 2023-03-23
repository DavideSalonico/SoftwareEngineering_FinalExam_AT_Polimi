package GC_11.model;

import java.util.List;
import GC_11.exceptions.columnIndexOutOfBoundsException;
import GC_11.exceptions.notEnoughFreeSpacesException;

public class Shelf {

    private Tile[][] myShelf;

    public Shelf(){
        myShelf= new Tile[6][5];
        for (int r=0; r<6;r++){
            for (int c=0; c<5; c++){
                myShelf[r][c] = new Tile(TileColor.EMPTY);
            }
        }
    }

    private void addTile(Tile t, int column){

    }

    private int freeSpaces(int column){
        int free = 0;
        for(int i=0; i<6; i++){
            if(myShelf[i][column].getColor() == TileColor.EMPTY){
                free++;
            }
        }
        return free;
    }

    public void addTiles(List<Tile> tileList, int column) throws notEnoughFreeSpacesException, columnIndexOutOfBoundsException {
        if(column <0 || column >4){
            throw new columnIndexOutOfBoundsException(column);
        }
        else
        {
            if(freeSpaces(column) < tileList.size()){

                throw new notEnoughFreeSpacesException(column, tileList.size());
            }
            else {
                int firstFreeSpace = 6- freeSpaces(column);
                for (Tile t : tileList){
                    myShelf[firstFreeSpace][column] = t;
                    firstFreeSpace++;
                }
            }
        }
    }

    /**
     *
     * @param line indicates the line to check
     * @param column indicates the column ti check
     * @return the tile in the position required
     * @throws columnIndexOutOfBoundsException when the position required
     * is outside the matrix of yhe shelf
     */
    public Tile getTile(int line, int column) throws columnIndexOutOfBoundsException{
        if (line<0 || line>=6 || column<0 || column>=5){
            throw new columnIndexOutOfBoundsException(-1);
        }
        else{
            return myShelf[line][column];
        }
    }
}
