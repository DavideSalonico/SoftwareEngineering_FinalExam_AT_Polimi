package GC_11.model;

import GC_11.network.message.GameViewMessage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.*;

/**
 * Chat class. It contains all the messages of the game separated into main chat and private chats.
 *
 * @see Message
 */
public class Chat implements Serializable {
    private List<Message> mainChat;
    private Map<Set<String>, List<Message>> pvtChats;
    private PropertyChangeListener listener;

    /**
     * Constructs an instance of the Chat class.
     * Initializes the main chat list and private chat map.
     */
    public Chat() {
        mainChat = new ArrayList<>();
        pvtChats = new HashMap<>();
    }

    /**
     * Constructs a copy of the Chat class.
     * Initializes the main chat list and private chat map with the values from the provided chat.
     *
     * @param chat The Chat instance to be copied.
     */
    public Chat(Chat chat) {
        this.mainChat = new ArrayList<>(chat.getMainChat());
        this.pvtChats = new HashMap<>(chat.getPvtChats());
    }

    /**
     * Sends a message to the main chat.
     *
     * @param user The Player who sent the message.
     * @param text The content of the message.
     */
    public void sendMessageToMainChat(Player user, String text) {
        List<Message> oldMainChat = this.mainChat;
        this.mainChat.add(new Message(user.getNickname(), text));

        PropertyChangeEvent evt = new PropertyChangeEvent(
                this,
                "CHANGED_MAIN_CHAT",
                oldMainChat,
                this.mainChat);
        this.listener.propertyChange(evt);
    }

    /**
     * Sends a private message between two players.
     *
     * @param sender   The Player who sent the message.
     * @param receiver The Player who receives the message.
     * @param message  The content of the message.
     */
    public void sendMessageToPrivateChat(Player sender, Player receiver, String message) {
        Map<Set<String>, List<Message>> oldPvtChats = this.pvtChats;

        boolean inserted = false;
        for(Set<String> key : this.pvtChats.keySet()) {
            if(key.contains(sender.getNickname()) && key.contains(receiver.getNickname())) {
                this.pvtChats.get(key).add(new Message(sender.getNickname(), message));
                inserted = true;
            }
        }
        if(!inserted)
            this.pvtChats.put(new HashSet<>(Arrays.asList(sender.getNickname(), receiver.getNickname())), Arrays.asList(new Message(sender.getNickname(), message)));

        PropertyChangeEvent evt = new PropertyChangeEvent(
                this,
                "CHANGED_PRIVATE_CHAT",
                null,
                null);
        this.listener.propertyChange(evt);
    }

    /**
     * Returns the list of messages in the main chat.
     *
     * @return The list of messages in the main chat.
     */
    public List<Message> getMainChat() {
        return this.mainChat;
    }

    /**
     * Returns the list of messages in the private chat between the sender and receiver.
     *
     * @param sender   The Player who sent the message.
     * @param receiver The Player who receives the message.
     * @return The list of messages in the private chat.
     */
    public List<Message> getPrivateChatMessages(Player sender, Player receiver) {
        return this.pvtChats.get(new HashSet<>(Arrays.asList(sender.getNickname(), receiver.getNickname())));
    }

    /**
     * Returns the map of private chats.
     *
     * @return The map of private chats.
     */
    public Map<Set<String>, List<Message>> getPvtChats() {
        return this.pvtChats;
    }

    /**
     * Returns the map of private chats associated with the sender player.
     *
     * @param sender The Player for whom to retrieve the private chats.
     * @return The map of private chats associated with the sender player.
     */
    public Map<String, List<Message>> getPrivateChats(Player sender) {
        Map<String, List<Message>> pvtChats = new HashMap<>();
        for(Set<String> key : this.pvtChats.keySet()) {
            if(key.contains(sender.getNickname())) {
                for(String nickname : key) {
                    if(!nickname.equals(sender.getNickname())) {
                        pvtChats.put(nickname, this.pvtChats.get(key));
                    }
                }
            }
        }
        return pvtChats;
    }

    /**
     * Sets the listener for property change events.
     *
     * @param listener The PropertyChangeListener to be set.
     */
    public void setListener(PropertyChangeListener listener) {
        this.listener = listener;
    }
}
