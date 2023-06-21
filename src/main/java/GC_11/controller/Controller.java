package GC_11.controller;

import GC_11.distributed.ServerMain;
import GC_11.exceptions.*;
import GC_11.model.Coordinate;
import GC_11.model.Game;
import GC_11.model.Tile;
import GC_11.model.TileColor;
import GC_11.model.Lobby;
import GC_11.util.choices.Choice;
import GC_11.util.choices.ChoiceFactory;
import GC_11.util.choices.ChoiceType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;


/**
 * At the moment, the Controller has an instance of view (Observer of it), JsonReader, so it can manage to create an object
 * of Game and it can give to JsonReader the job of receiving the List of Players and bind them to a generic Personal
 * Card read from JSON file
 */
public class Controller implements PropertyChangeListener {
    // Controller receive directly from Server an Object Choice which contains Player reference, type and params
    public Choice choice;
    public JsonReader reader;
    private Game model;
    private Lobby lobby;
    private ServerMain server;
    private ChoiceType lastChoice = ChoiceType.RESET_TURN;
    private ChoiceFactory choiceFactory;

    /**
     * Generic constructor of Controller with only the model
     *
     * @param game reference to Model
     */
    public Controller(Game game) {
        this.model = game;
        this.reader = new JsonReader();
        this.choice = null;
    }

    /**
     *Get of game attribute
     *
     * @param game
     */
    public Game getGame() {
        return this.model;
    }

    public Lobby getLobby() {
        return this.lobby;
    }


    /**
     * Set and Get of Choice attribute, which will be updated by Server
     *
     * @param choice
     */
    public void setChoice(Choice choice) {
        this.choice = choice;
    }

    public Choice getChoice() {
        return this.choice;
    }

    /**
     * Check if the Client who execute the action Choice is actually the Current Player of the Turn
     *
     * @return True if it's the current Player
     */
    public boolean checkTurn() {
        return model.getCurrentPlayer().equals(choice.getPlayer());
    }

    public void update(Choice choice)
            throws IllegalMoveException,
            ColumnIndexOutOfBoundsException,
            NotEnoughFreeSpacesException,
            ExceededNumberOfPlayersException,
            NameAlreadyTakenException, RemoteException {
        this.choice = choice;

        if (!checkTurn()) {
            throw new IllegalMoveException("It's not your Turn! Wait, it's " + model.getCurrentPlayer() + "'s turn");
        }

        checkExpectedMove();

        try {
            choice.executeOnServer(this);
        } catch (IllegalArgumentException e) {
            this.model.triggerException(e);
        }

        if (!choice.getType().equals(ChoiceType.PICK_COLUMN))
            this.lastChoice = this.choice.getType();
        else
            this.lastChoice = ChoiceType.RESET_TURN;
    }

    public void resetTurn(List<String> params) {
        if (params.size() != 0) throw new IllegalArgumentException("There shouldn't be options for this command!");

        this.model.getBoard().getSelectedTiles().clear();
    }

    //Sort of FSM to garantee the correct logic flow of moves
    private void checkExpectedMove() throws IllegalMoveException {
        ChoiceType currentChoice = this.choice.getType();
        switch (this.lastChoice) {
            case SELECT_TILE, DESELECT_TILE, RESET_TURN -> {
                if (currentChoice.equals(ChoiceType.DESELECT_TILE) && this.model.getBoard().getSelectedTiles().size() == 0) {
                    throw new IllegalMoveException("You can't make this move! there are no tiles selected");
                } else if (currentChoice.equals(ChoiceType.SELECT_TILE)
                        && this.model.getBoard().getSelectedTiles().size() == min(3, this.model.getCurrentPlayer().getShelf().maxFreeVerticalSpaces())) {
                    throw new IllegalMoveException("You can't make this move! there isn't enough space in your shelf");
                }
            }
            case CHOOSE_ORDER -> {
                if (currentChoice.equals(ChoiceType.CHOOSE_ORDER)
                        || currentChoice.equals(ChoiceType.PICK_COLUMN)
                        || currentChoice.equals(ChoiceType.RESET_TURN))
                    return;
                else throw new IllegalMoveException("You can't make this move!");
            }
            case PICK_COLUMN -> {
                throw new IllegalMoveException("You can't make this move! you have already picked a column"); //Non deve mai essere l'ultima mossa scelta, se va a buon fine viene resettato
            }
        }
    }

    public void deselectTile(List<String> params) throws IllegalMoveException {
        if (params.size() != 0) throw new IllegalArgumentException("There shouldn't be options for this command!");

        this.model.getBoard().deselectTile();
    }

