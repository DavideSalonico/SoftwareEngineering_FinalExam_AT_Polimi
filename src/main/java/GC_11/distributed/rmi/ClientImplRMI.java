package GC_11.distributed.rmi;

import GC_11.ClientApp;
import GC_11.distributed.Client;
import GC_11.distributed.ServerRMI;
import GC_11.network.choices.Choice;
import GC_11.network.message.GameViewMessage;
import GC_11.network.message.MessageView;
import GC_11.view.View;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ClientImplRMI extends UnicastRemoteObject implements Client, Serializable {

    private transient View view;

    private String nickname;
    private ServerRMI serverRMI;

    /**
     * Constructor for the ClientImplRMI class.
     * @param serverRMI The server instance.
     * @throws RemoteException If the remote object cannot be created.
     */
    public ClientImplRMI(ServerRMI serverRMI) throws RemoteException {
        this.serverRMI=serverRMI;
        view=ClientApp.view;
    }

    /**
     * Method called when to ask the first player to insert the number of players.
     * @return The number of players.
     */
    public int askMaxNumber() {
        boolean b = true;
        int number = 0;
        while (b) {
            Scanner inputLine = new Scanner(System.in);
            System.out.println("How many players do you want to play with ?");
            number = inputLine.nextInt();

            if (number < 5 && number > 1) {
                b = false;
            } else {
                System.out.println("matches only go from 2 to 4 players");
            }
        }
        return number;
    }

    @Override
    public View getView() {
        return ClientApp.view;
    }

    @Override
    public String getNickname() throws RemoteException {
        return ClientApp.view.getNickname();
    }

    @Override
    public void notifyDisconnection() throws RemoteException{
        //TODO
    }

    @Override
    public void startClient() throws RemoteException {

    }

    @Override
    public void notifyServer(Choice choice) throws RemoteException {
            new Thread(() -> {
                try {
                    serverRMI.receiveMessage(choice);
                } catch (RemoteException e) {
                    ClientApp.view.notifyDisconnection();
                }
            }).start();
    }
    @Override
    public void receiveFromServer(MessageView message) throws RemoteException {
        message.executeOnClient(this);
    }

    public void setServer(ServerRMI serverRMI) {
        this.serverRMI = serverRMI;
    }
}

