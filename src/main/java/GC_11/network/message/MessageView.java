package GC_11.network.message;

import GC_11.distributed.Client;

import java.io.Serializable;

public abstract class MessageView implements Serializable {
    public abstract void executeOnClient(Client client);

    public abstract MessageView sanitize(String key);
}
