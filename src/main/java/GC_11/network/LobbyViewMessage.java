package GC_11.network;

import java.util.List;

public class LobbyViewMessage extends MessageView{

    //private enum MSG_TYPE  { ERROR, NOTIFICATION, INPUT_STRING, INPUT_INT, UPDATE};

    //private String message;
    //private MSG_TYPE messageType;
    private int lobbyId;
    private int maxPlayers;
    //String fisrtPlayer;
    private List<String> playersNames;

    public LobbyViewMessage(Lobby lobby){
        this.lobbyId=1;
        this.maxPlayers= lobby.getMaxPlayers();
        this.playersNames = lobby.getPlayers();

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

    public int getLobbyId() {
        return lobbyId;
    }

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

