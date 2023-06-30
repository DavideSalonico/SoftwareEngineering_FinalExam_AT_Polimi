package GC_11.distributed;

public class AppServerMain {
    public static void main(String[] args) {
        ServerMain serverMain = new ServerMain(4322);
        try {
            serverMain.startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
