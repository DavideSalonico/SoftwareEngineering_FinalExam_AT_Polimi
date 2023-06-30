package GC_11.distributed;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerRMI extends Remote, Server {
    /**
     * register the client in the server
     *
     * @param client the client to register
     * @throws RemoteException if the client is already registered
     */
    void register(Client client) throws RemoteException;

}
