package GC_11.network.choices;

import GC_11.controller.Controller;
import GC_11.distributed.ServerMain;
import GC_11.exceptions.IllegalMoveException;
import GC_11.model.Player;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Represents a choice made by a player to select a tile.
 */
public class SelectTileChoice extends Choice {

    /**
     * Constructs a SelectTileChoice object.
     *
     * @param player The player making the choice.
     * @param params The parameters for the choice.
     * @param type   The type of the choice.
     * @throws IllegalMoveException If the choice is illegal.
     */
    public SelectTileChoice(Player player, List<String> params, ChoiceType type) throws IllegalMoveException, IllegalMoveException {
        super(player, params, type);

        if (params.size() != 2) throw new IllegalMoveException();
        Integer row_checker, col_checker;
        try {
            row_checker = Integer.parseInt(params.get(0));
            col_checker = Integer.parseInt(params.get(1));
        } catch (NumberFormatException e) {
            throw new IllegalMoveException("Wrong input format: lines and columns must be integers");
        }
        if (row_checker < 0 || row_checker >= 9 || col_checker < 0 || col_checker >= 9)
            throw new IllegalMoveException("Wrong input format: lines and columns must be between 0 and 8");
    }

    /**
     * Executes the choice on the server-side controller.
     *
     * @param controller The controller instance.
     * @throws RemoteException If a remote error occurs.
     */
    @Override
    public void executeOnServer(Controller controller) throws RemoteException {
        controller.selectTile(params);
    }
}
