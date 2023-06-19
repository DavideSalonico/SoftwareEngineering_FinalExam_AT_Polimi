package GC_11.controller;

import GC_11.model.GameViewMessage;
import GC_11.model.Triplet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileWriter;
import java.io.IOException;

public class JsonWriter {

    private GameViewMessage gameView;
    private static JSONParser parser = new JSONParser();

    public static void saveGame(GameViewMessage gameView) {
        gameView = gameView;

        // Creazione file JSON
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try(FileWriter writer = new FileWriter("src//main//resources//GameView.JSON")) {
            gson.toJson(gameView, writer);
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
