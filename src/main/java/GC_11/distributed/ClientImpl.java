package GC_11.distributed;

import GC_11.model.GameView;
import GC_11.util.Choice;
import GC_11.view.CLIview;
import GC_11.model.Player;
import GC_11.view.View;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

/*
public class ClientImpl extends UnicastRemoteObject implements Client, Runnable, PropertyChangeListener {

    View view;
    Player player;
    Server server;

    /**
     *
     * @param server is the server the client want to register to
     * @param view is the view the client is listening to
     * @throws RemoteException the exception can occur in network layer
     */
/*
    public ClientImpl(Server server, View view) throws RemoteException{
        super();
        this.server=server;
        this.view=view;
        initialize(server);
    }

    // Just for test

    public ClientImpl(Server server) throws RemoteException{
        super();
        this.server=server;
    }

    public ClientImpl(Server server,View view, int port) throws RemoteException {
        super(port);
        this.server=server;
        this.view=view;
        initialize(server);
    }

    public ClientImpl(Server server, View view, int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
        this.server=server;
        this.view=view;
        initialize(server);
    }

    /**
     * During the initialization phase, the client registers itself to the
     * server and set itself as view's listener
     * @param server is the server the client want to register to
     */

/*
    private void initialize(Server server) throws RemoteException{
        server.register(this);
        view.setListener(this);
    }

    /**
     * TODO: to implement by Mattia
     * This method is called when the server update the model and the modelview
     * and need to notify
     * @param gameView The resulting model view
     * @throws RemoteException
     */

/*
    @Override
    public void update(GameView gameView) throws RemoteException {
        PropertyChangeEvent evt = new PropertyChangeEvent(
                this,
                "NewGameView",
                null,
                gameView
        );
        this.view.propertyChange(evt);
    }


    public void update(Server server,PropertyChangeEvent arg) throws RemoteException {
        System.out.print("Ricevuto da:");
        System.out.println(arg.getSource());
        System.out.println(arg.getPropertyName());
        System.out.println(arg.getNewValue());
    }

    /**
     * Clients run a view's instance
     */
/*
    public void run(){ view.run();}

    /**
     * Client is view's listener, when the view notifies the change, client tries
     * to update the server. Otherwise, print an error.
     * Client tries to send the PropertyChangeEvent got from the view to
     * the controller on the server. The controller will parse the event and
     * check the syntax
     * @param evt is the event passed from the view.
     *
     */

/*
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        try{
            server.update(this, (Choice) evt.getNewValue());
        }catch(RemoteException e){
            System.out.println("Unable to update the server");
        }
    }

    public Player getPlayer() {
        return player;
    }

    public View getView() {
        return view;
    }

    public Server getServer() {
        return server;
    }
}

*/
