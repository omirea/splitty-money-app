package client.scenes;

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

public class ManageParticipantsCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final List<Participant> addedParticipants;
    private final List<Participant> editedParticipants;
    private final List<Participant> deletedParticipants;
    private Event event;

    @FXML
    private VBox displayParticipants;

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
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Revert changes?");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to discard the changes?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            mainCtrl.showEventOverview(event.getInvitationID());
        }
    }

    /**
     * method to open event page
     */
    public void onFinishClick() {
        System.out.println("Complete changes and return to event");
        for (Participant participant : addedParticipants) {
            server.createParticipant(participant);
        }
        for (Participant participant : editedParticipants) {
            server.updateParticipant(participant, participant.getId());
        }
        for (Participant participant : deletedParticipants) {
            server.deleteParticipant(participant.getId());
        }
        mainCtrl.showEventOverview(event.getInvitationID());
    }

    /**
     * method to add a random participant
     */
    public void addRandomParticipant() {
        Participant participant = new Participant(event, new ArrayList<Debt>(), new ArrayList<Debt>(),
                "name", "email", "iban", "bic");
        AddedParticipant addedParticipant = new AddedParticipant(participant);
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

    public void setEvent(String id) {
        event = server.getEventByInvitationId(id);
    }

//    public void addAllParticipants() {
//        List<Participant> pList = event.getParticipants();
//        for (Participant participant : pList) {
//            AddedParticipant addedParticipant = new AddedParticipant(participant);
//            displayParticipants.getChildren().add(addedParticipant.getNode());
//        }
//    }

    public void addAddedParticipant(Participant participant) {
        addedParticipants.add(participant);
    }

    public void addEditedParticipant(Participant participant) {
        editedParticipants.add(participant);
    }

    public void addRemovedParticipant(Participant participant) {
        deletedParticipants.add(participant);
    }
}
