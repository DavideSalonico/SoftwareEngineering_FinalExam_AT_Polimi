package GC_11.view.GUI;

import GC_11.view.Lobby.LobbyController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class GUIApplication extends Application {

    private static volatile boolean javaFxLaunched = false;
    public static Stage primaryStage;
    public static Pane lobbyLoad;
    public static Pane gameLaod;
    public static Parent endGameLoad;

    public static GUIController gameController;
    public static LobbyController lobbyController;

    public GUIApplication(){
        if (!javaFxLaunched) { // First time
            Platform.setImplicitExit(false);
            new Thread(()->Application.launch(GUIApplication.class)).start();
            javaFxLaunched = true;
        }
    }


    /**
     * Method that will be called when the game starts
     * @param primaryStage Stage reference
     */
    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URL("file:///" + System.getProperty("user.dir") + "\\src\\main\\java\\GC_11\\view\\GUI\\GUI.fxml"));


        gameLaod = loader.<Pane>load();
        gameController = loader.getController();
        GUI.setGameController(gameController);



        FXMLLoader loaderLobby = new FXMLLoader();
        loaderLobby.setLocation(new URL("file:///" + System.getProperty("user.dir") + "\\src\\main\\java\\GC_11\\view\\Lobby\\LobbyGUI.fxml"));

        lobbyLoad = loaderLobby.<Pane>load();
        lobbyController = loaderLobby.getController();
        GUI.setLobbyController(lobbyController);

        Scene scene = new Scene(gameLaod);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        primaryStage.getIcons().add(new Image("file:src/resources/GraphicalResources/Publisher material/Icon 50x50px.png"));
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();




    }




}


