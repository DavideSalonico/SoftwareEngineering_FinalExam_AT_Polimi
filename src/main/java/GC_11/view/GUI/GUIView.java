package GC_11.view.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class GUIView extends Application {


    public ImageView ICommonGoalCard;
    public ImageView IICommonGoalCard;
    public ColumnConstraints boardGrid;

    public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage primaryStage) throws MalformedURLException {
            FXMLLoader loader = new FXMLLoader();
            System.out.println(System.getProperty("user.dir"));
            loader.setLocation(new URL("file:///" + System.getProperty("user.dir") + "\\src\\main\\java\\GC_11\\view\\GUI\\GUI.fxml"));
            Pane pane = null;
            try {
                pane = loader.<Pane>load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Scene scene = new Scene(pane);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
}


