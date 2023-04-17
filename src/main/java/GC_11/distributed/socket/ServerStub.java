package GC_11.distributed.socket;

import GC_11.distributed.Client;

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
        }
        catch(IOException e){

        }
    }
}
