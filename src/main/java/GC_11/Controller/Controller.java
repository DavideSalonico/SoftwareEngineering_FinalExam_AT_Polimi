package GC_11.Controller;

import GC_11.View.CLIview;
import GC_11.View.View;
import GC_11.exceptions.IllegalMoveException;
import GC_11.model.Game;
import GC_11.util.Choice;

import java.util.List;
import java.util.Observable;
import java.util.Observer;


/**
 * At the moment, the Controller has an instance of view (Observer of it), JsonReader, so it can manage to create an object
 * of Game and it can give to JsonReader the job of receiving the List of Players and bind them to a generic Personal
 * Card read from JSON file
 */
public class Controller implements Observer {
    public List<CLIview> view;
    public JsonReader reader;
    private Game game;

    /**
     * Initialize Controller with 'Game' reference and JsonReader object
     * @param game reference to game
     */
    public Controller(Game game) {
        this.game = game;
        this.reader = new JsonReader();
    }

    /**
     * Set and Get of game attribute
     * @param game
     */
    public void setGame(Game game) {
        this.game = game;
    }
    public Game getGame(){
        return this.game;
    }

    /**
     * Add and Remove playerView from Controller list
     * @param x
     */
    public void addPlayerViewToGame(CLIview x){
        view.add(x);
    }
    public void removePlayerView(CLIview x){
        view.remove(x);
    }

    /**
     * Detect updates from view and computes through Controller
     * @param view     the observable object (CLIview)
     * @param arg   is the 'Choice' = action taken by Player (enum object)
     */
    @Override
    public void update(CLIview view, Choice arg) throws IllegalMoveException {

        if (!view.getPlayer().equals(game.getCurrentPlayer())){
            throw new IllegalMoveException("It's not your Turn");
        }

        switch (view.getPlayerChoice()){

        }
    }


}
