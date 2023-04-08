package GC_11;

import GC_11.controller.Controller;
import GC_11.model.Game;
import GC_11.model.GameView;
import GC_11.model.Tile;
import GC_11.model.TileColor;
import GC_11.view.CLIview;

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
        Game model = new Game(tmpPlayerNames);
        GameView modelView = new GameView(model);
        CLIview view = new CLIview(model.getCurrentPlayer());
        Controller controller = new Controller(model, view);

        System.out.println("Hello World!");
        model.setListener(view);
        model.setEndGame(true);
    }
}
