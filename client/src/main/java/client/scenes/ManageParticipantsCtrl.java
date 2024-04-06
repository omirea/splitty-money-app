package client.scenes;

import client.Main;
import client.nodes.AddedParticipant;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Debt;
import commons.Event;
import commons.Participant;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static client.Main.locale;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ManageParticipantsCtrl implements Main.LanguageSwitch {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private List<Participant> addedParticipants, editedParticipants, deletedParticipants;
    private Event event;

    @FXML
    private VBox displayParticipants;
    @FXML
    private Label manageParticipantsLabel;

    @FXML
    private Button cancelButton, finishButton, addButton;

    @Inject
    public ManageParticipantsCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        addedParticipants = new ArrayList<>();
        editedParticipants = new ArrayList<>();
        deletedParticipants = new ArrayList<>();
    }

    /**
     * method to cancel action
     */
    public void onCancelClick() {
        System.out.println("Going back to event");
        if (hasConfirmed()) {
            mainCtrl.showEventOverview(event.getInvitationID());
            addedParticipants = new ArrayList<>();
            editedParticipants = new ArrayList<>();
            deletedParticipants = new ArrayList<>();
        }
    }

    private static boolean hasConfirmed() {
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
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    /**
     * method to open event page
     */
    public void onFinishClick() {
        System.out.println("Complete changes and return to event");
        for (Participant participant : addedParticipants) {
            Participant p = server.createParticipant(participant);
            participant.setId(p.getId());
        }
        for (Participant participant : editedParticipants) {
            server.updateParticipant(participant, participant.getId());
        }
        for (Participant participant : deletedParticipants) {
            server.deleteParticipant(participant.getId());
        }
        addedParticipants = new ArrayList<>();
        editedParticipants = new ArrayList<>();
        deletedParticipants = new ArrayList<>();
        mainCtrl.showEventOverview(event.getInvitationID());
    }

    /**
     * method to add a random participant
     */
    public void addRandomParticipant() {
        Participant participant = new Participant(event, new ArrayList<Debt>(),
                new ArrayList<Debt>(), "name", "email",
                "acc holder", "iban", "bic");
        AddedParticipant addedParticipant = new AddedParticipant(participant, this);
        addedParticipants.add(participant);
        HBox hBox = addedParticipant.getNode();
        displayParticipants.getChildren().add(hBox);
    }

    /**
     * method to add participant
     */
    public void showAddParticipant() {
        mainCtrl.showAddParticipant(event.getInvitationID());
    }

    public void showEditParticipant(Participant participant) {
        addedParticipants.remove(participant);
        mainCtrl.showAddParticipant(event.getInvitationID(), participant);
    }

    public void setEvent(String id) {
        event = server.getEventByInvitationId(id);
    }

    public void addAllParticipants() {
        displayParticipants.getChildren().clear();
        List<Participant> pList = server.getParticipantsByInvitationId(event.getInvitationID());
        for (Participant participant : pList) {
            AddedParticipant addedParticipant = new AddedParticipant(participant, this);
            displayParticipants.getChildren().add(addedParticipant.getNode());
        }
    }

    public void addNewParticipant(Participant participant) {
        if (participant == null) return;
        if (participant.getId() != null) {
            addEditedParticipant(participant);
        } else {
            addAddedParticipant(participant);
        }
        AddedParticipant addedParticipant = new AddedParticipant(participant, this);
        displayParticipants.getChildren().add(addedParticipant.getNode());
    }

    public void addAddedParticipant(Participant participant) {
        addedParticipants.add(participant);
    }

    public void addEditedParticipant(Participant participant) {
        editedParticipants.add(participant);
    }

    public void addRemovedParticipant(Participant participant) {
        deletedParticipants.add(participant);
    }

    @Override
    public void LanguageSwitch() {
        manageParticipantsLabel.setText(Main.getLocalizedString("manageParticipants"));
        cancelButton.setText(Main.getLocalizedString("Cancel"));
        finishButton.setText(Main.getLocalizedString("Finish"));
        addButton.setText(Main.getLocalizedString("Add"));
    }

    public ServerUtils getServer() {
        return server;
    }
}
