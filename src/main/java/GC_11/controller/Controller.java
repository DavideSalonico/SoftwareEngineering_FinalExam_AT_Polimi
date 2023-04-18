package GC_11.controller;

import GC_11.distributed.ClientImpl;
import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.exceptions.IllegalMoveException;
import GC_11.exceptions.NotEnoughFreeSpacesException;
import GC_11.model.Coordinate;
import GC_11.model.Game;
import GC_11.model.Player;
import GC_11.model.Tile;
import GC_11.util.Choice;
import GC_11.view.View;
import GC_11.view.View;

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
    // Controller receive directly from Server an Object Choice which contains Player reference, choice and params
    public Choice choice;
    public JsonReader reader;
    private Game model;

    /**
     * Generic constructor of Controller with all params
     * @param game reference to Model
     * @param choice reference to User's choice
     */
    public Controller(Game game, Choice choice) {
        this.model = game;
        this.reader = new JsonReader();
        this.choice = choice;
    }

    /**
     * Generic constructor of Controller with only the model
     * @param game reference to Model
     */
    public Controller(Game game) {
        this.model = game;
        this.reader = new JsonReader();
        this.choice = null;
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
     * Set and Get of Choice attribute, which will be updated by Server
     * @param choice
     */
    public void setChoice(Choice choice){
        this.choice = choice;
    }
    public Choice getChoice(){
        return this.choice;
    }

    /**
     * Check if the Client who execute the action Choice is actually the Current Player of the Turn
     * @return True if it's the current Player
     */
    public boolean checkTurn(){
        return model.getCurrentPlayer().equals(choice.getPlayer());
    }

    // [MATTIA] : Ho aggiunto questo metodo con diversa signature per gestirlo sul server. Vediamo bene come fare con i parametri
    // TODO: Mettere tutti i metodi del controller in Try and Catch e gestire l'eccezzioni
    public void update(ClientImpl client, Choice arg) throws IllegalMoveException, ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {


        if (!client.getPlayer().equals(model.getCurrentPlayer())){
            throw new IllegalMoveException("It's not your Turn! Wait, it's " + model.getCurrentPlayer()+ "'s turn");
        }

        Player player = client.getPlayer();
        Choice.Type choice =  arg.getChoice();
        List<String> params = arg.getParams();

        switch (arg.getChoice()){
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

    private void selectTile(Player player, List<String> parameters){
        //TODO: decide if for every tile picked must be triggered a single event from the view (I think so) [Davide]
        List<Coordinate> coords = stringToCoordinate(parameters);
        List<Tile> tmp_list = new ArrayList<>();
        for(Coordinate c : coords){
            player.pickTile(model.getBoard().getTile(c.getRow(), c.getColumn()));
        }
    }

    private List<Coordinate> stringToCoordinate(List<String> parameters) {
        //TODO: to be implemented

        if (parameters.size()%2 != 0){
            // Errore
            System.out.println("Coordinate number must be even. You can't have odd numver of coordinate");
        }

        int row = 0;
        int col = 0;

        int num = 0;

        List<Coordinate> listOfCoordinates = new ArrayList<Coordinate>();

        int parsed = 0;
        for (int i=0; i<parameters.size();i++)
        {

            try{
                num = Integer.parseInt(parameters.get(i));
            }
            catch (NumberFormatException e){
                System.out.println("Formato illegale");
            }
            if (i%2==0){
                row = num;
                parsed++;
            }
            else {
                col = num;
                parsed++;
            }
            if (parsed == 2){

                listOfCoordinates.add(new Coordinate(row,col));
                parsed=0;
            }
        }
        return listOfCoordinates;
    }

    private void pickColumn(Player player, List<String> parameters) throws ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {
        int column = paramsToColumnIndex(parameters);
        player.getShelf().addTiles(player.getTiles(), column);
    }

    private int paramsToColumnIndex(List<String> parameters) {
        //TODO: to be implemented
        return 0;
    }

    private void chooseOrder(Player player, List<String> parameters){
        //TODO: how do we decide tiles' order?
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        try {
            update((View) evt.getSource(), (Choice) evt.getNewValue());
        } catch (IllegalMoveException | ColumnIndexOutOfBoundsException | NotEnoughFreeSpacesException e) {
            throw new RuntimeException(e);
        }
    }
}
