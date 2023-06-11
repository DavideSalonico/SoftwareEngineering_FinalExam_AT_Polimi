package GC_11.distributed;

import GC_11.controller.Controller;
import GC_11.exceptions.ExceededNumberOfPlayersException;
import GC_11.exceptions.NameAlreadyTakenException;
import GC_11.model.Game;
import GC_11.util.choices.Choice;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerRei extends Remote{
    /**
     * register the client in the server
     * @param client
     * @throws ExceededNumberOfPlayersException
     * @throws NameAlreadyTakenException
     * @throws RemoteException
     */
    void register (ClientRei client) throws ExceededNumberOfPlayersException, NameAlreadyTakenException, RemoteException;

    /**
     * update the model of the game with the choice of the client
     * @param client
     * @param choice
     * @throws RemoteException
     */
    void updateGame (ClientRei client, Choice choice)throws RemoteException;

    /**
     * update the model of the lobby with the choice of the client
     * @param client
     * @param choice
     * @throws RemoteException
     */
    void updateLobby (ClientRei client, Choice choice)throws RemoteException;

    /**
     * notify the clients that the game is updated
     * @throws RemoteException
     */
    public void notifyClients () throws RemoteException;

}
