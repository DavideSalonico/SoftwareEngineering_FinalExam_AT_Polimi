package GC_11.view.GUI;

import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.model.*;
import GC_11.network.GameViewMessage;
import GC_11.util.PlayerView;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

    public enum State {
        YOUR_TURN, WAITING, SELECTING_TILES, SELECTING_COLUMN, END
    }
    public State currentState;
    public String currentPlayerNickname;

    // ISTANZA MODEL CONTENUTA TEMPORANEAMENTE, POI CAPIRE COME RICEVERLA IN MANIERA DINAMICA
    public Game model;
    public Pane root;
    public GridPane mainGrid;
    public String clientNickName = "Pippo";  // TEMPORANEAMENTE FISSO A PIPPO DA RIMUOVERE (deve essere ricevuto dal server)
    public Text clientPoints;
    public ImageView personalGoal;
    public ImageView ICommonGoalCard;
    public ImageView IICommonGoalCard;
    Tooltip textCommonI;
    Tooltip textCommonII;
    public GridPane boardGridPane;
    public GridPane mainShelfGridPane;
    public ImageView firstPlayerToken;
    public GridPane playerShelf1;
    public GridPane playerShelf2;
    public ImageView deletableShelf;
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
    public Button confirmSelection;
    public Text selectTilesError;
    public Text selectColumnError;
    public TextArea chatTextArea;
    public TextField chatTextField;
    public Button sendMessageButton;

    // Initialize otherPlayers using PlayerView class as a container for the player's nickname, points and shelf (related to javafx objects)
    List<PlayerView> otherPlayers = new ArrayList<>();
    List<Player> others = new ArrayList<>();

    // The following maps contain the images of the tiles that will be used to create the GUI
    Map<Integer, Image> blueTiles = new HashMap<>();
    Map<Integer, Image> whiteTiles = new HashMap<>();
    Map<Integer, Image> greenTiles = new HashMap<>();
    Map<Integer, Image> yellowTiles = new HashMap<>();
    Map<Integer, Image> purpleTiles = new HashMap<>();
    Map<Integer, Image> cyanTiles = new HashMap<>();


    /**
     * This method is used to load the images of the tiles that will be used to create the GUI.
     */
    public void loadTilesImages(){
        // Percorsi dei file immagine
        String blueTilePath = "src/resources/GraphicalResources/item tiles/Cornici1.";
        String whiteTilePath = "src/resources/GraphicalResources/item tiles/Libri1.";
        String greenTilePath = "src/resources/GraphicalResources/item tiles/Gatti1.";
        String yellowTilePath = "src/resources/GraphicalResources/item tiles/Giochi1.";
        String purpleTilePath = "src/resources/GraphicalResources/item tiles/Piante1.";
        String cyanTilePath = "src/resources/GraphicalResources/item tiles/Trofei1.";


        for (int i = 1; i <= 3; i++) {
            blueTiles.put(i, new Image("file:" + blueTilePath + i + ".png"));
            whiteTiles.put(i, new Image("file:" + whiteTilePath + i + ".png"));
            greenTiles.put(i, new Image("file:" + greenTilePath + i + ".png"));
            yellowTiles.put(i, new Image("file:" + yellowTilePath + i + ".png"));
            purpleTiles.put(i, new Image("file:" + purpleTilePath + i + ".png"));
            cyanTiles.put(i, new Image("file:" + cyanTilePath + i + ".png"));
        }
    }


    /**
     * Initializes the GUIView automatically when the game starts, all the basic images are loaded and the game is created using
     * the instance of the GameView that we receive from the server.
     */
    @FXML
    public void initialize() {

        // Load all the images of the tiles
        loadTilesImages();

        // Save the desired dimensions of the window (specified in the FXML file) in order to resize the window when the game starts
        desiredWidth = root.getPrefWidth();
        desiredHeight = root.getPrefHeight();

        // Mi creo temporaneamente un modello di gioco per inizializzare bene la view
        List<String> tmpPlayerNames = new ArrayList<String>();
        tmpPlayerNames.add("Pippo");
        tmpPlayerNames.add("Pluto");
        tmpPlayerNames.add("Paperino");
        tmpPlayerNames.add("Giuseppe");
        model = new Game(tmpPlayerNames, null);



        // Put first Player's nickname into the chair on GUI
        Tooltip firstPlayer = new Tooltip("First PLAYER : " + model.getCurrentPlayer().getNickname());
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


        for (int i = 0; i < model.getPlayers().size(); i++) {
            if (!model.getPlayers().get(i).getNickname().equals(clientNickName)) {
                others.add(model.getPlayers().get(i));
            } else {
                clientPoints.setText("YOUR POINTS : " + model.getPlayers().get(i).getPoints());

                // OGNI TANTO MI TORNA PERSONAL GOAL NULL, CONTROLLARE!!!
                personalGoal.setImage(new Image("file:" +"src/resources/GraphicalResources/personal goal cards/Personal_Goals" + model.getPlayers().get(i).getPersonalGoal().getId() + ".png"));
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

            //remove the shelf of the player in the center (statically placed by the FXML)
            deletableShelf.setImage(null);
            player2Name.setText("");
            player2Points.setText("");

            // Questa ricerca dinamica delle celle da svuotare non funziona correttamente, cancello i riferimenti manulamente
            /*
            Node cell = getNodeByRowColumnIndex(1, 2, mainGrid);
            if (cell != null && cell instanceof Pane) {
                ((Pane) cell).getChildren().clear();
            }
            //rimuovo i riferimenti al nome e al punteggio
            cell = getNodeByRowColumnIndex(2, 2, mainGrid);
            if (cell != null && cell instanceof Pane) {
                ((Pane) cell).getChildren().clear();
            }
            */

        }

        // Initialize the other players with the data received from the server binding the GUI elements to each player's data
        for (int i = 0; i < others.size(); i++) {
            otherPlayers.get(i).initialize(others.get(i));
        }


        // TEST : Fill the shelf with the tiles randomly DA (RIMUOVERE)
        for (int i = 1; i < 6; i++) {
            for (int j = 1; j < 7; j++) {
                ImageView imageView1 = new ImageView(purpleTiles.get(1));
                ImageView imageView2 = new ImageView(yellowTiles.get(1));
                ImageView imageView3 = new ImageView(whiteTiles.get(1));
                ImageView imageView4 = new ImageView(blueTiles.get(1));

                imageView1.setFitHeight(29);
                imageView1.setFitWidth(29);

                imageView2.setFitHeight(29);
                imageView2.setFitWidth(29);

                imageView3.setFitHeight(29);
                imageView3.setFitWidth(29);

                imageView4.setFitHeight(50);
                imageView4.setFitWidth(50);

                playerShelf1.add(imageView1, i, j);
                playerShelf2.add(imageView2, i, j);
                playerShelf3.add(imageView3, i, j);
                mainShelfGridPane.add(imageView4, i, j);
            }
        }

        // PROVO A CAMBIARE UNA TILE per provare
        ImageView search = getImageViewFromGridPane(playerShelf1, 1, 1);
        search.setImage(null);
        search.setImage(yellowTiles.get(1));

        //Initialize Board with the data received from the server
        refreshBoard(model.getBoard());

    }


    public void updateView(GameViewMessage message){
        //TODO: update the view with the data received from the server
    }


    /**
     * Method that returns the node from the GridPane given the row and column index
     * @param column column
     * @param gridPane reference to GUI component
     * @ return Node reference, null if the node is not found
     */
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


    /**
     * Method that returns the ImageView from the GridPane given the row and column index
     * @param gridPane reference
     * @param targetRowIndex line
     * @param targetColumnIndex column
     * @return ImageView reference, null if the ImageView is not found
     */
    public ImageView getImageViewFromGridPane(GridPane gridPane, int targetRowIndex, int targetColumnIndex) {
        ObservableList<Node> children = gridPane.getChildren();

        for (Node node : children) {
            if (node instanceof ImageView) {
                Integer rowIndex = GridPane.getRowIndex(node);
                Integer columnIndex = GridPane.getColumnIndex(node);

                if (rowIndex != null && columnIndex != null && rowIndex == targetRowIndex && columnIndex == targetColumnIndex) {
                    return (ImageView) node;
                }
            }
        }

        return null; // Restituisce null se l'ImageView non è stata trovata
    }

    /**
     * Method that removes the tile from the board given the row and column index
     * @param row line
     * @param column column
     */
    public void removeTileFromBoard(int row, int column) {
        ImageView image = getImageViewFromGridPane(boardGridPane, row, column);
        image.setImage(null);
    }


    private List<ImageView> selectedImages = new ArrayList<>();
    /**
     * Method that set up the event handler for the ImageView to show it selected when clicked (MAX 3 tiles selected)
     * @param imageView ImageView to set up
     */
    private void setupImageViewSelection(ImageView imageView) {
        final String SELECTED_STYLE_CLASS = "selected-tile";

        imageView.setOnMouseClicked(event -> {
            if (selectedImages.contains(imageView)) {
                // L'immagine è già stata selezionata, rimuovila dalla lista
                selectedImages.remove(imageView);
                imageView.getStyleClass().remove(SELECTED_STYLE_CLASS);
            } else {
                if (selectedImages.size() < 3) {
                    // Aggiungi l'immagine alla lista delle selezioni
                    selectedImages.add(imageView);
                    System.out.println("Tile selected, n° selected: " + selectedImages.size());
                    imageView.getStyleClass().add(SELECTED_STYLE_CLASS);
                }
            }
        });
    }

    /**
     * Method bound to the button "Confirm" that will send the request to the server after the user has selected the tiles to draw from the board
     */
    public void selectionTilesConfirmed(){
        if(selectedImages.size() == 0){
            System.out.println("Select at least one tile !");
        }else{
            System.out.println("Request to server...");
            // Make the request to the server
        }
    }


    int columnSelected = 0;
    /**
     * Method bound to every button of the column selector that will set the columnSelected variable to the column selected
     * @param event to get the id of the button pressed
     */
    public void selectColumn(ActionEvent event){
        Button button = (Button) event.getSource();
        columnSelected = columnSelector.getButtons().indexOf(button) + 1;
        System.out.println("Column selected: " + columnSelected);
    }

    /**
     * Method bound to the button "Confirm" that will send the request to the server after the user has selected the column where to place the tile
     */
    public void columnSelectionConfirmed(){
        if(columnSelected != 0) {
            System.out.println("Select first the column !");
        }else {
            System.out.println("Request to server...");
            // Make the request to the server
        }
    }


    /**
     * It finds the PlayerView from the nickname between the otherPlayers list
     * @param nickname of the player
     * @return PlayerView reference, null if the player is not found
     */
    public PlayerView getPlayerViewFromNickname(String nickname){
        for(PlayerView playerView : otherPlayers){
            if(playerView.getClientNickName().getText().equals(nickname)){
                return playerView;
            }
        }
        return null;
    }

    /**
     * Method that updates Shelf and Points of the player given as parameter
     * @param player to update
     * @throws ColumnIndexOutOfBoundsException if the player is not found
     */
    public void updatePlayer(Board board, Player player) throws ColumnIndexOutOfBoundsException {
        PlayerView playerView = getPlayerViewFromNickname(player.getNickname());
        if (playerView != null) {
            refreshBoard(board);
            updateShelf(player, playerView.getShelf());
            updatePoints(player, playerView.getPoints());
        }
    }

    /**
     * Method that updates the Shelf of the player
     * @param player to update
     * @param shelf GridPane reference
     * @throws ColumnIndexOutOfBoundsException if the player is not found
     */
    public void updateShelf(Player player, GridPane shelf) throws ColumnIndexOutOfBoundsException {
        for (int i = 1; i < 6; i++) {  //COLUMNS
            for (int j = 1; j < 7; j++) {  //ROWS
                Tile t = player.getShelf().getTile(j, i);
                int id = t.getId() + 1;
                TileColor tileColor = t.getColor();
                ImageView image = switch (tileColor) {
                    case WHITE -> new ImageView(whiteTiles.get(id));
                    case PURPLE -> new ImageView(purpleTiles.get(id));
                    case GREEN -> new ImageView(greenTiles.get(id));
                    case BLUE -> new ImageView(blueTiles.get(id));
                    case CYAN -> new ImageView(cyanTiles.get(id));
                    case YELLOW -> new ImageView(yellowTiles.get(id));
                    default -> null;
                };

                if(image != null) {
                    image.setFitHeight(29);
                    image.setFitWidth(29);
                    shelf.add(image, i, j);
                }
            }
        }
    }

    /**
     * Method that updates the points of the player
     * @param player to update
     * @param points Text reference
     */
    public void updatePoints(Player player, Text points){
        points.setText("Points: " + player.getPoints());
    }


    /**
     * Method that updates the Board of the client who is using the GUI
     * @param player
     * @throws ColumnIndexOutOfBoundsException
     */
    public void updateClientShelf(Player player) throws ColumnIndexOutOfBoundsException {
        for (int i = 1; i < 6; i++) {  //COLUMNS
            for (int j = 1; j < 7; j++) {  //ROWS
                Tile t = player.getShelf().getTile(j, i);
                int id = t.getId() + 1;
                TileColor tileColor = t.getColor();
                ImageView image = switch (tileColor) {
                    case WHITE -> new ImageView(whiteTiles.get(id));
                    case PURPLE -> new ImageView(purpleTiles.get(id));
                    case GREEN -> new ImageView(greenTiles.get(id));
                    case BLUE -> new ImageView(blueTiles.get(id));
                    case CYAN -> new ImageView(cyanTiles.get(id));
                    case YELLOW -> new ImageView(yellowTiles.get(id));
                    default -> null;
                };

                if(image != null) {
                    image.setFitHeight(50);  //Other players shelf size is 29x29!
                    image.setFitWidth(50);
                    mainShelfGridPane.add(image, i, j);  //Add the image to specif Shelf
                }
            }
        }
    }

    /**
     * Method that updates the points of the client who is using the GUI
     * @param player
     */
    public void updateClientPoints(Player player){
        clientPoints.setText("Points: " + player.getPoints());
    }


    public void setBoardError(String error){
        selectTilesError.setText(error);
    }

    public void setColumnError(String error){
        selectColumnError.setText(error);
    }


    public void refreshBoard(Board board){
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                Tile t = board.getTile(i - 1, j - 1);
                int id = t.getId() + 1;
                TileColor tileColor = t.getColor();
                ImageView image = switch (tileColor) {
                    case WHITE -> new ImageView(whiteTiles.get(id));
                    case PURPLE -> new ImageView(purpleTiles.get(id));
                    case GREEN -> new ImageView(greenTiles.get(id));
                    case BLUE -> new ImageView(blueTiles.get(id));
                    case CYAN -> new ImageView(cyanTiles.get(id));
                    case YELLOW -> new ImageView(yellowTiles.get(id));
                    default -> null;
                };
                if (image != null) {
                    image.getStyleClass().add("selected-image");

                    // This line add the event handler to the image which show it selected when clicked (MAX 3 tiles selected)
                    setupImageViewSelection(image);

                    image.setFitHeight(43);
                    image.setFitWidth(43);
                    boardGridPane.add(image, i, j);
                }
            }
        }
    }

    /**
     * Method that receive a message from the server and update the chat
     * @param message sent by other player
     */
    public void updateChat(String message){
        chatTextArea.appendText(message + "\n");
    }

    /**
     * Method that send a message to other players in the chat
     */
    public void sendMessageOnChat(){
        chatTextArea.appendText(currentPlayerNickname + " : " + chatTextField.getText() + "\n");
        // MANDA MESSAGGIO AL SERVER
    }




    /**
     * Method that will be called when the game starts
     * @param primaryStage Stage reference
     */
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
        // QUESTE RIGHE DI CODICE PERMETTONO DI FARE UNA RESIZE DELLA FINESTRA IN BASE ALLE DIMENSIONI DELLO SCHERMO DEL PC!!!!
        // Funzionano ma dimensionano solo il PANE e non tutto il resto !!!
        /*Screen screen = Screen.getPrimary();
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




}


