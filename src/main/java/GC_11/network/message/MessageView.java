package GC_11.network.message;

import GC_11.distributed.Client;

import java.io.Serializable;

/**
 * Abstract base class for message views.
 * Provides methods for executing the message on the client and sanitizing the message view.
 */
public abstract class MessageView implements Serializable {

    /**
     * Executes the message on the client.
     *
     * @param client The client instance.
     */
    public abstract void executeOnClient(Client client);

    /**
     * Sanitizes the message view by removing sensitive information.
     *
     * @param key The key used for sanitization.
     * @return A sanitized version of the message view.
     */
    public abstract MessageView sanitize(String key);
}
