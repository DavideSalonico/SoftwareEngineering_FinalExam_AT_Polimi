package GC_11.util;

import GC_11.model.Player;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * PlayerView represents the visual representation of a player's information in the game interface.
 * It includes the player's nickname, points, and their shelf grid pane.
 */
public class PlayerView {
    Text clientNickName;
    Text points;
    GridPane shelf;

    /**
     * Constructs a PlayerView object with the specified components.
     *
     * @param clientNickName the Text component displaying the client's nickname
     * @param points         the Text component displaying the client's points
     * @param shelf          the GridPane representing the client's shelf
     */
    public PlayerView(Text clientNickName, Text points, GridPane shelf) {
        this.clientNickName = clientNickName;
        this.points = points;
        this.shelf = shelf;
    }

    /**
     * Initializes the PlayerView with the specified player's information.
     *
     * @param player the player object containing the player's data
     */
    public void initialize(Player player) {
        clientNickName.setText("Player : " + player.getNickname());
        points.setText("Points : " + player.getPoints());

    }

    /**
     * Returns the Text component displaying the client's nickname.
     *
     * @return the Text component displaying the client's nickname
     */
    public Text getClientNickName() {
        return clientNickName;
    }

    /**
     * Returns the Text component displaying the client's points.
     *
     * @return the Text component displaying the client's points
     */
    public Text getPoints() {
        return points;
    }

    /**
     * Returns the GridPane representing the client's shelf.
     *
     * @return the GridPane representing the client's shelf
     */
    public GridPane getShelf() {
        return shelf;
    }
}
