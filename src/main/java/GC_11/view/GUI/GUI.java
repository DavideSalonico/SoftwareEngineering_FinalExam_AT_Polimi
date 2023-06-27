package GC_11.view.GUI;

import GC_11.distributed.Client;
import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.exceptions.IllegalMoveException;
import GC_11.network.choices.ChoiceType;
import GC_11.network.message.GameViewMessage;
import GC_11.network.choices.Choice;
import GC_11.network.choices.ChoiceFactory;
import GC_11.network.message.LobbyViewMessage;
import GC_11.view.Lobby.LobbyApplication;
import GC_11.view.View;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class GUI extends View {
    private Client client;
    private String nickname;
    public GUIApplication guiApplication;
    public LobbyApplication lobbyApplication;
    private boolean inGame;


    /**
     * Every view is bound at only one player, it helps to manage every input that the controller receive
     */

    public GUI(Client client) {
        super();
        this.client = client;
        this.inGame = false;
        this.lobbyApplication = new LobbyApplication(client);
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
            guiApplication.setError(this.modelView.getExceptionMessage());
        }else{
            guiApplication.setError("");
            try {
                this.guiApplication.updatePlayer(modelView.getBoard(),modelView.getPlayer(modelView.getCurrentPlayer()));
            } catch (ColumnIndexOutOfBoundsException e) {
                throw new RuntimeException(e); //TODO handle this exception
            }
        }
    }

    @Override
    public void askNickname() {
        String nickname = this.lobbyApplication.confirmNickname();
        try {
            this.client.notifyServer(ChoiceFactory.createChoice(null, "ADD_PLAYER " + nickname));
            this.nickname = nickname;
        } catch (RemoteException | IllegalMoveException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askMaxNumber() {
        String number = this.lobbyApplication.sendNumberOfPlayer();
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
        }
    }

    @Override
    public void printLobby(LobbyViewMessage lobbyViewMessage) {
        this.lobbyApplication.updatePlayerList(lobbyViewMessage);
    }

    @Override
    public String getNickname() {
        return this.nickname;
    }

    @Override
    public void update(GameViewMessage modelView) {
        if(!this.inGame){
            try {
                this.guiApplication = this.lobbyApplication.changeScene();
                this.guiApplication.init(modelView);
            } catch (RemoteException e) {
                throw new RuntimeException(e); //TODO handle
            }
        }
        setInGame(true);
        this.setModelView(modelView);
        show();
    }

}
