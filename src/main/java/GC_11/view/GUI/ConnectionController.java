package GC_11.view.GUI;

import GC_11.ClientApp;
import GC_11.distributed.ClientFactory;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.rmi.UnknownHostException;

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

    String typeOfConnection;
    @FXML
    void chooseRMI(ActionEvent event) {
        typeOfConnection = "RMI";
        addressText.setDisable(false);
        confirmIP.setDisable(false);

    }
    @FXML
    void chooseSocket(ActionEvent event) {
        typeOfConnection = "SOCKET";
        addressText.setDisable(false);
        confirmIP.setDisable(false);
    }

    @FXML
    public void confirmIP(ActionEvent event) {
        try{
            ClientApp.client = ClientFactory.createClient(addressText.getText(), typeOfConnection);
        } catch (UnknownHostException e) {

        } catch (IOException e) {

        }

        //createChoice(addressText.getText());
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
        Platform.setImplicitExit(false);
        Platform.runLater(() -> {
            GUIApplication.mainStage.setScene(new Scene(GUIApplication.lobbyLoad));
        });

    }

    public void setError(String error) {
        Platform.runLater(() -> errorLabel.setText(error));
    }

}