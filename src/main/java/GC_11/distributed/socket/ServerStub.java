package GC_11.distributed.socket;

import GC_11.exceptions.*;
import GC_11.network.choices.Choice;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

public class ServerStub implements Server {
    String ip;
    int port;

    private Socket clientSock;

    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public ServerStub(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }


    @Override
    public void register(GC_11.distributed.Client client) throws RemoteException {
        try {
            this.clientSock = new Socket(ip, port);
            try {
                this.oos = new ObjectOutputStream(clientSock.getOutputStream());
            } catch (Exception e) {
                throw new RemoteException("Cannot create output stream", e);
            }
            try {
                this.ois = new ObjectInputStream(clientSock.getInputStream());
            } catch (Exception e) {
                throw new RemoteException("Cannot create input stream", e);
            }
        } catch (Exception e) {
            throw new RemoteException("Unable to connect to the server", e);
        }
    }

    @Override
    public void register(Client client) throws RemoteException {
        this.register(client);
    }

    @Override
    public void update(GC_11.distributed.Client client, Choice arg) throws RemoteException, ColumnIndexOutOfBoundsException, ExceededNumberOfPlayersException, NotEnoughFreeSpacesException, NameAlreadyTakenException, IllegalMoveException {
        try {
            oos.writeObject(arg);
        } catch (Exception e) {
            throw new RemoteException("Cannot send event", e);
        }
    }

    public void receive(Client client) throws RemoteException {
        Choice c;
        try {
            c = (Choice) ois.readObject();
        } catch (IOException e) {
            throw new RemoteException("Cannot receive event", e);
        } catch (ClassNotFoundException e) {
            throw new RemoteException("Cannot deserialize model", e);
        }

    }
}
