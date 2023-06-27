package GC_11.view.GUI;

import GC_11.distributed.Client;
import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.exceptions.IllegalMoveException;
import GC_11.model.*;
import GC_11.network.choices.Choice;
import GC_11.network.choices.ChoiceFactory;
import GC_11.network.message.GameViewMessage;
import GC_11.util.PlayerView;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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

public class GUIApplication extends Application {

    public enum State {
        YOUR_TURN, WAITING, SELECTING_TILES, SELECTING_COLUMN, END
    }
    public String currentPlayerNickname;
    private Client client;

    public Game model;
    public Pane root;
    public GridPane mainGrid;
    public String clientNickName = "Pippo";
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
    public ImageView deletableShelf2;
    public GridPane playerShelf3;
    public Text player1Name;
    public Text player2Name;
    public Text player3Name;
    public Text player1Points;
    public Text player2Points;
    public Text player3Points;
    public ButtonBar columnSelector;
    public Button confirmSelection;
    public Text error;
    public TextArea chatTextArea;
    public TextArea chatTextArea1;
    public TextArea chatTextArea2;
    public TextArea chatTextArea3;
    public TabPane tabPane;
    public TextField chatTextField;
    public Button sendMessageButton;

    public ImageView firstImage;
    public Button firstTile;
    public Button secondTile;
    public ImageView secondImage;
    public Button thirdTile;
    public ImageView thirdImage;

    public Button resetButton;

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

    public GUIApplication(){
        //Careful to errors
        new Thread(()->Application.launch(GUIApplication.class)).start();
    }

    public void setClient(Client client){
        this.client = client;
    }


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
     * Initializes the GUIApplication automatically when the game starts, all the basic images are loaded and the game is created using
     * the instance of the GameView that we receive from the server.
     */

