package GC_11.network.message;

import GC_11.ClientApp;
import GC_11.distributed.Client;

import java.rmi.RemoteException;

public class DisconnectionMessage extends MessageView{


    public void DisconnectMessage() {
    }
    @Override
    public void executeOnClient(Client client) throws RemoteException {
        ClientApp.view.notifyDisconnection();
    }

    @Override
    public MessageView sanitize(String key) {
        return new DisconnectionMessage();
    }
}
