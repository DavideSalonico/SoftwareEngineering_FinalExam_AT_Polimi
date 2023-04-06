package GC_11;


import GC_11.model.Board;
import GC_11.model.Coordinate;
import GC_11.model.Tile;
import GC_11.model.TileColor;
import org.junit.jupiter.api.Test;
import static junit.framework.Assert.*;

import java.util.List;

public class BoardTest {
    //TODO: To be implemented by Mattia


  /*  @Test
    void checkRightInit() {

        List<Coordinate> coordinateProibite = GC_11.Controller.JsonReader.readCoordinate(2);
        Board board = new Board(coordinateProibite);

        for(int r =0; r<9;r++){
            for(int c =0; c<9;c++){
                Tile t = board.getTile(r,c);
                    boolean proibita = false;
                    for(int i=0; i<coordinateProibite.size();i++){
                        if(coordinateProibite.get(i).getRow() == r && coordinateProibite.get(i).getColumn()==c){
                            proibita = true;
                        }
                }
                    if(proibita){
                        System.out.println(r + " " + c + ": proibita");
                    }
                    else{
                        System.out.println(r + " " + c + ": non proibita");
                    }

            }
        }

        // Check if the board is correctly initialized according to the number of players.

    }
*/
    @Test
    void checkTiledRemoved(){

        // Check if a tile is actually removed after being drawn by a player
    }

    @Test
    void checkRefill(){

        // Check if the board is correctly refilled
    }


}
