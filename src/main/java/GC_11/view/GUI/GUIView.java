package GC_11.view.GUI;

import GC_11.model.Game;
import GC_11.model.Tile;
import GC_11.model.TileColor;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GUIView extends Application {

    // ISTANZA MODEL CONTENUTA TEMPORANEAMENTE, POI CAPIRE COME RICEVERLA IN MANIERA DINAMICA
    @FXML
    public Game model;
    @FXML
    public String clientNickName = "Pippo";  // TEMPORANEAMENTE FISSO A PIPPO
    @FXML
    public ImageView ICommonGoalCard;
    @FXML
    public ImageView IICommonGoalCard;
    Tooltip textCommonI;
    Tooltip textCommonII;
    @FXML
    public GridPane boardGridPane;
    @FXML
    public GridPane mainShelfGridPane;
    @FXML
    public GridPane otherShelfGridPane;

    @FXML
    public ImageView firstPlayerToken;

    /**
     * Initializes the GUIView automatically when the game starts, all the basic images are loaded and the game is created using
     * the instance of the GameView that we receive from the server.
     */
    @FXML
    public void initialize() {
        //TODO: Metti in memoria tutte le tile, associa ogni cella ad un immagine, crea la costruzione dinamica delle restanti Shelf (copiandole da quella principale)

        // Mi creo temporaneamente un modello di gioco per inizializzare bene la view
        List<String> tmpPlayerNames = new ArrayList<String>();
        tmpPlayerNames.add("Pippo");
        tmpPlayerNames.add("Pluto");
        tmpPlayerNames.add("Paperino");
        tmpPlayerNames.add("Giuseppe");
        model = new Game(tmpPlayerNames, null);


        //Background contains the images of the scene that will be used to create the GUI ( PROBABILMENTE CI PENSA GIA' SceneBuilder in maniera statica)
        Image[] background = new Image[8];

        // Put current Player's nickname into the chair on GUI
        Tooltip firstPlayer = new Tooltip("CURRENT PLAYER : " + model.getCurrentPlayer().getNickname());
        Tooltip.install(firstPlayerToken, firstPlayer);

        //Get common goal cards from the model
        String pathCommonI = "src/resources/GraphicalResources/common goal cards/" + model.getCommonGoal(0).getId() + ".jpg";
        String pathCommonII = "src/resources/GraphicalResources/common goal cards/" + model.getCommonGoal(1).getId() + ".jpg";
        Image commonI = new Image("file:" + pathCommonI);
        Image commonII = new Image("file:" + pathCommonII);
        // Set common goal cards images on existing javafx objects
        ICommonGoalCard.setImage(commonI);
        IICommonGoalCard.setImage(commonII);
        ICommonGoalCard.setDisable(false);
        IICommonGoalCard.setDisable(false);
        //Get the Common Goal text and show only when user go with the mouse over the card
        textCommonI = new Tooltip(model.getCommonGoal(0).getText());
        textCommonII = new Tooltip(model.getCommonGoal(1).getText());
        Tooltip.install(ICommonGoalCard, textCommonI);
        Tooltip.install(IICommonGoalCard, textCommonII);

        // Config event handler for common goal cards
        //setupCommonGoalCardEvents();
        
        //The following maps contain the images of the tiles that will be used to create the GUI
        // Percorsi dei file immagine
        String blueTilePath = "src/resources/GraphicalResources/item tiles/Cornici1.";
        String whiteTilePath = "src/resources/GraphicalResources/item tiles/Libri1.";
        String greenTilePath = "src/resources/GraphicalResources/item tiles/Gatti1.";
        String orangeTilePath = "src/resources/GraphicalResources/item tiles/Giochi1.";
        String purpleTilePath = "src/resources/GraphicalResources/item tiles/Piante1.";
        String cyanTilePath = "src/resources/GraphicalResources/item tiles/Trofei1.";

        // The following maps contain the images of the tiles that will be used to create the GUI
        Map<Integer, Image> blueTiles = new HashMap<>();
        Map<Integer, Image> whiteTiles = new HashMap<>();
        Map<Integer, Image> greenTiles = new HashMap<>();
        Map<Integer, Image> orangeTiles = new HashMap<>();
        Map<Integer, Image> purpleTiles = new HashMap<>();
        Map<Integer, Image> cyanTiles = new HashMap<>();

        for (int i = 1; i <= 3; i++) {
            blueTiles.put(i, new Image("file:" + blueTilePath + i + ".png"));
            whiteTiles.put(i, new Image("file:" + whiteTilePath + i + ".png"));
            greenTiles.put(i, new Image("file:" + greenTilePath + i + ".png"));
            orangeTiles.put(i, new Image("file:" + orangeTilePath + i + ".png"));
            purpleTiles.put(i, new Image("file:" + purpleTilePath + i + ".png"));
            cyanTiles.put(i, new Image("file:" + cyanTilePath + i + ".png"));
        }

        // Initialize otherPlayers map using their nickname as key and their shelf as value
        Map<String, Image> otherPlayers = new HashMap<>();

        for (int i = 0; i<  model.getPlayers().size(); i++){
            if(!model.getPlayers().get(i).getNickname().equals(clientNickName)) {
                otherPlayers.put(model.getPlayers().get(i).getNickname(),new Image("file: src/resources/GraphicalResources/boards/bookshelf.png"));

            }
            //i punti
        }

        for(int i = 1; i<10; i++){
            for(int j = 1; j<10; j++){
                Tile t = model.getBoard().getTile(i-1,j-1);
                int id = t.getId()+1;
                TileColor tileColor = t.getColor();
                ImageView image;
                switch (tileColor) {
                    case WHITE:
                        image = new ImageView(whiteTiles.get(id));
                        break;
                    case PURPLE:
                        image = new ImageView(purpleTiles.get(id));
                        break;
                    case GREEN:
                        image = new ImageView(greenTiles.get(id));
                        break;
                    case BLUE:
                        image = new ImageView(blueTiles.get(id));
                        break;
                    case CYAN:
                        image = new ImageView(cyanTiles.get(id));
                        break;
                    case YELLOW:
                        image = new ImageView(orangeTiles.get(id));
                        break;
                    default:
                        image = null;
                        break;
                }
                if(image != null){
                    image.setFitHeight(41);
                    image.setFitWidth(41);
                    boardGridPane.add(image, i, j);
                }
            }
        }

    }


    //Method that will be called when the game starts
    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URL("file:///" + System.getProperty("user.dir") + "\\src\\main\\java\\GC_11\\view\\GUI\\GUI.fxml"));
        Pane pane;

        try {
            pane = loader.<Pane>load();
        } catch (IOException e) {
            System.out.println("Errore nel caricamento della GUI " + e.getMessage());
            throw new RuntimeException(e);

        }

        Scene scene = new Scene(pane);
        // SISTEMARE AGGIUNTA CSS
        //scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public void launchApp() {
        launch();
    }



}


