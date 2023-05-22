package GC_11.view;

import GC_11.model.GameViewMessage;
import GC_11.util.Choice;

import java.beans.PropertyChangeEvent;

public abstract class ViewGame extends View {

    public abstract Choice getPlayerChoice();

    protected GameViewMessage modelView;

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //System.out.println("evt" + evt);
        System.out.println("Event Type: " + evt.getPropertyName());
        this.modelView = (GameViewMessage) evt.getNewValue();
        run();
    }



    public void run(){
        boolean show_en = true;
        while(inGame){
            if(show_en) show();
            Choice choice = getPlayerChoice();
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

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    protected abstract void seeCommonGoal(Choice choice);

    protected abstract void seePersonalGoal(Choice choice);
}
