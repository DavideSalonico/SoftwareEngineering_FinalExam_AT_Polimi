package GC_11.network.choices;

import java.util.Scanner;

/**
 * ChoiceType is an enumeration representing the types of choices that can be made directly or indirectly by players.
 */
public enum ChoiceType {
    SELECT_TILE,
    DESELECT_TILE,
    PICK_COLUMN,
    CHOOSE_ORDER,
    SEND_MESSAGE,
    RESET_TURN,
    PONG,
    ADD_PLAYER,
    SET_MAX_NUMBER,
    LOAD_GAME,
    SHOW_CHAT;

    /**
     * Asks for additional parameters based on the choice type.
     *
     * @param input the choice type as a string.
     * @return the choice type with the additional parameters.
     */
    public static String askParams(String input) {
        Scanner s = new Scanner(System.in);
        String params;
        switch (input){
            case "SELECT_TILE":
                System.out.println("Insert the coordinates of the tile you want to select");
                params = s.nextLine();
                return input + " " + params;
            case "PICK_COLUMN":
                System.out.println("Insert the column you want to pick");
                params = s.nextLine();
                return input + " " + params;
            case "CHOOSE_ORDER":
                System.out.println("In witch position do you want to reorganize the tiles?");
                params = s.nextLine();
                return input + " " + params;
            case "SEND_MESSAGE":
                System.out.println("Enter the nickname of the player you want to contact, otherwise type \"Everyone\"");
                params = s.nextLine();
                System.out.println("Insert the message you want to send");
                params = params + " " +s.nextLine();
                return input + " " + params;
            default:
                return input;
        }
    }
}


