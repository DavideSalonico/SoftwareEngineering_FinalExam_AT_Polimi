package GC_11.distributed.socket;

/*
public class ServerStub implements Server {

    String ip;
    int port;

    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    private Socket socket;

    List<Client> clientList = new ArrayList<Client>();

    public ServerStub(String ip, int port){
        this.ip=ip;
        this.port=port;
    }

    @Override
    public void register(Client client) throws RemoteException {
        try{
            this.socket=new Socket(ip,port); // Create new socket

            // Trying to initialize outputStream from the socket
            try{
                this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            }catch(IOException e){
                throw new RemoteException("Cannot create output stream", e);
            }

            // Trying to initialize inputStream from the socket
            try{
                this.objectInputStream = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException("Cannot create input stream",e);
            }
        }catch(IOException e){
            throw new RemoteException("Unable to connect to the server", e);
        }
        finally {
            this.clientList.add(client);
        }

    }

    /**
     * This method get from the client the new choice
     * @param client  the client that generated the event
     * @param arg     the choice made by the client
     * @throws RemoteException

    @Override
    public void update(Client client, Choice arg) throws RemoteException {

    }

    public void receiveChoice(Client client) throws RemoteException {
        Choice choice;
        try{
            choice = (Choice) objectInputStream.readObject();
        } catch (IOException e) {
            throw new RemoteException("Cannot receive model view from client", e);
        } catch (ClassNotFoundException e) {
            throw new RemoteException("Cannot deserialize model view from client", e);
        }
    }


}
*/
