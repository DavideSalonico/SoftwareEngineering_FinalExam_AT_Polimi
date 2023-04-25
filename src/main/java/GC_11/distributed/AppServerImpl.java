package GC_11.distributed;

import GC_11.network.Lobby;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class AppServerImpl extends UnicastRemoteObject implements GC_11.distributed.socket.AppServer {

    List<Lobby> lobbyList = new ArrayList<Lobby>();

    protected AppServerImpl() throws RemoteException {
    }

    protected AppServerImpl(int port) throws RemoteException {
        super(port);
    }

    protected AppServerImpl(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
    }

    @Override
    public Server connect() throws RemoteException {
        return null;
    }
}
