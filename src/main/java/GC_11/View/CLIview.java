
package GC_11.View;

import GC_11.util.Observable;
import GC_11.util.Choice;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CLIview /*implements Serializable*/ extends Observable<Choice> implements Runnable  {

    // private final Choice controllerChoice;
    // private final Choice playerChoice;
    // private final Outcome outcome;

    @Override
    public void run(){
        while(true){

            // Controllare tramite view che venga eseguita solo quando è il turno del giocatore giusto

            System.out.println("--- NEW TURN ---");
            Choice choice = getPlayerChoice();
            setChanged();
            notifyObservers(choice);
        }


        // Lancia la visuale e chiede ai player cosa vogliono fare


    }

    // Capire come vogliamo costruire le classi di Choice e di Outcome (output)

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
                System.err.println("Invalid choice: " + input+  " Please retake.");
            }
        }
    }

    /*
    Da implementare quando vuglio essere notificato dal modello e mostrare il cambiamento nella view
    @Override
    public void update(TurnView model, Turn.Event arg) {
        switch (arg) {
            case CPU_CHOICE -> showChoices(model);
            case OUTCOME -> showOutcome(model);
            default -> System.err.println("Ignoring event from " + model + ": " + arg);
        }
    }
    */
}

