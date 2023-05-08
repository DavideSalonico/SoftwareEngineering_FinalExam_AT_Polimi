package GC_11.distributed.socket;

import GC_11.exceptions.ExceededNumberOfPlayersException;
import GC_11.exceptions.NameAlreadyTakenException;
import GC_11.util.Choice;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerClientHandler implements Runnable {

    private final ServerSock server;
    private final Socket clientSocket;

    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public ServerClientHandler(Socket socket, ServerSock server) {
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

        try {
            lobbySetup();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Errore nella creazione della lobby");
        }

        while(true){
            try {
                clientChoice = (Choice) inputStream.readObject();
                System.out.println("Received: "+ clientChoice.getChoice() + "from"+ clientChoice.getPlayer());
            } catch (IOException e) {
                System.err.println("Unable to get choice from client");
                this.closeConnection();
                break;

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

    private void lobbySetup() throws IOException, ClassNotFoundException {
        System.out.println("Lobby setup...");
        if(this.server.getLobby().getPlayers().size()==0) {
            System.out.println("Lobby is empty");
            String reply;
            outputStream.writeObject("Non c'è ancora nessun giocatore nella lobby. Vuoi crearne una?\n[S] Sì\n[N] no");
            outputStream.flush();
            reply = (String) inputStream.readObject();
            System.out.println("Risposta: "+reply);
            if (reply.equals("S") || reply.equals("Si") || reply.equals("Sì") || reply.equals("Y") || reply.equals("Yes") || reply.equals("YES") || reply.equals("s") || reply.equals("si") || reply.equals("sì")) {
                System.out.println("Creazione della lobby");
                int maxPlayers=-1;
                while (maxPlayers <= 1 || maxPlayers >= 5){
                    System.out.println("Inserire numero di giocatori");
                    outputStream.writeObject("Inserire il numero massimo di giocatori");
                    outputStream.flush();
                    reply = (String) inputStream.readObject();
                    maxPlayers = Integer.parseInt(reply);
                    System.out.println("Ricevuto:" + maxPlayers);
                    if (maxPlayers <= 1 || maxPlayers >= 5){
                        outputStream.writeObject("Il numero di giocatori deve essere compreso tra 2 e 4");
                        outputStream.flush();
                    }
                }
                outputStream.writeObject("OK");
                outputStream.flush();
                this.server.getLobby().setMaxPlayers(maxPlayers);
                outputStream.writeObject("Inserisci il tuo nome");
                outputStream.flush();
                String playerName = (String) inputStream.readObject();
                try{
                    this.server.getLobby().addPlayer(playerName);
                    this.server.getLobby().setFisrtPlayer(playerName);
                    System.out.println("Aggiunto " + playerName + " alla lobby");
                    outputStream.writeObject("Sei stato aggiunto alla lobby!");
                    outputStream.flush();
                } catch (ExceededNumberOfPlayersException e) {
                    outputStream.writeObject("Raggiunto il numero massimo di giocatori");
                } catch (NameAlreadyTakenException e) {
                    throw new RuntimeException(e);
                }
                String message = "Lobby creata!\nNumero massimo di giocatori: "+this.server.getLobby().getMaxPlayers() + "\n";
                message = message + "Giocatori nella lobby: \n";
                for (int i=0; i<this.server.getLobby().getPlayers().size();i++){
                    message=message+this.server.getLobby().getPlayers().get(i);
                }

                outputStream.writeObject(message);
                outputStream.flush();
            }
        }
        else{
            System.out.println("Lobby has" + this.server.getLobby().getPlayers().size() + "players");

        }

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