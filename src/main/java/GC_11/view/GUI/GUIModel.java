package GC_11.view.GUI;

import GC_11.distributed.ClientRMI;
import GC_11.distributed.socket.ClientSock;
import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.exceptions.IllegalMoveException;
import GC_11.network.GameViewMessage;
import GC_11.network.choices.Choice;
import GC_11.network.choices.ChoiceFactory;
import GC_11.view.ViewGame;
import javafx.application.Application;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;

public class GUIModel extends ViewGame {

    private Choice playerChoice;
    private ClientRMI client;
    private ClientSock clientSock;
    private String nickname;

    public GUIView view;

    /**
     * Every view is bound at only one player, it helps to manage every input that the controller receive
     */

    public GUIModel(String nickname, ClientRMI client) {
        super();
        this.nickname = nickname;
        this.client = client;
    }

    public GUIModel(String nickname,ClientSock client) {
        super();
        this.nickname = nickname;
        this.clientSock = client;
    }

    public void setPlayerChoice(Choice c) {
        this.playerChoice = c;
    }

    public void setModelView(GameViewMessage modelView) {
        this.modelView = modelView;
    }

    @Override
    public void run() throws RemoteException, ColumnIndexOutOfBoundsException {
        show();
        if (this.modelView.getCurrentPlayer().equals(this.nickname)) {
            Choice choice = getPlayerChoice();
            System.out.println("scelta fatta");
            PropertyChangeEvent evt = new PropertyChangeEvent(
                    this,
                    "CHOICE",
                    null,
                    choice);
            if (this.client!=null)              //TODO: Implementare un'interfaccia client che permetta di chiamare lo stesso metodo sia socket che RMI
                this.client.notifyServer(evt);
            else
                this.clientSock.notifyServer(evt);
        } else {
            //permettergli di scrivere in chat
        }
    }

    @Override
    public void show() throws ColumnIndexOutOfBoundsException {
        if (this.modelView.isError()) {
            view.setError(this.modelView.getExceptionMessage());
        }else{
            view.setError("");
            this.view.updatePlayer(modelView.getBoard(),modelView.getPlayer(modelView.getCurrentPlayer()));
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        try {
            run();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (ColumnIndexOutOfBoundsException e) {
            throw new RuntimeException(e);
        }
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

    public static void main(String[] args) {
        Application.launch(GUIView.class, args);
    }

}
