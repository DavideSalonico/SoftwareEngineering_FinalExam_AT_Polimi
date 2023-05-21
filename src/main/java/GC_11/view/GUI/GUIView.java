package GC_11.view.GUI;

import GC_11.model.Game;
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

    public Game model;
    public String clientNickName;
    public ImageView ICommonGoalCard;
    public ImageView IICommonGoalCard;
    public ColumnConstraints boardGrid;

    public static void main(String[] args) {
            launch(args);
    }

    // NON POSSO METTERE COSTRUTTORI PERCHE' LA CLASSE DEVE AVERE UN COSTRUTTORE DI DEFAULT CON CUI VIENE INZIALIZZATA

    public void setUI() {
        //TODO: Metti in memoria tutte le tile, associa ogni cella ad un immagine, crea la costruzione dinamica delle restanti Shelf (copiandole da quella principale)
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


