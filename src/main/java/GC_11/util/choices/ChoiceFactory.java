package GC_11.util.choices;

import GC_11.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ChoiceFactory {

    public static Choice createChoice(Player player, String input) {
        List<String> tmp = new ArrayList<String>();

        StringTokenizer st = new StringTokenizer(input);
        while (st.hasMoreTokens()) {
            tmp.add(st.nextToken());
        }

        ChoiceType type = ChoiceType.valueOf(tmp.get(0));

        List<String> params = new ArrayList<String>();
        for (String p : tmp) {
            if (tmp.indexOf(p) != 0) {
                params.add(p);
            }
        }
        switch (type){
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
            case SEND_MESSAGE->{
                return new SendMessageChoice(player, params, type);
            }
        }
        return null;
    }
}
