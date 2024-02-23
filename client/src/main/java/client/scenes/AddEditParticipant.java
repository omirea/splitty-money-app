package client.scenes;
import client.utils.ServerUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import javax.inject.Inject;
import java.awt.event.ActionEvent;
import java.util.Objects;

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

    public ServerUtils getServer() {
        return server;
    }

    public MainCtrl getMainCtrl() {
        return mainCtrl;
    }

    public TextField getBicTextField() {
        return bicTextField;
    }

    public void setBicTextField(TextField bicTextField) {
        this.bicTextField = bicTextField;
    }

    public TextField getEmailTextField() {
        return emailTextField;
    }

    public void setEmailTextField(TextField emailTextField) {
        this.emailTextField = emailTextField;
    }

    public TextField getIbanTextField() {
        return ibanTextField;
    }

    public void setIbanTextField(TextField ibanTextField) {
        this.ibanTextField = ibanTextField;
    }

    public TextField getNameTextField() {
        return nameTextField;
    }

    public void setNameTextField(TextField nameTextField) {
        this.nameTextField = nameTextField;
    }

    public Button getOkButton() {
        return okButton;
    }

    public void setOkButton(Button okButton) {
        this.okButton = okButton;
    }

    public Button getAbortButton() {
        return abortButton;
    }

    public void setAbortButton(Button abortButton) {
        this.abortButton = abortButton;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddEditParticipant that = (AddEditParticipant) o;
        return Objects.equals(server, that.server) && Objects.equals(mainCtrl, that.mainCtrl) && Objects.equals(bicTextField, that.bicTextField) && Objects.equals(emailTextField, that.emailTextField) && Objects.equals(ibanTextField, that.ibanTextField) && Objects.equals(nameTextField, that.nameTextField) && Objects.equals(okButton, that.okButton) && Objects.equals(abortButton, that.abortButton);
    }

    @Override
    public int hashCode() {
        return Objects.hash(server, mainCtrl, bicTextField, emailTextField, ibanTextField, nameTextField, okButton, abortButton);
    }
}
