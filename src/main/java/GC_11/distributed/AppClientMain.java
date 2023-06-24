package GC_11.distributed;

import GC_11.distributed.socket.ClientSock;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Objects;
import java.util.Scanner;

public class AppClientMain {

    public static void main(String[] args) throws NotBoundException, RemoteException {
        Scanner inputLine = new Scanner(System.in);
        System.out.println(
                        "███╗   ███╗██╗   ██╗    ███████╗██╗  ██╗███████╗██╗     ███████╗██╗███████╗\n" +
                        "████╗ ████║╚██╗ ██╔╝    ██╔════╝██║  ██║██╔════╝██║     ██╔════╝██║██╔════╝\n" +
                        "██╔████╔██║ ╚████╔╝     ███████╗███████║█████╗  ██║     █████╗  ██║█████╗  \n" +
                        "██║╚██╔╝██║  ╚██╔╝      ╚════██║██╔══██║██╔══╝  ██║     ██╔══╝  ██║██╔══╝  \n" +
                        "██║ ╚═╝ ██║   ██║       ███████║██║  ██║███████╗███████╗██║     ██║███████╗\n" +
                        "╚═╝     ╚═╝   ╚═╝       ╚══════╝╚═╝  ╚═╝╚══════╝╚══════╝╚═╝     ╚═╝╚══════╝\n" +
                        "                                                                           \n");

        Boolean flag = true;
        String choiceNetwork = null;
        String choiceInterface = null;
        while(flag) {
            System.out.println("do you prefer to play RMI or SOCKET?");
            choiceNetwork = inputLine.nextLine();
            if(Objects.equals(choiceNetwork, "RMI") || Objects.equals(choiceNetwork, "SOCKET")){
                flag = false;
            }
        }
        flag=true;
        while(flag) {
            System.out.println("do you prefer to play GUI or CLI?");
            choiceInterface = inputLine.nextLine();
            if (Objects.equals(choiceInterface, "GUI") || Objects.equals(choiceInterface, "CLI")) {
                flag = false;
            }
        }
        if(choiceNetwork.equals("RMI")) {
            serverRMISetup(choiceInterface);
        }
        else{
            serverSOCKETSetup(choiceInterface);
        }
    }

    private static void serverRMISetup(String choiceInterface)throws RemoteException, NotBoundException{
        Scanner inputLine = new Scanner(System.in);
        System.out.println("what's your nickname?");
        String nickname = inputLine.nextLine();
        System.out.println("Inserire indirizzo ip del server: ");
        Scanner s = new Scanner(System.in);
        String serverIp = s.nextLine();
        System.out.println("***** Getting the registry *****\n");
        Registry registry = LocateRegistry.getRegistry(serverIp,1099);
        System.out.println("***** looking up for the server *****\n");
        ServerRMI server = (ServerRMI) registry.lookup("server");
        System.out.println("***** Creating a client rmi implementation *****\n");
        ClientImplRMI client = new ClientImplRMI(server, nickname, choiceInterface);
    }

    private static void  serverSOCKETSetup(String choiceInterface){
        System.out.println("Inserire indirizzo ip del server: ");
        Scanner s = new Scanner(System.in);
        String serverIp = s.nextLine();
        ClientSock client = new ClientSock(serverIp, 4322,choiceInterface);
        client.startClient();
    }

}
