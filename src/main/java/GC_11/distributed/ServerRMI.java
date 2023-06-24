package GC_11.distributed;

import GC_11.exceptions.ExceededNumberOfPlayersException;
import GC_11.exceptions.NameAlreadyTakenException;
import GC_11.network.choices.Choice;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerRMI extends Remote {
    /**
     * register the client in the server
     *
     * @param client
     * @throws ExceededNumberOfPlayersException
     * @throws NameAlreadyTakenException
     * @throws RemoteException
     */
    void register(ClientRMI client) throws ExceededNumberOfPlayersException, NameAlreadyTakenException, RemoteException;

    /**
     * update the model of the game with the choice of the client
     *
     * @param client
     * @param choice
     * @throws RemoteException
     */
    void updateGame(ClientRMI client, Choice choice) throws RemoteException;

    /**
     * update the model of the lobby with the choice of the client
     *
     * @param client
     * @param choice
     * @throws RemoteException
     */
    void updateLobby(ClientRMI client, Choice choice) throws RemoteException;

    /**
     * notify the clients that the game is updated
     *
     * @throws RemoteException
     */
    public void notifyClients() throws RemoteException;


}
