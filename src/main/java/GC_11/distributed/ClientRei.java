package GC_11.distributed;

import GC_11.model.GameViewMessage;
import GC_11.network.LobbyViewMessage;

public interface ClientRei {

    void updateViewLobby (LobbyViewMessage newView);

    void updateViewGame (GameViewMessage newView);
}
