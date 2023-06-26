package GC_11.distributed;

import GC_11.network.choices.Choice;
import GC_11.network.message.MessageView;

import java.rmi.RemoteException;

public interface Server {
    void receiveMessage(Choice choice);
    
    void sendMessage(MessageView msg, String nickname);

    void notifyDisconnectionToClients();

    void sendHeartbeat();
}
