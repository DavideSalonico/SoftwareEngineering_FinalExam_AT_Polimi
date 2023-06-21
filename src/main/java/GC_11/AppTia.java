package GC_11;

import GC_11.controller.JsonWriter;
import GC_11.model.Game;
import GC_11.model.GameViewMessage;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AppTia {
    public static void main(String[] args) throws IOException, ParseException {

        List<String> tmpPlayerNames = new ArrayList<String>();
        tmpPlayerNames.add("Pippo");
        tmpPlayerNames.add("Pluto");
        tmpPlayerNames.add("Paperino");
        tmpPlayerNames.add("Giuseppe");
        Game g = new Game(tmpPlayerNames);
        GameViewMessage modelView = new GameViewMessage(g, null);
        //JsonWriter.saveGame(modelView);
        //JsonWriter.deleteGame();
        JsonWriter.loadGame();
    }
}
