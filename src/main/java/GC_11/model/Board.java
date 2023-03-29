package GC_11.model;

import GC_11.exceptions.IllegalMoveException;

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
     * Return picked Tile from Board, it creates new Tile with TileColor.EMPTY (Immutable object),
     * the controller firstly call checkLegalMove method and then the drawTile method
     * @param x = line
     * @param y = column
     * @return picked Tile
     */
    public Tile drawTile(int x, int y) throws IllegalMoveException {
        if(chessBoard[l][c].getColor().equals(TileColor.PROHIBITED) || chessBoard[l][c].getColor().equals(TileColor.EMPTY))
            throw new IllegalMoveException("You can't pick this Tile!");
        Tile picked = new Tile(chessBoard[x][y]);
        chessBoard[x][y] = new Tile(TileColor.EMPTY);
        return picked;
    }

    /**
     * It checks if there are only isolated Tiles on the Board so the Board needs a refill of Tiles
     * @return number of isolated Tiles if the board needs a refill, else it returns 0
     */
    public int checkDraw(){
        int counter = 0;
        for (int l =0; l<9;l++){
            for (int c=0; c<9;c++) {
                if ( !chessBoard[l][c].getColor().equals(TileColor.PROHIBITED) &&
                     !chessBoard[l][c].getColor().equals(TileColor.EMPTY) ){
                    if(l > 0 && !chessBoard[l-1][c].getColor().equals(TileColor.PROHIBITED) &&
                                 !chessBoard[l-1][c].getColor().equals(TileColor.EMPTY))
                         return 0;
                    if(c > 0 && !chessBoard[l][c-1].getColor().equals(TileColor.PROHIBITED) &&
                            !chessBoard[l][c-1].getColor().equals(TileColor.EMPTY))
                        return 0;
                    if(l < 9 && !chessBoard[l+1][c].getColor().equals(TileColor.PROHIBITED) &&
                            !chessBoard[l+1][c].getColor().equals(TileColor.EMPTY))
                        return 0;
                    if(c < 9 && !chessBoard[l][c+1].getColor().equals(TileColor.PROHIBITED) &&
                            !chessBoard[l][c+1].getColor().equals(TileColor.EMPTY))
                        return 0;

                    counter++;
                }
            }
        }
        return counter;
    }

    /**
     * Getter method of bag
     * @return bag of remaining Tiles
     */
    public Bag getBag(){
        return this.bag;
    }
}


