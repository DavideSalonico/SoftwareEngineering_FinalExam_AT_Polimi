package GC_11.network.choices;

import GC_11.controller.Controller;
import GC_11.distributed.ServerMain;
import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.exceptions.IllegalMoveException;
import GC_11.exceptions.NotEnoughFreeSpacesException;
import GC_11.model.Player;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a choice made by a player to choose the order of tiles.
 */
public class ChooseOrderChoice extends Choice {

    /**
     * Constructs a ChooseOrderChoice object.
     *
     * @param player The player making the choice.
     * @param params The parameters for the choice.
     * @param type   The type of the choice.
     * @throws IllegalMoveException If the choice is illegal.
     */
    public ChooseOrderChoice(Player player, List<String> params, ChoiceType type) throws IllegalMoveException {
        super(player, params, type);

        if (params.size() > 3) throw new IllegalMoveException();
        List<Integer> checkers = new ArrayList<Integer>(params.size());
        for (int i = 0; i < params.size(); i++)
            checkers.add(-1);
        try {
            for (String p : params) {
                checkers.set(params.indexOf(p), Integer.parseInt(p));
            }
        } catch (NumberFormatException e) {
            throw new IllegalMoveException();
        }
        for (Integer checker : checkers)
            if (checker < 0 || checker > 2) throw new IllegalMoveException();
    }

    /**
     * Executes the choice on the server-side controller.
     *
     * @param controller The controller instance.
     * @throws RemoteException If a remote error occurs.
     */
    @Override
    public void executeOnServer(Controller controller) throws RemoteException {
        controller.chooseOrder(params);
    }
}
