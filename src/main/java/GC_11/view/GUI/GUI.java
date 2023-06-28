package GC_11.view.GUI;

import GC_11.distributed.Client;
import GC_11.network.message.GameViewMessage;
import GC_11.network.message.LobbyViewMessage;
import GC_11.view.Lobby.LobbyApplication;
import GC_11.view.Lobby.LobbyController;
import GC_11.view.View;

public class GUI extends View {

    private Client client;
    private String nickname;
    public GUIApplication guiApplication;
    public LobbyApplication lobbyApplication;
    public static LobbyController lobbyController;
    private boolean inGame;

    public final Object lock = new Object();

    public static  void setController(LobbyController controller){
        GUI.lobbyController = controller;
    }

    /**
     * Every view is bound at only one player, it helps to manage every input that the controller receive
     */
    public GUI(Client client) {
        super();
        this.client = client;
        this.inGame = false;


        this.lobbyApplication = new LobbyApplication();

        while(lobbyController == null){

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        this.lobbyController.setLock(this.lock);
        this.lobbyController.setClient(client);
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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
            //guiApplication.setError(this.modelView.getExceptionMessage());
        }else{
            //guiApplication.setError("");
            //this.guiApplication.updatePlayer(modelView.getBoard(),modelView.getPlayer(modelView.getCurrentPlayer()));
        }
    }

    @Override
    public void askNickname() {
        System.out.println("askNickname required: ");

        synchronized (lock) {
            try {
                System.out.println("Thread addormentato");
                lock.wait(); // Il thread si addormenta e rilascia il lock
                System.out.println("Thread risvegliato");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
        System.out.println("MaxNumberPlayer required: ");

        synchronized (lock) {
            try {
                System.out.println("Thread addormentato");
                lock.wait(); // Il thread si addormenta e rilascia il lock
                System.out.println("Thread risvegliato");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