    public void selectTile(List<String> parameters) throws IllegalMoveException {
        if (parameters.size() != 2) throw new IllegalArgumentException("There shouldn't be options for this command!");
        Integer row, col;
        try {
            row = Integer.parseInt(parameters.get(0));
            col = Integer.parseInt(parameters.get(1));
        } catch (NumberFormatException e) {
            throw new InvalidParameterException("Invalid format. Row and column numbers must be integers!");
        }
        if (row < 0 || row >= 9 || col < 0 || col >= 9)
            throw new InvalidParameterException("Row or column out of bound!");
        //It could be possible to make a control about prohibited positions in the board based on the number of players
        //Maybe not necessary if we check Tile.Type?
        if (this.model.getBoard().getSelectedTiles().size() >= min(3, this.model.getCurrentPlayer().getShelf().maxFreeVerticalSpaces()))
            throw new IllegalMoveException("Unable to select one more tile. You've already selected 3 or you don't have enough space in your shelf");

        try {
            this.model.getBoard().selectTile(row, col);
        } catch (IllegalMoveException e) {
            throw new InvalidParameterException("You can't pick this tile!");
        }

    }

    public void pickColumn(List<String> parameters) throws ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException, RemoteException {
        int column = paramsToColumnIndex(parameters);
        //TODO: Da rivedere, se possibile farlo senza creare una lista di appoggio
        List<Tile> tmp_tiles = new ArrayList<Tile>();
        for (Coordinate c : model.getBoard().getSelectedTiles()) {
            tmp_tiles.add(this.model.getBoard().getTile(c.getRow(), c.getColumn()));
            this.model.getBoard().setTile(c.getRow(), c.getColumn(), new Tile(TileColor.EMPTY));
        }
        this.model.getCurrentPlayer().getShelf().addTiles(tmp_tiles, column);
        this.model.getBoard().resetSelectedTiles();

        //Update points (all of them)
        this.model.calculateCommonPoints();
        this.model.getCurrentPlayer().updatesPointsPersonalGoal();
        this.model.getCurrentPlayer().calculateAndGiveAdjacencyPoint();

        this.model.setNextCurrent();

        //this.lastChoice = ChoiceType.RESET_TURN;
    }

    private int paramsToColumnIndex(List<String> parameters) {
        if (parameters.size() != 1) throw new IllegalArgumentException("There shouldn't be options for this command!");
        Integer column_index;
        try {
            column_index = Integer.parseInt(parameters.get(0));
        } catch (NumberFormatException e) {
            throw new InvalidParameterException("Invalid format. Column number must be an integer!");
        }
        if (column_index < 0 || column_index >= 5) throw new InvalidParameterException("Column index out of bound!");
        return column_index;
    }

    public void chooseOrder(List<String> parameters) {
        //Integer parameters control
        Integer tilesSize = this.model.getBoard().getSelectedTiles().size();
        if (parameters.size() != tilesSize)
            throw new IllegalArgumentException("There shouldn't be options for this command!");
        List<Integer> ind = new ArrayList<>();
        for (int i = 0; i < tilesSize; i++) {
            ind.add(null);
        }
        try {
            for (int i = 0; i < tilesSize; i++) {
                ind.set(i, Integer.parseInt(parameters.get(i)));
            }
        } catch (NumberFormatException e) {
            throw new InvalidParameterException("Order list must be made by integers!");
        }
        //Not out of bound index control
        for (int i = 0; i < tilesSize; i++) {
            if (ind.get(i) < 0 || ind.get(i) > tilesSize) throw new InvalidParameterException("Invalid order!");
        }
        //No duplicates control
        for (int i = 0; i < tilesSize; i++) {
            for (int j = i + 1; j < tilesSize; j++) {
                if (ind.get(i).equals(ind.get(j)))
                    throw new InvalidParameterException("Invalid order. There are some duplicate positions!");
            }
        }

        try {
            this.model.getBoard().changeOrder(ind);
        } catch (Exception e) {
            throw new InvalidParameterException();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        try {
            update((Choice) evt.getNewValue());
        } catch (IllegalMoveException | ColumnIndexOutOfBoundsException | NotEnoughFreeSpacesException |
                 ExceededNumberOfPlayersException | NameAlreadyTakenException e) {
            try {
                this.model.triggerException(e);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(List<String> parameters) {
        if (parameters.size() != 2)
            throw new IllegalArgumentException("There should be exactly two ooptions for this command!");
        //TODO: Write length control on parameters.get(1)

        if (parameters.get(0).equals("Everyone")) {
            this.model.getChat().sendMessageToMainChat(this.model.getCurrentPlayer(), parameters.get(1));
        } else {
            if (this.model.getPlayer(parameters.get(0)) == null)
                throw new InvalidParameterException("Player not found!");
            if (this.model.getPlayer(parameters.get(0)).equals(this.model.getCurrentPlayer()))
                throw new InvalidParameterException("You can't send a message to yourself!");
            this.model.getChat().sendMessageToPrivateChat(this.model.getCurrentPlayer(), this.model.getPlayer(parameters.get(0)), parameters.get(1));
        }
    }

    public void insertName(String name){
        try{
            lobby.addPlayer(name);
        } catch (NameAlreadyTakenException | ExceededNumberOfPlayersException e) {
            lobby.triggerException(e);
        }
    }

    public void startGame(){
        this.model = new Game(lobby.getPlayers());
    }
}
