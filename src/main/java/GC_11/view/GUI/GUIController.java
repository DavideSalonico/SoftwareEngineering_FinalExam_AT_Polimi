package GC_11.view.GUI;

import GC_11.ClientApp;
import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.exceptions.IllegalMoveException;
import GC_11.model.*;
import GC_11.network.choices.Choice;
import GC_11.network.choices.ChoiceFactory;
import GC_11.network.message.GameViewMessage;
import GC_11.util.PlayerView;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.time.Duration;
import java.util.*;

public class GUIController {

    @FXML
    private ImageView ICommonGoalCard;

    @FXML
    private ImageView IICommonGoalCard;

    Tooltip textCommonI;
    Tooltip textCommonII;
    @FXML
    private GridPane boardGridPane;

    @FXML
    private TextArea chatTextArea;

    @FXML
    private TextArea chatTextArea1;

    @FXML
    private TextArea chatTextArea2;

    @FXML
    private TextArea chatTextArea3;

    @FXML
    private TextField chatTextField;

    @FXML
    private Text clientPoints;

    @FXML
    private ButtonBar columnSelector;

    @FXML
    private Button confirmSelection;

    @FXML
    private ImageView deletableShelf;

    @FXML
    private ImageView deletableShelf2;

    @FXML
    private Text error;

    @FXML
    private ImageView firstImage;

    @FXML
    private ImageView firstPlayerToken;

    @FXML
    private Button firstTile;

    @FXML
    private GridPane mainGrid;

    @FXML
    private GridPane mainShelfGridPane;

    @FXML
    private ImageView personalGoal;

    @FXML
    private Text player1Name;

    @FXML
    private Text player1Points;

    @FXML
    private Text player2Name;

    @FXML
    private Text player2Points;

    @FXML
    private Text player3Name;

    @FXML
    private Text player3Points;

    @FXML
    private GridPane playerShelf1;

    @FXML
    private GridPane playerShelf2;

    @FXML
    private GridPane playerShelf3;

    @FXML
    private Button resetButton;

    @FXML
    private Pane root;

    @FXML
    private ImageView secondImage;

    @FXML
    private Button secondTile;

    @FXML
    private Button sendMessageButton;

    @FXML
    private TabPane tabPane;

    @FXML
    private ImageView thirdImage;

    @FXML
    private Button thirdTile;


    @FXML
    public Label currentPlayerNicknameLabel;

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

    public GameViewMessage gameViewMessage;

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
     * This is the main method that the client call every time he gets some updates from the server.
     * @param message is GameViewMessage instance which contains all the information about the current state of the game.
     */
    public void updateView(GameViewMessage message){
        try {

            Platform.runLater(() -> {
                this.gameViewMessage = message;
                firstTile.setText("");
                secondTile.setText("");
                thirdTile.setText("");
                if(message.getCurrentPlayer().equals(ClientApp.view.getNickname()))
                    currentPlayerNicknameLabel.setText("IT'S YOUR TURN!");
                else
                    currentPlayerNicknameLabel.setText("IT'S "+message.getCurrentPlayer() +  "'S TURN");
                // Update Board and PlayerShelf with his points
                for (Player player : message.getPlayers()) {
                    updatePlayer(player);
                }

                setSelectedTiles(message.getBoard().getSelectedTiles());
                if( message.getBoard().getSelectedTiles().size() == 0)
                    refreshBoard(message.getBoard());

                updateChat(message.getFilteredPvtChats(), message.getMainChat());
                tilesOrdered = new ArrayList<>();

            });
        } catch (Exception e) {
            setError("Error in updateView :" + e.getMessage());
            System.out.println("Error in updateView :" + e.getMessage());
        }
    }


