package client.scenes;

import client.Main;
import client.nodes.EmailSenderService;
import client.nodes.SendEmailApplication;
import client.utils.ServerUtils;
import commons.Event;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import static client.Main.locale;

public class InvitationCtrl implements Main.LanguageSwitch{

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

//    @Autowired
//    private EmailSenderService emailSenderService;
    private SendEmailApplication sendEmail;

    Event event;

    @FXML
    private TextArea emailTextField;

    @FXML
    private Label eventName;

    @FXML
    private Label codeLabel;

    @FXML
    private Button copyButton;

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
        this.sendEmail=new SendEmailApplication();
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
    public void sendInvites(ActionEvent event) throws Exception {
        String[] email =emailTextField.getText().split("\n");
        for(String e: email){
            String[] args=new String[2];
            args[0]=e;
            args[1]=codeLabel.getText();
            sendEmail.main(args);
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

    public void copy(ActionEvent event) {
        String invitation=codeLabel.getText();
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(invitation);
        clipboard.setContent(content);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        switch (locale.getLanguage()) {
            case "nl":
                alert.setTitle("Kopiëren succesvol");
                alert.setContentText("Code succesvol gekopieerd");
                break;
            case "en":
                alert.setTitle("Copying Successful");
                alert.setContentText("Code Copied Successfully");
                break;
            default:
                break;
        }
        alert.setHeaderText(null);
        alert.show();
        PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
        pause.setOnFinished(e ->
                alert.hide());
        pause.play();
    }
}
