package GC_11.view.GUI;

import GC_11.ClientApp;
import GC_11.distributed.Client;
import GC_11.exceptions.IllegalMoveException;
import GC_11.network.choices.Choice;
import GC_11.network.choices.ChoiceFactory;
import GC_11.view.GUI.GUI;
import GC_11.view.GUI.GUIApplication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Lobby Controller, it manages the lobby GUI with reference to the lobby FXML's Components
 */
public class LobbyController {
    @FXML
    private ChoiceBox<String> chooseNumberPlayers;

    @FXML
    private TextField clientNickname;

    @FXML
    private Button confirmName;

    @FXML
    private Button sendNumberOfPlayers;

    @FXML
    private TextArea errorArea;

    @FXML
    private TextArea listPlayers;

    @FXML
    private Label text;

    @FXML
    private Label loadOldGameText;
    @FXML
    private Button confirmLoad;
    @FXML
    private Button declineLoad;

    /**
     * Method that sets the client
     * @param  client the client to be set
     */
    public void setClient(Client client) {
        Platform.runLater(() -> ClientApp.client = client);
    }

    /**
     * Initializes the GUI components and event listeners.
     */
    @FXML
    public void initialize() {
        confirmName.setDisable(true);
        clientNickname.textProperty().addListener((observable, oldValue, newValue) -> confirmName.setDisable(newValue.trim().isEmpty()));
        chooseNumberPlayers.getItems().addAll("2", "3", "4");

        chooseNumberPlayers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Show the confirm button only if an option is selected
            sendNumberOfPlayers.setDisable(newValue == null);
        });

        sendNumberOfPlayers.setVisible(false);
        sendNumberOfPlayers.setDisable(true);
    }

    /**
     * Sets an error message in the errorArea component.
     *
     * @param error The error message to be displayed.
     */
    public void setError(String error) {
        Platform.runLater(() -> errorArea.setText(error));
    }

    /**
     * Displays the list of players in the GUI and sets the appropriate visibility of components.
     *
     * @param players The list of players to be displayed.
     */
    @FXML
    public void showPlayers(List<String> players) {
        chooseNumberPlayers.setVisible(false);
        text.setVisible(false);
        listPlayers.setVisible(true);
        confirmName.setVisible(false);
        clientNickname.setVisible(false);
        sendNumberOfPlayers.setVisible(false);

        listPlayers.setText("Players in the lobby:\n");

        List <String> uniquePlayers = new ArrayList<>();
        for (String player : players)
            if(!uniquePlayers.contains(player))
                uniquePlayers.add(player);

        for (String player : uniquePlayers)
            listPlayers.appendText(player + "......\n");

        setError("Waiting for other players...");

    }

    /**
     * Retrieves the selected number of players from the chooseNumberPlayers ComboBox and performs necessary actions.
     */
    @FXML
    public void sendNumberOfPlayer(){
        createChoice("SET_MAX_NUMBER "+chooseNumberPlayers.getValue());
        GUI.maxNumber = Integer.parseInt(chooseNumberPlayers.getValue());
        sendNumberOfPlayers.setVisible(false);
    }

    /**
     * Creates a choice and sends it to the server.
     *
     * @param s The string representing the choice to be sent.
     */
    public void createChoice(String s) {
        Choice choice;

        try {
            choice = ChoiceFactory.createChoice(null, s);
        } catch (IllegalMoveException e) {
            throw new RuntimeException(e);
        }
        try {
            ClientApp.client.notifyServer(choice);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sends the nickname to the server and sets the appropriate visibility of components.
     */
    @FXML
    public void confirmNickname() {
        //if() {
        createChoice("ADD_PLAYER " + clientNickname.getText());
        GUI.nickname = clientNickname.getText();
        confirmName.setVisible(false);
        sendNumberOfPlayers.setVisible(true);
        //}
        //else
        //  setError("Nickname already taken");
    }

    /**
     * Sets the appropriate visibility of components to allow the user to enter the number of players.
     */
    @FXML
    public void changeToSetNumber(){
        Platform.runLater( () -> {
            confirmName.setDisable(true);
            chooseNumberPlayers.setVisible(true);
            clientNickname.setVisible(false);
            text.setText("Choose number of players");
        });
    }

    /**
     * Change the scene from lobby to game GUI.
     */
    @FXML
    public void changeSceneToGame() {
        Platform.setImplicitExit(false);
        Platform.runLater(() -> {
            Scene gameScene = new Scene(GUIApplication.gameLaod);
            gameScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
            GUIApplication.mainStage.setScene(gameScene);
        });

    }

    /**
     * Called when the user accept to Load old game.
     */
    @FXML
    public void acceptLoad(){
        createChoice("LOAD_GAME yes");
    }

    /**
     * Called when the user decline to Load old game.
     */
    @FXML
    public void declineLoad(){
        createChoice("LOAD_GAME no");
    }

    /**
     * Sets the appropriate visibility of components to allow the user to load an old game.
     */
    @FXML
    public void askLoadOldGame(){
        Platform.runLater(() -> {
            errorArea.setVisible(false);
            confirmName.setVisible(false);
            chooseNumberPlayers.setVisible(false);
            text.setVisible(false);
            clientNickname.setVisible(false);
            loadOldGameText.setVisible(true);
            confirmLoad.setVisible(true);
            declineLoad.setVisible(true);
        });
    }

}

