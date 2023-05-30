package GC_11.distributed;

import GC_11.model.GameViewMessage;
import GC_11.network.LobbyViewMessage;

import java.beans.PropertyChangeListener;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientRei extends Remote {

    void updateViewLobby (LobbyViewMessage newView)throws RemoteException;

    void updateViewGame (GameViewMessage newView)throws RemoteException;

    int askMaxNumber()throws RemoteException;

    String getNickname()throws RemoteException;

    public void run() throws RemoteException;

    public void updateStartGame(GameViewMessage newView) throws RemoteException;

}
