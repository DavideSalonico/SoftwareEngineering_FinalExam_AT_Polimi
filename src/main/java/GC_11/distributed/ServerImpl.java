package GC_11.distributed;

import GC_11.controller.Controller;
import GC_11.model.Game;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class ServerImpl extends UnicastRemoteObject implements Server{

    private Game game;

    private Controller controller;

    protected ServerImpl() throws RemoteException {
        super();
    }

    protected ServerImpl(int port) throws RemoteException {
        super(port);
    }

    protected ServerImpl(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
    }

    // [MATTIA]: capire bene come comportarsi quando ricevo la registrazione dal server
    @Override
    public void register(Client client) throws RemoteException {

    }

    // Got a problem with this, need to check parameters and Choice Class

    @Override
    public void update(Client client, PropertyChangeEvent arg) throws RemoteException {
        // this.controller.update(client.getView(), arg);
    }

}
