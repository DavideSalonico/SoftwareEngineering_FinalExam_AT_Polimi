package GC_11.model;

import java.util.List;

public class Board {

    private Tile[][] chessBoard;
    private Bag bag;

    /**
     * Constructor of Board entity, it initializes all the 9x9 matrix in Tile.EMPTY then sets the prohibited cells
     * into TileColor.PROHIBITED (get coordinates from JSON file)
     * @param coordinateList
     */
    public Board(List<Coordinate> coordinateList){

        this.bag = new Bag();
        chessBoard = new Tile[9][9];
        for (int i =0; i<9;i++){
            for (int j=0; j<9;j++){
                chessBoard[i][j] = new Tile(TileColor.EMPTY);
            }
        }
        for (Coordinate c : coordinateList){
            this.chessBoard[c.getRow()][c.getColumn()] = new Tile(TileColor.PROHIBITED);
        }
    }

    /**
     * Return Tile at line 'r' and column 'c'
     * @param r = line
     * @param c = column
     * @return Tile
     */
    public Tile getTile(int r, int c){
        return chessBoard[r][c];
    }

    public void setTile(int x, int y, Tile t){
        chessBoard[x][y] = t;
    }

    /**
     * Remove Tile from Board, it creates new Tile with TileColor.EMPTY (Immutable object)
     * @param x = line
     * @param y = column
     */
    public void drawTile(int x, int y){
        chessBoard[x][y] = new Tile(TileColor.EMPTY);

    }

    /**
     * It checks if there are only isolated Tiles on the Board so the Board needs a refill of Tiles
     * @return number of isolated Tiles if the board needs a refill, else it return 0
     */
    public int checkDraw(){
        int counter = 0;
        for (int l =0; l<9;l++){
            for (int c=0; c<9;c++) {
                    if ( chessBoard[l][c].get)
            }
        }
    }

    /**
     * Getter method of bag
     * @return bag of remaining Tiles
     */
    public Bag getBag(){
        return this.bag;
    }
}
