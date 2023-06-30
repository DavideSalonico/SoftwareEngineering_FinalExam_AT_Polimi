package GC_11.view.GUI;

import GC_11.ClientApp;
import GC_11.distributed.ClientFactory;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.rmi.UnknownHostException;


/**
 * Controller for the connection scene.
 */
public class ConnectionController {

    @FXML
    private TextField addressText;

    @FXML
    private Button rmiButton;

    @FXML
    private Button socketButton;

    @FXML
    private Label errorLabel;

    @FXML
    private Button confirmIP;

    private String typeOfConnection;

    /**
     * Sets the type of connection to RMI and enables the address text field and confirm IP button.
     *
     */
    @FXML
    void chooseRMI() {
        typeOfConnection = "SOCKET";
        addressText.setDisable(false);
        confirmIP.setDisable(false);
    }

    /**
     * Sets the type of connection to SOCKET and enables the address text field and confirm IP button.
     *
     */
    @FXML
    void chooseSocket() {
        typeOfConnection = "SOCKET";
        addressText.setDisable(false);
        confirmIP.setDisable(false);
    }

    /**
     * Creates a client with the specified IP address and connection type.
     * Displays an error message if the connection fails.
     *
     */
    @FXML
    public void confirmIP() {
        setError("");
        try{
            ClientApp.client = ClientFactory.createClient(addressText.getText(), typeOfConnection);
        } catch (UnknownHostException e) {
            setError("Unable to connect to the server");
        } catch (IOException e) {
            setError("Unable to connect to the server");
        }

    }

    /**
     * Initializes the connection controller.
     * Disables the address text field and confirm IP button.
     * Clears the error label.
     * Sets up an event listener to clear the address text field when clicked.
     */
    @FXML
    public void initialize() {
        addressText.setDisable(true);
        confirmIP.setDisable(true);

        errorLabel.setText("");

        addressText.setOnMouseClicked(event -> addressText.setText(""));
    }

    /**
     * Changes the scene to the lobby scene.
     */
    public void changeSceneToLobby() {
        Platform.setImplicitExit(false);
        Platform.runLater(() -> GUIApplication.mainStage.setScene(new Scene(GUIApplication.lobbyLoad)));
    }

    /**
     * Sets the error message to be displayed in the error label.
     *
     * @param error the error message
     */
    public void setError(String error) {
        Platform.runLater(() -> errorLabel.setText(error));
    }


}