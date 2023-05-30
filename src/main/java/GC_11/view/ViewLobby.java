package GC_11.view;

import GC_11.network.LobbyViewMessage;
import GC_11.network.MessageView;
import GC_11.util.Choice;

import java.beans.PropertyChangeEvent;

public abstract class ViewLobby extends View {


    protected LobbyViewMessage lobbyViewMessage;


    public void setLobbyViewMessage(){
        this.lobbyViewMessage = (LobbyViewMessage) this.messageView;;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.lobbyViewMessage= (LobbyViewMessage) evt.getNewValue();
        run();
    }
}
