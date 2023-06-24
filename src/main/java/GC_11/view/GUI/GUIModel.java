package GC_11.view.GUI;

import GC_11.distributed.ClientRMI;
import GC_11.distributed.socket.ClientSock;
import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.network.GameViewMessage;
import GC_11.network.choices.Choice;
import GC_11.view.ViewGame;

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
            view.setBoardError(this.modelView.getExceptionMessage());
        }else{
            this.view.updatePlayer(modelView.getBoard(),modelView.getPlayer(modelView.getCurrentPlayer()));
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    @Override
    public Choice getPlayerChoice() {
        /*while (true) {
            try {
                //ASPETTA LA
                return ChoiceFactory.createChoice(this.modelView.getPlayer(this.nickname), input);
            } catch (IllegalMoveException e) {
                System.err.println("Invalid CHOICE, Please retake.");
            }
        }*/
        return null;
    }

}
