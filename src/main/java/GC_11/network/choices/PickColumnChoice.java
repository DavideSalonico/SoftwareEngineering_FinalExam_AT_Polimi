package GC_11.network.choices;

import GC_11.controller.Controller;
import GC_11.distributed.ServerMain;
import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.exceptions.IllegalMoveException;
import GC_11.exceptions.NotEnoughFreeSpacesException;
import GC_11.model.Player;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Represents a choice made by a player to pick a column.
 */
public class PickColumnChoice extends Choice {

    /**
     * Constructs a PickColumnChoice object.
     *
     * @param player The player making the choice.
     * @param params The parameters for the choice.
     * @param type   The type of the choice.
     * @throws IllegalMoveException If the choice is illegal.
     */
    public PickColumnChoice(Player player, List<String> params, ChoiceType type) throws IllegalMoveException {
        super(player, params, type);

        if (params.size() != 1) throw new IllegalMoveException();
        Integer column_checker;
        try {
            column_checker = Integer.parseInt(params.get(0));
        } catch (NumberFormatException e) {
            throw new IllegalMoveException();
        }
        if (column_checker < 0 || column_checker >= 5) throw new IllegalMoveException();
    }

    /**
     * Executes the choice on the server-side controller.
     *
     * @param controller The controller instance.
     * @throws RemoteException If a remote error occurs.
     */
    @Override
    public void executeOnServer(Controller controller) throws RemoteException {
        controller.pickColumn(params);
    }
}
