package GC_11.model;

import GC_11.network.message.GameViewMessage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.*;

public class Chat implements Serializable {
    private List<Message> mainChat;
    private Map<Set<String>, List<Message>> pvtChats;
    private PropertyChangeListener listener;

    public Chat() {
        mainChat = new ArrayList<>();
        pvtChats = new HashMap<>();
    }

    public Chat(Chat chat) {
        this.mainChat = new ArrayList<>(chat.getMainChat());
        this.pvtChats = new HashMap<>(chat.getPvtChats());
    }

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

    public List<Message> getMainChat() {
        return this.mainChat;
    }

    public List<Message> getPrivateChatMessages(Player sender, Player receiver) {
        return this.pvtChats.get(new HashSet<>(Arrays.asList(sender.getNickname(), receiver.getNickname())));
    }

    public Map<Set<String>, List<Message>> getPvtChats() {
        return this.pvtChats;
    }

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

    public void setListener(PropertyChangeListener listener) {
        this.listener = listener;
    }
}
