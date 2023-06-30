package GC_11;

public class MainApp {
    public static void main(String[] args) {
        if(args.length > 0) {
            ClientApp.main(args);
        }
        else {
            ServerMainApp.main(args);
        }
    }
}
