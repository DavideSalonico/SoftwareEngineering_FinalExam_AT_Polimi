package GC_11.model;

import GC_11.controller.JsonReader;
import GC_11.exceptions.IllegalMoveException;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board implements PropertyChangeListener, Serializable {

    private Tile[][] chessBoard;
    private Bag bag;

    public void setListener(PropertyChangeListener listener) {
        this.listener = listener;
    }

    //The listener must be Game
    transient PropertyChangeListener listener;

    public Board(Board board){
        this.chessBoard = board.getChessBoard();
        this.bag = board.getBag();
    }

    /**
     * Duplicate method
     * @return
     */
    private Tile[][] getChessBoard() {
        return chessBoard;
    }

    /**
     * Constructor of Board entity, it initializes all the 9x9 matrix in Tile.EMPTY then sets the prohibited cells
     * into TileColor.PROHIBITED (get coordinates from JSON file)
     * @param coordinateList
     */
    public Board(List<Coordinate> coordinateList){

        this.bag = new Bag();
        this.bag.setListener(this);
        chessBoard = new Tile[9][9];
        for (int i =0; i<9;i++){
            for (int j=0; j<9;j++){
                chessBoard[i][j] = new Tile(TileColor.EMPTY);
            }
        }
        for (Coordinate c : coordinateList){
            this.chessBoard[c.getRow()][c.getColumn()] = new Tile(TileColor.PROHIBITED);
        }

        setBoard();
    }

    /**
     * Builder (overloaded)
     * @param num is the number of players
     */
    public Board(int num){
        this.bag = new Bag();
        this.bag.setListener(this);
        this.chessBoard = new Tile[9][9];
        List<Coordinate> coordinateList = new ArrayList<Coordinate>();
        JsonReader json = new JsonReader();
        coordinateList = json.readCoordinate(num);

        for (int i =0; i<9;i++){
            for (int j=0; j<9;j++){
                chessBoard[i][j] = new Tile(TileColor.EMPTY);
            }
        }
        for (Coordinate c : coordinateList){
            this.chessBoard[c.getRow()][c.getColumn()] = new Tile(TileColor.PROHIBITED);
        }

        setBoard();
    }


    /**
     * It sets empty cells of chessboard into some random Tile picked from the bag (it uses bag's methods)
     */
    private void setBoard() {
        int randomNum;
        List <Tile> tiles = bag.drawOutTiles();
        for (int line =0; line<9; line++){
            for (int column = 0; column<9; column++) {
                if(chessBoard[line][column].equals(TileColor.EMPTY)) {
                    randomNum = new Random().nextInt(tiles.size());
                    this.chessBoard[line][column] = tiles.get(randomNum);
                    tiles.remove(randomNum);
                }
            }
        }
        this.bag.updateBag(tiles);
    }

    /**
     * Return Tile at line 'r' and column 'c'
     * @param l = line
     * @param c = column
     * @return Tile
     */
    public Tile getTile(int l, int c){
        return chessBoard[l][c];
    }

    public void setTile(int x, int y, Tile t){
        chessBoard[x][y] = t;
    }

    /**
     * Return picked Tile from Board, it creates new Tile with TileColor.EMPTY (Immutable object),
     * the controller firstly call checkLegalMove method and then the drawTile method
     * @param l = line
     * @param c = column
     * @return picked Tile
     */
    public Tile drawTile(int l, int c) throws IllegalMoveException {
        if(chessBoard[l][c].getColor().equals(TileColor.PROHIBITED) || chessBoard[l][c].getColor().equals(TileColor.EMPTY))
            throw new IllegalMoveException("You can't pick this Tile!");
        Tile picked = new Tile(chessBoard[l][c]);

        PropertyChangeEvent evt = new PropertyChangeEvent(
                this,
                "TILE_PICKED",
                this.chessBoard[l][c],
                new Tile(TileColor.EMPTY));
        chessBoard[l][c] = new Tile(TileColor.EMPTY);
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
                    if(l != 0 && !chessBoard[l-1][c].getColor().equals(TileColor.PROHIBITED) &&
                                 !chessBoard[l-1][c].getColor().equals(TileColor.EMPTY))
                         return 0;
                    if(c  != 0 && !chessBoard[l][c-1].getColor().equals(TileColor.PROHIBITED) &&
                            !chessBoard[l][c-1].getColor().equals(TileColor.EMPTY))
                        return 0;
                    if(l != 8 && !chessBoard[l+1][c].getColor().equals(TileColor.PROHIBITED) &&
                            !chessBoard[l+1][c].getColor().equals(TileColor.EMPTY))
                        return 0;
                    if(c != 8 && !chessBoard[l][c+1].getColor().equals(TileColor.PROHIBITED) &&
                            !chessBoard[l][c+1].getColor().equals(TileColor.EMPTY))
                        return 0;

                    counter++;
                }
            }
        }
        //probabilmente non ci interessa sapere quante Tiles isolate ci sono, ma lascio per future necessità
        return counter;
    }

    /**
     * Checks (using checkDraw method) if the Board needs a Tiles' refill, if true, it will clean the board from remaining
     * Tiles inserting them into the Bag and after that it will set again all the Board's cells with random tiles using setBoard() method
     */
    public void needRefill(){
        if(checkDraw()>0){
            for(int line = 0; line<9; line++){
                for (int column = 0; column <9; column++){
                    if(!chessBoard[line][column].getColor().equals(TileColor.PROHIBITED) &&
                            !chessBoard[line][column].getColor().equals(TileColor.EMPTY)) {
                        this.bag.insertTile(chessBoard[line][column]);
                        chessBoard[line][column] = new Tile(TileColor.EMPTY);
                    }
                }
            }
            /* Can't manage to give right OldValue e NewValue */
            PropertyChangeEvent evt = new PropertyChangeEvent(
                    this,
                    "BOARD_REFILLED",
                    this.chessBoard,
                    this.chessBoard);
            this.listener.propertyChange(evt);
            setBoard();

        }
        // else nothing
    }


    /**
     * Getter method of bag
     * @return bag of remaining Tiles
     */
    public Bag getBag(){
        return this.bag;
    }

    public void print(){
        for(int i=0; i < 9; i++){
            for(int j=0; j < 9; j++){
                System.out.print("\t" + getTile(i, j).getColor().toString().charAt(0));
            }
            System.out.println();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.listener.propertyChange(evt);
    }
}


