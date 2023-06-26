package GC_11.network.message;

import GC_11.distributed.Client;

public class MaxNumberMessage extends MessageView{
    public MaxNumberMessage(MaxNumberMessage maxNumberMessage) {
        super();
    }

    @Override
    void executeOnClient(Client client) {
        client.getView().askMaxNumber();
    }

    @Override
    public MessageView sanitize(String key) {
        return new MaxNumberMessage(this);
    }
}
