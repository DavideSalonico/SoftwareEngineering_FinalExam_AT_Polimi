
package GC_11.View;

import GC_11.util.Observable;
import GC_11.util.Choice;
import GC_11.model.Player;
import org.w3c.dom.events.Event;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CLIview /*implements Serializable*/ extends Observable implements Runnable  {

    // private final Choice controllerChoice;
     private Choice playerChoice;
    // private final Outcome outcome;

    /**
     * Every view is bound at only one player, it helps to manage every input that the controller receive
     */
    private Player player;

    public CLIview(Player player) {
        this.player = player;
    }
    public void setPlayerChoice(Choice c){
        this.playerChoice= c;
    }

    @Override
    public void run(){
        while(true){

            System.out.println("--- NEW TURN ---");
            Choice choice = getPlayerChoice();
            setChanged();
            notifyObservers(choice);
        }


    }

    public Choice getPlayerChoice(){

        Scanner s = new Scanner(System.in);
        System.out.println("Options available: ");
        System.out.println( "Signs: " +
                Arrays.stream(Choice.values())
                        .map(Choice::name)
                        .collect(
                                Collectors.joining(",", "[", "]")));
        while (true){
            String input = s.next();
            try {
                return Choice.valueOf(input);
            } catch(IllegalArgumentException e){
                System.err.println("Invalid choice: " + input +  " Please retake.");
            }
        }
    }

    /*
    Da implementare quando voglio essere notificato dal modello e mostrare il cambiamento nella view
    @Override
    public void update(TurnView model, Turn.Event arg) {
        switch (arg) {
            case CPU_CHOICE -> showChoices(model);
            case OUTCOME -> showOutcome(model);
            default -> System.err.println("Ignoring event from " + model + ": " + arg);
        }
    }
    */

    public Player getPlayer(){
        return player;
    }
}

