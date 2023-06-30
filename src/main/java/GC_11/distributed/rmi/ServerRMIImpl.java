package GC_11.distributed.rmi;

import GC_11.ClientApp;
import GC_11.distributed.Client;
import GC_11.distributed.ServerMain;
import GC_11.distributed.ServerRMI;
import GC_11.model.Game;
import GC_11.network.choices.Choice;
import GC_11.network.message.*;

import java.beans.PropertyChangeEvent;
import java.io.Serializable;
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

public class ServerRMIImpl extends UnicastRemoteObject implements ServerRMI, Serializable {

    private Game gameModel;
    private List<Client> clients = new ArrayList<>();

    private ServerMain serverMain;

    /**
     * Constructor of the class ServerRMIImpl
     * @param serverMain the ServerMain bound to this ServerRMIImpl
     * @throws RemoteException if the remote operation fails
     */
    public ServerRMIImpl(ServerMain serverMain) throws RemoteException {
        super();
        this.serverMain = serverMain;
    }

    public List<Client> getClients() {
        return this.clients;
    }

    /**
     * Method that sets up the RMI server
     */
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
                        if(!isIPV6(inetAddress.getHostAddress()) && !inetAddress.isLoopbackAddress()){
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
            System.out.println("ServerRMI address: " + serverIp);
        } catch (Exception e) {
            System.err.println("server error: " + e.getMessage());
        }
    }

    @Override
    public synchronized void register(Client client) throws RemoteException {
        client.receiveFromServer(new NicknameMessage());
        clients.add(client);
        serverMain.addConnection(client.getNickname(), this);

    }

    public synchronized void notifyClients(PropertyChangeEvent evt) {
        for (Client c : clients) {
            new Thread(() -> {
                try {
                    c.receiveFromServer(new GameViewMessage(gameModel, null));
                    System.out.println(c.getNickname() + " aggiornato GAME correctly");
                } catch (RemoteException e) {
                    System.out.println(e.getMessage());
                }
            }).start();

        }
        System.out.println("\n");
    }

    /**
     * Method that notifies the client with the given nickname
     * @param nickname the nickname of the client to notify
     * @param messageView the message to send to the client
     * @throws RemoteException if the remote operation fails
     */
    public synchronized void notifyClient(String nickname, MessageView messageView) throws RemoteException {
        for (Client c : clients) {
            if (c.getNickname().equals(nickname)) {
                new Thread(() -> {
                    try {
                        c.receiveFromServer(messageView);
                        System.out.println(c.getNickname() + " aggiornato GAME correctly");
                    } catch (RemoteException e) {
                        //System.out.println(e.getMessage());
                        this.serverMain.removeConnection(nickname);
                    }
                }).start();
                System.out.println("\n");
            }
        }
    }

    private boolean isIPV6(String ip){
        return ip.contains(":");
    }

    @Override
    public void receiveMessage(Choice choice) throws RemoteException{
        this.serverMain.makeAMove(choice);
    }

    @Override
    public void sendMessage(MessageView msg, String nickname) throws RemoteException{
        try {
            this.notifyClient(nickname,msg);
        } catch (RemoteException e) {
            this.serverMain.removeConnection(nickname);
        }
    }

    @Override
    public void askMaxNumber() throws RemoteException { // TODO
        this.clients.get(0).receiveFromServer(new MaxNumberMessage());
    }

    @Override
    public void notifyDisconnectionToClients() {

    }

    @Override
    public void sendHeartbeat() {

    }
}

