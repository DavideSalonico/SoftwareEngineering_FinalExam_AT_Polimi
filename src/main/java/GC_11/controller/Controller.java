package GC_11.controller;

import GC_11.distributed.ServerMain;
import GC_11.exceptions.*;
import GC_11.model.Coordinate;
import GC_11.model.Game;
import GC_11.model.Tile;
import GC_11.model.TileColor;
import GC_11.model.Lobby;
import GC_11.network.choices.Choice;
import GC_11.network.choices.ChoiceType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
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
    private ChoiceType lastChoice = ChoiceType.RESET_TURN;
    private ServerMain server;

    /**
     * Generic constructor of Controller with only the model
     *
     * @param
     */
    public Controller(ServerMain server) {
        this.reader = new JsonReader();
        this.choice = null;
        this.server = server;
        this.lobby = new Lobby();
        this.lobby.setListener(this.server);
    }

    /**
     *Get of game attribute
     *
     *
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

    public void update(Choice choice) throws RemoteException {
        this.choice = choice;

        if (!checkTurn()) {
            this.model.triggerException(new IllegalMoveException("It's not your Turn! Wait, it's " + model.getCurrentPlayer().getNickname() + "'s turn"));
        }

        try {
            checkExpectedMove();
        } catch (IllegalMoveException e) {
            this.model.triggerException(e);
        }

        choice.executeOnServer(this);

        if (!choice.getType().equals(ChoiceType.PICK_COLUMN))
            this.lastChoice = this.choice.getType();
        else
            this.lastChoice = ChoiceType.RESET_TURN;
    }

    public void resetTurn(List<String> params) throws RemoteException {
        if (params.size() != 0)
            this.model.triggerException(new IllegalMoveException("There shouldn't be options for this command!"));
        this.model.getBoard().resetTurn();
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

    public void deselectTile(List<String> params) throws RemoteException {
        if (params.size() != 0)
            this.model.triggerException(new IllegalMoveException("There shouldn't be parameters for this command!"));

        try {
            this.model.getBoard().deselectTile();
        } catch (IllegalMoveException e) {
            this.model.triggerException(e);
        }
    }

    public void selectTile(List<String> parameters) throws RemoteException {
        if (parameters.size() != 2)
            this.model.triggerException(new IllegalMoveException("There should be 2 parameters for this command!"));
        Integer row = -1, col = -1;
        try {
            row = Integer.parseInt(parameters.get(0));
            col = Integer.parseInt(parameters.get(1));
        } catch (NumberFormatException e) {
            this.model.triggerException(e);
        }
        if (row < 0 || row >= 9 || col < 0 || col >= 9)
            this.model.triggerException(new IllegalMoveException("Row or column out of bound!"));
        if (this.model.getBoard().getSelectedTiles().size() >= min(3, this.model.getCurrentPlayer().getShelf().maxFreeVerticalSpaces()))
            this.model.triggerException(new IllegalMoveException("You can't select more tiles! You've already selected 3 or you don't have enough space in your shelf"));

        try {
            this.model.getBoard().selectTile(row, col);
        } catch (IllegalMoveException e) {
            this.model.triggerException(e);
        }
    }

    public void pickColumn(List<String> parameters) throws RemoteException {
        int column = 0;
        try {
            column = paramsToColumnIndex(parameters);
        } catch (IllegalMoveException e) {
            this.model.triggerException(e);
        }
        //TODO: Da rivedere, se possibile farlo senza creare una lista di appoggio
        List<Tile> tmp_tiles = new ArrayList<Tile>();
        for (Coordinate c : this.model.getBoard().getSelectedTiles()) {
            tmp_tiles.add(this.model.getBoard().getTile(c.getRow(), c.getColumn()));
            this.model.getBoard().setTile(c.getRow(), c.getColumn(), new Tile(TileColor.EMPTY));
        }
        try {
            this.model.getCurrentPlayer().getShelf().addTiles(tmp_tiles, column);
            this.model.getBoard().resetSelectedTiles();
            //Update points (all of them)
            this.model.calculateCommonPoints();
            this.model.getCurrentPlayer().updatesPointsPersonalGoal();
            this.model.getCurrentPlayer().calculateAndGiveAdjacencyPoint();
            this.model.getBoard().refillBoard();
            this.model.setNextCurrent();
        } catch (NotEnoughFreeSpacesException | ColumnIndexOutOfBoundsException e) {
            this.model.triggerException(e);
        }
        //this.lastChoice = ChoiceType.RESET_TURN;
    }

    private int paramsToColumnIndex(List<String> parameters) throws IllegalMoveException {
        if (parameters.size() != 1) throw new IllegalMoveException("There shouldn't be options for this command!");
        Integer column_index;
        try {
            column_index = Integer.parseInt(parameters.get(0));
        } catch (NumberFormatException e) {
            throw new IllegalMoveException("Invalid format. Column number must be an integer!");
        }
        if (column_index < 0 || column_index >= 5) throw new IllegalMoveException("Column index out of bound!");
        if(this.model.getBoard().getSelectedTiles().size() == 0) throw new IllegalMoveException("You can't make this move! there are no tiles selected");
        return column_index;

    }

    public void chooseOrder(List<String> parameters) throws RemoteException {
        //Integer parameters control
        Integer tilesSize = this.model.getBoard().getSelectedTiles().size();
        if (parameters.size() != tilesSize)
            this.model.triggerException(new IllegalMoveException("There shouldn't be options for this command!"));
        List<Integer> ind = new ArrayList<>();
        for (int i = 0; i < tilesSize; i++) {
            ind.add(null);
        }
        try {
            for (int i = 0; i < tilesSize; i++) {
                ind.set(i, Integer.parseInt(parameters.get(i)));
            }
        } catch (NumberFormatException e) {
            this.model.triggerException(new IllegalMoveException("Invalid format. Order list must be made by integers!"));
        }
        //Not out of bound index control
        for (int i = 0; i < tilesSize; i++) {
            if (ind.get(i) < 0 || ind.get(i) > tilesSize)
                this.model.triggerException(new IllegalMoveException("Invalid order. Some index are out of bound!"));
        }
        //No duplicates control
        for (int i = 0; i < tilesSize; i++) {
            for (int j = i + 1; j < tilesSize; j++) {
                if (ind.get(i).equals(ind.get(j)))
                    this.model.triggerException(new IllegalMoveException("Invalid order. There are some duplicate positions!"));
            }
        }

        try {
            this.model.getBoard().changeOrder(ind);
        } catch (IllegalMoveException e) {
            this.model.triggerException(e);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        try {
            update((Choice) evt.getNewValue());

            //this.model.triggerException(e);
        }
        catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(List<String> parameters) throws RemoteException {
        if (parameters.size() != 2)
            this.model.triggerException(new IllegalMoveException("There should be exactly two options for this command!"));
        if (parameters.get(0).length() >= 64 || parameters.get(1).length() >= 64)
            this.model.triggerException(new IllegalMoveException("Message too long"));
        if(!this.model.getPlayers().stream().map(i -> i.getNickname()).toList().contains(parameters.get(0)) || !parameters.get(0).equals("Everyone"))
            this.model.triggerException(new IllegalMoveException("Player not found!"));
        if (parameters.get(0).equals(this.model.getCurrentPlayer().getNickname()))
            this.model.triggerException(new IllegalMoveException("You can't send a message to yourself!"));

        if (parameters.get(0).equals("Everyone"))
            this.model.getChat().sendMessageToMainChat(this.model.getCurrentPlayer(), parameters.get(1));
        else
            this.model.getChat().sendMessageToPrivateChat(this.model.getCurrentPlayer(), this.model.getPlayer(parameters.get(0)), parameters.get(1));
    }

    public void insertName(String name){
        try{
            lobby.addPlayer(name);
        } catch (NameAlreadyTakenException | ExceededNumberOfPlayersException e) {
            lobby.triggerException(e);
        }
    }

    public void setMaxPlayers(int maxPlayers){
        this.lobby.setMaxPlayers(maxPlayers);
    }

    public void startGame(){
        this.model = new Game(this.lobby.getPlayers(), this.server);
    }
}
