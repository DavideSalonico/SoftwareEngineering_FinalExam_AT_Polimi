package GC_11.distributed;

import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.network.GameViewMessage;
import GC_11.network.LobbyViewMessage;

import java.beans.PropertyChangeEvent;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientRMI extends Remote {


    void updateViewLobby(LobbyViewMessage newView) throws RemoteException;

    void updateViewGame(GameViewMessage newView) throws RemoteException;

    int askMaxNumber() throws RemoteException;

    void notifyServer(PropertyChangeEvent evt) throws RemoteException;

}
