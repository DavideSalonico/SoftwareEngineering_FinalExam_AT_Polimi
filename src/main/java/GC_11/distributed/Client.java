package GC_11.distributed;

import GC_11.network.MessageView;
import GC_11.network.choices.Choice;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public interface Client extends Remote, Serializable {
    void recieveFromServer(MessageView message) throws RemoteException;

    void notifyServer(Choice choice) throws RemoteException;

    String getNickname() throws RemoteException;
}
