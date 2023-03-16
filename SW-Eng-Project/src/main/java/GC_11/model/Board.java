package GC_11;

public class Board {

    private Tile[][] chessBoard;
    private Bag bag;


    public Board(int n){

    }
    public Tile getTile(int x, int y){
        return chessBoard[x][y];
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
