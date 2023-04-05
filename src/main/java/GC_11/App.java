package GC_11;

import GC_11.model.Game;
import GC_11.model.Tile;
import GC_11.model.TileColor;

import java.util.ArrayList;
import java.util.List;

public class App
{
    public static void main( String[] args ){
        //Just a sample code to try if everything works fine
        List<String> tmpPlayerNames = new ArrayList<String>();
        tmpPlayerNames.add("Pippo");
        tmpPlayerNames.add("Pluto");
        tmpPlayerNames.add("Paperino");
        Game game = new Game(tmpPlayerNames);
        game.getBoard().setListener(game);

        game.getBoard().print();
        game.getBoard().setTile(0, 0, new Tile(TileColor.GREEN));



    }
}
