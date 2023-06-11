package GC_11;
//NON TOCCARE ASSOLUTAMENTE
//App per provare il model in locale

import GC_11.controller.Controller;
import GC_11.model.Game;
import GC_11.model.GameViewMessage;
import GC_11.view.GameCLI;

import java.util.ArrayList;
import java.util.List;

public class DavideApp {
    public static void main( String[] args ){
        /*//Just a sample code to try if everything works fine
        List<String> tmpPlayerNames = new ArrayList<String>();
        tmpPlayerNames.add("Pippo");
        tmpPlayerNames.add("Pluto");
        tmpPlayerNames.add("Paperino");
        tmpPlayerNames.add("Giuseppe");
        Game model = new Game(tmpPlayerNames);
        GameViewMessage modelView = new GameViewMessage(model, null);
        GameCLI view = new GameCLI(model.getCurrentPlayer());
        view.setModelView(modelView);
        Controller controller = new Controller(model);
        model.setListener(view);
        view.setListener(controller);
        view.run();*/

    }
}
