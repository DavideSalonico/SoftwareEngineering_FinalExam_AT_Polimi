package GC_11.view;

import GC_11.network.choices.Choice;

public class LobbyCLI extends ViewLobby {


    int maxPlayer = 0;
    boolean flag = true;

    @Override
    public void show() {
        maxPlayer = this.lobbyViewMessage.getMaxPlayers();
        if (flag) {
            int count = 1;
            System.out.println("#############################\n\nthe game is about to start !!! \nthere will be " + maxPlayer + " players!\n");
            for (String p : this.lobbyViewMessage.getPlayersNames()) {
                System.out.println(count + ": " + p);
                count++;
            }
            flag = false;
        } else {
            System.out.println(this.lobbyViewMessage.getPlayersNames().size() + ": " + this.lobbyViewMessage.getPlayersNames().get(this.lobbyViewMessage.getPlayersNames().size() - 1));
        }
    }

    @Override
    public Choice getPlayerChoice() {
        return null;
    }

    public void run() {
        show();
    }

    @Override
    protected void seeCommonGoal(Choice choice) {

    }

    @Override
    protected void seePersonalGoal(Choice choice) {

    }

}
