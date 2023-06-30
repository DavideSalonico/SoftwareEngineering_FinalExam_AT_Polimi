package GC_11.controller;

import GC_11.distributed.ServerMain;
import GC_11.exceptions.*;
import GC_11.model.*;
import GC_11.network.choices.Choice;
import GC_11.network.choices.ChoiceType;
import GC_11.network.message.AskLoadGame;
import GC_11.network.message.GameViewMessage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;


/**
 * The Controller class handles the game logic and interactions between the model and the view.
 * It receives updates from the server and processes choices made by players during their turns.
 */
public class Controller implements PropertyChangeListener {
    // Controller receive directly from ServerRMI an Object Choice which contains Player reference, type and params
    public Choice choice;
    public JsonReader reader;
    private Game model;
    private Lobby lobby;
    private ChoiceType lastChoice = ChoiceType.RESET_TURN;
    private ServerMain server;

    private boolean setOldGameAvailable = false;

    /**
     * Creates a new Controller with the provided server.
     *
     * @param server The server to manage the game.
     */
    public Controller(ServerMain server) {
        this.reader = new JsonReader();
        this.choice = null;
        this.server = server;
        this.lobby = new Lobby();
        this.lobby.setListener(this.server);
    }

    /**
     * Get of game attribute
     */
    public Game getGame() {
        return this.model;
    }

    /**
     * Get the lobby.
     *
     * @return The lobby.
     */
    public Lobby getLobby() {
        return this.lobby;
    }


    /**
     * Set and Get of Choice attribute, which will be updated by ServerRMI
     *
     * @param choice
     */
    public void setChoice(Choice choice) {
        this.choice = choice;
    }

    /**
     * Get the current choice made by the player.
     *
     * @return The current choice made by the player.
     */
    public Choice getChoice() {
        return this.choice;
    }

    /**
     * Check if the client executing the action choice is the current player of the turn.
     *
     * @return True if it's the current player's turn, False otherwise.
     */
    public boolean checkTurn() {
        return model.getCurrentPlayer().getNickname().equals(choice.getPlayer().getNickname());
    }

    /**
     * Update the game state based on the player's choice and perform error checking.
     *
     * @param choice The player's choice.
     * @throws RemoteException If a remote communication error occurs.
     */
    public void update(Choice choice) throws RemoteException {
        this.choice = choice;

        if (model != null) {
            if (!checkTurn() && !choice.getType().equals(ChoiceType.SEND_MESSAGE)) {
                String errPlayer;
                if(choice.getPlayer().getNickname() != null) errPlayer = choice.getPlayer().getNickname();
                else errPlayer = "";
                this.server.triggerPersonalException(new IllegalMoveException(errPlayer + " it's not your Turn! Wait, it's " + model.getCurrentPlayer().getNickname() + "'s turn"), choice.getPlayer().getNickname());
                return;
            }
        }


        try {
            checkExpectedMove();
        } catch (IllegalMoveException e) {
            this.model.triggerException(e);
            return;
        }

        try {
            choice.executeOnServer(this); //TODO: exception handling
        } catch (ExceededNumberOfPlayersException e) {
            throw new RuntimeException(e);
        } catch (NameAlreadyTakenException e) {
            throw new RuntimeException(e);
        }

        if (!choice.getType().equals(ChoiceType.PICK_COLUMN) && !choice.getType().equals(ChoiceType.SEND_MESSAGE))
            this.lastChoice = this.choice.getType();
        else
            this.lastChoice = ChoiceType.RESET_TURN;
    }

    /**
     * Reset the current player's turn and clear selected tiles.
     *
     * @param params The parameters for resetting the turn.
     */
    public void resetTurn(List<String> params) {
        if (params.size() != 0) {
            this.model.triggerException(new IllegalMoveException("There shouldn't be options for this command!"));
            return;
        }
        this.model.getBoard().resetTurn();
    }


    /**
     * Check if the current choice matches the expected move based on the last choice made.
     *
     * @throws IllegalMoveException If the current choice is not the expected move.
     */
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

    /**
     * Deselects a tile from the selected tiles.
     *
     * @param params The list of parameters for the command.
     */
    public void deselectTile(List<String> params) {
        if (params.size() != 0) {
            this.model.triggerException(new IllegalMoveException("There shouldn't be parameters for this command!"));
            return;
        }

        try {
            this.model.getBoard().deselectTile();
        } catch (IllegalMoveException e) {
            this.model.triggerException(e);
        }
    }

