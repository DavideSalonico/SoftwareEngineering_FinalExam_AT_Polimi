package GC_11.Controller;

import GC_11.model.*;
import GC_11.model.common.CommonGoalCard;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class JsonReader {
    private List<Triplet> read;
    private List<Player> players;
    private static JSONParser parser = new JSONParser();

    public JsonReader(List<Player> players) {
        this.players = players;
        JSONParser parser = new JSONParser();
    }

    public JsonReader() {
        this.parser = new JSONParser();
    }

    /**
     * This method set the PersonalGoalCard for every player reading the format of the personalGoalCard from
     * a JSON file
     *
     * @throws FileNotFoundException
     */

    public void setGoals() throws FileNotFoundException {

        try (Reader inputFile = new FileReader("JSON FILE PATH")) {
            JSONObject jsonObject = (JSONObject) parser.parse(inputFile);

            for (Player p : players) {
                int id = (int) jsonObject.get("id");
                PersonalGoalCard card;
            }
        } catch (Exception e) {
            //Handle Exception
        }
    }

    /**
     * This static method takes as parameter the id and return the personal goal card with that corresponding id
     * @param id is the integer that represent a random number between 0 and 11
     * @return the personal goal card with that specific id saved in a JSON file
     */

    public static PersonalGoalCard readPersonalGoalCard(int id)
    {
        try (Reader inputFile = new FileReader("JSON FILE PATH"))
        {
            JSONArray cards = (JSONArray) parser.parse(inputFile);
            if (id > 0 && id <= 11)
            {
                JSONObject card = (JSONObject) cards.get(id);
                JSONArray coordinates = (JSONArray) card.get("tiles");

                List <Triplet> listOfCoordinates = new ArrayList<Triplet>();
                for(int i=0; i< coordinates.size(); i++)
                {
                    int row = listOfCoordinates.get(i).getRow();
                    int column = listOfCoordinates.get(i).getCol();
                    TileColor tc = listOfCoordinates.get(i).getColor();
                    listOfCoordinates.add(new Triplet(row,column,tc));
                }
                PersonalGoalCard pgc = new PersonalGoalCard(listOfCoordinates);
                return pgc;
            }
            else
                return null;
        }
        catch(Exception e)
        {
                e.printStackTrace();
                return null;
        }
    }

    public static List<Coordinate> readCoordinate(int numberOfPlayers) throws Exception {

        // Return an exception if a wrong number of players is passed as parameter
        if(numberOfPlayers < 0 || numberOfPlayers >4)
        {
            throw new Exception("Wrong number of players");

        }
        else
        {
            // Try to read the file, otherwise throw an exception
            try (Reader inputFile = new FileReader("JSON FILE PATH"))
            {

                // Read the entire object with all the coordinates
                JSONObject allCoordinates = (JSONObject) parser.parse(inputFile);

                // First read the coordinates for four players that are equals for each number of players
                JSONArray fourPlayersCoordinates = (JSONArray) allCoordinates.get("prohibited4Players");

                // List of all prohibited Coordinates
                List <Coordinate> coordinatesList = extractCoordinates(fourPlayersCoordinates);


                // If the number of players is 3, add the additional coordinates into the list
                if(numberOfPlayers ==3){

                    JSONArray threePlayersCoordintes = (JSONArray) allCoordinates.get("prohibited3Players");

                    coordinatesList.addAll(extractCoordinates(threePlayersCoordintes));

                }
                // If the number of players is 2, add the additional coordinates into the list
                if(numberOfPlayers == 2){
                    JSONArray twoPlayersCoordintes = (JSONArray) allCoordinates.get("prohibited2Players");

                    coordinatesList.addAll(extractCoordinates(twoPlayersCoordintes));
                }
                return coordinatesList;
            }
            catch(Exception e)
            {
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * This function return a List<Coordinate> from a JSONArray which contain JSONObject with the prohibited coordinates for that specific number of players
     * @param jsonCoordinates is the JSONArray for a specific number of players
     * @return a List<Coordinate>
     */
    private static List <Coordinate> extractCoordinates(JSONArray jsonCoordinates){

        // List of all prohibited Coordinates
        List <Coordinate> coordinatesList = new ArrayList<Coordinate>();

        // For every JSON object coordinates in JSON array fourPlayerCoordinates, read the couple of coordinates x and y and add it into the list.
        for(int i=0; i< jsonCoordinates.size();i++){

            JSONObject coordinates = (JSONObject) jsonCoordinates.get(i);

            Long row = (Long) coordinates.get("r");
            Long column = (Long) coordinates.get("c");

            int r = (int ) row.intValue();
            int c = (int ) column.intValue();

            coordinatesList.add( new Coordinate(r,c));
        }
        return coordinatesList;
    }
}



        // https://stackoverflow.com/questions/10926353/how-to-read-json-file-into-java-with-simple-json-library


