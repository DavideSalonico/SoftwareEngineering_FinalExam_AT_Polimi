package GC_11.model;

import java.util.List;

public class Board {

    private Tile[][] chessBoard;
    private Bag bag;

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

    public Tile getTile(int r, int c){
        return chessBoard[r][c];
    }

    public void setTile(int x, int y, Tile t){
        chessBoard[x][y] = t;
    }

    public boolean removeTile(int x, int y){

        if (chessBoard[x][y] == null){
            return false;
        }
        else{
            return true;
        }
    }

    //public int checkDraw(){}

    public Bag getBag(){
        return this.bag;
    }
}
