package GC_11.network.message;

import GC_11.ClientApp;
import GC_11.distributed.Client;

public class AskLoadGame extends MessageView{
    @Override
    public void executeOnClient(Client client) {
        ClientApp.view.askLoadGame();
    }

    @Override
    public MessageView sanitize(String key) {
        return null;
    }
}
