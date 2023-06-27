package GC_11.network.message;

import GC_11.distributed.Client;

import java.rmi.RemoteException;

public class NicknameMessage extends MessageView{
    public NicknameMessage(){
        super();
    }
    public NicknameMessage(NicknameMessage nicknameMessage) {
        super();
    }

    @Override
    public void executeOnClient(Client client) {
        try {
            client.getView().askNickname();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MessageView sanitize(String key) {
        return new NicknameMessage(this);
    }
}
