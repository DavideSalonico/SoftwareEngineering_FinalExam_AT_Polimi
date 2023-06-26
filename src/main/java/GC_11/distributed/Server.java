package GC_11.distributed;

import GC_11.network.choices.Choice;
import GC_11.network.message.MessageView;

import java.rmi.RemoteException;

public interface Server {
    void receiveMessage(Choice choice) throws RemoteException;
    
    void sendMessage(MessageView msg, String nickname) throws RemoteException;

    void askMaxNumber() throws RemoteException;

    void notifyDisconnectionToClients() throws RemoteException;

    void sendHeartbeat() throws RemoteException;
}
