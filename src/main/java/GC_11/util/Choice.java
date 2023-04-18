package GC_11.util;

import GC_11.model.Player;

import java.io.Serializable;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Choice implements Serializable{
    public Choice(Player player, String input) throws IllegalArgumentException {
        this.player = player;

        List<String> tmp = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(input);
        while (st.hasMoreTokens()) {
            tmp.add(st.nextToken());
        }

        this.choice = Choice.Type.valueOf(tmp.get(0));

        for(String p : tmp){
            if(tmp.indexOf(p) != 0){
                params.add(p);
            }
        }

        //Controls
        switch(this.choice){
            case FIND_MATCH:
            case SEE_PERSONALGOAL:
                if(params.size() != 0) throw new IllegalArgumentException();
                break;
            case SEE_COMMONGOAL:
            case PICK_COLUMN:
            case INSERT_NAME:
                if(params.size() != 1) throw new IllegalArgumentException();
            case SELECT_TILE:
            case LOGIN:
                if(params.size() != 2) throw new IllegalArgumentException();
            case CHOOSE_ORDER:
                if(params.size() > 3) throw new IllegalArgumentException();
        }
    }

    public enum Type{
        INSERT_NAME, LOGIN, FIND_MATCH, SEE_COMMONGOAL, SEE_PERSONALGOAL, SELECT_TILE, PICK_COLUMN, CHOOSE_ORDER
    }

    private List<String> params;
    private Type choice;

    private Player player;

    public Type getChoice() {
        return choice;
    }

    public List<String> getParams() {
        return params;
    }

    public Player getPlayer() {
        return player;
    }

    //Parameters
    //Common Goal: 0 o 1 (int)
    //Personal Goal: NO PARAMS
    //Select Tile: riga e colonna (int, int)
}
