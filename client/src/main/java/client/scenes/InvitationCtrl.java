package client.scenes;

import client.utils.ServerUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javax.inject.Inject;

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

    public ServerUtils getServer() {return server;}

    public MainCtrl getMainCtrl() {return mainCtrl;}

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

    /**
     * method to send invite
     * @param event to send invite to
     */
    public void sendInvites(ActionEvent event) {
        String[] email =emailTextField.getText().split("\n");
        for(String e: email){
            System.out.println(e);
        }
    }

    /**
     * method to go back to event page
     */
    public void goBackToEvent(){
        mainCtrl.showEventOverview(123);
    }
}
