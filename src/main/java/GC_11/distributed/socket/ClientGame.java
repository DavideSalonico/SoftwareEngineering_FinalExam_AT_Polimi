package GC_11.distributed.socket;


import GC_11.model.Player;
import GC_11.util.Choice;
import GC_11.view.View;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientGame {

    private View view;
    private final String ip;
    private final int port;
    private final Player player;

    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public ClientGame(String ip, int port){
        this.ip=ip;
        this.port=port;
        this.player=new Player();
    }


    public void startClient() throws IOException, ClassNotFoundException {
        Socket socket = new Socket(ip,port);
        System.out.println("---Client---");
        System.out.println("Connection established");
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

        lobbySubscribe();

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
        }

        outputStream.close();
        inputStream.close();
        socket.close();
    }

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

    private String getServerMessage() throws IOException, ClassNotFoundException {
        return (String) inputStream.readObject();
    }

}

