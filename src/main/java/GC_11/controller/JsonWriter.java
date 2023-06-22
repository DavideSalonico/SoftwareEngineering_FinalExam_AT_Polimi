package GC_11.controller;

import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.model.*;
import GC_11.model.common.CommonGoalCard;
import GC_11.network.GameViewMessage;
import com.google.gson.*;
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


    public static void deleteGame() {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) jsonParser.parse(new FileReader("src//main//resources//GameView.JSON"));
            jsonObject.entrySet().clear();
        } catch (IOException | ParseException e) {
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

        try (FileWriter writer = new FileWriter("src//main//resources//GameView.JSON")) {

            JsonObject json = new JsonObject();   // Oggetto principale

            // Board

            String jsonBoard = gson.toJson(gameView.getBoard());
            JsonObject board = JsonParser.parseString(jsonBoard).getAsJsonObject();
            json.add("board", board);


            // Players
            String jsonPlayers = gson.toJson(gameView.getPlayers());
            JsonArray jsonArrayPlayers = JsonParser.parseString(jsonPlayers).getAsJsonArray();
            json.add("players", jsonArrayPlayers);

            // Common goal cards

            int[] commonGoalCards = new int[2];
            List<String> WinngingPlayers = new ArrayList<String>();
            JsonArray jsonArrayCommonGoalCards = new JsonArray();

            for (CommonGoalCard commonGoalCard : gameView.getCommonGoalCards()) {
                for (Player player : commonGoalCard.getWinningPlayers()) {
                    WinngingPlayers.add(player.getNickname());
                }
                JsonObject commonGoalCardJson = new JsonObject();
                commonGoalCardJson.addProperty("id", commonGoalCard.getId());
                commonGoalCardJson.addProperty("winningPlayers", WinngingPlayers.toString());
                jsonArrayCommonGoalCards.add(commonGoalCardJson);
                WinngingPlayers.clear();
            }


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

    public static void loadGame() {
        try (FileReader reader = new FileReader("src//main//resources//GameView.JSON")) {
            JsonObject game = JsonParser.parseReader(reader).getAsJsonObject();

            // Retrieving and building the board

            String board = game.get("board").toString();
            JsonObject jsonBoard = JsonParser.parseString(board).getAsJsonObject();
            JsonArray boardTiles = jsonBoard.get("chessBoard").getAsJsonArray();
            Board board1 = new Board();


            // Loading the board
            for (int i = 0; i < boardTiles.size(); i++) {
                JsonArray jsonTiles = boardTiles.get(i).getAsJsonArray();
                int j = 0;
                for (JsonElement je : jsonTiles) {
                    JsonObject jsonTile = je.getAsJsonObject();
                    TileColor tc = TileColor.valueOf(jsonTile.get("color").toString().replace("\"", ""));
                    Tile t = new Tile(tc, jsonTile.get("id").getAsInt());
                    board1.setTile(i, j, t);
                    j++;
                }
                //System.out.println();
            }

            // Retrieving players and their personal goal cards
            List<Player> playersList = new ArrayList<Player>();

            String players = game.get("players").toString();
            JsonArray playersArray = JsonParser.parseString(players).getAsJsonArray();


            for (int i = 0; i < playersArray.size(); i++) {

                JsonObject jsonPlayer = playersArray.get(i).getAsJsonObject();
                JsonObject jsonPersonalGoal = jsonPlayer.get("personalGoal").getAsJsonObject();

                String nickname = jsonPlayer.get("nickname").toString();
                nickname = nickname.replace("\"", "");


                int personalGoalCardId = jsonPersonalGoal.get("id").getAsInt();

                PersonalGoalCard personalGoalCard = JsonReader.readPersonalGoalCard(personalGoalCardId);

                Player player = new Player(nickname, personalGoalCard);
                JsonObject jsonShelf = jsonPlayer.get("shelf").getAsJsonObject();
                JsonArray jsonShelfTiles = jsonShelf.getAsJsonArray("myShelf");

                // Building the shelf of the player
                int r = 0;
                for (JsonElement column : jsonShelfTiles) {
                    JsonArray listOfTilesInColumn = column.getAsJsonArray();
                    int c = 0;
                    for (JsonElement tile : listOfTilesInColumn) {
                        JsonObject jsonTile = tile.getAsJsonObject();
                        TileColor tc = TileColor.valueOf(jsonTile.get("color").toString().replace("\"", ""));
                        Tile t = new Tile(tc, jsonTile.get("id").getAsInt());
                        player.getShelf().setTile(r, c, t);
                        c++;
                    }
                    r++;
                }
                playersList.add(player);
            }

            // Recupera le commonGoalCards
            JsonArray commonGoalCards = game.get("commonGoalCards").getAsJsonArray();
            int[] commonGoalCardsIds = new int[2];
            ArrayList<ArrayList<Player>> winningPlayersList = new ArrayList<ArrayList<Player>>();

            for (int i = 0; i < 2; i++) {
                JsonObject commonGoalCard = commonGoalCards.get(i).getAsJsonObject();
                commonGoalCardsIds[i] = commonGoalCard.get("id").getAsInt();
                String winningPlayers = commonGoalCard.get("winningPlayers").getAsString();
                winningPlayers = winningPlayers.replace("[", "");
                winningPlayers = winningPlayers.replace("]", "");
                String[] winningPlayersArray = winningPlayers.split(", ");
                ArrayList<Player> list = new ArrayList<Player>();
                winningPlayersList.add(list);
                for (String player : winningPlayersArray) {
                    for (Player p : playersList) {
                        if (p.getNickname().equals(player)) {
                            winningPlayersList.get(i).add(p);
                        }
                    }
                }
            }

            System.out.println("Partita caricata correttamente");
            //Game loadedGame = new Game(playersList, board1, commonGoalCardsIds);


            //System.out.println(loadedGame.getBoard());

        } catch (FileNotFoundException e) {
            System.out.println("Errore nell'apertura del file JSON del salvataggio della partita");
        } catch (IOException e) {
            System.out.println("Errore nell'apertura del file JSON del salvataggio della partita");
        } catch (ColumnIndexOutOfBoundsException e) {
            System.out.println("Errore nell'apertura del file JSON del salvataggio della partita");
            throw new RuntimeException(e);
        }
    }

    public GameViewMessage readGame() {
        // Leggi file JSON nel path
        return gameView;
    }

}
