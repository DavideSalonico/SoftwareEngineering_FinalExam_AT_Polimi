package GC_11.distributed.socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerGame {
    private int port;
    private ServerSocket serverSocket;

    public ServerGame(int port){
        this.port=port;
    }

    public void startServer() throws IOException {
        // Opening TCP port
        serverSocket = new ServerSocket(port);
        System.out.println("Server socket ready on port: "+port);

        // Waiting for connection
        Socket socket = serverSocket.accept();
        System.out.println("Received client connection");

        // For input and output
        Scanner in = new Scanner(socket.getInputStream());
        PrintWriter out = new PrintWriter(socket.getOutputStream());

        while(true){
            String line = in.nextLine();
            if(line.equals("quit")){
                break;
            }else{
                out.println("Received: "+line);
                out.flush();
            }
        }
        System.out.println("Closing socket");
        in.close();
        out.close();
        socket.close();
        serverSocket.close();
    }
}
