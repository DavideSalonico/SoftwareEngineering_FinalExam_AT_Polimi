package GC_11.network.message;

import GC_11.distributed.Client;
import GC_11.model.Lobby;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a message view for the lobby.
 * Contains information about the lobby's maximum number of players and player names.
 */
public class LobbyViewMessage extends MessageView {
    private Exception exception;
    private int maxPlayers;
    private List<String> playersNames;

    /**
     * Constructs a LobbyViewMessage object from a lobby.
     *
     * @param lobby The lobby instance.
     */
    public LobbyViewMessage(Lobby lobby) {
        this.maxPlayers = lobby.getMaxPlayers();
        this.playersNames = lobby.getPlayers();
    }

    /**
     * Constructs a LobbyViewMessage object from another LobbyViewMessage object.
     *
     * @param lobbyViewMessage The LobbyViewMessage object to copy.
     */
    public LobbyViewMessage(LobbyViewMessage lobbyViewMessage) {
        super();
        this.maxPlayers = lobbyViewMessage.getMaxPlayers();
        this.playersNames = new ArrayList<>(lobbyViewMessage.getPlayersNames());
    }

    /**
     * Constructs a LobbyViewMessage object from a lobby and an exception.
     *
     * @param lobby The lobby instance.
     * @param e     The exception.
     */
    public LobbyViewMessage(Lobby lobby, Exception e) {
        super();
        if(e != null){
            this.exception = new Exception(e);
        }
        this.maxPlayers = lobby.getMaxPlayers();
        this.playersNames = new ArrayList<>(lobby.getPlayers());
    }

    /**
     * Print the lobby view message on the client.
     *
     * @param client The client instance.
     */
    @Override
    public void executeOnClient(Client client) {
        try {
            client.getView().printLobby(this);
        } catch (RemoteException e) {
            throw new RuntimeException(e); // TODO
        }
    }

    /**
     * Sanitizes the lobby view message by creating a new instance of LobbyViewMessage.
     *
     * @param key The key used for sanitization.
     * @return A sanitized version of the lobby view message.
     */
    @Override
    public MessageView sanitize(String key) {
        return new LobbyViewMessage(this);
    }

    /**
     * Returns the maximum number of players in the lobby.
     *
     * @return The maximum number of players.
     */
    public int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     * Returns the list of player names in the lobby.
     *
     * @return The list of player names.
     */
    public List<String> getPlayersNames() {
        return playersNames;
    }
}

