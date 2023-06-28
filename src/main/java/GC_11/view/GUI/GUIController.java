package GC_11.view.GUI;

import GC_11.distributed.Client;
import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.exceptions.IllegalMoveException;
import GC_11.model.*;
import GC_11.network.choices.Choice;
import GC_11.network.choices.ChoiceFactory;
import GC_11.network.message.GameViewMessage;
import GC_11.util.PlayerView;
import javafx.application.Platform;
import javafx.collections.ObservableList;
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

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public String currentPlayerNickname;
    private Client client;
    public Game model;

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
                createChoice("SELECT_TILE " + boardGridPane.getRowIndex(imageView) + " " + boardGridPane.getColumnIndex(imageView));

            } catch (IllegalMoveException e) {
                throw new RuntimeException(e);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }




    public void getChoice(){

    }

    int columnSelected = 0;
    /**
     * Method bound to every button of the column selector that will set the columnSelected variable to the column selected
     * @param event to get the id of the button pressed
     */
    @FXML
    public void selectColumn(ActionEvent event) throws IllegalMoveException, RemoteException {
        if(selectedImages.size() == tilesOrdered.size() && selectedImages.size() != 0){
            setError("");
            Button button = (Button) event.getSource();
            columnSelected = columnSelector.getButtons().indexOf(button);
            System.out.println("PICK_COLUMN: " + columnSelected);
            columnSelector.setDisable(true);
            createChoice("PICK_COLUMN: " + columnSelected);
        }else {
            setError("Select and order all the tiles first !");
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
                    boardGridPane.add(image, i, j);

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
    @FXML
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
    @FXML
    public String confirmTilesOrder() throws IllegalMoveException, RemoteException {
        if(selectedImages.size() != 0){
            System.out.println(chooseOrder());

            for (Node node : boardGridPane.getChildren()) {
                node.setOnMouseClicked(null);
                node.getStyleClass().clear();
            }
            createChoice(chooseOrder());
        }
        setError("You have to select at least one tile and order it!");
        return "You have to select at least one tile!";
    }

    public void createChoice(String input) throws IllegalMoveException, RemoteException {
        Choice choice = ChoiceFactory.createChoice(new Player(currentPlayerNickname), input);
        this.client.notifyServer(choice);
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


    @FXML
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

}

