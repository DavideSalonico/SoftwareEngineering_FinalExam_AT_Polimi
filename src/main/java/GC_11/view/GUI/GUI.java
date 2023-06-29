package GC_11.view.GUI;

import GC_11.network.message.GameViewMessage;
import GC_11.network.message.LobbyViewMessage;
import GC_11.view.Lobby.LobbyController;
import GC_11.view.View;


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
     * Every view is bound at only one player, it helps to manage every input that the controller receive
     */
    public GUI() {
        super();
        this.guiApplication = new GUIApplication();

    }

    public static  void setLobbyController(LobbyController controller){
        lobbyController = controller;
    }
    public static void setGameController(GUIController controller){
        gameController = controller;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

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

        System.out.println("DIGIT YOUR NICKNAME:");

        while (this.nickname == null){
            try {
                System.out.println("waiting for nickname");
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void askMaxNumber() {

        lobbyController.changeToSetNumber();
        System.out.println("MaxNumberPlayer required: " + "nickname attuale : " + this.nickname);

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
