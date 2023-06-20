package GC_11.controller;

import GC_11.model.*;
import GC_11.model.common.CommonGoalCard;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JsonWriter {

    private GameViewMessage gameView;
    private static JSONParser parser = new JSONParser();


    public static void deleteGame(){
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = null;
        try{
            jsonObject = (JSONObject) jsonParser.parse(new FileReader("src//main//resources//GameView.JSON"));
            jsonObject.entrySet().clear();
        }
        catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        try (FileWriter file = new FileWriter("src//main//resources//GameView.JSON")) {
            file.write(jsonObject.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveGame(GameViewMessage gameView) {
        gameView = gameView;

        // Creazione gson
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try(FileWriter writer = new FileWriter("src//main//resources//GameView.JSON")) {

            String json = gson.toJson(gameView.getBoard());
            writer.write(json);
            writer.flush();
            //gson.toJson(gameView.getBoard(), writer);

            System.out.println("File JSON creato correttamente");
        } catch (IOException e) {
            System.out.println("Errore nella scrittura del file JSON del salvataggio della partita");
            e.printStackTrace();
        }
    }


    public GameViewMessage readGame() {
        // Leggi file JSON nel path
        return gameView;
    }

}
