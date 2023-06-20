package GC_11.controller;

import GC_11.model.*;
import GC_11.model.common.CommonGoalCard;
import com.google.gson.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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

            JsonObject json = new JsonObject();   // Oggetto principale

            // Board

            String jsonBoard = gson.toJson(gameView.getBoard());
            JsonObject board= JsonParser.parseString(jsonBoard).getAsJsonObject();
            json.add("board", board);


            // Players
            String jsonPlayers = gson.toJson(gameView.getPlayers());
            JsonArray jsonArrayPlayers = JsonParser.parseString(jsonPlayers).getAsJsonArray();
            json.add("players", jsonArrayPlayers);

            // Common goal cards

            int[] commonGoalCards = new int[2];
            List<String> WinngingPlayers = new ArrayList<String>();
            for (CommonGoalCard commonGoalCard : gameView.getCommonGoalCards()) {
                for (Player player : commonGoalCard.getWinningPlayers()) {
                    WinngingPlayers.add(player.getNickname());
                }
            }

            JsonObject commonGoalCard1 = new JsonObject();
            commonGoalCard1.addProperty("id", gameView.getCommonGoalCards().get(0).getId());
            commonGoalCard1.addProperty("winningPlayers", WinngingPlayers.toString());
            JsonObject commonGoalCard2 = new JsonObject();
            commonGoalCard2.addProperty("id", gameView.getCommonGoalCards().get(1).getId());
            commonGoalCard2.addProperty("winningPlayers", WinngingPlayers.toString());


            JsonArray jsonArrayCommonGoalCards = new JsonArray();
            jsonArrayCommonGoalCards.add(commonGoalCard1);
            jsonArrayCommonGoalCards.add(commonGoalCard2);

            json.add("commonGoalCards", jsonArrayCommonGoalCards);



            // Write JSON file
            gson.toJson(json, writer);
            writer.flush();


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
