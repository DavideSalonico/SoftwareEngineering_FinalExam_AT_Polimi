package GC_11.network.choices;

import GC_11.controller.Controller;
import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.exceptions.IllegalMoveException;
import GC_11.exceptions.NotEnoughFreeSpacesException;
import GC_11.model.Player;

import java.util.List;

public class SendMessageChoice extends Choice {
    public SendMessageChoice(Player player, List<String> params, ChoiceType type) throws IllegalMoveException {
        super(player, params, type);

        if (params.size() != 2) throw new IllegalMoveException();
        if (params.get(0).length() >= 64 || params.get(1).length() >= 64)
            throw new IllegalMoveException("Message too long");
    }

    @Override
    public void executeOnServer(Controller controller) throws IllegalMoveException, ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {
        controller.sendMessage(params);
    }
}
