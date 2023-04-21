package GC_11.controller;

import GC_11.exceptions.*;
import GC_11.model.Coordinate;
import GC_11.model.Game;
import GC_11.model.Player;
import GC_11.model.Tile;
import GC_11.model.common.CommonGoalCard;
import GC_11.network.Lobby;
import GC_11.util.Choice;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.security.InvalidParameterException;
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
    private Lobby lobbyModel;

    /**
     * Generic constructor of Controller with all params
     * @param game reference to Model
     * @param choice reference to User's choice
     */
    public Controller(Game game, Choice choice, Lobby lobby) {
        this.model = game;
        this.reader = new JsonReader();
        this.choice = choice;
        this.lobbyModel = lobby;
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
    // TODO: Mettere tutti i metodi del controller in Try and Catch e gestire l'eccezioni
    public void update(Choice arg)
            throws IllegalMoveException,
            ColumnIndexOutOfBoundsException,
            NotEnoughFreeSpacesException,
            ExceededNumberOfPlayersException,
            NameAlreadyTakenException {

        if (!checkTurn()){
            throw new IllegalMoveException("It's not your Turn! Wait, it's " + model.getCurrentPlayer()+ "'s turn");
        }

        Player player = choice.getPlayer();
        Choice.Type choice =  arg.getChoice();
        List<String> params = arg.getParams();

        switch (arg.getChoice()){
            case INSERT_NAME -> insertName(player, params);
            case LOGIN -> login(player, params);
            case FIND_MATCH -> findMatch(player, params);
            //case SEE_COMMONGOAL-> seeCommonGoal(); gestita direttamente dalla view?
            //case SEE_PERSONALGOAL -> seePersonalGoal();
            case SELECT_TILE -> selectTile(player, params);
            case CHOOSE_ORDER ->chooseOrder(player, params);
            case PICK_COLUMN-> pickColumn(player, params);
        }

        model.setNextCurrent();
    }

    private void findMatch(Player player, List<String> params) {
        //if(player.equals(lobbyModel.getBoss()))) We should check that only the main player can start the game
        this.model = new Game(lobbyModel.getPlayers());
        lobbyModel.startGame(this.model);
    }

    private void login(Player player, List<String> params) {
        //TODO
        System.out.println("Player "+ params.get(0) + " logged successfully");
    }

    private void insertName(Player player, List<String> params) throws ExceededNumberOfPlayersException, NameAlreadyTakenException {
        //TODO
        if(params.size() != 1) throw new IllegalArgumentException();
        if(params.get(0).length() >= 64) throw new InvalidParameterException();
        lobbyModel.addPlayer(params.get(0));
    }

    private void seeCommonGoal(){

    }

    private void seePersonalGoal(){

    }

    private void selectTile(Player player, List<String> parameters){
        if(parameters.size() != 2) throw new IllegalArgumentException();
        Integer row, col;
        try{
            row = Integer.parseInt(parameters.get(0));
            col = Integer.parseInt(parameters.get(1));
        } catch(NumberFormatException e){
            throw new InvalidParameterException();
        }
        if(row < 0 || row >= 9 || col < 0 || col >= 9 ) throw new InvalidParameterException();
        //It could be possible to make a control about prohibited positions in the board based on the number of players
        //Maybe not necessary if we check Tile.Type?
        player.pickTile(model.getBoard().getTile(row, col));
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
        player.resetTiles();
    }

    private int paramsToColumnIndex(List<String> parameters) {
        if(parameters.size() != 1) throw new IllegalArgumentException();
        Integer column_index;
        try{
            column_index = Integer.parseInt(parameters.get(0));
        } catch(NumberFormatException e){
            throw new InvalidParameterException();
        }
        if(column_index < 0 || column_index >= 5) throw new InvalidParameterException();
        return column_index;
    }

    private void chooseOrder(Player player, List<String> parameters){
        //Integer parameters control
        Integer tilesSize = player.getTiles().size();
        if(parameters.size() > tilesSize) throw new IllegalArgumentException();
        List<Integer> ind = new ArrayList<>();
        try{
            for(int i = 0; i < tilesSize; i++){
                ind.set(i, Integer.parseInt(parameters.get(i)));
            }
        } catch(NumberFormatException e){
            throw new InvalidParameterException();
        }
        //Not out of bound index control
        for(int i = 0; i < tilesSize; i++){
            if(ind.get(i) < 1 || ind.get(i) > tilesSize) throw new InvalidParameterException();
        }
        //No duplicates control
        for(int i = 0; i < tilesSize; i++){
            for(int j = i + 1; j < tilesSize; j++){
                if(ind.get(i).equals(ind.get(j))) throw new InvalidParameterException();
            }
        }

        List<Tile> tmpTiles = new ArrayList<Tile>(player.getTiles());
        for(int i = 0; i < tilesSize; i++){
            tmpTiles.set(ind.get(i), player.getTiles().get(i));
        }
        player.resetTiles(); //Probably it's not necessary
        player.setTiles(tmpTiles);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        try {
            update((Choice) evt.getNewValue());
        } catch (IllegalMoveException | ColumnIndexOutOfBoundsException | NotEnoughFreeSpacesException |
                 ExceededNumberOfPlayersException | NameAlreadyTakenException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertTiles(List<Tile> tilesOrder, int column, Player player) throws ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {
        player.insertTiles(tilesOrder, column);
        player.calculateAndGiveAdjacencyPoint();
        player.updatesPointsPersonalGoal();
        this.getGame().calculateCommonPoints();
    }
}
