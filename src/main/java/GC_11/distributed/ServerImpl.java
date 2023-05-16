package GC_11.distributed;


import GC_11.controller.Controller;
import GC_11.exceptions.*;
import GC_11.model.Game;
import GC_11.util.choices.Choice;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServerImpl extends UnicastRemoteObject implements Server, PropertyChangeListener {

    private Game game;

    private Controller controller;

    List<Client> clients = new ArrayList<Client>();
    Client currentClient = null;
    String response;

    public ServerImpl() throws RemoteException {
        super();
    }

    public ServerImpl(int port) throws RemoteException {
        super(port);
    }

    public ServerImpl(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
    }

    // [MATTIA]: capire bene come comportarsi quando ricevo la registrazione dal server
    @Override
    public void register(Client client) throws RemoteException {
        this.clients.add(client);
    }

    // Got a problem with this, need to check parameters and Choice Class

    @Override
    public void update(Client client, Choice arg)
            throws RemoteException,
            ColumnIndexOutOfBoundsException,
            ExceededNumberOfPlayersException,
            NotEnoughFreeSpacesException,
            NameAlreadyTakenException,
            IllegalMoveException {
        this.controller.update(arg);

        this.currentClient = client;
        System.out.print("Ricevuto da: " + client);
    }

    // Viene chiamato quando il model cambia qualcosa
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
       /* PropertyChangeEvent newEvent = new PropertyChangeEvent(this,"Reply",null,response);
        try {
            currentClient.update(this,newEvent); // For debugging
            for(Client c : clients){
                c.update((GameView) evt.getNewValue());
            }

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        */
    }

}