package GC_11.view.Lobby;

import GC_11.ClientApp;
import GC_11.distributed.Client;
import GC_11.exceptions.IllegalMoveException;
import GC_11.network.choices.Choice;
import GC_11.network.choices.ChoiceFactory;
import GC_11.view.GUI.GUI;
import GC_11.view.GUI.GUIApplication;
import GC_11.view.View;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.util.List;

public class LobbyController {
    public Stage primaryStage;

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

    public void setClient(Client client) {
        Platform.runLater(() -> ClientApp.client = client);
    }

    @FXML
    public void initialize() {
        confirmName.setDisable(true);
        clientNickname.textProperty().addListener((observable, oldValue, newValue) -> {
            confirmName.setDisable(newValue.trim().isEmpty());
        });
        chooseNumberPlayers.getItems().addAll("2", "3", "4");

        chooseNumberPlayers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Mostra il bottone di conferma solo se è selezionata un'opzione
            sendNumberOfPlayers.setDisable(newValue == null);
        });

        sendNumberOfPlayers.setVisible(false);
        sendNumberOfPlayers.setDisable(true);
    }

    public void setError(String error) {
        Platform.runLater(() -> errorArea.setText(error));
    }

    @FXML
    public void showPlayers(List<String> players) {
        chooseNumberPlayers.setVisible(false);
        text.setVisible(false);
        listPlayers.setVisible(true);
        confirmName.setVisible(false);
        clientNickname.setVisible(false);
        sendNumberOfPlayers.setVisible(false);

        for (String player : players)
            listPlayers.appendText(player + "\n");

        setError("Waiting for other players...");

    }

    @FXML
    public void sendNumberOfPlayer(){
        int numberOfPlayers = Integer.parseInt((String) chooseNumberPlayers.getValue());
        System.out.println(numberOfPlayers);
        //waitingRoom();
        createChoice("SET_MAX_NUMBER "+chooseNumberPlayers.getValue());
        GUI.maxNumber = Integer.parseInt((String) chooseNumberPlayers.getValue());
        sendNumberOfPlayers.setVisible(false);
    }

    public void createChoice(String s) {
        Choice choice;

        try {
            choice = ChoiceFactory.createChoice(null, s);
        } catch (IllegalMoveException e) {
            throw new RuntimeException(e);
        }
        try {
            System.out.println("Sending choice: ");
            while(ClientApp.client == null) {
                System.out.println("Waiting for client...");
                Thread.sleep(100);
            }
            ClientApp.client.notifyServer(choice);
        } catch (RemoteException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void confirmNickname() {
        ClientApp.view.setNickname(clientNickname.getText());
        createChoice("ADD_PLAYER " + clientNickname.getText());
        confirmName.setVisible(false);

        sendNumberOfPlayers.setVisible(true);

        /*confirmName.setDisable(true);
        chooseNumberPlayers.setVisible(true);
        clientNickname.setVisible(false);
        text.setText("Scegli il numero di giocatori");*/

    }

    public void changeToSetNumber(){
        Platform.runLater( () -> {
            confirmName.setDisable(true);
            chooseNumberPlayers.setVisible(true);
            clientNickname.setVisible(false);
            text.setText("Scegli il numero di giocatori");
        });
    }


    @FXML
    public GUIApplication changeScene() throws RemoteException {
        GUIApplication guiApplication = new GUIApplication();
        //guiApplication.setClient(this.client);

        try {
            guiApplication.start(this.primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return guiApplication;
    }

    public void changeSceneToGame() {
        Platform.setImplicitExit(false);
        Platform.runLater(() -> {
            Scene gameScene = new Scene(GUIApplication.gameLaod);
            gameScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            GUIApplication.mainStage.setScene(gameScene);
        });

    }

    @FXML
    public void acceptLoad(){
        createChoice("LOAD_GAME yes");
    }

    @FXML
    public void declineLoad(){
        createChoice("LOAD_GAME no");
    }
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