    /**
     * Selects a tile on the game board.
     *
     * @param parameters The list of parameters for the command.
     */
    public void selectTile(List<String> parameters) {
        if (parameters.size() != 2) {
            this.model.triggerException(new IllegalMoveException("There should be 2 parameters for this command!"));
            return;
        }
        Integer row = -1, col = -1;
        try {
            row = Integer.parseInt(parameters.get(0));
            col = Integer.parseInt(parameters.get(1));
        } catch (NumberFormatException e) {
            this.model.triggerException(e);
            return;
        }
        if (row < 0 || row >= 9 || col < 0 || col >= 9) {
            this.model.triggerException(new IllegalMoveException("Row or column out of bound!"));
            return;
        }
        if (this.model.getBoard().getSelectedTiles().size() >= this.model.getCurrentPlayer().getShelf().maxFreeVerticalSpaces()) {
            this.model.triggerException(new IllegalMoveException("You can't select more tiles! You don't have enough space in your shelf"));
            return;
        }
        if (this.model.getBoard().getSelectedTiles().size() >= 3) {
            this.model.triggerException(new IllegalMoveException("You can't select more tiles! You've already selected 3"));
            return;
        }

        try {
            this.model.getBoard().selectTile(row, col);
        } catch (IllegalMoveException e) {
            this.model.triggerException(e);
        }
    }

    /**
     * Picks a column on the shelf's player to place the selected tiles.
     *
     * @param parameters The list of parameters for the command.
     * @throws RemoteException If a remote exception occurs.
     */
    public void pickColumn(List<String> parameters) throws RemoteException {
        boolean end = false;
        int column = 0;
        try {
            column = paramsToColumnIndex(parameters);
        } catch (IllegalMoveException e) {
            this.model.triggerException(e);
            return;
        }
        //TODO: Da rivedere, se possibile farlo senza creare una lista di appoggio
        List<Tile> tmp_tiles = new ArrayList<Tile>();
        for (Coordinate c : this.model.getBoard().getSelectedTiles()) {
            tmp_tiles.add(this.model.getBoard().getTile(c.getRow(), c.getColumn()));
        }
        try {
            if (this.model.getCurrentPlayer().getShelf().addTiles(tmp_tiles, column)) {
                end = true;
            }
            for (Coordinate c : this.model.getBoard().getSelectedTiles()) {
                this.model.getBoard().setTile(c.getRow(), c.getColumn(), new Tile(TileColor.EMPTY, 0));
            }
            this.model.getBoard().resetSelectedTiles();
            //Update points (all of them)
            this.model.calculateCommonPoints();
            this.model.getCurrentPlayer().updatesPointsPersonalGoal();
            this.model.getCurrentPlayer().calculateAndGiveAdjacencyPoint();
            this.model.getBoard().refillBoard();
            if (this.model.setNextCurrent()) {
                this.model.setEndGame(true);
                this.model.triggerEnd();
            }
        } catch (NotEnoughFreeSpacesException | ColumnIndexOutOfBoundsException e) {
            this.model.triggerException(e);
            return;
        }

        if (end) {
            this.model.setLastTurn(true);
        }
    }


    /**
     * Converts parameters to a column index.
     *
     * @param parameters The list of parameters to convert.
     * @return The column index.
     * @throws IllegalMoveException If an illegal move exception occurs.
     */
    private int paramsToColumnIndex(List<String> parameters) throws IllegalMoveException {
        if (parameters.size() != 1) throw new IllegalMoveException("There shouldn't be options for this command!");
        Integer column_index;
        try {
            column_index = Integer.parseInt(parameters.get(0));
        } catch (NumberFormatException e) {
            throw new IllegalMoveException("Invalid format. Column number must be an integer!");
        }
        if (column_index < 0 || column_index >= 5) throw new IllegalMoveException("Column index out of bound!");
        if (this.model.getBoard().getSelectedTiles().size() == 0)
            throw new IllegalMoveException("You can't make this move! there are no tiles selected");
        return column_index;

    }

    /**
     * Allows the player to choose the order of selected tiles.
     *
     * @param parameters The list of parameters for the command.
     */
    public void chooseOrder(List<String> parameters) {
        //Integer parameters control
        Integer tilesSize = this.model.getBoard().getSelectedTiles().size();
        if (parameters.size() != tilesSize) {
            this.model.triggerException(new IllegalMoveException("There shouldn't be options for this command!"));
            return;
        }
        List<Integer> ind = new ArrayList<>();
        for (int i = 0; i < tilesSize; i++) {
            ind.add(null);
        }
        try {
            for (int i = 0; i < tilesSize; i++) {
                ind.set(i, Integer.parseInt(parameters.get(i)));
            }
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            this.model.triggerException(new IllegalMoveException("Invalid format!"));
            return;
        }
        //Not out of bound index control
        for (int i = 0; i < tilesSize; i++) {
            if (ind.get(i) < 0 || ind.get(i) > tilesSize) {
                this.model.triggerException(new IllegalMoveException("Invalid order. Some index are out of bound!"));
                return;
            }
        }
        //No duplicates control
        for (int i = 0; i < tilesSize; i++) {
            for (int j = i + 1; j < tilesSize; j++) {
                if (ind.get(i).equals(ind.get(j))) {
                    this.model.triggerException(new IllegalMoveException("Invalid order. There are some duplicate positions!"));
                    return;
                }
            }
        }

        try {
            this.model.getBoard().changeOrder(ind);
        } catch (IllegalMoveException e) {
            this.model.triggerException(e);
        }
    }

