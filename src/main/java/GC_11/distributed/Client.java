package GC_11.distributed;

import GC_11.network.MessageView;
import GC_11.network.choices.Choice;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote, Serializable {
    void receiveFromServer(MessageView message) throws RemoteException;

    void notifyServer(Choice choice) throws RemoteException;

    String getNickname() throws RemoteException;

    int askMaxNumber();
}
