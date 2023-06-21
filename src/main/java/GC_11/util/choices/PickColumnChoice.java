package GC_11.util.choices;

import GC_11.controller.Controller;
import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.exceptions.IllegalMoveException;
import GC_11.exceptions.NotEnoughFreeSpacesException;
import GC_11.model.Player;

import java.rmi.RemoteException;
import java.security.InvalidParameterException;
import java.util.List;

public class PickColumnChoice extends Choice {
    public PickColumnChoice(Player player, List<String> params, ChoiceType type) throws IllegalArgumentException {
        super(player, params, type);

        if (params.size() != 1) throw new IllegalArgumentException();
        Integer column_checker;
        try {
            column_checker = Integer.parseInt(params.get(0));
        } catch (NumberFormatException e) {
            throw new InvalidParameterException();
        }
        if (column_checker < 0 || column_checker >= 5) throw new InvalidParameterException();
    }

    @Override
    public void executeOnServer(Controller controller) throws IllegalMoveException, ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException, RemoteException {
        controller.pickColumn(params);
    }
}
