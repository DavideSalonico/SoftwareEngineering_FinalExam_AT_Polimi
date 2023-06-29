package GC_11.network.message;

import GC_11.distributed.Client;

import java.rmi.RemoteException;

public class PingMessage extends MessageView{

    public PingMessage() {
    }
    @Override
    public void executeOnClient(Client client) throws RemoteException {

    }

    @Override
    public MessageView sanitize(String key) {
        return new PingMessage();
    }
}
