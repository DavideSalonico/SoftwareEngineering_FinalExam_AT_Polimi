package GC_11.view.GUI;

import GC_11.distributed.Client;
import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.exceptions.IllegalMoveException;
import GC_11.network.message.GameViewMessage;
import GC_11.network.choices.Choice;
import GC_11.network.choices.ChoiceFactory;
import GC_11.network.message.LobbyViewMessage;
import GC_11.view.View;

import java.beans.PropertyChangeEvent;

public class GUI extends View {
    private Client client;
    private String nickname;
    public GUIApplication guiApplication;


    /**
     * Every view is bound at only one player, it helps to manage every input that the controller receive
     */

    public GUI(Client client) {
        super();
        this.client = client;
        this.guiApplication = new GUIApplication();
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setModelView(GameViewMessage modelView) {
        this.modelView = modelView;
    }

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
    public void update(GameViewMessage modelView) {
        //TODO
    }

    public Choice getPlayerChoice() {
            try {
                String input = guiApplication.chooseOrder(); // chooseOrder() returns a string that represents the choice of the player but it is called by the view BUTTON
                return ChoiceFactory.createChoice(this.modelView.getPlayer(this.nickname), input);
            } catch (IllegalMoveException e) {
                System.err.println("Invalid CHOICE, Please retake.");
            }
        return null;
    }

}
