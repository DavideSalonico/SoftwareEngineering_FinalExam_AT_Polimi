package GC_11.controller;

import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.exceptions.NotEnoughFreeSpacesException;
import GC_11.model.Coordinate;
import GC_11.model.Player;
import GC_11.model.Tile;
import GC_11.view.CLIview;
import GC_11.exceptions.IllegalMoveException;
import GC_11.model.Game;
import GC_11.util.Choice;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;


/**
 * At the moment, the Controller has an instance of view (Observer of it), JsonReader, so it can manage to create an object
 * of Game and it can give to JsonReader the job of receiving the List of Players and bind them to a generic Personal
 * Card read from JSON file
 */
public class Controller implements PropertyChangeListener {
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
    public void update(CLIview view, Choice arg) throws IllegalMoveException, ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {

        if (!view.getPlayer().equals(model.getCurrentPlayer())){
            throw new IllegalMoveException("It's not your Turn! Wait, it's " + model.getCurrentPlayer()+ "'s turn");
        }

        Player player = view.getPlayer();
        String params = null;

        switch (view.getPlayerChoice()){
            // Azioni da mettere dentro al client
            //case INSERT_NAME
            //case LOGIN
            //case FIND_MATCH
            //case SEE_COMMONGOAL-> seeCommonGoal(); gestita direttamente dalla view?
            //case SEE_PERSONALGOAL -> seePersonalGoal();
            case SELECT_TILE -> selectTile(player, params);
            case CHOOSE_ORDER ->chooseOrder(player, params);
            case PICK_COLUMN-> pickColumn(player, params);
        }

        model.setNextCurrent();
    }

    private void seeCommonGoal(){

    }

    private void seePersonalGoal(){

    }

    private void selectTile(Player player, String parameters){
        //TODO: decide if for every tile picked must be triggered a single event from the view (I think so) [Davide]
        List<Coordinate> coords = stringToCoordinate(parameters);
        List<Tile> tmp_list = new ArrayList<>();
        for(Coordinate c : coords){
            player.pickTile(model.getBoard().getTile(c.getRow(), c.getColumn()));
        }
    }

    private List<Coordinate> stringToCoordinate(String parameters) {
        //TODO: to be implemented
        return null;
    }

    private void pickColumn(Player player, String parameters) throws ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {
        int column = paramsToColumnIndex(parameters);
        player.getShelf().addTiles(player.getTiles(), column);
    }

    private int paramsToColumnIndex(String parameters) {
        //TODO: to be implemented
        return 0;
    }

    private void chooseOrder(Player player, String parameters){
        //TODO: how do we decide tiles' order?
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        try {
            update((CLIview) evt.getSource(), (Choice) evt.getNewValue());
        } catch (IllegalMoveException | ColumnIndexOutOfBoundsException | NotEnoughFreeSpacesException e) {
            throw new RuntimeException(e);
        }
    }
}
