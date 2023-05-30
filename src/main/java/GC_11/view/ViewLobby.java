package GC_11.view;

import GC_11.network.LobbyViewMessage;
import GC_11.util.Choice;

import java.beans.PropertyChangeEvent;

public abstract class ViewLobby extends View {

    boolean show_en = true;

    protected LobbyViewMessage lobbyViewMessage;


    public abstract void update (LobbyViewMessage lvm);


}
