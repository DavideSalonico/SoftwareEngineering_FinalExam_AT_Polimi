package GC_11.Controller;

import GC_11.View.View;
import GC_11.model.Game;
import java.util.Observable;
import java.util.Observer;


/**
 * At the moment, the Controller has an instance of view (Observer of it), JsonReader, so it can manage to create an object
 * of Game and it can give to JsonReader the job of receiving the List of Players and bind them to a generic Personal
 * Card read from JSON file
 */
public class Controller implements Observer {
    public View view;
    public JsonReader reader;
    private Game game;

    public Controller(Game game, View view) {
        this.game = game;
        this.view = view;
    }

    /**
     * This method bind random Personal Goal to players of a specific game
     */

    /**
     *
     * @param o     the observable object (view)
     * @param arg   an argument passed to the {@code notifyObservers}
     *                 method.
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o!= view){
            //scarta
            return;
        }
    }
}
