package GC_11.network.choices;

import GC_11.controller.Controller;
import GC_11.distributed.ServerMain;
import GC_11.exceptions.IllegalMoveException;
import GC_11.model.Player;

import java.rmi.RemoteException;
import java.util.List;

public class HeartbeatChoice extends Choice{
    public HeartbeatChoice(Player player, List<String> params, ChoiceType type) throws IllegalMoveException {
        super(player, params, type);
    }

    @Override
    public void executeOnServer(Controller controller) throws RemoteException {
        controller.getServer().isAlive(this.params.get(0));
    }
}
