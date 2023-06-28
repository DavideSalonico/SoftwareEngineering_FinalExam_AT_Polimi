package GC_11;

import GC_11.distributed.Client;
import GC_11.view.GUI.GUI;
import GC_11.view.View;
import javafx.application.Application;
import javafx.stage.Stage;

import java.rmi.RemoteException;

public class ClientApp  {
    public static View view;
    public static Client client;
    public static void main (String[] args) throws RemoteException {
        view = new GUI();
    }

}
