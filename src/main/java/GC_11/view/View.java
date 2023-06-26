package GC_11.view;

import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.network.message.LobbyViewMessage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;

/**
 * Abstract class view, father of all the views,it defines the signature of all the method that the view require to print
 * all the necessary data during the Game
 */
public abstract class View implements PropertyChangeListener {
    private PropertyChangeListener listener;
    public abstract void show() throws ColumnIndexOutOfBoundsException;
    public void run() throws RemoteException, ColumnIndexOutOfBoundsException {}
    public void setListener(PropertyChangeListener listener) {
        this.listener = listener;
    }
    public PropertyChangeListener getListener() {
        return this.listener;
    }
    @Override
    public abstract void propertyChange(PropertyChangeEvent evt);

    public abstract void askNickname();

    public abstract void askMaxNumber();

    public abstract void printLobby(LobbyViewMessage lobbyViewMessage);

}
