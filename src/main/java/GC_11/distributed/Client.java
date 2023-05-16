package GC_11.distributed;

import GC_11.model.GameView;
import GC_11.model.Player;
import GC_11.view.View;

import java.beans.PropertyChangeListener;
import java.rmi.Remote;

public abstract class Client implements Remote, PropertyChangeListener {

    View view;
    Player player;

    public abstract void update (GameView view);
    protected abstract void connectionSetup();
    protected abstract void lobbySetup();
    protected abstract void sendMessage(Object o);





}
