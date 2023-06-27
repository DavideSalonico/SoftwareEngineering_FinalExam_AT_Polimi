package GC_11.view.Lobby;

import GC_11.network.message.LobbyViewMessage;
import GC_11.view.GUI.GUIApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class LobbyGUIView extends Application {

    public Button confirmName;
    public TextField clientNickname;
    public TextArea listPlayers;

    public ChoiceBox chooseNumberPlayers;

    public TextArea errorArea;
    public Label text;

    public void initialize() {
        confirmName.setDisable(true);
        clientNickname.textProperty().addListener((observable, oldValue, newValue) -> {
            confirmName.setDisable(newValue.trim().isEmpty());
        });
        chooseNumberPlayers.getItems().addAll("2", "3", "4");
    }

    public void setError(String error) {
        errorArea.setText(error);
    }

    public void showPlayers(List<String> players) {
        chooseNumberPlayers.setVisible(false);
        text.setVisible(false);
        listPlayers.setVisible(true);
        confirmName.setVisible(false);
        clientNickname.setVisible(false);

        for(String player : players)
            listPlayers.appendText(player + "\n");

        setError("Waiting for other players...");
    }
    
    public void waitingRoom(){

        //OTTIENI LISTA PLAYER DA GAMEVIEWMESSAGE

        // TEMPORANEO
        List<String> players = new ArrayList<>();
        players.add("player1");
        players.add("player2");
        players.add("player3");
        showPlayers(players);
    }

    public void sendNumberOfPlayer(){
        int numberOfPlayers = Integer.parseInt((String) chooseNumberPlayers.getValue());
        System.out.println(numberOfPlayers);

        //SEND NUMBER OF PLAYERS TO SERVER

        waitingRoom();
    }

    public void confirmNickname() {
        confirmName.setDisable(true);
        chooseNumberPlayers.setVisible(true);
        clientNickname.setVisible(false);
        text.setText("Scegli il numero di giocatori");

        chooseNumberPlayers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Mostra il bottone di conferma solo se è selezionata un'opzione
            confirmName.setDisable(newValue == null);
        });

        //manda il nickname al server

        confirmName.setOnAction(event -> sendNumberOfPlayer());
    }

    public void updatePlayerList(LobbyViewMessage message) {
        List<String> players = message.getPlayersNames();
        listPlayers.setText("");
        for(String player : players)
            listPlayers.appendText(player + "\n");
    }

    public void changeScene(Stage primaryStage){
        GUIApplication guiApplication = new GUIApplication();
        try {
            guiApplication.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URL("file:///" + System.getProperty("user.dir") + "\\src\\main\\java\\GC_11\\view\\Lobby\\LobbyGUI.fxml"));
        Pane pane;

        try {
            pane = loader.<Pane>load();
        } catch (IOException e) {
            System.out.println("Errore nel caricamento della GUI " + e.getMessage());
            throw new RuntimeException(e);

        }
        Scene scene = new Scene(pane);

        primaryStage.getIcons().add(new Image("file:src/resources/GraphicalResources/Publisher material/Icon 50x50px.png"));
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
