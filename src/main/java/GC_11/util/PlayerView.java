package GC_11.util;

import GC_11.model.Player;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class PlayerView {
    Text clientNickName;
    Text points;
    GridPane shelf;

    public PlayerView(Text clientNickName, Text points, GridPane shelf) {
        this.clientNickName = clientNickName;
        this.points = points;
        this.shelf = shelf;
    }

    public void initialize(Player player){
        clientNickName.setText("Player : " + player.getNickname());
        points.setText("Points : " + player.getPoints());

    }
    public Text getClientNickName() {
        return clientNickName;
    }

    public Text getPoints() {
        return points;
    }

    public GridPane getShelf() {
        return shelf;
    }
}
