package GC_11.model;

import java.io.Serializable;

/**
 * The Message class represents a chat message with a sender and text content.
 * It provides methods for accessing the sender and text of the message.
 */
public class Message implements Serializable {
    private String sender;
    private String text;

    /**
     * Constructs a Message object with the specified sender and text.
     *
     * @param sender The sender of the message.
     * @param text   The text content of the message.
     */
    public Message(String sender, String text) {
        this.sender = sender;
        this.text = text;
    }

    /**
     * Constructs a copy of the Message object.
     *
     * @param mess The Message instance to be copied.
     */
    public Message(Message mess) {
        this.sender = mess.getSender();
        this.text = mess.getText();
    }

    /**
     * Returns the sender of the message.
     *
     * @return The sender of the message.
     */
    public String getSender() {
        return sender;
    }

    /**
     * Returns the text content of the message.
     *
     * @return The text content of the message.
     */
    public String getText() {
        return text;
    }
}
