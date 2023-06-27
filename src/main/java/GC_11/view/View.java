package GC_11.view;

import GC_11.network.choices.Choice;
import GC_11.network.message.GameViewMessage;
import GC_11.network.message.LobbyViewMessage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Abstract class view, father of all the views,it defines the signature of all the method that the view require to print
 * all the necessary data during the Game
 */
public abstract class View {

    protected String nickname;
    protected GameViewMessage modelView;
    public abstract void show();
    public abstract Choice getPlayerChoice();
    public abstract void askNickname();
    public abstract void askMaxNumber();
    public abstract void askLoadGame();
    public abstract void printLobby(LobbyViewMessage lobbyViewMessage);
    public abstract String getNickname();
    public abstract void update(GameViewMessage modelView);
}
