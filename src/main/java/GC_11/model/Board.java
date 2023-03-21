package GC_11.model;

public class Board {

    private Tile[][] chessBoard;
    private Bag bag;


    public Board(int nPlayer){
        this.bag = new Bag();
        chessBoard = new Tile[9][9];
        for (int i =0; i<9;i++){
            for (int j=0; j<9;j++){
                chessBoard[i][j] = new Tile(TileColor.EMPTY);
            }
        }
        initProhibitedCells();
        if (nPlayer==2){
            init2PlayersBoard();
        }
        else if(nPlayer==3){
            init3PlayersBoard();
        }
    }

    private void initProhibitedCells(){
        chessBoard[0][0] = new Tile(TileColor.PROHIBITED);
        chessBoard[0][1] = new Tile(TileColor.PROHIBITED);
        chessBoard[0][2] = new Tile(TileColor.PROHIBITED);
        chessBoard[0][3] = new Tile(TileColor.PROHIBITED);
        chessBoard[0][6]  = new Tile(TileColor.PROHIBITED);
        chessBoard[0][7]  = new Tile(TileColor.PROHIBITED);
        chessBoard[0][8] = new Tile(TileColor.PROHIBITED);
        chessBoard[1][0] = new Tile(TileColor.PROHIBITED);
        chessBoard[1][1] = new Tile(TileColor.PROHIBITED);
        chessBoard[1][2] = new Tile(TileColor.PROHIBITED);
        chessBoard[1][6] = new Tile(TileColor.PROHIBITED);
        chessBoard[1][7] = new Tile(TileColor.PROHIBITED);
        chessBoard[1][8] = new Tile(TileColor.PROHIBITED);
        chessBoard[2][0]= new Tile(TileColor.PROHIBITED);
        chessBoard[2][1]= new Tile(TileColor.PROHIBITED);
        chessBoard[2][7]= new Tile(TileColor.PROHIBITED);
        chessBoard[2][8] = new Tile(TileColor.PROHIBITED);
        chessBoard[3][8] = new Tile(TileColor.PROHIBITED);
        chessBoard[5][0]= new Tile(TileColor.PROHIBITED);
        chessBoard[6][0] = new Tile(TileColor.PROHIBITED);
        chessBoard[6][1] = new Tile(TileColor.PROHIBITED);
        chessBoard[6][7] =  new Tile(TileColor.PROHIBITED);
        chessBoard[6][8] = new Tile(TileColor.PROHIBITED);
        chessBoard[7][0] = new Tile(TileColor.PROHIBITED);
        chessBoard[7][1] = new Tile(TileColor.PROHIBITED);
        chessBoard[7][2] = new Tile(TileColor.PROHIBITED);
        chessBoard[7][6] = new Tile(TileColor.PROHIBITED);
        chessBoard[7][8] = new Tile(TileColor.PROHIBITED);
        chessBoard[8][0] = new Tile(TileColor.PROHIBITED);
        chessBoard[8][1] = new Tile(TileColor.PROHIBITED);
        chessBoard[8][2] = new Tile(TileColor.PROHIBITED);
        chessBoard[8][5] = new Tile(TileColor.PROHIBITED);
        chessBoard[8][6] = new Tile(TileColor.PROHIBITED);
        chessBoard[8][7] = new Tile(TileColor.PROHIBITED);
        chessBoard[8][8] = new Tile(TileColor.PROHIBITED);

    }

    private void init2PlayersBoard(){
        chessBoard[0][4] = new Tile(TileColor.PROHIBITED);
        chessBoard[0][5] = new Tile(TileColor.PROHIBITED);
        chessBoard[1][3] = new Tile(TileColor.PROHIBITED);
        chessBoard[2][2] = new Tile(TileColor.PROHIBITED);
        chessBoard[2][6] = new Tile(TileColor.PROHIBITED);
        chessBoard[3][0] = new Tile(TileColor.PROHIBITED);
        chessBoard[3][7] = new Tile(TileColor.PROHIBITED);
        chessBoard[4][0] = new Tile(TileColor.PROHIBITED);
        chessBoard[4][8] = new Tile(TileColor.PROHIBITED);
        chessBoard[5][1] = new Tile(TileColor.PROHIBITED);
        chessBoard[5][8] = new Tile(TileColor.PROHIBITED);
        chessBoard[6][2] = new Tile(TileColor.PROHIBITED);
        chessBoard[6][6] = new Tile(TileColor.PROHIBITED);
        chessBoard[7][5] = new Tile(TileColor.PROHIBITED);
        chessBoard[8][3] = new Tile(TileColor.PROHIBITED);
        chessBoard[8][4] = new Tile(TileColor.PROHIBITED);
    }

    private void init3PlayersBoard(){
        chessBoard[0][4] = new Tile(TileColor.PROHIBITED);
        chessBoard[1][3] = new Tile(TileColor.PROHIBITED);
        chessBoard[3][7] = new Tile(TileColor.PROHIBITED);
        chessBoard[4][0] = new Tile(TileColor.PROHIBITED);
        chessBoard[4][8] = new Tile(TileColor.PROHIBITED);
        chessBoard[5][1] = new Tile(TileColor.PROHIBITED);
        chessBoard[7][5] = new Tile(TileColor.PROHIBITED);
        chessBoard[8][4] = new Tile(TileColor.PROHIBITED);
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
