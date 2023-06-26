package GC_11.distributed;

import GC_11.distributed.RMI.ClientImplRMI;
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

        System.out.println("Please insert server IP address: ");
        Scanner s = new Scanner(System.in);
        String serverIp = s.nextLine();

        if(choiceNetwork.equals("RMI")) {
            clientRMISetup(choiceInterface, serverIp);
        }
        else{
            clientSOCKETSetup(choiceInterface, serverIp);
        }
    }

    private static void clientRMISetup(String choiceInterface, String serverIp)throws RemoteException, NotBoundException{
        Scanner inputLine = new Scanner(System.in);
        System.out.println("what's your nickname?");
        String nickname = inputLine.nextLine();
        Registry registry = LocateRegistry.getRegistry(serverIp,1099);
        ServerRMI serverRMI = (ServerRMI) registry.lookup("server");
        ClientImplRMI client = new ClientImplRMI(serverRMI, nickname, choiceInterface);
        try {
            serverRMI.register(client);
            client.setServer(serverRMI);
        } catch (RemoteException e) {
            System.err.println("error in the registration: " + e.getCause() + "\n" + e.getMessage() + "\n" + e.getStackTrace() + "\n\n\n" + e.toString());
        }
    }

    private static void  clientSOCKETSetup(String choiceInterface, String serverIp){
        ClientSock client = new ClientSock(serverIp, 4322,choiceInterface);
        client.startClient();
    }
}
