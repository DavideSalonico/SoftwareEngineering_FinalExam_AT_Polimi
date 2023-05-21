package GC_11.view;

import GC_11.network.LobbyViewMessage;
import GC_11.util.Choice;

import java.beans.PropertyChangeEvent;

public abstract class ViewLobby extends View {

    boolean show_en = true;

    protected LobbyViewMessage lobbyViewMessage;

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //System.out.println("evt" + evt);
        System.out.println("Event Type: " + evt.getPropertyName());
        this.lobbyViewMessage = (LobbyViewMessage) evt.getNewValue();
    }

    public abstract void update (LobbyViewMessage lvm);


}
