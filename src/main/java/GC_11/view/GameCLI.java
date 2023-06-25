package GC_11.view;
//TODO: 1) Implementare COME LA SCELTA DEL GIOCAGORE VA AL SERVER E QUINDI AL CONTROLLER E POI AL GAME
//TODO: 2) cambiare il metodo run con quello aggiornato di dave

import GC_11.distributed.Client;
import GC_11.distributed.socket.ClientSock;
import GC_11.exceptions.IllegalMoveException;
import GC_11.model.Message;
import GC_11.model.Player;
import GC_11.model.common.CommonGoalCard;
import GC_11.network.choices.Choice;
import GC_11.network.choices.ChoiceFactory;
import GC_11.network.choices.ChoiceType;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class GameCLI extends ViewGame {

    // private final Choice controllerChoice;
    private Client client;
    private ClientSock clientSock;

    // private final Outcome outcome;

    /**
     * Every view is bound at only one player, it helps to manage every input that the controller receive
     */

    public GameCLI(String nickname, Client client) {
        super();
        this.nickname = nickname;
        this.client = client;
    }

    public GameCLI(String nickname,ClientSock client) {
        super();
        this.nickname = nickname;
        this.clientSock = client;
    }

    @Override
    public synchronized void run() throws RemoteException {
        Choice choice;
         show();
            System.out.println("\n\nIT IS THE TURN OF: " + this.modelView.getCurrentPlayer());
            if (this.modelView.getCurrentPlayer().equals(this.nickname)) {
                choice = getPlayerChoice();
                System.out.println("scelta fatta");
                this.sendChoice(choice);
            }
            else {
                    this.printChat();
                    Scanner s = new Scanner(System.in);
                    while (true) {
                        System.out.println("Do you want to send a message? (yes/no)");
                        String input = s.nextLine();
                        if(input.equals("yes")) {
                            try {
                                input = ChoiceType.askParams("SEND_MESSAGE");
                                choice = ChoiceFactory.createChoice(this.modelView.getPlayer(this.nickname), input);
                                System.out.println();
                                this.sendChoice(choice);
                                break;
                            } catch (IllegalMoveException e) {
                                System.err.println("Invalid type: " + input + " Please retake." + e.getMessage());
                            }
                        }else if(input.equals("no"))
                            System.out.println("OK!");
                            break;
                    }
            }
    }

    @Override
    public void show() {
        if (this.modelView.isError()) {
            System.out.println(this.modelView.getExceptionMessage());
        }
        else {
            //Player current = this.modelView.getCurrentPlayer();
            System.out.println("*****************************************************");

            //Printing Board
            this.modelView.getBoard().print();

            //Printing CommonGoalCards
            int i = 1;
            for (CommonGoalCard common : this.modelView.getCommonGoalCards()) {
                System.out.println("Common goal " + i + ": " + common.getText());
                i++;
            }

            //Printing Players with relative objects
            for (Player p : this.modelView.getPlayers()) {
                System.out.println("\n\n----------------------------------");
                System.out.println("Player : " + p.getNickname());
                System.out.println("Points from CommonGoalCards: " + p.getPointsCommonGoals());
                System.out.println("Points from PersonalGoalCard: " + p.getPointsPersonalGoal());
                System.out.println("Points from Adjacency: " + p.getPointsAdjacency());
                System.out.println("Total Points: " + p.getPoints() + "\n");
                System.out.println("this is " + p.getNickname() + "'s shelf:");
                p.getShelf().print();
                if (this.nickname.equals(p.getNickname())) {
                    //Printing Personal Goal Card
                    System.out.println("\nPersonal Goal: ");
                    p.getPersonalGoal().print();
                }
            }
        }
    }

    public Choice getPlayerChoice() {

        Scanner s = new Scanner(System.in);
        this.printOptions();
        while (true) {
            String input = s.nextLine();
            input = ChoiceType.askParams(input);
            try {
                if(input.equals("SHOW_CHAT")) {
                    this.printChat();
                    this.printOptions();
                }
                else
                    return ChoiceFactory.createChoice(this.modelView.getPlayer(this.nickname), input);
            } catch (IllegalMoveException e) {
                System.err.println("Invalid type: " + input + " Please retake.");
            }
        }
    }

    public void sendChoice(Choice choice) throws RemoteException {
        PropertyChangeEvent evt = new PropertyChangeEvent(
                this,
                "CHOICE",
                null,
                choice);
        if (this.client!=null)              //TODO: Implementare un'interfaccia client che permetta di chiamare lo stesso metodo sia socket che RMI
            this.client.notifyServer(choice);
        else
            this.clientSock.notifyServer(evt);
    }

    private void printOptions(){
        System.out.println("\nOptions available: " +
                Arrays.stream(ChoiceType.values())
                        .map(ChoiceType::name)
                        .collect(
                                Collectors.joining(",", "[", "]")));
    }
    private void printChat(){
        System.out.println("\n\n----------------------------------");
        System.out.println("Main Chat: ");
        for (Message message : this.modelView.getMainChat()) {
            System.out.println(message.getSender() + ": " + message.getText());
        }

        System.out.print("Private Chats: ");
        for(String nickname : this.modelView.getFilteredPvtChats().keySet() ) {
            System.out.print("--- " + nickname + " ---");
            for(Message message : this.modelView.getFilteredPvtChats().get(nickname)) {
                System.out.println(message.getSender() + ": " + message.getText());
            }
            System.out.println();
        }
    }
}

