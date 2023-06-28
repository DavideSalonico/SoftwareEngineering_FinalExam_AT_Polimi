package GC_11;

import GC_11.model.Chat;
import GC_11.model.Message;
import GC_11.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ChatTest {
    private Chat chat;
    private ListenerVoid listener;
    private Player player1;
    private Player player2;
    private Player player3;

    @BeforeEach
    void setUp() {
        chat = new Chat();
        listener = new ListenerVoid();
        chat.setListener(listener);
        player1 = new Player("Player 1");
        player2 = new Player("Player 2");
        player3 = new Player("Player 3");
    }

    @Test
    void testSendMessageToMainChat() {
        chat.setListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                assertEquals("CHANGED_MAIN_CHAT", evt.getPropertyName());

                List<Message> oldMainChat = (List<Message>) evt.getOldValue();
                assertNotNull(oldMainChat);
                assertEquals(0, oldMainChat.size());

                List<Message> newMainChat = (List<Message>) evt.getNewValue();
                assertNotNull(newMainChat);
                assertEquals(1, newMainChat.size());

                Message message = newMainChat.get(0);
                assertEquals(player1.getNickname(), message.getSender());
                assertEquals("Hello, everyone!", message.getText());
            }
        });

        chat.sendMessageToMainChat(player1, "Hello, everyone!");

        List<Message> mainChat = chat.getMainChat();
        assertNotNull(mainChat);
        assertEquals(1, mainChat.size());

        Message message = mainChat.get(0);
        assertEquals(player1.getNickname(), message.getSender());
        assertEquals("Hello, everyone!", message.getText());
    }

    @Test
    void testSendMessageToPrivateChat() {
        chat.setListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                assertEquals("CHANGED_PRIVATE_CHAT", evt.getPropertyName());
                assertNull(evt.getOldValue());
                assertNull(evt.getNewValue());
            }
        });

        chat.sendMessageToPrivateChat(player1, player2, "Private message from Player 1 to Player 2");

        Map<Set<String>, List<Message>> pvtChats = chat.getPvtChats();
        assertNotNull(pvtChats);
        assertEquals(1, pvtChats.size());

        Set<String> chatParticipants = new HashSet<>(Arrays.asList(player1.getNickname(), player2.getNickname()));
        List<Message> privateChat = pvtChats.get(chatParticipants);
        assertNotNull(privateChat);
        assertEquals(1, privateChat.size());

        Message message = privateChat.get(0);
        assertEquals(player1.getNickname(), message.getSender());
        assertEquals("Private message from Player 1 to Player 2", message.getText());
    }

    @Test
    void testGetMainChat() {
        List<Message> mainChat = chat.getMainChat();
        assertNotNull(mainChat);
        assertEquals(0, mainChat.size());

        chat.sendMessageToMainChat(player1, "Message 1");
        chat.sendMessageToMainChat(player2, "Message 2");

        mainChat = chat.getMainChat();
        assertNotNull(mainChat);
        assertEquals(2, mainChat.size());

        Message message1 = mainChat.get(0);
        assertEquals(player1.getNickname(), message1.getSender());
        assertEquals("Message 1", message1.getText());

        Message message2 = mainChat.get(1);
        assertEquals(player2.getNickname(), message2.getSender());
        assertEquals("Message 2", message2.getText());
    }

    @Test
    void testGetPrivateChatMessages() {
        chat.sendMessageToPrivateChat(player1, player2, "Message 1");
        chat.sendMessageToPrivateChat(player2, player1, "Message 2");

        List<Message> privateChat1 = chat.getPrivateChatMessages(player1, player2);
        assertNotNull(privateChat1);
        assertEquals(1, privateChat1.size());

        Message message1 = privateChat1.get(0);
        assertEquals(player1.getNickname(), message1.getSender());
        assertEquals("Message 1", message1.getText());

        List<Message> privateChat2 = chat.getPrivateChatMessages(player2, player1);
        assertNotNull(privateChat2);
        assertEquals(1, privateChat2.size());

        Message message2 = privateChat2.get(0);
        assertEquals(player2.getNickname(), message2.getSender());
        assertEquals("Message 2", message2.getText());
    }

    @Test
    void testGetPvtChats() {
        Map<Set<String>, List<Message>> pvtChats = chat.getPvtChats();
        assertNotNull(pvtChats);
        assertEquals(0, pvtChats.size());

        chat.sendMessageToPrivateChat(player1, player2, "Message 1");
        chat.sendMessageToPrivateChat(player2, player1, "Message 2");

        pvtChats = chat.getPvtChats();
        assertNotNull(pvtChats);
        assertEquals(1, pvtChats.size());

        Set<String> chatParticipants = new HashSet<>(Arrays.asList(player1.getNickname(), player2.getNickname()));
        List<Message> privateChat = pvtChats.get(chatParticipants);
        assertNotNull(privateChat);
        assertEquals(2, privateChat.size());

        Message message1 = privateChat.get(0);
        assertEquals(player1.getNickname(), message1.getSender());
        assertEquals("Message 1", message1.getText());

        Message message2 = privateChat.get(1);
        assertEquals(player2.getNickname(), message2.getSender());
        assertEquals("Message 2", message2.getText());
    }

    @Test
    void testGetPrivateChats() {
        chat.sendMessageToPrivateChat(player1, player2, "Message 1");
        chat.sendMessageToPrivateChat(player2, player1, "Message 2");
        chat.sendMessageToPrivateChat(player1, player3, "Message 3");

        Map<String, List<Message>> privateChats1 = chat.getPrivateChats(player1);
        assertNotNull(privateChats1);
        assertEquals(2, privateChats1.size());

        List<Message> privateChat1 = privateChats1.get(player2.getNickname());
        assertNotNull(privateChat1);
        assertEquals(2, privateChat1.size());

        Message message1 = privateChat1.get(0);
        assertEquals(player1.getNickname(), message1.getSender());
        assertEquals("Message 1", message1.getText());

        Message message2 = privateChat1.get(1);
        assertEquals(player2.getNickname(), message2.getSender());
        assertEquals("Message 2", message2.getText());

        List<Message> privateChat2 = privateChats1.get(player3.getNickname());
        assertNotNull(privateChat2);
        assertEquals(1, privateChat2.size());

        Message message3 = privateChat2.get(0);
        assertEquals(player1.getNickname(), message3.getSender());
        assertEquals("Message 3", message3.getText());

        Map<String, List<Message>> privateChats2 = chat.getPrivateChats(player2);
        assertNotNull(privateChats2);
        assertEquals(1, privateChats2.size());

        List<Message> privateChat3 = privateChats2.get(player1.getNickname());
        assertNotNull(privateChat3);
        assertEquals(2, privateChat3.size());

        Message message4 = privateChat3.get(0);
        assertEquals(player2.getNickname(), message4.getSender());
        assertEquals("Message 2", message4.getText());

        Message message5 = privateChat3.get(1);
        assertEquals(player1.getNickname(), message5.getSender());
        assertEquals("Message 1", message5.getText());

        Map<String, List<Message>> privateChats3 = chat.getPrivateChats(player3);
        assertNotNull(privateChats3);
        assertEquals(1, privateChats3.size());

        List<Message> privateChat4 = privateChats3.get(player1.getNickname());
        assertNotNull(privateChat4);
        assertEquals(1, privateChat4.size());

        Message message6 = privateChat4.get(0);
        assertEquals(player1.getNickname(), message6.getSender());
        assertEquals("Message 3", message6.getText());
    }
}
