package GC_11.view;

import GC_11.distributed.Client;
import GC_11.network.LobbyViewMessage;
import GC_11.util.Choice;

import java.beans.PropertyChangeEvent;
import java.util.Scanner;

public class LobbyTUI extends View {

    private LobbyViewMessage lobbyView;
    Scanner inputLine;
    Client client;

    public LobbyTUI(Client c){
        this.inputLine = new Scanner(System.in);
        this.client=c;
    }

    public LobbyTUI(){
        this.inputLine=new Scanner(System.in);
    }

    public void setClient (Client c){
        this.client=c;
    }

    @Override
    public void show() {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }


    public Choice getPlayerChoice() {
        while(true){
            String msg = inputLine.nextLine();
            PropertyChangeEvent evt = new PropertyChangeEvent(
                    this,
                    "message",
                    null,
                    msg);
            this.client.propertyChange(evt);
        }
    }

    public void run(){
        printWelcome();
    }



    public void printWelcome(){

        System.out.println("Benvenuto su MyShelf. Come desideri proseguire?\n1 - Connettiti al server");
        String userCommand = inputLine.nextLine();
        evaluateUserCommand(userCommand);

    }


    private void evaluateUserCommand(String command){
        if(command.equals("1")){
            System.out.println("Inserire indirizzo ip del server:\n");
            String ip = inputLine.nextLine();
            System.out.println("Vuoi utilizzare RMI o socket?\n1 - Socket\n2 - RMI [Attualmente non disponibile]\n");
            String netMod = inputLine.nextLine();

            switch(netMod){
                case "1" :
                    //Crea client Socket
                    break;
                case "2":
                    //Crea client RMI
                    break;
                default:
                    System.out.println("Operazione non gestita. Riprovare");
            }
        }
    }
}
