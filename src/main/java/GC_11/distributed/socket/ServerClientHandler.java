package GC_11.distributed.socket;

import GC_11.util.Choice;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerClientHandler implements Runnable {

    private final ServerGame server;
    private final Socket clientSocket;

    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public ServerClientHandler(Socket socket, ServerGame server) {
        this.clientSocket = socket;
        this.server=server;
    }

    @Override
    public void run() {

        // Init phase
        try {
            this.inputStream = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            System.err.println("Unable to get input stream");
        }
        try {
            this.outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Unable to get output stream");
        }
        Choice clientChoice = null;

        while(true){
            try {
                clientChoice = (Choice) inputStream.readObject();
                System.out.println("Received: "+ clientChoice.getChoice() + "from"+ clientChoice.getPlayer());
            } catch (IOException e) {
                System.err.println("Unable to get choice from client");
                this.closeConnection();

            } catch (ClassNotFoundException e) {
                System.err.println("Unable to deserialize choice from client");
            }


            // Elaborazione del dato



            // Risposta
            if (clientChoice != null){
                try{
                    outputStream.writeObject("Risposta a" +clientChoice.getChoice() + "from" + clientChoice.getPlayer() );
                }
                catch(IOException e){
                    System.err.println("Unable to reply to client");
                    closeConnection();
                }
                if (clientChoice.getChoice().equals(Choice.Type.LOGIN)){
                    break;
                }
            }
        }

        this.closeConnection();


    }
    private void closeConnection(){
        System.out.println("Closing socket: "+ clientSocket.getInetAddress()+ ":" + clientSocket.getPort());
        try {
            inputStream.close();
        } catch (IOException e) {
            System.err.println("Unable to close input stream");
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            System.err.println("Unable to close output stream");
        }
        try {
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Unable to close socket");
        }
        this.server.notifyDisconnectionAllSockets(this.clientSocket,this);
    }

    public void notifyDisconnection(Socket socket){
        String alert = "Socket " + socket.getInetAddress() + ":" + socket.getPort() + " has disconnected";
        try{
            outputStream.writeObject(alert);
            outputStream.flush();
        }catch(IOException e){
            System.err.println("Unable to notify socket disconnection");
        }
    }
}
