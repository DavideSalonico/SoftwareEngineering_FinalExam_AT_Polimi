package GC_11.util.choices;

import GC_11.controller.Controller;
import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.exceptions.IllegalMoveException;
import GC_11.exceptions.NotEnoughFreeSpacesException;
import GC_11.model.Player;

import java.security.InvalidParameterException;
import java.util.List;

public class SendMessageChoice extends Choice {
    public SendMessageChoice(Player player, List<String> params, ChoiceType type) {
        super(player, params, type);

        if (params.size() != 2) throw new IllegalArgumentException();
        if (params.get(0).length() >= 64 || params.get(1).length() >= 64)
            throw new InvalidParameterException("Message too long");
    }

    @Override
    public void executeOnServer(Controller controller) throws IllegalMoveException, ColumnIndexOutOfBoundsException, NotEnoughFreeSpacesException {
        controller.sendMessage(params);
    }
}
