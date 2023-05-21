package GC_11.network;

import GC_11.model.Game;

import java.util.List;

public class LobbyViewMessage extends MessageView{

    //private enum MSG_TYPE  { ERROR, NOTIFICATION, INPUT_STRING, INPUT_INT, UPDATE};

    //private String message;
    //private MSG_TYPE messageType;
    private int maxPlayers;
    private List<String> playersNames;
    private Game game;

    public LobbyViewMessage(Lobby lobby){
        this.maxPlayers= lobby.getMaxPlayers();
        this.playersNames = lobby.getPlayers();
        this.game = lobby.getGameModel();
    }

/*
    public LobbyViewMessage(Lobby lobby, MSG_TYPE msgType){
        this.lobbyId=lobby.getLobbyID();
        this.maxPlayers=lobby.getMaxPlayers();
        this.fisrtPlayer=lobby.getFisrtPlayer();
        this.playersNames=lobby.getPlayers();
        this.messageType=msgType;
        this.message=null;
    }
*/

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public List<String> getPlayersNames() {
        return playersNames;
    }


}
/*
    public MSG_TYPE getMessageType() {
        return messageType;
    }

    public String getFisrtPlayer() {
        return fisrtPlayer;
    }

    public String getMessage() {
        return message;
    }
*/

