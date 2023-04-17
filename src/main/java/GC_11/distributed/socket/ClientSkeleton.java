package GC_11.distributed.socket;

import GC_11.distributed.Client;
import GC_11.model.GameView;
import GC_11.util.Choice;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

public class ClientSkeleton implements Client {

    private final ObjectOutputStream oos;

    private final ObjectInputStream ois;

    public ClientSkeleton(Socket socket) throws RemoteException{
        try{

            // Get Output stream from the socket
            this.oos = new ObjectOutputStream(socket.getOutputStream());
        }catch(IOException e)
        {
            throw new RemoteException("Cannote create output stream", e);
        }
        try {
            // Get Input stream from the socket
            this.ois = new ObjectInputStream(socket.getInputStream());
        }
        catch (IOException e)
        {
            throw new RemoteException("Cannote create input stream", e);
        }
    }

    @Override
    public void update(GameView gameView, Choice arg) throws RemoteException {
        try
        {
            oos.writeObject(gameView);
        }
        catch(IOException e)
        {
            throw new RemoteException("Cannot send model view", e);
        }
        try
        {
            oos.writeObject(arg);
        }
        catch(IOException e)
        {
            throw new RemoteException("Cannot send event", e);
        }
    }




}
