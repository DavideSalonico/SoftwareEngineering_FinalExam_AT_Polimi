package GC_11.view.Lobby;

import GC_11.distributed.Client;
import GC_11.exceptions.IllegalMoveException;
import GC_11.network.choices.Choice;
import GC_11.network.choices.ChoiceFactory;
import GC_11.view.GUI.GUI;
import GC_11.view.GUI.GUIApplication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class LobbyController {
    public Stage primaryStage;

    public static Client client;

    @FXML
    private ChoiceBox<String> chooseNumberPlayers;

    @FXML
    private TextField clientNickname;

    @FXML
    private Button confirmName;

    @FXML
    private TextArea errorArea;

    @FXML
    private TextArea listPlayers;

    @FXML
    private Label text;


    public void setClient(Client client) {
        Platform.runLater(() -> this.client = client);
    }

    @FXML
    public void initialize() {
        confirmName.setDisable(true);
        clientNickname.textProperty().addListener((observable, oldValue, newValue) -> {
            confirmName.setDisable(newValue.trim().isEmpty());
        });
        chooseNumberPlayers.getItems().addAll("2", "3", "4");
    }

    public void setError(String error) {
        Platform.runLater(() -> errorArea.setText(error));
    }

    @FXML
    public void showPlayers(List<String> players) {
        Platform.runLater( () -> {
            chooseNumberPlayers.setVisible(false);
            text.setVisible(false);
            listPlayers.setVisible(true);
            confirmName.setVisible(false);
            clientNickname.setVisible(false);

            for(String player : players)
                listPlayers.appendText(player + "\n");

            setError("Waiting for other players...");
        });
    }

    @FXML
    public void waitingRoom(){

        //OTTIENI LISTA PLAYER DA GAMEVIEWMESSAGE

        // TEMPORANEO
        List<String> players = new ArrayList<>();
        players.add("player1");
        players.add("player2");
        players.add("player3");
        showPlayers(players);
    }

    @FXML
    public void sendNumberOfPlayer(){
        int numberOfPlayers = Integer.parseInt((String) chooseNumberPlayers.getValue());
        System.out.println(numberOfPlayers);
        //waitingRoom();
        createChoice("SET_MAX_NUMBER "+chooseNumberPlayers.getValue().toString());
        GUI.maxNumber = Integer.parseInt((String) chooseNumberPlayers.getValue());
    }

    public void createChoice(String s) {
        Choice choice;

        try {
            choice = ChoiceFactory.createChoice(null, s);
        } catch (IllegalMoveException e) {
            throw new RuntimeException(e);
        }
        try {
            System.out.println("Sending choice: " + choice );
            GUI.client.notifyServer(choice);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void confirmNickname() {

        chooseNumberPlayers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Mostra il bottone di conferma solo se è selezionata un'opzione
            confirmName.setDisable(newValue == null);
        });

        confirmName.setOnAction(event -> sendNumberOfPlayer());
        createChoice("ADD_PLAYER " + clientNickname.getText());
        GUI.nickname = clientNickname.getText();

        /*confirmName.setDisable(true);
        chooseNumberPlayers.setVisible(true);
        clientNickname.setVisible(false);
        text.setText("Scegli il numero di giocatori");*/

    }

    //USA platform.runLater
    /*@FXML
    public void updatePlayerList(LobbyViewMessage message) {
        Platform.runLater(() ->{
            List<String> players = message.getPlayersNames();
            listPlayers.setText("");
            for(String player : players)
                listPlayers.appendText(player + "\n");
        });
    }*/

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

}
