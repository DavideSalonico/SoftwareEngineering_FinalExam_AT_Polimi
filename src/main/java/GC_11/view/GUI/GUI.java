package GC_11.view.GUI;

import GC_11.distributed.Client;
import GC_11.network.message.GameViewMessage;
import GC_11.network.message.LobbyViewMessage;
import GC_11.view.Lobby.LobbyController;
import GC_11.view.View;

public class GUI extends View {

    private Client client;
    public static String nickname;
    public GUIApplication guiApplication;
    public static GUIController gameController;
    public static LobbyController lobbyController;
    private boolean inGame;



    /**
     * Every view is bound at only one player, it helps to manage every input that the controller receive
     */
    public GUI(Client client) {
        super();
        this.client = client;
        this.inGame = false;

        this.guiApplication = new GUIApplication();

        while (lobbyController == null){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        this.lobbyController.setClient(client);
    }

    public static  void setLobbyController(LobbyController controller){
        GUI.lobbyController = controller;
    }
    public static void setGameController(GUIController controller){
        GUI.gameController = controller;
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
            gameController.setError("");
            gameController.updateView(modelView);
        }
    }

    @Override
    public void askNickname() {
        System.out.println("DIGIT YOUR NICKNAME:");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        /*String nickname = this.lobbyApplication.confirmNickname();
        try {
            this.client.notifyServer(ChoiceFactory.createChoice(null, "ADD_PLAYER " + nickname));
            this.nickname = nickname;
        } catch (RemoteException | IllegalMoveException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void askMaxNumber() {
        System.out.println("MaxNumberPlayer required: " + "nickname attuale : " + this.nickname);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        /*String number = this.lobbyController.sendNumberOfPlayer();
        try{
            parseInt(number);
        }catch (NumberFormatException e){
            System.out.println("Please insert a number");
            askMaxNumber();
        }finally {
            try {
                this.client.notifyServer(ChoiceFactory.createChoice(null, "SET_MAX_NUMBER " + number));
            } catch (RemoteException | IllegalMoveException e) {
                e.printStackTrace();
            }
        }*/
    }

    @Override
    public void askLoadGame() {

    }

    @Override
    public void printLobby(LobbyViewMessage lobbyViewMessage) {
        this.lobbyController.updatePlayerList(lobbyViewMessage);
    }

    @Override
    public String getNickname() {
        return this.nickname;
    }

    @Override
    public void update(GameViewMessage modelView) {
        /*if(!this.inGame){
            try {
                this.guiApplication = this.lobbyController.changeScene();
                this.guiApplication.init(modelView);
            } catch (RemoteException e) {
                throw new RuntimeException(e); //TODO handle
            }
        }
        setInGame(true);
        this.setModelView(modelView);
        show();
    */}

}
