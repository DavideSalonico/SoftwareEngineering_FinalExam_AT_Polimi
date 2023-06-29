package GC_11.controller;

import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.model.*;
import GC_11.model.common.CommonGoalCard;
import GC_11.network.message.GameViewMessage;
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

    public static void saveGame(Game game) {
        game = game;

        // Creazione gson
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter("src//main//resources//GameView.JSON")) {

            JsonObject json = new JsonObject();   // Oggetto principale

            //Attributes

            json.addProperty("endGame", game.isEndGame());
            json.addProperty("lastTurn", game.getLastTurn());
            json.addProperty("lastPlayer",game.getEndPlayer());

            // Board

            String jsonBoard = gson.toJson(game.getBoard());
            JsonObject board = JsonParser.parseString(jsonBoard).getAsJsonObject();
            json.add("board", board);

            // Bag
            String jsonBag = gson.toJson(game.getBoard().getBag());
            JsonObject bag = JsonParser.parseString(jsonBag).getAsJsonObject();
            json.add("bag", bag);


            // Players
            String jsonPlayers = gson.toJson(game.getPlayers());
            JsonArray jsonArrayPlayers = JsonParser.parseString(jsonPlayers).getAsJsonArray();
            json.add("players", jsonArrayPlayers);

            // Common goal cards

            int[] commonGoalCards = new int[2];
            List<String> WinngingPlayers = new ArrayList<String>();
            JsonArray jsonArrayCommonGoalCards = new JsonArray();

            for (CommonGoalCard commonGoalCard : game.getCommonGoal()) {
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

            // CurrentPlayer

            JsonObject currentPlayer = new JsonObject();
            currentPlayer.addProperty("nickname", game.getCurrentPlayer().getNickname());
            json.add("currentPlayer", currentPlayer);


            // Write JSON file
            gson.toJson(json, writer);
            writer.flush();


            System.out.println("File JSON creato correttamente");
        } catch (IOException e) {
            System.out.println("Errore nella scrittura del file JSON del salvataggio della partita");
            e.printStackTrace();
        }
    }

    public static Game loadGame() {
        try (FileReader reader = new FileReader("src//main//resources//GameView.JSON")) {
            JsonObject game = JsonParser.parseReader(reader).getAsJsonObject();

            //Retrieve Attributes

            boolean endGame = game.get("endGame").getAsBoolean();
            boolean lastTurn = game.get("lastTurn").getAsBoolean();
            String endPlayer = null;
            try{
                endPlayer = game.get("lastPlayer").getAsString();
            }
            catch (NullPointerException e){
                endPlayer = null;
            }

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

            JsonObject currentPlayer = game.get("currentPlayer").getAsJsonObject();
            String nickname = currentPlayer.get("nickname").getAsString();

            Game loadedGame = new Game(playersList, board1, commonGoalCardsIds, winningPlayersList.get(0), winningPlayersList.get(1), nickname);
            loadedGame.setEndGame(endGame);
            loadedGame.setLastTurn(lastTurn);
            if(endPlayer != null){
                loadedGame.setEndPlayer(endPlayer);
            }
            System.out.println("Partita caricata correttamente");
            return loadedGame;

        } catch (FileNotFoundException e) {
            System.out.println("Errore nell'apertura del file JSON del salvataggio della partita");
        } catch (IOException e) {
            System.out.println("Errore nell'apertura del file JSON del salvataggio della partita");
        } catch (ColumnIndexOutOfBoundsException e) {
            System.out.println("Errore nell'apertura del file JSON del salvataggio della partita");
            throw new RuntimeException(e);
        }
        return null;
    }

    public static List<String> getNicknames() {
        List<String> playersNicknames = null;
        try (FileReader reader = new FileReader("src//main//resources//GameView.JSON")) {
            JsonElement jsonElement = JsonParser.parseReader(reader);
            if(jsonElement.isJsonNull()){
                return null;
            }
            JsonObject game = jsonElement.getAsJsonObject();

            playersNicknames = new ArrayList<String>();

            String players = game.get("players").toString();
            JsonArray playersArray = JsonParser.parseString(players).getAsJsonArray();

            for (int i = 0; i < playersArray.size(); i++) {

                JsonObject jsonPlayer = playersArray.get(i).getAsJsonObject();

                String nickname = jsonPlayer.get("nickname").toString();
                nickname = nickname.replace("\"", "");

                playersNicknames.add(nickname);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return playersNicknames;
    }
}
