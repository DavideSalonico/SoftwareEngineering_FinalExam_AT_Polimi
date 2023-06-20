package GC_11.util;

import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class PlayerView {
    Text clientNickName;
    Text points;
    ImageView shelf;

    public PlayerView(Text clientNickName, Text points, ImageView shelf) {
        this.clientNickName = clientNickName;
        this.points = points;
        this.shelf = shelf;
    }

    public Text getClientNickName() {
        return clientNickName;
    }

    public Text getPoints() {
        return points;
    }

    public ImageView getShelf() {
        return shelf;
    }
}
