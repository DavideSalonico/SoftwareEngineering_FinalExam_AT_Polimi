//TODO: 1) Implementare COME LA SCELTA DEL GIOCAGORE VA AL SERVER E QUINDI AL CONTROLLER E POI AL GAME
package GC_11.view;

import GC_11.model.GameViewMessage;
import GC_11.model.common.CommonGoalCard;
import GC_11.util.Choice;
import GC_11.model.Player;

import java.beans.PropertyChangeEvent;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class GameCLI extends ViewGame {

    // private final Choice controllerChoice;
     private Choice playerChoice;

    // private final Outcome outcome;

    /**
     * Every view is bound at only one player, it helps to manage every input that the controller receive
     */

    public GameCLI(Player player) {
        this.player = player;
    }
    public void setPlayerChoice(Choice c){
        this.playerChoice = c;
    }

    public void setModelView(GameViewMessage modelView){
        this.modelView = modelView;
    }

    @Override
    public void run(){
        boolean show_en = true;
        while(inGame){
            if(show_en) show();
            Choice choice = getPlayerChoice();
            System.out.println("scelta fatta");
            switch (choice.getChoice()){
                //Controls already made in the creation of choice client-side
                case SEE_COMMONGOAL -> {
                    seeCommonGoal(choice);
                    show_en = false;
                }
                case SEE_PERSONALGOAL -> {
                    seePersonalGoal(choice);
                    show_en = false;
                }
                default -> {
                    PropertyChangeEvent evt = new PropertyChangeEvent(
                            this,
                            "CHOICE",
                            null,
                            choice);
                    this.listener.propertyChange(evt);
                    show_en = true;
                }
            }
        }
    }

    @Override
    public void show(){
        if(this.modelView.isError()){
            System.out.println(this.modelView.getExceptionMessage());
            return;
        }

        Player current = this.modelView.getCurrentPlayer();
        System.out.println("*****************************************************");

        //Printing Board
        this.modelView.getBoard().print();

        //Printing CommonGoalCards
        for(CommonGoalCard common : this.modelView.getCommonGoalCards()){
            System.out.println(common.getText());
        }

        //Printing Players with relative objects
        for(Player p : this.modelView.getPlayers()){
            System.out.println("Player : " + p.getNickname());
            p.getShelf().print();
            if(p.equals(current)){
                //Printing Personal Goal Card
                if(current.getPersonalGoal() == null) System.out.println("Null personal goal card");
                else current.getPersonalGoal().print();
            }
        }
    }

    public Choice getPlayerChoice(){

        Scanner s = new Scanner(System.in);
        System.out.println("Options available: ");
        System.out.println( "Signs: " +
                Arrays.stream(Choice.Type.values())
                        .map(Choice.Type::name)
                        .collect(
                                Collectors.joining(",", "[", "]")));
        while (true){
            String input = s.nextLine();
            try {
                return new Choice(this.player, input);
            } catch(IllegalArgumentException e){
                System.err.println("Invalid choice: " + input +  " Please retake.");
            }
        }
    }

    // 0-BASED INDEXING !!!
    @Override
    protected void seeCommonGoal(Choice choice) {
        int index = parseInt(choice.getParams().get(0));
        System.out.println(this.modelView.getCommonGoalCard(index).getText());
    }

    @Override
    protected void seePersonalGoal(Choice choice) {
        if(choice.getPlayer().getPersonalGoal() != null) choice.getPlayer().getPersonalGoal().print();
        else System.out.println("Null PersonalGoalCard");
    }
}

