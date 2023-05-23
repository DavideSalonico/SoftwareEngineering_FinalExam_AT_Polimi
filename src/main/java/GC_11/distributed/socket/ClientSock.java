package GC_11.distributed.socket;


import GC_11.distributed.Client;
import GC_11.distributed.ClientRei;
import GC_11.model.GameViewMessage;
import GC_11.model.Player;
import GC_11.network.LobbyViewMessage;
import GC_11.view.View;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

public class ClientSock implements PropertyChangeListener, ClientRei {

    String ip;
    int port;

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

     public ClientSock(String ip, int port){

         this.port = port;
         this.ip = ip;

         try {
             System.out.println("Connecting to server on port " + port);
             socket = new Socket(ip, port);
             out = new ObjectOutputStream(socket.getOutputStream());
             in = new ObjectInputStream(socket.getInputStream());
         } catch (UnknownHostException e) {
             System.out.println("Unknown host");
             e.printStackTrace();
         } catch (IOException e) {
             System.out.println("Error in loading streams");
             e.printStackTrace();
         }
         finally {
                System.out.println("Connection established");
         }

     }

    @Override
    public void updateViewLobby(LobbyViewMessage newView) throws RemoteException {
        try{
            LobbyViewMessage lobbyMessage = (LobbyViewMessage) in.readObject();
        }
        catch (IOException e) {
            System.out.println("Unable to get message from server");
        } catch (ClassNotFoundException e) {
            System.out.println("Class LobbyViewMessage not found");
        }
        finally {
            System.out.println("LobbyViewMessage received");
        }
    }

    @Override
    public void updateViewGame(GameViewMessage newView) throws RemoteException {
        try{
            GameViewMessage gameMessage = (GameViewMessage) in.readObject();
        }
        catch (IOException e) {
            System.out.println("Unable to get message from server");
        } catch (ClassNotFoundException e) {
            System.out.println("Class GameViewMessage not found");
        }
    }



    @Override
    public int askMaxNumber() throws RemoteException {
        return 0;
    }

    @Override
    public String getNickname() throws RemoteException {
        return null;
    }

    @Override
    public void run() throws RemoteException {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
         // Send message to server
    }
}

