package GC_11.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chat implements Serializable {
    private Map<Player, List<String>> mainChat;
    private Map<Player, Map<Player, List<String>>> privateChats;
    private PropertyChangeListener listener;

    public Chat(){
        mainChat = new HashMap<>();
        privateChats = new HashMap<>();
    }

    public void sendMessageToMainChat(Player user, String message) {
        List<String> messages = mainChat.getOrDefault(user, new ArrayList<String>());
        messages.add(message);
        mainChat.put(user, messages);

        PropertyChangeEvent evt = new PropertyChangeEvent(
                this,
                "CHANGED_MAIN_CHAT",
                null,
                null);
        this.listener.propertyChange(evt);
    }

    public void sendMessageToPrivateChat(Player sender, Player receiver, String message) {
        Map<Player, List<String>> senderChats = privateChats.getOrDefault(sender, new HashMap<>());
        List<String> messages = senderChats.getOrDefault(receiver, new ArrayList<>());
        messages.add(message);
        senderChats.put(receiver, messages);
        privateChats.put(sender, senderChats);

        // Also add the message to the receiver's private chat
        Map<Player, List<String>> receiverChats = privateChats.getOrDefault(receiver, new HashMap<>());
        List<String> receiverMessages = receiverChats.getOrDefault(sender, new ArrayList<>());
        receiverMessages.add(message);
        receiverChats.put(sender, receiverMessages);
        privateChats.put(receiver, receiverChats);

        PropertyChangeEvent evt = new PropertyChangeEvent(
                this,
                "CHANGED_PRIVATE_CHAT",
                null,
                null);
        this.listener.propertyChange(evt);
    }

    public List<String> getMainChatMessages() {
        List<String> allMessages = new ArrayList<>();
        for (List<String> messages : mainChat.values()) {
            allMessages.addAll(messages);
        }
        return allMessages;
    }

    public List<String> getPrivateChatMessages(Player sender, Player receiver) {
        Map<Player, List<String>> senderChats = privateChats.get(sender);
        if (senderChats != null) {
            List<String> messages = senderChats.get(receiver);
            if (messages != null) {
                return messages;
            }
        }
        return new ArrayList<>(); // Return an empty list if no messages found
    }

    public void setListener(PropertyChangeListener listener){
        this.listener = listener;
    }
}
