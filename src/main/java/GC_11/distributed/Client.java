package GC_11.distributed;

import GC_11.network.message.MessageView;
import GC_11.network.choices.Choice;
import GC_11.view.View;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote, Serializable {
    void receiveFromServer(MessageView message) throws RemoteException;

    void notifyServer(Choice choice) throws RemoteException;

    String getNickname() throws RemoteException;

    int askMaxNumber() throws RemoteException;

    View getView() throws RemoteException;

    void notifyDisconnection() throws RemoteException;

    void startClient() throws RemoteException;
}
