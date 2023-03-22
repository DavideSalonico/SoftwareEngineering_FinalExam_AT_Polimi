package GC_11.Controller;

import GC_11.model.PersonalGoalCard;
import GC_11.model.Player;
import GC_11.model.Triplet;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class JsonReader {
    private List <Triplet> read;
    private List <Player> players;
    private JSONParser parser;

    public JsonReader(List<Player> players) {
        this.players = players;
        JSONParser parser = new JSONParser();
    }

    public void setGoals() throws FileNotFoundException {
        Object obj = parser.parse(new FileReader("path of JSON FILE"));
        JSONObject jsonObject =  (JSONObject) obj;

        for( Player p : players) {
            int id = (int) jsonObject.get("id");
            PersonalGoalCard card;

        }

        // https://stackoverflow.com/questions/10926353/how-to-read-json-file-into-java-with-simple-json-library
    }



}
