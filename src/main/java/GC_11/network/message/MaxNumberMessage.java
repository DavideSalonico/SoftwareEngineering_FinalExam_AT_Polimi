package GC_11.network.message;

import GC_11.distributed.Client;

import java.rmi.RemoteException;

public class MaxNumberMessage extends MessageView{
    public MaxNumberMessage(MaxNumberMessage maxNumberMessage) {
        super();
    }

    public MaxNumberMessage() {
        super();
    }

    @Override
    void executeOnClient(Client client) {
        try {
            client.getView().askMaxNumber();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MessageView sanitize(String key) {
        return new MaxNumberMessage(this);
    }
}
