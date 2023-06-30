package GC_11.view;
//TODO: 1) Implementare COME LA SCELTA DEL GIOCAGORE VA AL SERVER E QUINDI AL CONTROLLER E POI AL GAME
//TODO: 2) cambiare il metodo run con quello aggiornato di dave

import GC_11.ClientApp;
import GC_11.distributed.Client;
import GC_11.distributed.ClientFactory;
import GC_11.exceptions.IllegalMoveException;
import GC_11.model.Message;
import GC_11.model.Player;
import GC_11.model.common.CommonGoalCard;
import GC_11.network.choices.Choice;
import GC_11.network.choices.ChoiceFactory;
import GC_11.network.choices.ChoiceType;
import GC_11.network.message.GameViewMessage;
import GC_11.network.message.LobbyViewMessage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class GameCLI extends View {
    private boolean firstTime = true;
    private Object lock = new Object();
    private boolean alreadyReading = false;


    /**
     * Every view is bound at only one player, it helps to manage every input that the controller receive
     */

    public GameCLI() {
        super();
    }

    /**
     * Initialize the CLI
     */
    public void init() {
        boolean flag = true;
        String choiceNetwork = null;
        Scanner inputLine = new Scanner(System.in);
        System.out.println(
                "███╗   ███╗██╗   ██╗    ███████╗██╗  ██╗███████╗██╗     ███████╗██╗███████╗\n" +
                        "████╗ ████║╚██╗ ██╔╝    ██╔════╝██║  ██║██╔════╝██║     ██╔════╝██║██╔════╝\n" +
                        "██╔████╔██║ ╚████╔╝     ███████╗███████║█████╗  ██║     █████╗  ██║█████╗  \n" +
                        "██║╚██╔╝██║  ╚██╔╝      ╚════██║██╔══██║██╔══╝  ██║     ██╔══╝  ██║██╔══╝  \n" +
                        "██║ ╚═╝ ██║   ██║       ███████║██║  ██║███████╗███████╗██║     ██║███████╗\n" +
                        "╚═╝     ╚═╝   ╚═╝       ╚══════╝╚═╝  ╚═╝╚══════╝╚══════╝╚═╝     ╚═╝╚══════╝\n" +
                        "                                                                           \n");
        while (flag) {
            System.out.println("do you prefer to play RMI or SOCKET?");
            choiceNetwork = inputLine.nextLine();
            if (Objects.equals(choiceNetwork, "RMI") || Objects.equals(choiceNetwork, "SOCKET")) {
                flag = false;
            }
        }
        System.out.println("Please insert server IP address: ");
        Scanner s = new Scanner(System.in);
        String serverIp = s.nextLine();
        try {
            ClientApp.client = ClientFactory.createClient(serverIp, choiceNetwork);
        } catch (IOException e) {
            System.out.println("Unable to connect to server");
        }
    }

    @Override
    public void notifyDisconnection() {
        System.out.println("Game unavailable because server is down or someone disconnected");
    }

    @Override
    public void askNickname() {
        System.out.println("Hi, welcome to MyShelfie. Please insert your nickname: ");
        Scanner s = new Scanner(System.in);
        String nickname = s.nextLine();
        ClientApp.view.setNickname(nickname);
        try {
            ClientApp.client.notifyServer(ChoiceFactory.createChoice(null, "ADD_PLAYER " + nickname));
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
        try {
            parseInt(maxNumber);
        } catch (NumberFormatException e) {
            System.out.println("Please insert a number");
            askMaxNumber();
        } finally {
            try {
                ClientApp.client.notifyServer(ChoiceFactory.createChoice(null, "SET_MAX_NUMBER " + maxNumber));
            } catch (RemoteException | IllegalMoveException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void askLoadGame() {
        System.out.println("Do you want to load a previous game? (yes/no)");
        Scanner s = new Scanner(System.in);
        String answer = s.nextLine();
        try {
            ClientApp.client.notifyServer(ChoiceFactory.createChoice(null, "LOAD_GAME " + answer));
        } catch (RemoteException | IllegalMoveException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printLobby(LobbyViewMessage lobbyViewMessage) {

        if (firstTime) {
            System.out.println("#############################\n\nthe game is about to start !!! \nwaits for other players to join\n\n#############################");
            firstTime = false;
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
        } else {
            //Player current = this.modelView.getCurrentPlayer();
            System.out.println("*****************************************************");

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
            System.out.println("\n\n");
            //Printing Board
            this.modelView.getBoard().print();

            if (!this.modelView.isEndGame()) {
                System.out.println("\n\nIT IS THE TURN OF: " + this.modelView.getCurrentPlayer());
                if (this.modelView.getCurrentPlayer().equals(this.nickname)) {
                    this.printOptions();
                } else
                    System.out.println("\nMake a move: [SHOW_CHAT,SEND_MESSAGE]");
            }
            //Case game is finished
            else {
                System.out.println("\n\nTHE GAME IS FINISHED");
                System.out.println("THE WINNER IS: " + this.modelView.getWinner());
                try {
                    TimeUnit.SECONDS.sleep(20);
                } catch (InterruptedException e) {
                    System.exit(1);
                }
                System.exit(0);
            }
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
        alreadyReading = false;
        System.out.println("Choice made: " + choice.getType());
        try {
            this.sendChoice(choice);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        return choice;
    }

    public void sendChoice(Choice choice) throws RemoteException {
        if (ClientApp.client != null)
            ClientApp.client.notifyServer(choice);
    }

    private void printOptions() {
        System.out.println("\nOptions available: " +
                Arrays.stream(ChoiceType.values())
                        .filter(choice -> choice != ChoiceType.PONG &&
                                choice != ChoiceType.ADD_PLAYER &&
                                choice != ChoiceType.SET_MAX_NUMBER)
                        .map(ChoiceType::name)
                        .collect(
                                Collectors.joining(",", "[", "]")));
    }

    private void printChat() {
        System.out.println("\n\n----------------------------------");
        System.out.println("Main Chat: ");
        for (Message message : this.modelView.getMainChat()) {
            System.out.println(message.getSender() + ": " + message.getText());
        }

        System.out.println("Private Chats: ");
        for (String nickname : this.modelView.getFilteredPvtChats().keySet()) {
            System.out.println("--- " + nickname + " ---");
            for (Message message : this.modelView.getFilteredPvtChats().get(nickname)) {
                System.out.println(message.getSender() + ": " + message.getText());
            }
        }
        System.out.println();
    }
}

