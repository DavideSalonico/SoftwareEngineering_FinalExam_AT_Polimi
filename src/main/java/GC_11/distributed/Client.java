package GC_11.distributed;

import GC_11.model.GameView;
import GC_11.model.Player;
import GC_11.util.Choice;
import GC_11.view.View;

import java.beans.PropertyChangeEvent;
import java.rmi.Remote;
import java.rmi.RemoteException;

public abstract class Client implements Remote {

    View view;
    Player player;

    protected abstract void connectionSetup();
    protected abstract void lobbySetup();
    protected abstract void sendMessage();





}
