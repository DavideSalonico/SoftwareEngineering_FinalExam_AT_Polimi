package GC_11;

import GC_11.controller.Controller;
import GC_11.model.Game;
import GC_11.model.GameView;
import GC_11.view.GUI.GUIModel;

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
        GameView modelView = new GameView(model, null);
        //CLIview view = new CLIview(model.getCurrentPlayer());
        //view.setModelView(modelView);
        Controller controller = new Controller(model);
        //model.setListener(view);
        //view.setListener(controller);
        //view.run();



        GUIModel guiModel = new GUIModel(modelView,"Pippo");
    }
}
