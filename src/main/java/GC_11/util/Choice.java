package GC_11.util;

import java.io.Serializable;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Choice implements Serializable{
    public Choice(String input) {
        List<String> tmp = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(input, " ");
        while (st.hasMoreTokens()){
            
        }

    }

    public enum Type{
        INSERT_NAME, LOGIN, FIND_MATCH, SEE_COMMONGOAL, SEE_PERSONALGOAL, SELECT_TILE, PICK_COLUMN, CHOOSE_ORDER
    }

    private List<String> params;
    private Type choice;

    public Type getChoice() {
        return choice;
    }

    public List<String> getParams() {
        return params;
    }

    //Parameters
    //Common Goal: 0 o 1 (int)
    //Personal Goal: NO PARAMS
    //Select Tile: riga e colonna (int, int)
}
