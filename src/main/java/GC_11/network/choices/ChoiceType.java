package GC_11.network.choices;

import java.util.Scanner;

public enum ChoiceType {
    SELECT_TILE,
    DESELECT_TILE,
    PICK_COLUMN,
    CHOOSE_ORDER,
    SEND_MESSAGE,
    RESET_TURN;

    public static String askParams(String input) {
        Scanner s = new Scanner(System.in);
        String params;
        switch (input){
            case "SELECT_TILE":
                System.out.println("Insert the coordinates of the tile you want to select");
                params = s.nextLine();
                return input + " " + params;
            case "DESELECT_TILE":
                return input;
            case "PICK_COLUMN":
                System.out.println("Insert the column you want to pick");
                params = s.nextLine();
                return input + " " + params;
            case "CHOOSE_ORDER":
                System.out.println("In witch position do you want to reorganize the tiles?");
                params = s.nextLine();
                return input + " " + params;
            case "SEND_MESSAGE":
                System.out.println("Insert the message you want to send");
                params = s.nextLine();
                return input + " " + params;
            case "RESET_TURN":
                return input;
            default:
                return input;
        }
    }
}


