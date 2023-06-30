package GC_11.view.GUI;

import GC_11.network.message.GameViewMessage;
import GC_11.network.message.LobbyViewMessage;
import GC_11.view.Lobby.LobbyController;
import GC_11.view.View;


/**
 * GUI class, it manages the GUI with reference to the GUI FXML's Components, receives updates from the server and call
 * the appropriate methods to update the GUI
 */
public class GUI extends View {
    public static String nickname;
    public static int maxNumber = 0;
    public GUIApplication guiApplication;
    public static GUIController gameController;
    public static LobbyController lobbyController;
    public static ConnectionController connectionController;
    private boolean inGame = false;

    public int i = 0;

    /**
     * Constructor of the GUI, it will launch the GUI as a new Thread
     */
    public GUI() {
        super();
        this.guiApplication = new GUIApplication();

    }

    /**
     * Method that will set LobbyController in a static variable so it can be used by the client to update the GUI
     * @param  controller LobbyController reference
     */
    public static  void setLobbyController(LobbyController controller){
        lobbyController = controller;
    }

    /**
     * Method that will set GameController in a static variable so it can be used by the client to update the GUI
     * @param  controller GameController reference
     */
    public static void setGameController(GUIController controller){
        gameController = controller;
    }

    /**
     * Variable to set if the client runs the game for the first time
     * @param inGame
     */
    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    /**
     * Method that will set the modelView in a static variable so it can be used by the client to update the GUI
     * @param modelView
     */
    public void setModelView(GameViewMessage modelView) {
        this.modelView = modelView;
    }

    @Override
    public void show() {
        if (this.modelView.isError()) {
            gameController.setError(this.modelView.getExceptionMessage());
        }else{
            if(this.modelView.isEndGame()){
                gameController.showEndGame();
            }
            gameController.setError("");
            gameController.updateView(modelView);
        }
    }

    @Override
    public void askNickname() {
        guiApplication.connectionController.changeSceneToLobby();

        while (this.nickname == null){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void askMaxNumber() {

        lobbyController.changeToSetNumber();

        while (this.maxNumber == 0){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void askLoadGame() {
        this.lobbyController.askLoadOldGame();
    }

    @Override
    public void printLobby(LobbyViewMessage lobbyViewMessage) {
        this.lobbyController.showPlayers(lobbyViewMessage.getPlayersNames());
    }

    @Override
    public String getNickname() {
        return this.nickname;
    }

    @Override
    public void update(GameViewMessage modelView) {
        if(!this.inGame){
            this.lobbyController.changeSceneToGame();
            this.gameController.init(modelView);

            setInGame(true);
        }else{
            this.setModelView(modelView);
            show();
        }
    }

    @Override
    public void init() {
    }

    //@Override
    public void notifyDisconnection() {
        this.gameController.playerDisconnected();
    }

}
