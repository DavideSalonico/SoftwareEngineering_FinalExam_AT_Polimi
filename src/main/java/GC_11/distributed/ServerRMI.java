package GC_11.distributed;

import GC_11.distributed.RMI.ClientImplRMI;
import GC_11.exceptions.ExceededNumberOfPlayersException;
import GC_11.exceptions.NameAlreadyTakenException;
import GC_11.network.choices.Choice;

import java.beans.PropertyChangeEvent;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerRMI extends Remote {
    /**
     * register the client in the server
     *
     * @param client
     * @throws RemoteException
     */
    void register(ClientImplRMI client) throws RemoteException;

    /**
     * update the model of the game with the choice of the client
     *
     * @param client
     * @param choice
     * @throws RemoteException
     */
    void updateGame(Client client, Choice choice) throws RemoteException;

    /**
     * update the model of the lobby with the choice of the client
     *
     * @param client
     * @param choice
     * @throws RemoteException
     */
    void updateLobby(Client client, Choice choice) throws RemoteException;

    /**
     * notify the clients that the game is updated
     *
     * @throws RemoteException
     */
    void notifyClients(PropertyChangeEvent evt) throws RemoteException;
}
