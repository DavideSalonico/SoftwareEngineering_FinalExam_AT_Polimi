package GC_11.view.GUI;

import GC_11.distributed.Client;
import GC_11.distributed.socket.ClientSock;
import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.exceptions.IllegalMoveException;
import GC_11.network.message.GameViewMessage;
import GC_11.network.choices.Choice;
import GC_11.network.choices.ChoiceFactory;
import GC_11.network.message.LobbyViewMessage;
import GC_11.view.View;
import javafx.application.Application;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;

public class GUIModel extends View {

    private Choice playerChoice;
    private Client client;
    private ClientSock clientSock;
    private String nickname;
    private PropertyChangeListener listener;

    public GUIView view;


    /**
     * Every view is bound at only one player, it helps to manage every input that the controller receive
     */

    public GUIModel(String nickname, Client client) {
        super();
        this.nickname = nickname;
        this.client = client;
        Application.launch(GUIView.class);
    }

    public void setPlayerChoice(Choice c) {
        this.playerChoice = c;
    }

    public void setModelView(GameViewMessage modelView) {
        this.modelView = modelView;
    }

    @Override
    public void run() {
        /*show();
        if (this.modelView.getCurrentPlayer().equals(this.nickname)) {
            Choice choice = getPlayerChoice();
            System.out.println("scelta fatta");
            PropertyChangeEvent evt = new PropertyChangeEvent(
                    this,
                    "CHOICE",
                    null,
                    choice);
            if (this.client!=null)
                this.client.notifyServer(choice);
            else
                this.clientSock.notifyServer(evt);
        }

         */
    }

    @Override
    public void show() {
        if (this.modelView.isError()) {
            view.setError(this.modelView.getExceptionMessage());
        }else{
            view.setError("");
            try {
                this.view.updatePlayer(modelView.getBoard(),modelView.getPlayer(modelView.getCurrentPlayer()));
            } catch (ColumnIndexOutOfBoundsException e) {
                throw new RuntimeException(e); //TODO handle this exception
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //TODO
    }

    @Override
    public void askNickname() {
        //TODO
    }

    @Override
    public void askMaxNumber() {

    }

    @Override
    public void printLobby(LobbyViewMessage lobbyViewMessage) {

    }

    @Override
    public String getNickname() {
        return this.nickname;
    }

    @Override
    public Choice getPlayerChoice() {
            try {
                String input = view.chooseOrder(); // chooseOrder() returns a string that represents the choice of the player but it is called by the view BUTTON
                return ChoiceFactory.createChoice(this.modelView.getPlayer(this.nickname), input);
            } catch (IllegalMoveException e) {
                System.err.println("Invalid CHOICE, Please retake.");
            }
        return null;
    }

}
