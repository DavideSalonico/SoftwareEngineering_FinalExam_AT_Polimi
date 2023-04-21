package GC_11.distributed.socket;

import GC_11.distributed.Server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AppServer extends Remote {
    Server connect() throws RemoteException;
}
