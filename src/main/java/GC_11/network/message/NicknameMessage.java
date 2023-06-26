package GC_11.network.message;

import GC_11.distributed.Client;

public class NicknameMessage extends MessageView{
    public NicknameMessage(NicknameMessage nicknameMessage) {
        super();
    }

    @Override
    void executeOnClient(Client client) {
        client.getView().askNickname();
    }

    @Override
    public MessageView sanitize(String key) {
        return new NicknameMessage(this);
    }
}
