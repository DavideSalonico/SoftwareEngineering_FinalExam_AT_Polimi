package GC_11.network.message;

import GC_11.distributed.Client;

import java.rmi.RemoteException;

public class AskLoadGame extends MessageView{
    @Override
    public void executeOnClient(Client client) {
        try {
            client.getView().askLoadGame();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MessageView sanitize(String key) {
        return null;
    }
}
