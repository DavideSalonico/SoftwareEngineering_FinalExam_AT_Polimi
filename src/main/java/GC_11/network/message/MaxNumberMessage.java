package GC_11.network.message;

import GC_11.distributed.Client;

import java.rmi.RemoteException;

/**
 * Represents a message for requesting the maximum number of players from the client.
 */
public class MaxNumberMessage extends MessageView{

    /**
     * Constructs a MaxNumberMessage object.
     */
    public MaxNumberMessage() {
        super();
    }

    /**
     * Constructs a MaxNumberMessage object based on another MaxNumberMessage object.
     *
     * @param maxNumberMessage The MaxNumberMessage object to copy.
     */
    public MaxNumberMessage(MaxNumberMessage maxNumberMessage) {
        super();
    }

    /**
     * Executes the max number message on the client by asking for the maximum number of players.
     *
     * @param client The client instance.
     */
    @Override
    public void executeOnClient(Client client) {
        try {
            client.getView().askMaxNumber();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sanitizes the max number message by creating a new instance of MaxNumberMessage.
     *
     * @param key The key used for sanitization.
     * @return A sanitized version of the max number message.
     */
    @Override
    public MessageView sanitize(String key) {
        return new MaxNumberMessage(this);
    }
}