    public void init(GameViewMessage gameViewMessage) {
        // Load all the images of the tiles
        loadTilesImages();

        // Put first Player's nickname into the chair on GUI
        Tooltip firstPlayer = new Tooltip("First PLAYER : " + gameViewMessage.getPlayer(gameViewMessage.getCurrentPlayer()));
        Tooltip.install(firstPlayerToken, firstPlayer);

        //Get common goal cards from the model
        String pathCommonI = "src/resources/GraphicalResources/common goal cards/" + gameViewMessage.getCommonGoalCard(0).getId() + ".jpg";
        String pathCommonII = "src/resources/GraphicalResources/common goal cards/" + gameViewMessage.getCommonGoalCard(1).getId() + ".jpg";
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

        //Initialize the other players and the main player (client)
        for (int i = 0; i < gameViewMessage.getPlayers().size(); i++) {
            if (!gameViewMessage.getPlayers().get(i).getNickname().equals(clientNickName)) {
                others.add(gameViewMessage.getPlayers().get(i));
            } else {
                clientPoints.setText("YOUR POINTS : " + gameViewMessage.getPlayers().get(i).getPoints());

                // OGNI TANTO MI TORNA PERSONAL GOAL NULL, CONTROLLARE!!!
                personalGoal.setImage(new Image("file:src/resources/GraphicalResources/personal goal cards/Personal_Goals" + gameViewMessage.getPlayers().get(i).getPersonalGoal().getId() + ".png"));
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

            clearCellContent(mainGrid,1,2);
            clearCellContent(mainGrid,2,2);


        } else if (others.size() == 1) {
            otherPlayers.add(new PlayerView(player3Name, player3Points, playerShelf3));

            //remove the shelf of the player in the center (statically placed by the FXML)
            clearCellContent(mainGrid,1,2);
            clearCellContent(mainGrid,2,2);

            clearCellContent(mainGrid,1,1);
            clearCellContent(mainGrid,2,1);

        }

        // Initialize the other players with the data received from the server binding the GUI elements to each player's data
        for (int i = 0; i < others.size(); i++) {
            otherPlayers.get(i).initialize(others.get(i));
        }

        //Initialize Board with the data received from the server
        refreshBoard(gameViewMessage.getBoard());


    }
    @FXML
    public void initialize() {

        //textField per la chat editabile comodamente
        chatTextField.setOnMouseClicked(event -> {
            chatTextField.setText(""); // Seleziona tutto il testo del TextField
        });


        //Column Selector invisible
        columnSelector.setDisable(true);

        //Button Reset invisible
        resetButton.setVisible(false);

        // Initialize order tile
        firstTile.setText("");
        firstImage.setImage(null);
        secondTile.setText("");
        secondImage.setImage(null);
        thirdTile.setText("");
        thirdImage.setImage(null);

        // Load all the images of the tiles
        loadTilesImages();


        // I temporarily create a game model to initialize the view
        List<String> tmpPlayerNames = new ArrayList<String>();
        tmpPlayerNames.add("Pippo");
        tmpPlayerNames.add("Pluto");
        //tmpPlayerNames.add("Paperino");
        //tmpPlayerNames.add("Giuseppe");
        model = new Game(tmpPlayerNames, null);



        // Put first Player's nickname into the chair on GUI    RIMUOVI
        Tooltip firstPlayer = new Tooltip("First PLAYER : " + model.getCurrentPlayer().getNickname());
        Tooltip.install(firstPlayerToken, firstPlayer);

        //Get common goal cards from the model    RIMUOVI
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


        // RIMUOVI
        for (int i = 0; i < model.getPlayers().size(); i++) {
            if (!model.getPlayers().get(i).getNickname().equals(clientNickName)) {
                others.add(model.getPlayers().get(i));
            } else {
                clientPoints.setText("YOUR POINTS : " + model.getPlayers().get(i).getPoints());

                // OGNI TANTO MI TORNA PERSONAL GOAL NULL, CONTROLLARE!!!
                personalGoal.setImage(new Image("file:src/resources/GraphicalResources/personal goal cards/Personal_Goals" + model.getPlayers().get(i).getPersonalGoal().getId() + ".png"));
            }
        }

        // Versione con 4 giocatori    RIMUOVI TUTTO
        if (others.size() == 3) {
            otherPlayers.add(new PlayerView(player1Name, player1Points, playerShelf1));
            otherPlayers.add(new PlayerView(player2Name, player2Points, playerShelf2));
            otherPlayers.add(new PlayerView(player3Name, player3Points, playerShelf3));
        }
        // Versione con 3 giocatori
        else if (others.size() == 2) {
            otherPlayers.add(new PlayerView(player1Name, player1Points, playerShelf1));
            otherPlayers.add(new PlayerView(player3Name, player3Points, playerShelf3));

            clearCellContent(mainGrid,1,2);
            clearCellContent(mainGrid,2,2);


        } else if (others.size() == 1) {
            otherPlayers.add(new PlayerView(player3Name, player3Points, playerShelf3));

            //remove the shelf of the player in the center (statically placed by the FXML)
            clearCellContent(mainGrid,1,2);
            clearCellContent(mainGrid,2,2);

            clearCellContent(mainGrid,1,1);
            clearCellContent(mainGrid,2,1);

        }




        //RIMUOVI
        // Initialize the other players with the data received from the server binding the GUI elements to each player's data
        for (int i = 0; i < others.size(); i++) {
            otherPlayers.get(i).initialize(others.get(i));
        }

        // RIMUOVI, serviva solo per provare
        // TEST : Fill the shelf with the tiles randomly DA (RIMUOVERE)
        for (int i = 1; i < 6; i++) {
            for (int j = 1; j < 7; j++) {
                ImageView imageView1 = new ImageView(purpleTiles.get(1));
                ImageView imageView2 = new ImageView(yellowTiles.get(1));
                ImageView imageView3 = new ImageView(whiteTiles.get(1));
                ImageView imageView4 = new ImageView(blueTiles.get(1));

                imageView1.setFitHeight(23);
                imageView1.setFitWidth(23);

                imageView2.setFitHeight(23);
                imageView2.setFitWidth(23);

                imageView3.setFitHeight(23);
                imageView3.setFitWidth(23);

                imageView4.setFitHeight(45);
                imageView4.setFitWidth(45);

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
     * @param row line of the node
     * @param col column of the node
     * @param gridPane reference to GUI component
     * @ return Node reference, null if the node is not found
     */
    private void clearCellContent(GridPane gridPane, int row, int col) {
        List<Node> nodesToRemove = new ArrayList<>();

        for (Node node : gridPane.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer colIndex = GridPane.getColumnIndex(node);

            if (rowIndex != null && colIndex != null && rowIndex.intValue() == row && colIndex.intValue() == col) {
                nodesToRemove.add(node);
            }
        }

        gridPane.getChildren().removeAll(nodesToRemove);
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
    public void removeTileFromBoard(Integer row, Integer column) {
        ImageView image = getImageViewFromGridPane(boardGridPane, row, column);
        image = null;
    }


    private List<ImageView> selectedImages = new ArrayList<>();
    /**
     * Method that set up the event handler for the ImageView to show it selected when clicked (MAX 3 tiles selected)
     * @param imageView ImageView to set up
     */
    private void setupImageViewSelection(ImageView imageView) {
        final String SELECTED_STYLE_CLASS = "selected-tile";

        imageView.setOnMouseClicked(event -> {
            try {
                sendTileSelected(boardGridPane.getRowIndex(imageView), boardGridPane.getColumnIndex(imageView));
            } catch (IllegalMoveException e) {
                throw new RuntimeException(e);
            }
        });
    }


    public Choice sendTileSelected(int row, int column) throws IllegalMoveException {
        Choice choice = ChoiceFactory.createChoice(new Player(currentPlayerNickname), "SELECT_TILE " + row + " " + column);
        return choice;
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
    public String selectColumn(ActionEvent event){
        if(selectedImages.size() == tilesOrdered.size() && selectedImages.size() != 0){
            setError("");
            Button button = (Button) event.getSource();
            columnSelected = columnSelector.getButtons().indexOf(button) + 1;
            System.out.println("PICK_COLUMN: " + columnSelected);
            columnSelector.setDisable(true);
            return "PICK_COLUMN: " + columnSelected;
        }else {
            setError("Select and order all the tiles first !");
            return "Select all the tiles first !";
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
                    image.setFitHeight(35);
                    image.setFitWidth(35);
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
     * @param player to update
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
     * @param player to update
     */
    public void updateClientPoints(Player player){
        clientPoints.setText("Points: " + player.getPoints());
    }


    public void setError(String errorMSG){
        error.setText(errorMSG);
    }

    public void refreshBoard(Board board){
        boardGridPane.getChildren().clear();
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

                    image.setFitHeight(34);
                    image.setFitWidth(34);
                    boardGridPane.add(image, i, j);

                }
            }
        }
    }

    /**
     * Method that send a message to other players in the chat
     */
    public void sendMessageOnChat(){
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        AnchorPane selectedAnchorPane = (AnchorPane) selectedTab.getContent();
        TextArea selectedChatArea = (TextArea) selectedAnchorPane.lookup(".text-area");
        selectedChatArea.appendText(currentPlayerNickname + " : " + chatTextField.getText() + "\n");
        chatTextField.setText("Enter a message...");
        // MANDA MESSAGGIO AL SERVER
    }


    /**
     * Method that remove a tile from the board and put it available to be inserted in the shelf
     * @param selectedTiles
     */
    public void setSelectedTiles(List <Coordinate> selectedTiles){
        firstImage.setImage(null);
        secondImage.setImage(null);
        thirdImage.setImage(null);

        BoxBlur boxBlur = new BoxBlur();
        boxBlur.setWidth(4); // Modifica la larghezza dello sfocato
        boxBlur.setHeight(4); // Modifica l'altezza dello sfocato
        boxBlur.setIterations(2); // Modifica il numero di iterazioni dello sfocato

        resetButton.setVisible(false);
        if(selectedTiles.size() > 0){
            //Button reset visibility
            resetButton.setVisible(true);
        } else

        if(selectedTiles.size() == 1){
            ImageView image = getImageViewFromGridPane(boardGridPane, selectedTiles.get(0).getRow() + 1, selectedTiles.get(0).getColumn() + 1);
            firstImage.setImage(image.getImage());
            image.getStyleClass().clear();
            image.setEffect(boxBlur);
            image.setOnMouseClicked(null);

        } else if (selectedTiles.size() == 2) {
            ImageView image = getImageViewFromGridPane(boardGridPane, selectedTiles.get(0).getRow() + 1, selectedTiles.get(0).getColumn() + 1);
            firstImage.setImage(image.getImage());
            image.getStyleClass().clear();
            image.setEffect(boxBlur);
            image.setOnMouseClicked(null);
            image = getImageViewFromGridPane(boardGridPane, selectedTiles.get(1).getRow() + 1, selectedTiles.get(1).getColumn() + 1);
            secondImage.setImage(image.getImage());
            image.getStyleClass().clear();
            image.setEffect(boxBlur);
            image.setOnMouseClicked(null);

        } else if (selectedTiles.size() == 3) {
            ImageView image = getImageViewFromGridPane(boardGridPane, selectedTiles.get(0).getRow() + 1, selectedTiles.get(0).getColumn() + 1);
            firstImage.setImage(image.getImage());
            image.getStyleClass().clear();
            image.setEffect(boxBlur);
            image.setOnMouseClicked(null);
            image = getImageViewFromGridPane(boardGridPane, selectedTiles.get(1).getRow() + 1, selectedTiles.get(1).getColumn() + 1);
            secondImage.setImage(image.getImage());
            image.getStyleClass().clear();
            image.setEffect(boxBlur);
            image.setOnMouseClicked(null);
            image = getImageViewFromGridPane(boardGridPane, selectedTiles.get(2).getRow() + 1, selectedTiles.get(2).getColumn() + 1);
            thirdImage.setImage(image.getImage());
            image.getStyleClass().clear();
            image.setEffect(boxBlur);
            image.setOnMouseClicked(null);

        }
    }

    List <Integer> tilesOrdered = new ArrayList<>();
    int [] tilesOrder = new int[3];
    public void setTileOrder(ActionEvent event){
        if(tilesOrdered.size() < 3)
            if(event.getSource() == firstTile && !tilesOrdered.contains((Integer) 0) && firstImage.getImage() != null){
                tilesOrder[0] = tilesOrdered.size();
                tilesOrdered.add(0);
                firstTile.setText(String.valueOf(tilesOrder[0]));
            } else if (event.getSource() == secondTile && !tilesOrdered.contains((Integer) 1) && secondImage.getImage() != null){
                tilesOrder[1] = tilesOrdered.size();
                tilesOrdered.add(1);
                secondTile.setText(String.valueOf(tilesOrder[1]));
            } else if (event.getSource() == thirdTile && !tilesOrdered.contains((Integer) 2) && thirdImage.getImage() != null){
                tilesOrder[2] = tilesOrdered.size();
                tilesOrdered.add(2);
                thirdTile.setText(String.valueOf(tilesOrder[2]));
            }
    }

    public String chooseOrder(){
        String input = "CHOOSE_ORDER ";
        if(tilesOrdered.size() == selectedImages.size() && tilesOrdered.size() != 0){
            setError("");
            for( int i = 0; i < tilesOrdered.size(); i++){
                input = input  + tilesOrder[i] + " ";
            }
            columnSelector.setDisable(false);
            return input;
        } else {
            columnSelector.setDisable(true);
            setError("First of all, order every tile selected!!");
            return "First of all, order every tile selected!!";  // Genera eccezione da gestire
        }
    }

    /**
     * Method bound to the button "Confirm" that will send the request to the server after the user has selected the column where to place the tile
     */
    public String confirmTilesOrder(){
        if(selectedImages.size() != 0){
            System.out.println(chooseOrder());

            for (Node node : boardGridPane.getChildren()) {
                node.setOnMouseClicked(null);
                node.getStyleClass().clear();
            }
            return chooseOrder();
        }
        setError("You have to select at least one tile and order it!");
        return "You have to select at least one tile!";
    }

    /**
     * Method that update all the chat at every update received from the server
     * @param pvtChat is the map that contains all the messages of the players contained in the set as key
     */
    public void updateChat(Map <String, List<Message>> pvtChat, List<Message> globalChat){
        tabPane.getTabs().clear();
        List<String> tabNames = new ArrayList<>();
        for(String playerName : pvtChat.keySet()){
            tabNames.add(playerName);
        }

        for(String tabName : tabNames){
            if(!tabPane.getTabs().contains(tabName)){
                Tab tab = new Tab(tabName);
                tabPane.getTabs().add(tab);
                tabPane.getSelectionModel().select(tab);
                AnchorPane anchorPane = new AnchorPane();
                TextArea textArea = new TextArea();
                textArea.getStyleClass().add("text-area");
                textArea.setEditable(false);
                textArea.setWrapText(true);
                textArea.setPrefHeight(160);
                textArea.setPrefWidth(160);
                fillChat(textArea, pvtChat.get(tabName));
                anchorPane.getChildren().add(textArea);
                tab.setContent(anchorPane);
            }
        }

        // Manage to create the global chat
        Tab tab = new Tab("Global Chat");
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
        AnchorPane anchorPane = new AnchorPane();
        TextArea textArea = new TextArea();
        textArea.getStyleClass().add("text-area");
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefHeight(160);
        textArea.setPrefWidth(160);
        fillChat(textArea, globalChat);
        anchorPane.getChildren().add(textArea);
        tab.setContent(anchorPane);

    }


    /**
     * Method that fill the chat with the messages
     * @param textArea TextArea reference
     * @param messages List of messages
     */
    public void fillChat(TextArea textArea, List<Message> messages){
        for(Message message : messages){
            textArea.appendText(message.getSender() + " : " + message.getText() + "\n");
        }
    }


    public void resetButtonAction(){
        resetButton.setVisible(false);
        List<String> tmpPlayerNames = new ArrayList<String>();
        tmpPlayerNames.add("Pippo");
        tmpPlayerNames.add("Pluto");
        tmpPlayerNames.add("Paperino");
        tmpPlayerNames.add("Giuseppe");
        model = new Game(tmpPlayerNames, null);

        selectedImages.clear();
        tilesOrdered.clear();
        firstImage.setImage(null);
        secondImage.setImage(null);
        thirdImage.setImage(null);
        firstTile.setText("");
        secondTile.setText("");
        thirdTile.setText("");
        setError("");
        columnSelector.setDisable(true);

        refreshBoard(model.getBoard());
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
        Scene scene = new Scene(pane);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        primaryStage.getIcons().add(new Image("file:src/resources/GraphicalResources/Publisher material/Icon 50x50px.png"));
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }




}


