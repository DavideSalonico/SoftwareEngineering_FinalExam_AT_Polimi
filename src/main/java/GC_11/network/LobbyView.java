package GC_11.network;

import java.util.List;
import java.util.Scanner;

public class LobbyView{

    private enum MSG_TYPE  { ERROR, NOTIFICATION, INPUT_STRING, INPUT_INT, UPDATE};

    private String message;
    private MSG_TYPE messageType;
    private int lobbyId;
    private int maxPlayers;
    String fisrtPlayer;
    private List<String> playersNames;

    public LobbyView(String msg, MSG_TYPE msgType){
        this.message=msg;
        this.messageType=msgType;
        this.lobbyId=-1;
        this.maxPlayers=-1;
        this.fisrtPlayer=null;
        this.playersNames=null;
    }

    public LobbyView(Lobby lobby, MSG_TYPE msgType){
        this.lobbyId=lobby.getLobbyID();
        this.maxPlayers=lobby.getMaxPlayers();
        this.fisrtPlayer=lobby.getFisrtPlayer();
        this.playersNames=lobby.getPlayers();
        this.messageType=msgType;
        this.message=null;
    }


    public int getLobbyId() {
        return lobbyId;
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
        return fisrtPlayer;
    }

    public String getMessage() {
        return message;
    }

}
