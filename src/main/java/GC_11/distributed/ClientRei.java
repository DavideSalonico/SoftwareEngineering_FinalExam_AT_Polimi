package GC_11.distributed;

import GC_11.model.GameView;
import GC_11.network.LobbyView;

public interface ClientRei {

    void updateViewLobby (LobbyView newView);

    void updateViewGame (GameView newView);
}
