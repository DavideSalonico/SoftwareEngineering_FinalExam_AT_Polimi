package GC_11.view;
//TODO: 1) Implementare COME LA SCELTA DEL GIOCAGORE VA AL SERVER E QUINDI AL CONTROLLER E POI AL GAME
//TODO: 2) cambiare il metodo run con quello aggiornato di dave

import GC_11.distributed.Client;
import GC_11.distributed.socket.ClientSock;
import GC_11.exceptions.ColumnIndexOutOfBoundsException;
import GC_11.exceptions.IllegalMoveException;
import GC_11.model.Message;
import GC_11.model.Player;
import GC_11.model.common.CommonGoalCard;
import GC_11.network.choices.Choice;
import GC_11.network.choices.ChoiceFactory;
import GC_11.network.choices.ChoiceType;
import GC_11.network.message.GameViewMessage;
import GC_11.network.message.LobbyViewMessage;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class GameCLI extends View {

    private Client client;

    private boolean firstTime = true;
    private Object lock = new Object();
    private boolean alreadyReading = false;


    /**
     * Every view is bound at only one player, it helps to manage every input that the controller receive
     */

    public GameCLI(String nickname, Client client) {
        super();
        this.nickname = nickname;
        this.client = client;
    }

    /*@Override
    public synchronized void run(){
        Choice choice;
         show();
            System.out.println("\n\nIT IS THE TURN OF: " + this.modelView.getCurrentPlayer());
            if (this.modelView.getCurrentPlayer().equals(this.nickname)) {
                choice = getPlayerChoice();
                System.out.println("scelta fatta");
                try {
                    this.sendChoice(choice);
                } catch (RemoteException e) {
                    throw new RuntimeException(e); //TODO: handle this exception
                }
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
                                try {
                                    this.sendChoice(choice);
                                } catch (RemoteException e) {
                                    throw new RuntimeException(e); //TODO: handle this exception
                                }
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

     */
    @Override
    public void askNickname() {
        System.out.println("Hi, welcome to MyShelfie. Please insert your nickname: ");
        Scanner s = new Scanner(System.in);
        String nickname = s.nextLine();
        try {
            this.client.notifyServer(ChoiceFactory.createChoice(null, "ADD_PLAYER " + nickname));
            this.nickname = nickname;
        } catch (RemoteException | IllegalMoveException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askMaxNumber() {
        System.out.println("Please insert the max number of players: ");
        Scanner s = new Scanner(System.in);
        String maxNumber = s.nextLine();
        try{
            int max = parseInt(maxNumber);
        }catch (NumberFormatException e){
            System.out.println("Please insert a number");
            askMaxNumber();
        }finally {
            try {
                this.client.notifyServer(ChoiceFactory.createChoice(null, "SET_MAX_NUMBER " + maxNumber));
            } catch (RemoteException | IllegalMoveException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void printLobby(LobbyViewMessage lobbyViewMessage) {

        if (firstTime) {
            int count = 1;
            System.out.println("#############################\n\nthe game is about to start !!! \nthere will be " + lobbyViewMessage.getMaxPlayers() + " players!\n");
            for (String p : lobbyViewMessage.getPlayersNames()) {
                System.out.println(count + ": " + p);
                count++;
            }
            firstTime = false;
        } else {
            System.out.println(lobbyViewMessage.getPlayersNames().size() + ": " + lobbyViewMessage.getPlayersNames().get(lobbyViewMessage.getPlayersNames().size() - 1));
        }
    }

    @Override
    public String getNickname() {
        return this.nickname;
    }

    public void update(GameViewMessage gameViewMessage) {
        this.modelView = gameViewMessage;
        synchronized (lock) {
            show();
            if (!alreadyReading) {
                alreadyReading = true;
                new Thread(this::getPlayerChoice).start();
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
            System.out.println("\n\nIT IS THE TURN OF: " + this.modelView.getCurrentPlayer());
            if (this.modelView.getCurrentPlayer().equals(this.nickname)) {
                this.printOptions();
            }else
                System.out.println("\nMake a move: [SHOW_CHAT,SEND_MESSAGE]");
        }
    }

    public Choice getPlayerChoice() {

        Scanner s = new Scanner(System.in);
        Boolean flag = true;
        Choice choice = null;
        while (flag) {
            String input = s.nextLine();
            synchronized (lock) {
                input = ChoiceType.askParams(input);
                try {
                    if (input.equals("SHOW_CHAT")) {
                        this.printChat();
                        System.out.println("YOU CAN MAKE ANOTHER MOVE");
                    } else {
                        choice = ChoiceFactory.createChoice(this.modelView.getPlayer(this.nickname), input);
                        flag = false;
                    }
                } catch (IllegalMoveException e) {
                    System.err.println("Invalid type: " + input + " Please retake.");
                }
            }
        }
        alreadyReading= false;
        System.out.println("scelta fatta");
        try {
            this.sendChoice(choice);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        return choice;
    }

    public void sendChoice(Choice choice) throws RemoteException {
        if (this.client!=null)
            this.client.notifyServer(choice);
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

        System.out.println("Private Chats: ");
        for(String nickname : this.modelView.getFilteredPvtChats().keySet() ) {
            System.out.println("--- " + nickname + " ---");
            for(Message message : this.modelView.getFilteredPvtChats().get(nickname)) {
                System.out.println(message.getSender() + ": " + message.getText());
            }
        }
        System.out.println();
    }
}

