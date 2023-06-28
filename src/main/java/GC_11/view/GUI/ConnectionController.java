package GC_11.view.GUI;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
    @FXML
    void chooseRMI(ActionEvent event) {
        createChoice("RMI");
        addressText.setDisable(false);
        confirmIP.setDisable(false);

    }
    @FXML
    void chooseSocket(ActionEvent event) {
        createChoice("SOCKET");
        addressText.setDisable(false);
        confirmIP.setDisable(false);
    }

    @FXML
    public void confirmIP(ActionEvent event) {
        createChoice(addressText.getText());

        // eventuale cambio scena credo
    }

    public void createChoice(String s){

        // SEND to server the string s
    }
    @FXML
    public void initialize() {
        addressText.setDisable(true);
        confirmIP.setDisable(true);

        errorLabel.setText("");

        addressText.setOnMouseClicked(event -> {
            addressText.setText("");
        });
    }


    public void changeSceneToLobby() {
        Platform.runLater(() -> {
            try {
                GUIApplication.mainStage.setScene(new Scene(GUIApplication.lobbyLoad));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void setError(String error) {
        Platform.runLater(() -> errorLabel.setText(error));
    }

}