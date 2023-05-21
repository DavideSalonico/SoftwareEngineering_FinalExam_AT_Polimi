package GC_11.distributed;

import GC_11.exceptions.ExceededNumberOfPlayersException;
import GC_11.exceptions.NameAlreadyTakenException;
import GC_11.util.Choice;

import java.beans.PropertyChangeListener;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerRei extends Remote, PropertyChangeListener {

    void register (ClientRei client) throws ExceededNumberOfPlayersException, NameAlreadyTakenException, RemoteException;
    void updateGame (ClientRei client, Choice choice)throws RemoteException;

    void updateLobby (ClientRei client, Choice choice)throws RemoteException;
}
