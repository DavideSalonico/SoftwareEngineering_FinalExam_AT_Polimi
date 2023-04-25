package GC_11.distributed;

import GC_11.model.GameView;
import GC_11.util.Choice;

import java.beans.PropertyChangeEvent;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {

    /**
     * Notify the client of a model change
     * @param gameView the sending server
     */
    void update(GameView gameView) throws RemoteException;

    void update(Server server, PropertyChangeEvent arg) throws RemoteException;


}
