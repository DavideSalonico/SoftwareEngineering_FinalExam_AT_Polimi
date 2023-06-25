package GC_11.distributed;

import GC_11.controller.Controller;
import GC_11.exceptions.*;
import GC_11.model.Game;
import GC_11.network.GameViewMessage;
import GC_11.model.Lobby;
import GC_11.network.LobbyViewMessage;
import GC_11.network.choices.Choice;

import java.beans.PropertyChangeEvent;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;

public class ServerImplRMI extends UnicastRemoteObject implements ServerRMI {

    private Controller gameController;

    private Game gameModel;

    private Lobby lobbyModel;

    private int maxPlayer;
    private List<ClientRMI> clients = new ArrayList<>();

    private ServerMain serverMain;

    public ServerImplRMI(ServerMain serverMain) throws RemoteException {
        super();
        this.serverMain = serverMain;
    }

    public List<ClientRMI> getClients() {
        return this.clients;
    }

    public void setup() {
        try {

            String serverIp = "127.0.0.1";
            try {
                Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

                while (networkInterfaces.hasMoreElements()) {
                    NetworkInterface networkInterface = networkInterfaces.nextElement();
                    Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress inetAddress = inetAddresses.nextElement();
                        if(!inetAddress.getHostAddress().startsWith("f") && !inetAddress.isLoopbackAddress()){
                            serverIp=inetAddress.getHostAddress();
                        }
                    }
                }
            } catch (SocketException e) {
                e.printStackTrace();
            }

            System.setProperty("java.rmi.server.hostname", serverIp);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("server", this);
            System.out.println("SERVER RMI RUNNING");
            System.out.println("Server address: " + serverIp);
        } catch (Exception e) {
            System.err.println("server error: " + e.getMessage());
        }
    }

    @Override
    public synchronized void register(ClientRMI client) throws ExceededNumberOfPlayersException, NameAlreadyTakenException, RemoteException {
        clients.add(client);
        serverMain.addConnection(client.getNickname(), "RMI");
    }

    @Override
    public void updateGame(ClientRMI client, Choice choice) throws RemoteException{
        //PropertyChangeEvent evt = new PropertyChangeEvent(client, "choice made", null, choice);
        System.out.println(client.getNickname() + ": " + choice.getType());
        //this.gameController.propertyChange(evt);
        this.serverMain.makeAMove(choice);
    }

    @Override
    public void updateLobby(ClientRMI client, Choice choice) throws RemoteException {
        PropertyChangeEvent evt = new PropertyChangeEvent(client, "choice made", null, choice);
        this.gameController.propertyChange(evt);
    }

    public synchronized void notifyClients(PropertyChangeEvent evt) {
        for (ClientRMI c : clients) {
            new Thread(() -> {
                try {
                    c.updateViewGame(new GameViewMessage(gameModel, null, evt));
                    System.out.println(c.getNickname() + " aggiornato GAME correctly");
                } catch (RemoteException e) {
                    System.out.println(e.getMessage());
                }
            }).start();

        }
        System.out.println("\n");
    }

    public synchronized void notifyClientsLobby(LobbyViewMessage lobbyViewMessage) throws RemoteException {
        for (ClientRMI c : clients) {
            new Thread(() -> {
                try {
                    c.updateViewLobby(lobbyViewMessage);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }).start();
            System.out.println(c.getNickname() + " aggiornato");
        }
        System.out.println("\n");
    }

    public void notifyDisconnection(String nickname, GameViewMessage msg) throws RemoteException {
        for (ClientRMI c : clients) {
            if (!c.getNickname().equals(nickname)) {
                new Thread(() -> {
                    try {
                        c.updateViewGame(msg);
                        System.out.println(c.getNickname() + " aggiornato GAME correctly");
                    } catch (RemoteException e) {
                        System.out.println(e.getMessage());
                    }
                }).start();
                System.out.println("\n");
            }

        }
    }

    public synchronized void notifyClient(String nickname, GameViewMessage gameViewMessage) throws RemoteException {
        for (ClientRMI c : clients) {
            if (c.getNickname().equals(nickname)) {
                new Thread(() -> {
                    try {
                        c.updateViewGame(gameViewMessage);
                        System.out.println(c.getNickname() + " aggiornato GAME correctly");
                    } catch (RemoteException e) {
                        System.out.println(e.getMessage());
                    }
                }).start();
                System.out.println("\n");
            }
        }
    }

}

