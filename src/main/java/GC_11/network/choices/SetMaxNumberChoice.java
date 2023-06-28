package GC_11.network.choices;

import GC_11.controller.Controller;
import GC_11.exceptions.ExceededNumberOfPlayersException;
import GC_11.exceptions.IllegalMoveException;
import GC_11.exceptions.NameAlreadyTakenException;
import GC_11.model.Player;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Represents a choice made by a player to set the maximum number of players.
 */
public class SetMaxNumberChoice extends Choice{

    /**
     * Constructs a SetMaxNumberChoice object.
     *
     * @param player The player making the choice.
     * @param params The parameters for the choice.
     * @param type   The type of the choice.
     * @throws IllegalMoveException If the choice is illegal.
     */
    public SetMaxNumberChoice(Player player, List<String> params, ChoiceType type) throws IllegalMoveException {
        super(player, params, type);
    }

    /**
     * Executes the choice on the server-side controller.
     *
     * @param controller The controller instance.
     * @throws RemoteException If a remote error occurs.
     */
    @Override
    public void executeOnServer(Controller controller) throws RemoteException{
        controller.setMaxPlayers(Integer.parseInt(params.get(0)));
    }
}
