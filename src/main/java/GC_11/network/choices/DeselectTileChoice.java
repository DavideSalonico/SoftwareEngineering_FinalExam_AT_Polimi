package GC_11.network.choices;

import GC_11.controller.Controller;
import GC_11.distributed.ServerMain;
import GC_11.exceptions.IllegalMoveException;
import GC_11.model.Player;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Represents a choice made by a player to deselect a tile.
 */
public class DeselectTileChoice extends Choice {

    /**
     * Constructs a DeselectTileChoice object.
     *
     * @param player The player making the choice.
     * @param params The parameters for the choice.
     * @param type   The type of the choice.
     * @throws IllegalMoveException If the choice is illegal.
     */
    public DeselectTileChoice(Player player, List<String> params, ChoiceType type) throws IllegalMoveException {
        super(player, params, type);

        if (params.size() != 0) throw new IllegalMoveException();
    }

    /**
     * Executes the choice on the server-side controller.
     *
     * @param controller The controller instance.
     * @throws RemoteException If a remote error occurs.
     */
    @Override
    public void executeOnServer(Controller controller) throws RemoteException {
        controller.deselectTile(params);
    }
}
