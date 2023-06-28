package GC_11.network.message;

import GC_11.distributed.Client;

import java.rmi.RemoteException;

/**
 * Represents a message for requesting the nickname from the client.
 */
public class NicknameMessage extends MessageView{

    /**
     * Constructs a NicknameMessage object.
     */
    public NicknameMessage(){
        super();
    }

    /**
     * Constructs a NicknameMessage object based on another NicknameMessage object.
     *
     * @param nicknameMessage The NicknameMessage object to copy.
     */
    public NicknameMessage(NicknameMessage nicknameMessage) {
        super();
    }

    /**
     * Executes the nickname message on the client by asking for the nickname.
     *
     * @param client The client instance.
     */
    @Override
    public void executeOnClient(Client client) {
        try {
            client.getView().askNickname();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sanitizes the nickname message by creating a new instance of NicknameMessage.
     *
     * @param key The key used for sanitization.
     * @return A sanitized version of the nickname message.
     */
    @Override
    public MessageView sanitize(String key) {
        return new NicknameMessage(this);
    }
}
