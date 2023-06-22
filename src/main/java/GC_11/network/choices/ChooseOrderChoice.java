package GC_11.network.choices;

import GC_11.controller.Controller;
import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.exceptions.IllegalMoveException;
import GC_11.exceptions.NotEnoughFreeSpacesException;
import GC_11.model.Player;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class ChooseOrderChoice extends Choice {

    public ChooseOrderChoice(Player player, List<String> params, ChoiceType type) throws IllegalArgumentException {
        super(player, params, type);

        if (params.size() > 3) throw new IllegalArgumentException();
        List<Integer> checkers = new ArrayList<Integer>(params.size());
        for (int i = 0; i < params.size(); i++)
            checkers.add(-1);
        try {
            for (String p : params) {
                checkers.set(params.indexOf(p), Integer.parseInt(p));
            }
        } catch (NumberFormatException e) {
            throw new InvalidParameterException();
        }
        for (Integer checker : checkers)
            if (checker < 0 || checker > 2) throw new InvalidParameterException();
    }

    @Override
    public void executeOnServer(Controller controller) throws IllegalMoveException, ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {
        controller.chooseOrder(params);
    }
}
