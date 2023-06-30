package GC_11;

import GC_11.distributed.Client;
import GC_11.view.GUI.GUI;
import GC_11.view.GameCLI;
import GC_11.view.View;

public class ClientApp {
    public static View view;
    public static Client client;

    public static void main(String[] args) {
        if (args.length > 0) {
            if (args[0].equals("GUI")) {
                view = new GUI();
            } else {
                view = new GameCLI();
                view.init();

            }
        }
    }
}

