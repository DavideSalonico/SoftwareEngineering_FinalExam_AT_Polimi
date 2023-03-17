package GC_11.Controller;

import GC_11.View.View;

import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {
    public View view;


    @Override
    public void update(Observable o, Object arg) {
        if (o!= view){
            //scarta
            return;
        }
    }
}
