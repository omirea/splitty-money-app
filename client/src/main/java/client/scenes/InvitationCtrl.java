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
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import javax.inject.Inject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InvitationCtrl implements Main.LanguageSwitch{


    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private SendEmailApplication sendEmailApplication;

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
        int counter=0;
        for(String e: email){
            if(validateEmail(e)) {
                counter++;
                String[] args = new String[2];
                args[0] = e;
                args[1] = codeLabel.getText();
                sendEmailApplication = new SendEmailApplication();
                sendEmailApplication.main(args);
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
        }
    }

    /**
     * method to check if the email input is valid
     * @return true or false if the email is valid
     */
    public boolean validateEmail(String email){
        String regex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\." +
                "[0-9]{1,3}\\.[0-9]{1,3}\\])" +
                "|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p= Pattern.compile(regex);
        Matcher m= p.matcher(email.trim());
        if(email.equals("")) {
            return true;
        }
        if(m.find() && m.group().equals(email.trim())){
            return true;
        }else{
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle(Main.getLocalizedString("alertValidateEmailTitle"));
            alert.setContentText(Main.getLocalizedString("alertValidateEmailContent"));
            alert.setHeaderText(null);
            alert.showAndWait();
            return false;
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

    public void copy(ActionEvent event) {
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
