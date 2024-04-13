package client.scenes;

import client.Main;
import client.nodes.SendEmailApplication;
import client.utils.ServerUtils;
import commons.Event;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.util.Duration;


import javax.inject.Inject;

public class InvitationCtrl implements Main.LanguageSwitch{

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private final SendEmailApplication sendEmail;

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

    /**
     * method to send invite
     */
    public void sendInvites() {
        String[] email =emailTextField.getText().split("\n");
        int counter=0;
        for(String e: email){
            counter++;
            String[] args=new String[2];
            args[0]=e;
            args[1]=codeLabel.getText();
            sendEmail.main(args);
        }
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        if(counter==0){
            alert.setTitle(Main.getLocalizedString("alertNoEmailsTitle"));
            alert.setContentText(Main.getLocalizedString("alertNoEmailsContent"));
            alert.showAndWait();
            return;
        }
        alert.setTitle(Main.getLocalizedString("alertSentEmailTitle"));
        if(counter==1)
            alert.setContentText(Main.getLocalizedString("alertSentEmailContent"));

        alert.showAndWait();
        emailTextField.clear();
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
        mainCtrl.getAnotherStage().close();
    }

    @Override
    public void LanguageSwitch() {
        giveInviteCodeLabel.setText(Main.getLocalizedString("givePeopleInvCode"));
        inviteEmailLabel.setText(Main.getLocalizedString("inviteFollowingPeopleByEmail"));
        back.setText(Main.getLocalizedString("Back"));
        copyButton.setText(Main.getLocalizedString("Copy"));
        sendInvitesButton.setText(Main.getLocalizedString("sendInvites"));
    }

    /**
     * method to copy the invite code
     */
    public void copy() {
        String invitation=codeLabel.getText();
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(invitation);
        clipboard.setContent(content);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(Main.getLocalizedString("alertCopyingTitle"));
        alert.setContentText(Main.getLocalizedString("alertCopyingContent"));
        alert.setHeaderText(null);
        alert.show();
        PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
        pause.setOnFinished(e ->
                alert.hide());
        pause.play();
    }
}
