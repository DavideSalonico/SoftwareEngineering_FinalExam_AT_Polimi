package GC_11.distributed;

import GC_11.exceptions.ExceededNumberOfPlayersException;
import GC_11.exceptions.NameAlreadyTakenException;
import GC_11.model.Game;
import GC_11.model.GameView;
import GC_11.network.LobbyView;
import GC_11.util.Choice;
import GC_11.view.ViewGame;
import GC_11.view.ViewLobby;

public class ClientImplRei implements ClientRei{

    ViewLobby viewLobby;
    ViewGame viewGame;
    private String nickname;

    public ClientImplRei(ServerRei server, String nickname) throws ExceededNumberOfPlayersException, NameAlreadyTakenException {
        this.nickname = nickname;
        System.out.println("HELLO " + nickname + "!!!");
        server.register(this);
    }

    @Override
    public void updateViewLobby(LobbyView newView) {

    }

    @Override
    public void updateViewGame(GameView newView) {

    }

    public String getNickname() {
        return nickname;
    }

    public void run() {
        viewLobby.run();
    }
}
