package GC_11.view.GUI;

import GC_11.model.Game;
import GC_11.model.Player;
import GC_11.model.Tile;
import GC_11.model.TileColor;
import GC_11.util.PlayerView;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GUIView extends Application {

    // ISTANZA MODEL CONTENUTA TEMPORANEAMENTE, POI CAPIRE COME RICEVERLA IN MANIERA DINAMICA


    public Game model;

    @FXML
    public Pane root;

    public GridPane mainGrid;
    public String clientNickName = "Pippo";  // TEMPORANEAMENTE FISSO A PIPPO DA RIMUOVERE (deve essere ricevuto dal server)
    public Text clientPoints;
    public ImageView personalGoal;
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

    @FXML
    public GridPane playerShelf1;
    @FXML
    public GridPane playerShelf2;
    @FXML
    public GridPane playerShelf3;

    public Text player1Name;
    public Text player2Name;
    public Text player3Name;

    public Text player1Points;
    public Text player2Points;
    public Text player3Points;

    public ButtonBar columnSelector;

    double desiredWidth;
    double desiredHeight;

    /**
     * Initializes the GUIView automatically when the game starts, all the basic images are loaded and the game is created using
     * the instance of the GameView that we receive from the server.
     */
    @FXML
    public void initialize() {
        //TODO: Metti in memoria tutte le tile, associa ogni cella ad un immagine, crea la costruzione dinamica delle restanti Shelf (copiandole da quella principale)

        // Imposta le dimensioni della finestra in base alla dimensione dello schermo e alle dimensioni specificate nel file FXML
        desiredWidth = root.getPrefWidth();
        desiredHeight = root.getPrefHeight();

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
        ICommonGoalCard.getStyleClass().add("selected-image");
        IICommonGoalCard.getStyleClass().add("selected-image");

        // Config event handler for common goal cards
        //setupCommonGoalCardEvents();

        //The following maps contain the images of the tiles that will be used to create the GUI
        // Percorsi dei file immagine
        String blueTilePath = "src/resources/GraphicalResources/item tiles/Cornici1.";
        String whiteTilePath = "src/resources/GraphicalResources/item tiles/Libri1.";
        String greenTilePath = "src/resources/GraphicalResources/item tiles/Gatti1.";
        String yellowTilePath = "src/resources/GraphicalResources/item tiles/Giochi1.";
        String purpleTilePath = "src/resources/GraphicalResources/item tiles/Piante1.";
        String cyanTilePath = "src/resources/GraphicalResources/item tiles/Trofei1.";

        // The following maps contain the images of the tiles that will be used to create the GUI
        Map<Integer, Image> blueTiles = new HashMap<>();
        Map<Integer, Image> whiteTiles = new HashMap<>();
        Map<Integer, Image> greenTiles = new HashMap<>();
        Map<Integer, Image> yellowTiles = new HashMap<>();
        Map<Integer, Image> purpleTiles = new HashMap<>();
        Map<Integer, Image> cyanTiles = new HashMap<>();

        for (int i = 1; i <= 3; i++) {
            blueTiles.put(i, new Image("file:" + blueTilePath + i + ".png"));
            whiteTiles.put(i, new Image("file:" + whiteTilePath + i + ".png"));
            greenTiles.put(i, new Image("file:" + greenTilePath + i + ".png"));
            yellowTiles.put(i, new Image("file:" + yellowTilePath + i + ".png"));
            purpleTiles.put(i, new Image("file:" + purpleTilePath + i + ".png"));
            cyanTiles.put(i, new Image("file:" + cyanTilePath + i + ".png"));
        }

        // Initialize otherPlayers using PlayerView class as a container for the player's nickname, points and shelf (related to javafx objects)

        List<PlayerView> otherPlayers = new ArrayList<>();
        List<Player> others = new ArrayList<>();

        for (int i = 0; i < model.getPlayers().size(); i++) {
            if (!model.getPlayers().get(i).getNickname().equals(clientNickName)) {
                others.add(model.getPlayers().get(i));
            } else {
                clientPoints.setText("YOUR POINTS : " + model.getPlayers().get(i).getPoints());

                // NON FUNZIONA ANCORA BENE CAPISCI PERCHE' (riga sotto)
                personalGoal.setImage(new Image("file:src/resources/GraphicalResources/personal goal cards/Personal_Goals" + model.getPlayers().get(i).getPersonalGoal().getId() + ".png"));
            }
        }

        // Versione con 4 giocatori
        if (others.size() == 3) {
            otherPlayers.add(new PlayerView(player1Name, player1Points, playerShelf1));
            otherPlayers.add(new PlayerView(player2Name, player2Points, playerShelf2));
            otherPlayers.add(new PlayerView(player3Name, player3Points, playerShelf3));
        }
        // Versione con 3 giocatori
        else if (others.size() == 2) {
            otherPlayers.add(new PlayerView(player1Name, player1Points, playerShelf1));
            otherPlayers.add(new PlayerView(player3Name, player3Points, playerShelf3));

            // rimuovo la shelf del giocatore al centro (posta staticamente dall'FXML)
            Node cell = getNodeByRowColumnIndex(1, 2, mainGrid);
            if (cell != null && cell instanceof Pane) {
                ((Pane) cell).getChildren().clear();
            }
            //rimuovo i riferimenti al nome e al punteggio
            cell = getNodeByRowColumnIndex(2, 2, mainGrid);
            if (cell != null && cell instanceof Pane) {
                ((Pane) cell).getChildren().clear();
            }
        }
        for (int i = 0; i < others.size(); i++) {
            otherPlayers.get(i).initialize(others.get(i));
        }


       /* for(int i = 0; i<otherPlayers.size(); i++){
            mainGrid.add(otherPlayers.get(i).getClientNickName(), i, 2);
            mainGrid.add(otherPlayers.get(i).getPoints(), i, 2);
            GridPane.setHalignment(otherPlayers.get(i).getPoints(), HPos.RIGHT);
            mainGrid.add(otherPlayers.get(i).getShelf(), i, 1);
            //DISEGNA UNA GRIGLIA SOPRA LE SHELF E COMPILARE CON UN CICLO FOR
        }
    */
        // TEST : Fill the shelf with the tiles randomly
        for (int i = 1; i < 6; i++) {
            for (int j = 1; j < 7; j++) {
                ImageView imageView1 = new ImageView(purpleTiles.get(1));
                ImageView imageView2 = new ImageView(yellowTiles.get(1));
                ImageView imageView3 = new ImageView(whiteTiles.get(1));
                ImageView imageView4 = new ImageView(blueTiles.get(1));

                imageView1.setFitHeight(29);
                imageView1.setFitWidth(29);
                imageView1.getStyleClass().add("selected-image");  // This css style is used to highlight the selected tile

                imageView2.setFitHeight(29);
                imageView2.setFitWidth(29);
                imageView2.getStyleClass().add("selected-image");

                imageView3.setFitHeight(29);
                imageView3.setFitWidth(29);
                imageView3.getStyleClass().add("selected-image");

                imageView4.setFitHeight(50);
                imageView4.setFitWidth(50);
                imageView4.getStyleClass().add("selected-image");

                playerShelf1.add(imageView1, i, j);
                playerShelf2.add(imageView2, i, j);
                playerShelf3.add(imageView3, i, j);
                mainShelfGridPane.add(imageView4, i, j);
            }
        }

        // Set all the points of Players
        //clientPoints.setText("YOUR POINTS: " + model.getPlayer(clientNickName).getPoints());
        // player1Points.setText("POINTS: " + model.getPlayer(otherPlayers.get(0).getClientNickName().getText()).getPoints());

        // Fill the board dynamically using the model
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                Tile t = model.getBoard().getTile(i - 1, j - 1);
                int id = t.getId() + 1;
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
                        image = new ImageView(yellowTiles.get(id));
                        break;
                    default:
                        image = null;
                        break;
                }
                if (image != null) {
                    image.setFitHeight(41);
                    image.setFitWidth(41);
                    boardGridPane.add(image, i, j);
                }
            }
        }

    }


    // Metodo ausiliario per ottenere il nodo della cella dalla riga e colonna
    private Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> children = gridPane.getChildren();

        for (Node node : children) {
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer columnIndex = GridPane.getColumnIndex(node);

            if (rowIndex != null && columnIndex != null && rowIndex == row && columnIndex == column) {
                result = node;
                break;
            }
        }

        return result;
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
        /* QUESTE RIGHE DI CODICE PERMETTONO DI FARE UNA RESIZE DELLA FINESTRA IN BASE ALLE DIMENSIONI DELLO SCHERMO DEL PC!!!!
        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getBounds().getWidth();
        double screenHeight = screen.getBounds().getHeight();

        // Calcola le nuove dimensioni mantenendo le proporzioni originali
        double scaleFactor = Math.min(screenWidth / desiredWidth, screenHeight / desiredHeight); //desiredWidth e Height non sono inizializzate
        double newWidth = desiredWidth * scaleFactor;
        double newHeight = desiredHeight * scaleFactor;

        // Imposta le nuove dimensioni della finestra
        primaryStage.setWidth(newWidth);
        primaryStage.setHeight(newHeight);
        */
        Scene scene = new Scene(pane);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public void launchApp() {
        launch();
    }


}


