package GC_11.model;

import java.io.Serializable;

public class Message implements Serializable {
    private String sender;
    private String text;

    public Message(String sender, String text) {
        this.sender = sender;
        this.text = text;
    }

    public Message(Message mess) {
        this.sender = mess.getSender();
        this.text = mess.getText();
    }

    public String getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }
}
