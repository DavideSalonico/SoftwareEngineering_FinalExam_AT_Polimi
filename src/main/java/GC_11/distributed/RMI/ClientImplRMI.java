package GC_11.distributed.rmi;

import GC_11.distributed.Client;
import GC_11.distributed.ServerRMI;
import GC_11.network.message.MessageView;
import GC_11.network.choices.Choice;
import GC_11.view.*;
import GC_11.view.GUI.GUI;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Objects;
import java.util.Scanner;

public class ClientImplRMI extends UnicastRemoteObject implements Client, Serializable {

    private transient View view;
    private String nickname;
    private ServerRMI serverRMI;

    public ClientImplRMI(ServerRMI serverRMI, String nickname, String choiceInterface) throws RemoteException {
        //this.nickname = nickname;
        //System.out.println("HELLO " + nickname + "!!!\n");
        this.serverRMI=serverRMI;
        if(Objects.equals(choiceInterface, "CLI")) {
            this.view = new GameCLI(this.nickname, this);
        }
        else{
            this.view = new GUI();
        }
    }

    public String getNickname() {
        return this.getView().getNickname();
    }

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
        return this.view;
    }

    @Override
    public void notifyDisconnection() throws RemoteException{
        //TODO
    }

    @Override
    public void notifyServer(Choice choice) throws RemoteException {
            new Thread(() -> {
                try {
                    serverRMI.receiveMessage(choice);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
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

