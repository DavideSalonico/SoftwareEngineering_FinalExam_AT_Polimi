package GC_11.model.common;

import GC_11.model.Player;

public class CommonGoalCard1 extends CommonGoalCard{


    @Override
    public int calculatePoints(Player p) {
        return 0;
    }

    @Override
    public boolean check() {
        return false;
    }
}
