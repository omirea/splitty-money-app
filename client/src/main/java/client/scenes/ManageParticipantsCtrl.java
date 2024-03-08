package client.scenes;

import client.nodes.AddedParticipant;
import client.utils.RefServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ManageParticipantsCtrl {

    private final RefServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private VBox displayParticipants;

    @Inject
    public ManageParticipantsCtrl(RefServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
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
        // TODO: navigate to add/edit participants page
    }
}
