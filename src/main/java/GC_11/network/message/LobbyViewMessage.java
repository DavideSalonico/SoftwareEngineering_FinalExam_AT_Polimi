package GC_11.network.message;

import GC_11.distributed.Client;
import GC_11.model.Lobby;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class LobbyViewMessage extends MessageView {
    private Exception exception;

    public LobbyViewMessage(Lobby lobby) {
        this.maxPlayers = lobby.getMaxPlayers();
        this.playersNames = lobby.getPlayers();
    }

    public LobbyViewMessage(LobbyViewMessage lobbyViewMessage) {
        super();
        this.maxPlayers = lobbyViewMessage.getMaxPlayers();
        this.playersNames = new ArrayList<>(lobbyViewMessage.getPlayersNames());
    }

    public LobbyViewMessage(Lobby lobby, Exception e) {
        super();
        if(e != null){
            this.exception = new Exception(e);
        }
        this.maxPlayers = lobby.getMaxPlayers();
        this.playersNames = new ArrayList<>(lobby.getPlayers());
    }

    @Override
    public void executeOnClient(Client client) {
        try {
            client.getView().printLobby(this);
        } catch (RemoteException e) {
            throw new RuntimeException(e); // TODO
        }
    }

    @Override
    public MessageView sanitize(String key) {
        return new LobbyViewMessage(this);
    }

    private int maxPlayers;
    private List<String> playersNames;

    public int getMaxPlayers() {
        return maxPlayers;
    }
    public List<String> getPlayersNames() {
        return playersNames;
    }
}

