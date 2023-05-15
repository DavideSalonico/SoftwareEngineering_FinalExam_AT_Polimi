package GC_11.util;

import GC_11.model.Player;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Choice implements Serializable{

    protected List<String> params;
    protected Type choice;
    protected Player player;

    public enum Type{
        INSERT_NAME,
        LOGIN,
        FIND_MATCH,
        SEE_COMMONGOAL,
        SEE_PERSONALGOAL,
        SELECT_TILE,
        DESELECT_TILE,
        PICK_COLUMN,
        CHOOSE_ORDER,
        RESET_TURN
    }

    public Choice(Player player, String input) throws IllegalArgumentException {
        this.player = player;
        this.params=new ArrayList<String>();

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
            case DESELECT_TILE:
            case SEE_PERSONALGOAL:
            case RESET_TURN:
                if(params.size() != 0) throw new IllegalArgumentException();
                break;
            case SEE_COMMONGOAL:
                if(params.size() != 1) throw new IllegalArgumentException();
                Integer common_checker;
                try{
                    common_checker = Integer.parseInt(params.get(0));
                } catch(NumberFormatException e){
                    throw new InvalidParameterException();
                }
                if(common_checker != 0 && common_checker != 1) throw new InvalidParameterException();
                break;
            case PICK_COLUMN:
                if(params.size() != 1) throw new IllegalArgumentException();
                Integer column_checker;
                try{
                    column_checker = Integer.parseInt(params.get(0));
                } catch(NumberFormatException e){
                    throw new InvalidParameterException();
                }
                if(column_checker < 0 || column_checker >= 5) throw new InvalidParameterException();
                break;
            case INSERT_NAME:
                if(params.size() != 1) throw new IllegalArgumentException();
                //Chosen a Max for name length
                if(params.get(0).length() >= 64) throw new InvalidParameterException();
                break;
            case SELECT_TILE:
                if(params.size() != 2) throw new IllegalArgumentException();
                Integer row_checker, col_checker;
                try{
                    row_checker = Integer.parseInt(params.get(0));
                    col_checker = Integer.parseInt(params.get(1));
                } catch(NumberFormatException e){
                    throw new InvalidParameterException();
                }
                if(row_checker < 0 || row_checker >= 9 || col_checker < 0 || col_checker >= 9 ) throw new InvalidParameterException();
                break;
            case LOGIN:
                if(params.size() != 2) throw new IllegalArgumentException();
                if(params.get(0).length() >= 64) throw new InvalidParameterException();
                //Chosen password minimum length
                if(params.get(1).length() < 8 || params.get(1).length() >= 64) throw new InvalidParameterException();
                break;
            case CHOOSE_ORDER:
                if(params.size() > 3) throw new IllegalArgumentException();
                List<Integer> checkers = new ArrayList<Integer>(params.size());
                for(int i = 0; i < params.size(); i++)
                    checkers.add(-1);
                try{
                    for(String p : params){
                        checkers.set(params.indexOf(p), Integer.parseInt(p));
                    }
                } catch(NumberFormatException e){
                    throw new InvalidParameterException();
                }
                for(Integer checker : checkers)
                    if(checker < 0 || checker > 2) throw new InvalidParameterException();
                break;
            default:
                throw new IllegalArgumentException();
        }
    }


    public Type getChoice() {
        return choice;
    }

    public List<String> getParams() {
        return params;
    }

    public Player getPlayer() {
        return player;
    }
}
