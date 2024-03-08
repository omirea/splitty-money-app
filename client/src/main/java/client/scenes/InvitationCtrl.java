package client.scenes;
import client.utils.ServerUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javax.inject.Inject;
import java.util.Objects;

public class InvitationCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private TextArea emailTextField;

    @FXML
    private Button sendInvitesButton;

    @FXML
    private Button back;

    @Inject
    public InvitationCtrl(ServerUtils server, MainCtrl mainCtrl){
        this.server=server;
        this.mainCtrl=mainCtrl;
    }

    @FXML
    void sendInvites(ActionEvent event) {
        String[] email =emailTextField.getText().split("\n");
        for(String e: email){
            System.out.println(e);
        }
    }

    public ServerUtils getServer() {
        return server;
    }

    public MainCtrl getMainCtrl() {
        return mainCtrl;
    }

    public TextArea getEmailTextField() {
        return emailTextField;
    }

    public void setEmailTextField(TextArea emailTextField) {
        this.emailTextField = emailTextField;
    }

    public Button getSendInvitesButton() {
        return sendInvitesButton;
    }

    public void setSendInvitesButton(Button sendInvitesButton) {
        this.sendInvitesButton = sendInvitesButton;
    }

    public Button getBack(){return back;}

    public void goBackToEvent(){
        mainCtrl.showEventOverview("123");
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvitationCtrl that = (InvitationCtrl) o;
        return Objects.equals(server, that.server) && Objects.equals(mainCtrl, that.mainCtrl) && Objects.equals(emailTextField, that.emailTextField) && Objects.equals(sendInvitesButton, that.sendInvitesButton);
    }

    @Override
    public int hashCode() {
        return Objects.hash(server, mainCtrl, emailTextField, sendInvitesButton);
    }

}
