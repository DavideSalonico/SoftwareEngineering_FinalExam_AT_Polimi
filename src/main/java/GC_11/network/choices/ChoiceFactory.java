package GC_11.network.choices;

import GC_11.exceptions.IllegalMoveException;
import GC_11.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ChoiceFactory {

    public static Choice createChoice(Player player, String input) throws IllegalMoveException {
        List<String> tmp = new ArrayList<String>();

        StringTokenizer st = new StringTokenizer(input);
        while (st.hasMoreTokens()) {
            tmp.add(st.nextToken());
        }
        ChoiceType type;
        try {
            type = ChoiceType.valueOf(tmp.get(0));
        }
        catch (IllegalArgumentException e) {
            throw new IllegalMoveException("wrong choice type");
        }
        List<String> params = new ArrayList<String>();
        for (String p : tmp) {
            if (tmp.indexOf(p) != 0) {
                params.add(p);
            }
        }
        switch (type) {
            case RESET_TURN -> {
                return new ResetTurnChoice(player, params, type);
            }
            case SELECT_TILE -> {
                return new SelectTileChoice(player, params, type);
            }
            case PICK_COLUMN -> {
                return new PickColumnChoice(player, params, type);
            }
            case DESELECT_TILE -> {
                return new DeselectTileChoice(player, params, type);
            }
            case CHOOSE_ORDER -> {
                return new ChooseOrderChoice(player, params, type);
            }
            case SEND_MESSAGE -> {
                return new SendMessageChoice(player, params, type);
            }
            case ADD_PLAYER -> {
                return new AddNicknameChoice(player, params, type);
            }
            case SET_MAX_NUMBER -> {
                return new SetMaxNumberChoice(player, params, type);
            }
        }
        return null;
    }
}
