package client.scenes;

import client.Main;
import client.nodes.AddedParticipant;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import static client.Main.locale;

public class ManageParticipantsCtrl implements Main.LanguageSwitch {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    @FXML
    private VBox displayParticipants;
    @FXML
    private Label manageParticipantsLabel;

    @FXML
    private Button cancelButton;

    @FXML
    private Button finishButton;

    @FXML
    private Button addButton;


    @Inject
    public ManageParticipantsCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * method to cancel action
     */
    public void onCancelClick() {
        System.out.println("Going back to event");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        switch(locale.getLanguage()) {
            case "nl":
                alert.setTitle("Aanpassingen terugdraaien");
                alert.setContentText("Weet je zeker dat je de aanpassingen wilt terugdraaien?");
                break;
            case "en":
                alert.setTitle("Revert changes");
                alert.setContentText("Are you sure you want to discard the changes?");
                break;
            default:
                break;
        }
        alert.setHeaderText(null);
        alert.showAndWait();
        // TODO: open new window
    }

    /**
     * method to open event page
     */
    public void onFinishClick() {
        System.out.println("Complete changes and return to event");
        // TODO: connect to database, open new window
    }

    /**
     * method to add a random participant
     */
    public void addRandomParticipant() {
        AddedParticipant addedParticipant = new AddedParticipant();
        HBox hBox = addedParticipant.getNode();
        displayParticipants.getChildren().add(hBox);
    }

    /**
     * method to add participant
     */
    public void addParticipant() {
        // TODO: open new window
    }

    @Override
    public void LanguageSwitch() {
        manageParticipantsLabel.setText(Main.getLocalizedString("manageParticipants"));
        cancelButton.setText(Main.getLocalizedString("Cancel"));
        finishButton.setText(Main.getLocalizedString("Finish"));
        addButton.setText(Main.getLocalizedString("Add"));
    }
}
