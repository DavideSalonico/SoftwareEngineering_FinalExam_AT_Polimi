
package GC_11.view;

import GC_11.model.GameView;
import GC_11.model.common.CommonGoalCard;
import GC_11.util.choices.Choice;
import GC_11.model.Player;
import GC_11.util.choices.ChoiceFactory;
import GC_11.util.choices.ChoiceType;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class CLIview extends View /*implements Runnable*/{

    /**
     * Every view is bound at only one player, it helps to manage every input that the controller receive
     */

    public CLIview(Player player) {
        this.player = player;
    }

    public void setModelView(GameView modelView){
        this.modelView = modelView;
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
                Arrays.stream(ChoiceType.values())
                        .map(ChoiceType::name)
                        .collect(
                                Collectors.joining(",", "[", "]")));
        while (true){
            String input = s.nextLine();
            try {
                return ChoiceFactory.createChoice(player, input);
            } catch(IllegalArgumentException e){
                System.err.println("Invalid type: " + input +  " Please retake.");
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

