package GC_11.network;

import GC_11.model.Game;
import GC_11.model.Lobby;

import java.util.List;

public class LobbyViewMessage extends MessageView {

    private enum MSG_TYPE {INSERT_PLAYER, SET_NUMBER_OF_PLAYER, INITIALIZE, GET_LOBBY_VIEW, UPDATE, START}

    private String message;
    private MSG_TYPE messageType;
    private int maxPlayers;
    private List<String> playersNames;
    private Game game;

    public LobbyViewMessage(Lobby lobby) {
        this.maxPlayers = lobby.getMaxPlayers();
        this.playersNames = lobby.getPlayers();
        this.game = lobby.getGameModel();
    }

    public LobbyViewMessage() {
        this.message = null;
        this.messageType = null;
        this.game = null;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public List<String> getPlayersNames() {
        return playersNames;
    }

    public MSG_TYPE getMessageType() {
        return messageType;
    }

    public String getFisrtPlayer() {
        return playersNames.get(0);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMessageType(MSG_TYPE messageType) {
        this.messageType = messageType;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public void setPlayersNames(List<String> playersNames) {
        this.playersNames = playersNames;
    }
}

