package GC_11.distributed.socket;

import GC_11.model.GameViewMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {
    /**
     * Notify the client of a model change
     *
     * @param gameViewMessage The resulting model view
     */
    void update(GameViewMessage gameViewMessage) throws RemoteException;
}
