package client.scenes;

import client.utils.RefServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class EditParticipantsCtrl {

    private final RefServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private VBox displayParticipants;

    @Inject
    public EditParticipantsCtrl(RefServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @FXML
    public void onCancelClick() {
        System.out.println("Going back to event");
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Revert changes?");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to discard the changes?");
        alert.showAndWait();
//        mainCtrl.showEventOverview("");
        // TODO: open new window
    }

    @FXML
    public void onFinishClick() {
        System.out.println("Complete changes and return to event");
        // TODO: connect to database, open new window
        
    }
}
