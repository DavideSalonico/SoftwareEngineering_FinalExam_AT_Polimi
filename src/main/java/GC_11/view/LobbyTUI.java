package GC_11.view;

import GC_11.network.LobbyView;

import java.util.Scanner;

public class LobbyTUI implements Runnable{

    private LobbyView lobbyView;
    Scanner inputLine;


    public LobbyTUI(){
        this.inputLine = new Scanner(System.in);
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
            String input = inputLine.nextLine();

        }
    }





}
