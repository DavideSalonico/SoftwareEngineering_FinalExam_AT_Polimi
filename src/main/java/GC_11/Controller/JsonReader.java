package GC_11.Controller;

import GC_11.model.PersonalGoalCard;
import GC_11.model.Player;
import GC_11.model.TileColor;
import GC_11.model.Triplet;
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
    private JSONParser parser;

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

    public PersonalGoalCard readPersonalGoalCard(int id)
    {
        try (Reader inputFile = new FileReader("JSON FILE PATH"))
        {
            JSONArray cards = (JSONArray) this.parser.parse(inputFile);
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
            }
            return null;
        }
        catch(Exception e)
        {
                e.printStackTrace();
                return null;
        }
    }
}



        // https://stackoverflow.com/questions/10926353/how-to-read-json-file-into-java-with-simple-json-library


