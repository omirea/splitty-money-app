package client.scenes;

import client.Main;
import client.utils.ServerUtils;
import commons.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;

import javax.inject.Inject;

public class InvitationCtrl implements Main.LanguageSwitch{

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    Event event;

    @FXML
    private TextArea emailTextField;

    @FXML
    private Label eventName;

    @FXML
    private Label codeLabel;

    @FXML
    private Button sendInvitesButton;

    @FXML
    private Button back;

    @FXML
    private Label giveInviteCodeLabel;

    @FXML
    private Label inviteEmailLabel;


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

    public void setEvent(String id) {
        event = server.getEventByInvitationId(id);
        codeLabel.setText(event.getInvitationID());
        eventName.setText(event.getName());
    }


    /**
     * method to go back to event page
     */
    public void goBackToEvent(){
        mainCtrl.showEventOverview(event.getInvitationID());
    }

    @Override
    public void LanguageSwitch() {
        giveInviteCodeLabel.setText(Main.getLocalizedString("givePeopleInvCode"));
        inviteEmailLabel.setText(Main.getLocalizedString("inviteFollowingPeopleByEmail"));
        back.setText(Main.getLocalizedString("Back"));
        sendInvitesButton.setText(Main.getLocalizedString("sendInvites"));
    }

    public void copy(MouseEvent mouseEvent) {
        String invitation=codeLabel.getText();
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(invitation);
        clipboard.setContent(content);
    }
}
