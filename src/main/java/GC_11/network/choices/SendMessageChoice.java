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
 * Represents a choice made by a player to send a message.
 */
public class SendMessageChoice extends Choice {

    /**
     * Constructs a SendMessageChoice object.
     *
     * @param player The player making the choice.
     * @param params The parameters for the choice.
     * @param type   The type of the choice.
     * @throws IllegalMoveException If the choice is illegal.
     */
    public SendMessageChoice(Player player, List<String> params, ChoiceType type) throws IllegalMoveException {
        super(player, params, type);
        for(int i=2; i<params.size(); i++){
            String tmp = getString(params, i);
            params.set(1, params.get(1).concat(" " + tmp));;
        }
        for (int i = params.size() - 1; i >= 2; i--) {
            params.remove(i);
        }

        if (params.size() != 2) throw new IllegalMoveException();
        if (params.get(0).length() >= 64 || params.get(1).length() >= 64)
            throw new IllegalMoveException("Message too long");
    }

    /**
     * Helper method to get a string from the parameters list.
     *
     * @param params The list of parameters.
     * @param i      The index of the string to retrieve.
     * @return The string at the specified index.
     */
    private static String getString(List<String> params, int i) {
        String tmp = params.get(i);
        return tmp;
    }

    /**
     * Executes the choice on the server-side controller.
     *
     * @param controller The controller instance.
     * @throws RemoteException If a remote error occurs.
     */
    @Override
    public void executeOnServer(Controller controller) throws RemoteException {
        controller.sendMessage(player, params);
    }
}