    /**
     * Method that returns the node from the GridPane given the row and column index
     * @param row line of the node
     * @param col column of the node
     * @param gridPane reference to GUI component
     * @ return Node reference, null if the node is not found
     */
    private void clearCellContent(GridPane gridPane, int row, int col) {
        Platform.runLater( () -> {
            List<Node> nodesToRemove = new ArrayList<>();

            for (Node node : gridPane.getChildren()) {
                Integer rowIndex = GridPane.getRowIndex(node);
                Integer colIndex = GridPane.getColumnIndex(node);

                if (rowIndex != null && colIndex != null && rowIndex.intValue() == row && colIndex.intValue() == col) {
                    nodesToRemove.add(node);
                }
            }

            gridPane.getChildren().removeAll(nodesToRemove);
        });
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



    private List<ImageView> selectedImages = new ArrayList<>();
    /**
     * Method that set up the event handler for the ImageView to show it selected when clicked (MAX 3 tiles selected)
     * @param imageView ImageView to set up
     */
    private void setupImageViewSelection(ImageView imageView) {
        final String SELECTED_STYLE_CLASS = "selected-tile";

        imageView.setOnMouseClicked(event -> {
            createChoice("SELECT_TILE " + (boardGridPane.getRowIndex(imageView) -1) + " " + (boardGridPane.getColumnIndex(imageView)-1));

        });
    }




    int columnSelected = 0;
    /**
     * Method bound to every button of the column selector that will set the columnSelected variable to the column selected
     * @param event to get the id of the button pressed
     */
    @FXML
    public void selectColumn(ActionEvent event) throws IllegalMoveException, RemoteException {
        setError("");
        Button button = (Button) event.getSource();
        columnSelected = columnSelector.getButtons().indexOf(button);
        //columnSelector.setDisable(true);
        if (columnSelected > -1)
            createChoice("PICK_COLUMN " + columnSelected);

        secondTile.setText("");
        firstTile.setText("");
        thirdTile.setText("");
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
    public void updatePlayer(Player player) {
        PlayerView playerView = getPlayerViewFromNickname(player.getNickname());
        if(player.getNickname().equals(ClientApp.view.getNickname())){
            updateClientPoints(player);
            updateClientShelf(player);
        }

        if (playerView != null) {
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
    public void updateShelf(Player player, GridPane shelf) {
        for (int i = 0; i < 5; i++) {  //COLUMNS
            for (int j = 0; j < 6; j++) {  //ROWS
                Tile t = null;
                try {
                    t = player.getShelf().getTile(j, i);
                } catch (ColumnIndexOutOfBoundsException e) {
                    throw new RuntimeException(e);
                }
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
                    image.setFitHeight(23);
                    image.setFitWidth(23);
                    shelf.add(image, i+1, j+1);
                }else {
                    clearCellContent(shelf, j + 1, i + 1);

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
    public void updateClientShelf(Player player) {
        for (int i = 0; i < 5; i++) {  //COLUMNS
            for (int j = 0; j < 6; j++) {  //ROWS
                Tile t = null;
                try {
                    t = player.getShelf().getTile(j, i);
                } catch (ColumnIndexOutOfBoundsException e) {
                    setError(e.getMessage());
                }
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
                    image.setFitHeight(44);  //Other players shelf size is 29x29!
                    image.setFitWidth(44);
                    mainShelfGridPane.add(image, i+1, j+1);  //Add the image to specif Shelf
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
        Platform.runLater(() -> {
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
                    boardGridPane.add(image, j, i);

                }else {
                    //clearCellContent(boardGridPane, i, j);
                }
            }
        }
        });
    }

    /**
     * Method that send a message to other players in the chat
     */
    @FXML
    public void sendMessageOnChat(){
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        AnchorPane selectedAnchorPane = (AnchorPane) selectedTab.getContent();
        TextArea selectedChatArea = (TextArea) selectedAnchorPane.lookup(".text-area");


        createChoice("SEND_MESSAGE " + selectedTab.getText() + " " +chatTextField.getText());

        chatTextField.setText("Enter a message...");

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
        if(selectedTiles.size() > 0 && gameViewMessage.getCurrentPlayer().equals(ClientApp.view.getNickname())){
            //Button reset visibility
            resetButton.setVisible(true);
        }

        if(selectedTiles.size() == 1){
            ImageView image = getImageViewFromGridPane(boardGridPane, selectedTiles.get(0).getRow() +1, selectedTiles.get(0).getColumn() +1);
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
    @FXML
    public void setTileOrder(ActionEvent event){
        if(gameViewMessage.getBoard().getSelectedTiles().size() <= 3 && gameViewMessage.getCurrentPlayer().equals(ClientApp.view.getNickname()) ) {
            if (event.getSource() == firstTile && !tilesOrdered.contains((Integer) 0) && firstImage.getImage() != null) {
                tilesOrder[0] = tilesOrdered.size();
                tilesOrdered.add(0);
                firstTile.setText(String.valueOf(tilesOrder[0]));
            } else if (event.getSource() == secondTile && !tilesOrdered.contains((Integer) 1) && secondImage.getImage() != null) {
                tilesOrder[1] = tilesOrdered.size();
                tilesOrdered.add(1);
                secondTile.setText(String.valueOf(tilesOrder[1]));
            } else if (event.getSource() == thirdTile && !tilesOrdered.contains((Integer) 2) && thirdImage.getImage() != null) {
                tilesOrder[2] = tilesOrdered.size();
                tilesOrdered.add(2);
                thirdTile.setText(String.valueOf(tilesOrder[2]));
            }
        }
    }

    public String chooseOrder(){
        String input = "CHOOSE_ORDER ";
        setError("");
        for (int i = 0; i < tilesOrdered.size(); i++) {
            input = input + tilesOrder[i] + " ";
        }
        columnSelector.setDisable(false);
        return input;
    }

    /**
     * Method bound to the button "Confirm" that will send the request to the server after the user has selected the column where to place the tile
     */
    @FXML
    public void confirmTilesOrder() {
        createChoice(chooseOrder());
        firstTile.setText("");
        secondTile.setText("");
        thirdTile.setText("");
    }

    public void createChoice(String input) {
        try {
            Choice choice = ChoiceFactory.createChoice(gameViewMessage.getPlayer(ClientApp.client.getNickname()), input);
            ClientApp.client.notifyServer(choice);
        }catch (RemoteException | IllegalMoveException e){
            setError("Exception : " + e.getMessage());
        }
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
        try {
            for (Player player : gameViewMessage.getPlayers()) {
                if (!tabNames.contains(player.getNickname()) && !player.getNickname().equals(ClientApp.client.getNickname())) {
                    tabNames.add(player.getNickname());
                }
            }
        }catch (RemoteException e){
            setError("Exception : " + e.getMessage());
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
                if(pvtChat.get(tabName) != null)
                    fillChat(textArea, pvtChat.get(tabName));
                anchorPane.getChildren().add(textArea);
                tab.setContent(anchorPane);
            }
        }

        // Manage to create the global chat
        Tab tab = new Tab("Everyone");
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


    @FXML
    public void resetButtonAction(){
        resetButton.setVisible(false);

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

        createChoice("RESET_TURN");
    }

    /**
     * Method called at the beginning of the game to load all components of the GUI that we need to show in this particular game instance
     * @param gameViewMessage
     */
    public void init(GameViewMessage gameViewMessage) {
        // Load all the images of the tiles
        loadTilesImages();

        this.gameViewMessage = gameViewMessage;
        try {
            // Put first Player's nickname into the chair on GUI
            Tooltip firstPlayer = new Tooltip("First PLAYER : " + gameViewMessage.getPlayer(gameViewMessage.getCurrentPlayer()).getNickname());
            Tooltip.install(firstPlayerToken, firstPlayer);

            //Get common goal cards from the GameViewMessage
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
            textCommonI = new Tooltip(gameViewMessage.getCommonGoalCard(0).getText());
            textCommonII = new Tooltip(gameViewMessage.getCommonGoalCard(1).getText());
            Tooltip.install(ICommonGoalCard, textCommonI);
            Tooltip.install(IICommonGoalCard, textCommonII);
            ICommonGoalCard.getStyleClass().add("selected-image");
            IICommonGoalCard.getStyleClass().add("selected-image");

            //Initialize the other players and the main player (client)
            for (int i = 0; i < gameViewMessage.getPlayers().size(); i++) {
                if (!gameViewMessage.getPlayers().get(i).getNickname().equals(ClientApp.view.getNickname())) {
                    others.add(gameViewMessage.getPlayers().get(i));
                } else {
                    clientPoints.setText("YOUR POINTS : " + gameViewMessage.getPlayers().get(i).getPoints());


                    personalGoal.setImage(new Image("file:src/resources/GraphicalResources/personal goal cards/Personal_Goals" + (gameViewMessage.getPlayers().get(i).getPersonalGoal().getId()+1) + ".png"));
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

                clearCellContent(mainGrid, 1, 2);
                clearCellContent(mainGrid, 2, 2);


            } else if (others.size() == 1) {
                otherPlayers.add(new PlayerView(player3Name, player3Points, playerShelf3));

                //remove the shelf of the player in the center (statically placed by the FXML)
                clearCellContent(mainGrid, 1, 2);
                clearCellContent(mainGrid, 2, 2);

                clearCellContent(mainGrid, 1, 1);
                clearCellContent(mainGrid, 2, 1);

            }

            // Initialize the other players with the data received from the server binding the GUI elements to each player's data
            for (int i = 0; i < others.size(); i++) {
                otherPlayers.get(i).initialize(others.get(i));
            }

            //Initialize Board with the data received from the server
            refreshBoard(gameViewMessage.getBoard());
        } catch (Exception e) {
            e.printStackTrace();
            setError("Error in init method at line " + e.getStackTrace()[0].getLineNumber());
            System.out.println("Error in init method at line " + e.getStackTrace()[0].getLineNumber());
        }

        updateView(gameViewMessage);

    }
    @FXML
    public void initialize() {

        chatTextField.setOnMouseClicked(event -> {
            chatTextField.setText("");
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

    }

    public void playerDisconnected(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Player disconnected");
        alert.setHeaderText("You have been disconnected from the game, it could be a problem with your connection or with the server");
        alert.setContentText("The game will close in 10 seconds");

        Stage dialogStage = (Stage) alert.getDialogPane().getScene().getWindow();
        dialogStage.initModality(Modality.APPLICATION_MODAL);

        dialogStage.setOnCloseRequest(event -> System.exit(0));

        alert.showAndWait();

        CountdownService countdownService = new CountdownService();
        countdownService.setCountdownDuration(Duration.ofSeconds(20));
        countdownService.start();

        countdownService.messageProperty().addListener((observable, oldValue, newValue) ->
                currentPlayerNicknameLabel.setText(newValue)
        );

    }

    public void showEndGame(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fine del gioco");
        if (gameViewMessage.getWinner().getNickname().equals(ClientApp.view.getNickname())) {
            alert.setHeaderText("You win the game!");
        }
        else
            alert.setHeaderText("You lose the game!");

        String finalTable = "The winner is " + gameViewMessage.getWinner().getNickname() + " with " + gameViewMessage.getWinner().getPoints() + " points!" + "\n\n";

        List<Player> sortedPlayer = new ArrayList<>(gameViewMessage.getPlayers());
        Collections.sort(sortedPlayer, Comparator.comparingInt(Player::getPoints).reversed());
        StringBuilder sb = new StringBuilder();
        for (Player person : sortedPlayer) {
            sb.append("Player: ").append(person.getNickname()).append(", Points: ").append(person.getPoints()).append("\n");
        }


        alert.setContentText(finalTable +  sb.toString());


        Stage dialogStage = (Stage) alert.getDialogPane().getScene().getWindow();
        dialogStage.initModality(Modality.APPLICATION_MODAL);

        dialogStage.setOnCloseRequest(event -> System.exit(0));

        alert.showAndWait();

        CountdownService countdownService = new CountdownService();
        countdownService.setCountdownDuration(Duration.ofSeconds(20));
        countdownService.start();

        countdownService.messageProperty().addListener((observable, oldValue, newValue) ->
                currentPlayerNicknameLabel.setText(newValue)
        );
    }
    private class CountdownService extends ScheduledService<Void> {
        private int countdown;

        public void setCountdownDuration(Duration duration) {
            countdown = (int) duration.toSeconds();
        }

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    while (countdown >= 0) {
                        updateMessage("Close in : " + countdown + " s");
                        Thread.sleep(1000);
                        countdown--;
                    }
                    System.exit(0); // Arresta l'applicazione dopo il countdown
                    return null;
                }
            };
        }
    }

}

