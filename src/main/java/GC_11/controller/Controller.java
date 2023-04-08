package GC_11.controller;

import GC_11.view.CLIview;
import GC_11.exceptions.IllegalMoveException;
import GC_11.model.Game;
import GC_11.util.Choice;

import java.util.List;


/**
 * At the moment, the Controller has an instance of view (Observer of it), JsonReader, so it can manage to create an object
 * of Game and it can give to JsonReader the job of receiving the List of Players and bind them to a generic Personal
 * Card read from JSON file
 */
public class Controller{
    public List<CLIview> view;
    public JsonReader reader;
    private Game model;

    /**
     * Initialize Controller with 'Game' reference and JsonReader object
     *
     * @param game reference to game
     * @param view
     */
    public Controller(Game game, CLIview view) {
        this.model = game;
        this.reader = new JsonReader();
    }

    /**
     * Set and Get of game attribute
     * @param game
     */
    public void setGame(Game game) {
        this.model = game;
    }
    public Game getGame(){
        return this.model;
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
    git * @param arg   is the 'Choice' = action taken by Player (enum object)
     */
    public void update(CLIview view, Choice arg) throws IllegalMoveException {

        if (!view.getPlayer().equals(model.getCurrentPlayer())){
            throw new IllegalMoveException("It's not your Turn! Wait, it's " + model.getCurrentPlayer()+ "'s turn");
        }

        switch (view.getPlayerChoice()){
            // Azioni da mettere dentro al client
            //case INSERT_NAME
            //case LOGIN
            //case FIND_MATCH
            case SEE_COMMONGOAL-> seeCommonGoal();
            case SEE_PERSONALGOAL -> seePersonalGoal();
            case SELECT_TILE -> selectTile();
            case PICK_COLUMN-> pickColumn();
            case CHOOSE_ORDER ->chooseOrder();
        }
    }

    private void seeCommonGoal(){

    }

    private void seePersonalGoal(){

    }

    private void selectTile(){

    }

    private void pickColumn(){

    }

    private void chooseOrder(){

    }
}