    /**
     * Handles the property change event.
     *
     * @param evt The property change event.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        try {
            update((Choice) evt.getNewValue());

            //this.model.triggerException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sends a message from a player to another player or the entire lobby.
     *
     * @param player The player sending the message.
     * @param parameters The list of parameters for the command.
     * @throws RemoteException If a remote exception occurs.
     */
    public void sendMessage(Player player, List<String> parameters) throws RemoteException {
        if (parameters.size() != 2) {
            this.model.triggerException(new IllegalMoveException("There should be exactly two parameters for this command!"));
            return;
        }
        if (parameters.get(0).length() >= 64 || parameters.get(1).length() >= 64) {
            this.model.triggerException(new IllegalMoveException("Message too long"));
            return;
        }
        if (!this.model.getPlayers().stream().map(i -> i.getNickname()).toList().contains(parameters.get(0)) && !parameters.get(0).equals("Everyone")) {
            this.model.triggerException(new IllegalMoveException("Player not found!"));
            return;
        }
        if (parameters.get(0).equals(player.getNickname())) {
            this.model.triggerException(new IllegalMoveException("You can't send a message to yourself!"));
            return;
        }
        if (parameters.get(0).equals("Everyone"))
            this.model.getChat().sendMessageToMainChat(player, parameters.get(1));
        else
            this.model.getChat().sendMessageToPrivateChat(player, this.model.getPlayer(parameters.get(0)), parameters.get(1));
    }

    /**
     * Sets the maximum number of players in the lobby.
     *
     * @param maxPlayers The maximum number of players.
     */
    public void setMaxPlayers(int maxPlayers) {
        this.lobby.setMaxPlayers(maxPlayers);
    }

    /**
     * Starts the game with the current players in the lobby.
     */
    public void startGame() {
        // Check if players' name are the same in the JSON file
        List<String> playersInJsonFile = JsonWriter.getNicknames();
        // If the number of players is the same
        if (playersInJsonFile != null) {

            if (playersInJsonFile.size() == lobby.getPlayers().size()) {
                boolean equals = true;
                // For every player's nickname in the JsonFile check if it's present in the lobby
                for (String playerNickname : playersInJsonFile) {
                    // If it's not present in the lobby, set equals to false
                    if (!lobby.getPlayers().contains(playerNickname)) {
                        equals = false;
                    }
                }
                if (equals) {
                    this.setOldGameAvailable = true;
                    this.server.notifyClient(new AskLoadGame(), this.lobby.getPlayers().get(0));
                } else {
                    this.model = new Game(lobby.getPlayers(), this.server);
                    this.server.notifyClients(new GameViewMessage(this.model, null));
                }
            } else {
                this.model = new Game(lobby.getPlayers(), this.server);
                this.server.notifyClients(new GameViewMessage(this.model, null));
            }
            // If the players' nicknames matches, ask the first player if he wants to load the game
        } else {
            this.model = new Game(lobby.getPlayers(), this.server);
            this.server.notifyClients(new GameViewMessage(this.model, null));
        }
    }

    /**
     * Returns the server.
     *
     * @return The server.
     */
    public ServerMain getServer() {
        return this.server;
    }

    /**
     * sets the OldGameAvailable boolean.
     * @param b the new value of the boolean.
     */
    public void setOldGameAvailable(boolean b) {
        this.setOldGameAvailable = b;
    }

    /**
     * Sets the game model.
     *
     * @param model The game model.
     */
    public void setGame(Game model) {
        this.model = model;
    }


    public void selectLoadGame(String response) {
        if (this.setOldGameAvailable) {
            if (response.equalsIgnoreCase("si") || response.equalsIgnoreCase("yes")) {
                this.model = JsonWriter.loadGame();
                if (this.model != null) {
                    this.model.getBoard().setListener(this.model);
                    this.model.setListener(this.server);
                }
            }
            else {
                this.model = new Game(lobby.getPlayers(), this.server);
            }
        }
        this.setOldGameAvailable = false;
        this.server.notifyClients(new GameViewMessage(this.model, null));
    }
}

