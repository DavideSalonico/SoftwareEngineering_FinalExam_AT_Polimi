package GC_11.distributed.socket;

import GC_11.ClientApp;
import GC_11.distributed.Client;
import GC_11.network.message.GameViewMessage;
import GC_11.network.message.MessageView;
import GC_11.network.choices.Choice;
import GC_11.view.GameCLI;
import GC_11.view.View;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

public class ClientSock implements PropertyChangeListener, Client {
    String ip;
    int port;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    String graphicInterface;
    private View view;

    private boolean connected;

    /**
     * Constructor for the ClientSock class.
     * @param ip The IP address of the server.
     * @param port The port of the server.
     * @throws IOException
     */

    public ClientSock(String ip, int port) throws IOException, UnknownHostException {

        this.port = port;
        this.ip = ip;

        try {
            System.out.println("Connecting to server on port " + port);
            socket = new Socket(ip, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            connected=true;
            System.out.println("Connection established");
        } catch (UnknownHostException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * Method to notify the server of a choice made by the client.
     * @param choice The choice made by the client.
     */
    public void notifyServer(Choice choice){
        sendChoiceToServer(choice);
    }


    /**
     * Method to send a choice to the server.
     * @param c The choice to be sent.
     */
    public void sendChoiceToServer(Choice c) {
        try {
            out.writeObject(c);
            out.flush();
            out.reset();
        } catch (IOException e) {
            //System.out.println("Error during sending message to server");
            closeConnection();
        }
    }

    /**
     * Method to receive a generic message (MessageView) from the server.
     * As it receives a MessageView, executes it on the client.
     */
    public void receiveFromServer(MessageView message) throws RemoteException {
        receiveGameViewFromServer();
    }


    /**
     * Method to receive a generic message (MessageView) from the server.
     * As it receives a MessageView, executes it on the client.
     */
    public void receiveGameViewFromServer() {
        if (connected){
            try {
                MessageView msg = (MessageView) in.readObject();
                msg.executeOnClient(this);
            } catch (IOException | ClassNotFoundException e) {
                closeConnection();
            }
        }
    }

    /**
     * Thread to open an incoming stream from the server.
     * It is always listening for incoming messages.
     * If the connection is lost, it closes the connection.
     */
    Thread readGameViewThread = new Thread(new Runnable() {
        @Override
        public void run() {
            System.out.println("Running readGameView Thread");
            boolean connectionAvailable = true;
            while (connectionAvailable) {
                try {
                    receiveGameViewFromServer();
                } catch (Exception e) {
                    connectionAvailable = false;
                }
            }
        }
    });



    /**
     * Called whenever a view is sending a choice to the server
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed. Contains the choice
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        sendChoiceToServer((Choice) evt.getNewValue());
    }

    /**
     * Method to start the client.
     * It starts the thread to listen for incoming messages.
     */
    public void startClient() {
        readGameViewThread.start();
    }


    private void closeConnection() {
        if (connected){
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                //System.out.println("Error during closing connection");
            }
            ClientApp.view.notifyDisconnection();
            connected=false;
        }
    }


    public void setView(View view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }

    @Override
    public String getNickname() throws RemoteException {
        return ClientApp.view.getNickname();
    }

    @Override
    public int askMaxNumber() {

        return -1;
    }
}

