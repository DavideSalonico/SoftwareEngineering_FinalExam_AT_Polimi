package GC_11.distributed;

import GC_11.distributed.RMI.ClientImplRMI;
import GC_11.distributed.socket.ClientSock;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientFactory {
    public static Client createClient(String serverIp, String connectionProtocol) {
        if(connectionProtocol.equals("RMI")) {
            try {
                Registry registry = LocateRegistry.getRegistry(serverIp,1099);
                ServerRMI serverRMI = (ServerRMI) registry.lookup("server");
                ClientImplRMI client = new ClientImplRMI(serverRMI);
                serverRMI.register(client);
                return client ;
            } catch (RemoteException | NotBoundException e) {
                return null;
            }
        }
        else if(connectionProtocol.equals("SOCKET")) {
            return new ClientSock(serverIp, 4322);
        }
        return null;
    }
}