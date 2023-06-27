package GC_11.view.Lobby;

import GC_11.distributed.Client;
import GC_11.exceptions.IllegalMoveException;
import GC_11.network.choices.Choice;
import GC_11.network.choices.ChoiceFactory;
import GC_11.network.message.LobbyViewMessage;
import GC_11.view.GUI.GUI;
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
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;


public class LobbyApplication extends Application {

    public Stage primaryStage;
    public Button confirmName;
    public TextField clientNickname;
    public TextArea listPlayers;
    public ChoiceBox chooseNumberPlayers;
    public TextArea errorArea;
    public Label text;

    private Client client;

    public LobbyApplication(){
        //this.client = client;

    }

    public void via(){
        Thread thread = new Thread(() -> Application.launch(LobbyApplication.class));
        thread.start();

        try {
            thread.join(); // Attendere la terminazione del thread
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

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

    public void setClient(Client client) {
        this.client = client;
    }
    public String sendNumberOfPlayer(){
        int numberOfPlayers = Integer.parseInt((String) chooseNumberPlayers.getValue());
        System.out.println(numberOfPlayers);
        waitingRoom();
        return chooseNumberPlayers.getValue().toString();
    }

    public void createChoice(String s) {
        Choice choice;
        try {
            choice = ChoiceFactory.createChoice(null, s);
        } catch (IllegalMoveException e) {
            throw new RuntimeException(e);
        }
        try {
            client.notifyServer(choice);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
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

        confirmName.setOnAction(event -> sendNumberOfPlayer());
        createChoice("ADD_PLAYER " +clientNickname.getText());
        notifyAll();
    }

    public void updatePlayerList(LobbyViewMessage message) {
        List<String> players = message.getPlayersNames();
        listPlayers.setText("");
        for(String player : players)
            listPlayers.appendText(player + "\n");
    }

    public GUIApplication changeScene() throws RemoteException {
        GUIApplication guiApplication = new GUIApplication();
        guiApplication.setClient(this.client);

        try {
            guiApplication.start(this.primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return guiApplication;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

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
        LobbyController controller = new LobbyController();
        // controller = loader.getController();
        loader.setController(controller);
        GUI.lobbyController = controller;

        primaryStage.getIcons().add(new Image("file:src/resources/GraphicalResources/Publisher material/Icon 50x50px.png"));
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
