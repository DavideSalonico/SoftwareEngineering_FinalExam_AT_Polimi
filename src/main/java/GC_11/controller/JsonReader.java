package GC_11.controller;

import GC_11.model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * JsonReader is a utility class that provides methods to read data from JSON files.
 */
public class JsonReader {
    private List<Triplet> read;
    private List<Player> players;
    private static JSONParser parser = new JSONParser();

    /**
     * Constructs a JsonReader object with the specified list of players.
     *
     * @param players the list of players
     */
    public JsonReader(List<Player> players) {
        this.players = players;
        JSONParser parser = new JSONParser();
    }

    /**
     * Constructs a JsonReader object.
     */
    public JsonReader() {
        this.parser = new JSONParser();
    }

    /**
     * This static method takes as parameter the id and return the personal goal card with that corresponding id
     *
     * @param index is the integer that represent a random number between 0 and 11
     * @return the personal goal card with that specific id saved in a JSON file
     */
    public static PersonalGoalCard readPersonalGoalCard(int index) {
        try (Reader inputFile = new FileReader("src//main//resources//PersonalGoalCards.JSON")) {
            JSONArray cards = (JSONArray) parser.parse(inputFile);
            if (index >= 0 && index < 12) {
                JSONObject card = (JSONObject) cards.get(index);

                JSONArray coordinates = (JSONArray) card.get("tiles");

                List<Triplet> listOfCoordinates = new ArrayList<Triplet>();
                for (int i = 0; i < coordinates.size(); i++) {
                    JSONObject goal = (JSONObject) coordinates.get(i);

                    Long r = (Long) goal.get("row");
                    Long c = (Long) goal.get("column");
                    String color = (String) goal.get("color");

                    int row = r.intValue();
                    int column = c.intValue();
                    TileColor tc = TileColor.StringToColor(color);

                    listOfCoordinates.add(new Triplet(row, column, tc));
                }
                return new PersonalGoalCard(index, listOfCoordinates);
            } else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Reads the prohibited coordinates based on the number of players from a JSON file.
     *
     * @param numberOfPlayers the number of players
     * @return a list of prohibited coordinates
     */
    public static List<Coordinate> readCoordinate(int numberOfPlayers) {

        // Try to read the file, otherwise throw an exception
        try (Reader inputFile = new FileReader("src//main//resources//ProhibitedCoordinates.JSON")) {

            // Read the entire object with all the coordinates
            JSONObject allCoordinates = (JSONObject) parser.parse(inputFile);

            // First read the coordinates for four players that are equals for each number of players
            JSONArray fourPlayersCoordinates = (JSONArray) allCoordinates.get("prohibited4Players");

            // List of all prohibited Coordinates
            List<Coordinate> coordinatesList = extractCoordinates(fourPlayersCoordinates);


            // If the number of players is 3, add the additional coordinates into the list
            if (numberOfPlayers == 3) {

                JSONArray threePlayersCoordintes = (JSONArray) allCoordinates.get("prohibited3Players");

                coordinatesList.addAll(extractCoordinates(threePlayersCoordintes));

            }
            // If the number of players is 2, add the additional coordinates into the list
            if (numberOfPlayers == 2) {
                JSONArray twoPlayersCoordintes = (JSONArray) allCoordinates.get("prohibited2Players");

                coordinatesList.addAll(extractCoordinates(twoPlayersCoordintes));
            }
            return coordinatesList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Extracts coordinates from a JSONArray and returns a list of Coordinate objects.
     *
     * @param jsonCoordinates the JSONArray containing the coordinates
     * @return a list of Coordinate objects
     */
    private static List<Coordinate> extractCoordinates(JSONArray jsonCoordinates) {

        // List of all prohibited Coordinates
        List<Coordinate> coordinatesList = new ArrayList<Coordinate>();

        // For every JSON object coordinates in JSON array fourPlayerCoordinates, read the couple of coordinates x and y and add it into the list.
        for (int i = 0; i < jsonCoordinates.size(); i++) {

            JSONObject coordinates = (JSONObject) jsonCoordinates.get(i);

            Long row = (Long) coordinates.get("r");
            Long column = (Long) coordinates.get("c");

            int r = (int) row.intValue();
            int c = (int) column.intValue();

            coordinatesList.add(new Coordinate(r, c));
        }
        return coordinatesList;
    }
}


// https://stackoverflow.com/questions/10926353/how-to-read-json-file-into-java-with-simple-json-library


