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
    public static Stage mainStage;
    public static Pane connectionLoad;
    public static Pane lobbyLoad;
    public static Pane gameLaod;
    public static Parent endGameLoad;

    public static ConnectionController connectionController;
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

        FXMLLoader connection = new FXMLLoader();
        connection.setLocation(new URL("file:///" + System.getProperty("user.dir") + "\\src\\main\\java\\GC_11\\view\\GUI\\connection.fxml"));
        connectionLoad = connection.<Pane>load();
        connectionController = connection.getController();



        Scene scene = new Scene(lobbyLoad);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        mainStage = primaryStage;
        primaryStage.getIcons().add(new Image("file:src/resources/GraphicalResources/Publisher material/Icon 50x50px.png"));
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();




    }




}


