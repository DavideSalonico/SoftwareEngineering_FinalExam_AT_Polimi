package GC_11.view;

import GC_11.distributed.Client;
import GC_11.distributed.socket.ClientSock;
import GC_11.network.LobbyView;
import GC_11.util.Choice;

import java.util.Scanner;

public class LobbyTUI extends View implements Runnable {

    private LobbyView lobbyView;
    Scanner inputLine;
    Client client;

    public LobbyTUI(Client c){
        this.inputLine = new Scanner(System.in);
        this.client=c;
    }

    @Override
    public void show() {

    }

    @Override
    public Choice getPlayerChoice() {
        return null;
    }

    public void run(){
        printWelcome();
    }

    @Override
    protected void seeCommonGoal(Choice choice) {

    }

    @Override
    protected void seePersonalGoal(Choice choice) {

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
            String input = inputLine.nextLine();
            if (input.equals("1")){
                System.out.println("Inizializzazione del server");
            }
            else {
                System.out.println("Errore, comando non conosciuto");
            }

        }
    }





}
