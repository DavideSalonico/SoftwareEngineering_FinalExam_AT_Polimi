package GC_11.distributed;

import GC_11.exceptions.ExceededNumberOfPlayersException;
import GC_11.exceptions.NameAlreadyTakenException;
import GC_11.util.Choice;

public interface ServerRei {
    void register (ClientImplRei client) throws ExceededNumberOfPlayersException, NameAlreadyTakenException;
    void updateGame (ClientRei client, Choice choice);

    void updateLobby (ClientRei client, Choice choice);
}
