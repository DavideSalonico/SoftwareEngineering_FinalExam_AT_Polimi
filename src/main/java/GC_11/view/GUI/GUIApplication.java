package GC_11.view.GUI;

import GC_11.view.GUI.LobbyController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * Class that will launch the GUI, it manage the GUI controllers and the FXML load of all Scene
 */
public class GUIApplication extends Application {

    private static volatile boolean javaFxLaunched = false;
    public static Stage mainStage;
    public static Pane connectionLoad;
    public static Pane lobbyLoad;
    public static Pane gameLaod;

    public static ConnectionController connectionController;
    public static GUIController gameController;
    public static LobbyController lobbyController;

    /**
     * Method that will be called when the GUI starts, it will launch the GUI as a new Thread
     */
    public GUIApplication(){
        if (!javaFxLaunched) { // First time
            Platform.setImplicitExit(false);
            new Thread(()->Application.launch(GUIApplication.class)).start();
            javaFxLaunched = true;
        }
    }

    /**
     * Method that will be called when the GUI starts automatically, it will load the FXML files and set the controllers in static variables so
     * they can be used by the client to update the GUI
     * @param primaryStage Stage reference
     */
    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/GUI.fxml"));

        gameLaod = loader.<Pane>load();
        gameController = loader.getController();
        GUI.setGameController(gameController);

        FXMLLoader loaderLobby = new FXMLLoader();
        loaderLobby.setLocation(getClass().getResource("/fxml/LobbyGUI.fxml"));

        lobbyLoad = loaderLobby.<Pane>load();
        lobbyController = loaderLobby.getController();
        GUI.setLobbyController(lobbyController);

        FXMLLoader connection = new FXMLLoader();
        connection.setLocation(getClass().getResource("/fxml/Connection.fxml"));
        connectionLoad = connection.<Pane>load();
        connectionController = connection.getController();
        GUI.connectionController = connectionController;

        Scene scene = new Scene(connectionLoad);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        mainStage = primaryStage;
        primaryStage.getIcons().add(new Image("file:src/resources/GraphicalResources/Publisher material/Icon 50x50px.png"));
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

}


