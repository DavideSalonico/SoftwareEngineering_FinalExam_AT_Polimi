package GC_11.distributed.socket;

import GC_11.util.Choice;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

public class ClientSkeleton {

    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public ClientSkeleton(Socket socket) throws RemoteException{
        try{
            this.objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
        }catch(IOException e){
            throw new RemoteException("Cannot create output stream", e);
        }
        try{
            this.objectInputStream=new ObjectInputStream(socket.getInputStream());
        }catch (IOException e){
            throw new RemoteException("Cannot create input stream", e);
        }
    }

    public void sendChoice(Choice choice) throws RemoteException {
        try{
            objectOutputStream.writeObject(choice);
        }catch(IOException e){
            throw new RemoteException("Cannot send choice", e);
        }
    }




}
