package GC_11.network;

import GC_11.view.LobbyTUI;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AppLobbyTUI implements PropertyChangeListener {

    public static void main(String[] args) {
        LobbyTUI lt = new LobbyTUI();
        lt.run();


    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
