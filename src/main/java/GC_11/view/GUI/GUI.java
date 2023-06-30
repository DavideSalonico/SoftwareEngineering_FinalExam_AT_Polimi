/**
 * The GUI class manages the graphical user interface (GUI) and receives updates from the server to update the GUI accordingly.
 * It includes references to various controllers and provides methods for setting them, handling user input, and updating the GUI.
 */
package GC_11.view.GUI;

import GC_11.network.message.GameViewMessage;
import GC_11.network.message.LobbyViewMessage;
import GC_11.view.Lobby.LobbyController;
import GC_11.view.View;


public class GUI extends View {
    public static String nickname;
    public static int maxNumber = 0;
    public GUIApplication guiApplication;
    public static GUIController gameController;
    public static LobbyController lobbyController;
    public static ConnectionController connectionController;
    private boolean inGame = false;

    /**
     * Constructor of the GUI class, launches the GUI as a new thread.
     */
    public GUI() {
        super();
        this.guiApplication = new GUIApplication();
    }

    /**
     * Sets the LobbyController in a static variable to be used by the client to update the GUI.
     * @param controller the LobbyController reference
     */
    public static void setLobbyController(LobbyController controller) {
        lobbyController = controller;
    }

    /**
     * Sets the GameController in a static variable to be used by the client to update the GUI.
     * @param controller the GameController reference
     */
    public static void setGameController(GUIController controller) {
        gameController = controller;
    }

    /**
     * Sets whether the client is currently in a game for the first time.
     * @param inGame true if the client is in first access to game, false otherwise
     */
    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    /**
     * Sets the modelView in a static variable to be used by the client to update the GUI.
     * @param modelView the GameViewMessage containing the model view
     */
    public void setModelView(GameViewMessage modelView) {
        this.modelView = modelView;
    }

    /**
     * Displays the GUI based on the received model view.
     */
    @Override
    public void show() {
        if (this.modelView.isError()) {
            gameController.setError(this.modelView.getExceptionMessage());
        } else {
            if (this.modelView.isEndGame()) {
                gameController.showEndGame();
            }
            gameController.setError("");
            gameController.updateView(modelView);
        }
    }

    /**
     * Asks the user to enter a nickname for the game, method called automatically by the client when the server invoke it.
     */
    @Override
    public void askNickname() {
        guiApplication.connectionController.changeSceneToLobby();

        while (this.nickname == null) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Asks the user to enter the maximum number of players for the game, method called automatically by the client when the server invoke it.
     */
    @Override
    public void askMaxNumber() {
        lobbyController.changeToSetNumber();

        while (this.maxNumber == 0) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Asks the user if they want to load an old game, method called automatically by the client when the server invoke it.
     */
    @Override
    public void askLoadGame() {
        this.lobbyController.askLoadOldGame();
    }

    /**
     * Prints the lobby view based on the received LobbyViewMessage.
     * @param lobbyViewMessage the LobbyViewMessage containing the lobby view information
     */
    @Override
    public void printLobby(LobbyViewMessage lobbyViewMessage) {
        this.lobbyController.showPlayers(lobbyViewMessage.getPlayersNames());
    }

    /**
     * Returns the nickname entered by the user.
     * @return the nickname entered by the user
     */
    @Override
    public String getNickname() {
        return this.nickname;
    }

    /**
     * Updates the GUI based on the received model view.
     * @param modelView the GameViewMessage containing the updated model view
     */
    @Override
    public void update(GameViewMessage modelView) {
        if (!this.inGame) {
            this.lobbyController.changeSceneToGame();
            this.gameController.init(modelView);

            setInGame(true);
        } else {
            this.setModelView(modelView);
            show();
        }
    }

    @Override
    public void init() {
    }

    //@Override
    public void notifyDisconnection() {
        this.gameController.playerDisconnected();
    }
}
