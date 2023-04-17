package GC_11.distributed.socket;

import GC_11.distributed.Client;
import GC_11.model.GameView;
import GC_11.util.Choice;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

public class ServerStub {

    String ip;
    int port;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    private Socket socket;

    public ServerStub(String ip, int port){
        this.ip = ip;
        this.port=port;
    }


    public void register(Client client) throws RemoteException{
        try
        {
            this.socket=new Socket(ip,port);
            try{
                this.oos = new ObjectOutputStream(socket.getOutputStream());
            }catch(IOException e){
                throw new RemoteException("Cannot create output stream", e);
            }
            try {
                this.ois = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                throw new RemoteException("Cannot create input stream", e);
            }
        }
        catch(IOException e){
            throw new RemoteException("Unable to connect to the server", e);
        }
    }

    public void update(Client client, Choice arg) throws RemoteException {
        try{
            oos.writeObject(arg);
        }catch(IOException e){
            throw new RemoteException("Cannot receive model view from client");
        }
    }

    public void receive(Client client) throws RemoteException{
        GameView gm;
        try{
            gm = (GameView) ois.readObject();
        }
        catch (IOException e){
            throw new RemoteException("Cannot receive model view from client", e);
        }
        catch(ClassNotFoundException e)
        {
            throw new RemoteException("Cannot deserialize model view from client",e);
        }
        /*
        Turn.Event arg;
        try {
            arg = (Turn.Event) ois.readObject();
        } catch (IOException e) {
            throw new RemoteException("Cannot receive event from client", e);
        } catch (ClassNotFoundException e) {
            throw new RemoteException("Cannot deserialize event from client", e);
        }
        */

        // client.update(gm, arg)
    }

    public void close() throws RemoteException {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RemoteException("Cannot close socket", e);
        }
    }


}
