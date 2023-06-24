package GC_11.view;

import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.exceptions.IllegalMoveException;
import GC_11.exceptions.NotEnoughFreeSpacesException;
import GC_11.network.GameViewMessage;
import GC_11.model.Player;
import GC_11.network.MessageView;
import GC_11.network.choices.Choice;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;

/**
 * Abstract class view, father of all the views,it defines the signature of all the method that the view require to print
 * all the necessary data during the Game
 */
public abstract class View implements PropertyChangeListener {

    //Controller must register
    private PropertyChangeListener listener;

    public abstract void show() throws ColumnIndexOutOfBoundsException;

    @Override
    public abstract void propertyChange(PropertyChangeEvent evt);

    public void run() throws RemoteException, ColumnIndexOutOfBoundsException {}

    public void setListener(PropertyChangeListener listener) {
        this.listener = listener;
    }

    public PropertyChangeListener getListener() {
        return this.listener;
    }
}
