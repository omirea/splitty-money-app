package client.scenes;
import client.utils.ServerUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import javax.inject.Inject;
import java.awt.event.ActionEvent;

public class AddEditParticipant {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    @FXML
    private TextField bicTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField ibanTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private Button okButton;
    @FXML
    private Button abortButton;

    @Inject
    public AddEditParticipant(ServerUtils server, MainCtrl mainCtrl){
        this.server=server;
        this.mainCtrl=mainCtrl;
    }
    @FXML
    void abort(ActionEvent event) {

    }

    @FXML
    void ok(ActionEvent event) {

    }


}
