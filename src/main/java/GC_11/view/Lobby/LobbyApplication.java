package GC_11.view.Lobby;

import GC_11.distributed.Client;
import GC_11.view.GUI.GUI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;


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
        Thread thread = new Thread(() -> Application.launch(LobbyApplication.class));
        thread.start();

        /*try {
            thread.join(); // Attendere la terminazione del thread
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

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
