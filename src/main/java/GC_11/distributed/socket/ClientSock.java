package GC_11.distributed.socket;


import GC_11.distributed.Client;
import GC_11.model.Player;
import GC_11.util.Choice;
import GC_11.view.View;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.Scanner;

public class ClientSock extends Client implements PropertyChangeListener {

    private View view;
    private String ip;
    private int port;
    private final Player player;
    private Socket socket;

    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public ClientSock(){
        this.player=new Player();
    }


    public void startClient() throws IOException, ClassNotFoundException {
        System.out.println("---Client---");
        this.view.run();
        connectionSetup();



        /*lobbySubscribe();

        Scanner commandInput = new Scanner(System.in);
        while(true){
            String command = commandInput.nextLine();
            try{
                if (command.equals("quit")){
                    break;
                }
                else{
                    Choice choiceToSend = new Choice(player,command);
                    System.out.println("Sending choice...");
                    outputStream.writeObject(choiceToSend);
                    outputStream.flush();
                    String response = (String) this.inputStream.readObject();
                    System.out.println("Received " + response);
                }
            }catch (IllegalArgumentException e){
                System.out.println("Errore nel comando");
            }
        }*/

        outputStream.close();
        inputStream.close();
        socket.close();
    }

    /*
    private void lobbySubscribe() {
        try{
            String msg = getServerMessage();
            System.out.println(msg);
            Scanner input = new Scanner(System.in);
            String reply = input.nextLine();
            outputStream.writeObject(reply);
            outputStream.flush();
            msg=getServerMessage();
            System.out.println(msg);
            while(!msg.equals("OK")){
                reply = input.nextLine();
                outputStream.writeObject(reply);
                outputStream.flush();
                msg =(String) inputStream.readObject();
                System.out.println(msg);
                msg =(String) inputStream.readObject();
                System.out.println(msg);
            }
            msg=(String) inputStream.readObject();
            System.out.println(msg);
            reply=input.nextLine();
            outputStream.writeObject(reply);
            outputStream.flush();
            msg=(String) inputStream.readObject();
            System.out.println(msg);

        }
        catch(IOException e){
            System.out.println("Impossibile contattare il server.");
        }
        catch(ClassNotFoundException e){
            System.out.println("Errore nella deserializzazione");
        }
    }
*/
    private Object getServerMessage() throws IOException, ClassNotFoundException {
        return inputStream.readObject();
    }

    /**
     * This method is called by the view. Whenever the view get an input,
     * notify the client with this method. This method, once called, try to send a message to the server
     * @param evt A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.sendMessage(evt.getNewValue());
    }

    @Override
    protected void connectionSetup() {
        try {
            this.socket = new Socket(this.ip, this.port);
        } catch (UnknownHostException e) {
            System.out.println("Unable to reach the server.\n");
        } catch (IOException e) {
            System.out.println("Error during setup phase.\n");
        }
        finally {
            System.out.println("Connection established");
        }

        System.out.println("Initializing output stream...");
        try {
            this.outputStream=new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Got output stream");
        }
        catch (IOException e){
            System.err.println("Cannot get output stream");
            System.err.println(e.getMessage());
        }
        System.out.println("Initializing input stream...");
        try{
            this.inputStream=new ObjectInputStream(socket.getInputStream());
            System.out.println("Got input stream");
        }
        catch (IOException e){
            System.err.println("Cannot get input stream");
            System.err.println(e.getMessage());
        }
    }

    @Override
    protected void lobbySetup() {
        try{
            String msg = (String) inputStream.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void sendMessage(Object o) {
        try{
            this.outputStream.writeObject(o);
        } catch (IOException e) {
            System.out.println("Unable to send message.\n");
        }
    }
}

