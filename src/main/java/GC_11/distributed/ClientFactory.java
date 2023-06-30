package GC_11.distributed;

import GC_11.ClientApp;
import GC_11.distributed.rmi.ClientImplRMI;
import GC_11.distributed.socket.ClientSock;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientFactory {
    public static Client createClient(String serverIp, String connectionProtocol) {
        if(connectionProtocol.equals("RMI")) {
            try {
                Registry registry = LocateRegistry.getRegistry(serverIp,1099);
                ServerRMI serverRMI = (ServerRMI) registry.lookup("server");
                ClientImplRMI client = new ClientImplRMI(serverRMI);
                ClientApp.client = client;
                serverRMI.register(client);
                return client ;
            } catch (RemoteException | NotBoundException e) {
                throw new RuntimeException(e);
            }
        }
        else if(connectionProtocol.equals("SOCKET")) {
            try{
                ClientSock client = new ClientSock(serverIp, 4322);
                client.startClient();
                return client;
            }
            catch (UnknownHostException e ){

            }
            catch (IOException e) {

            }
        }
        return null;
    }
}