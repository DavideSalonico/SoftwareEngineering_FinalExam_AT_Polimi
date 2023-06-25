package GC_11.network.choices;

import GC_11.controller.Controller;
import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.exceptions.IllegalMoveException;
import GC_11.exceptions.NotEnoughFreeSpacesException;
import GC_11.model.Player;

import java.rmi.RemoteException;
import java.util.List;

public class SendMessageChoice extends Choice {
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

    private static String getString(List<String> params, int i) {
        String tmp = params.get(i);
        return tmp;
    }

    @Override
    public void executeOnServer(Controller controller) throws RemoteException {
        controller.sendMessage(params);
    }
}
