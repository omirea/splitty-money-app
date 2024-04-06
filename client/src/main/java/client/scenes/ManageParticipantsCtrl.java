package client.scenes;

import client.Main;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import commons.Participant;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static client.Main.locale;

public class ManageParticipantsCtrl implements Main.LanguageSwitch {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private List<Participant> addedParticipants, editedParticipants, deletedParticipants;

    private ObservableList<Participant> data;
    private Event event;

    @FXML
    private VBox displayParticipants;
    @FXML
    private Label manageParticipantsLabel;
    @FXML
    private TableView<Participant> recentParticipants;
    @FXML
    private TableColumn<Participant, Button> deleteColumn;
    @FXML
    private TableColumn<Participant, Button> editColumn;
    @FXML
    private TableColumn<Participant, String> nameColumn;


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

    @FXML
    public void initialize() {

        nameColumn.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getName()));
        deleteColumn.setCellValueFactory(q -> {
            Button deleteParticipant = new Button();
            ImageView trashCan = new ImageView();
            trashCan.setFitWidth(20);
            trashCan.setFitHeight(20);
            Image trash =new Image(Objects.requireNonNull
                    (getClass().getResourceAsStream("/icons/trash.png")));
            trashCan.setImage(trash);
            deleteParticipant.setGraphic(trashCan);

            deleteParticipant.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(q.getValue().getId()==null) {
                        deleteParticipant.setDisable(true);
                        Alert alert=new Alert(Alert.AlertType.WARNING);
                        switch(locale.getLanguage()) {
                            case "nl":
                                alert.setTitle("Niet-opgeslagen deelnemer");
                                alert.setContentText
                                        ("Klik eerst op de knop Voltooien " +
                                                "om de deelnemer op te slaan");
                                break;
                            case "en":
                                alert.setTitle
                                        ("Unsaved Participant");
                                alert.setContentText
                                        ("First Click The Finish Button To Save The Participant");
                                break;
                            default:
                                break;
                        }
                        alert.setHeaderText(null);
                        alert.showAndWait();
                    }else{
                        deleteParticipantFromDb(q.getValue());
                    }


                }
            });

            return new SimpleObjectProperty<>(deleteParticipant);
        });
        editColumn.setCellValueFactory(q -> {
            Button toParticipant = new Button("Edit");
            //toParticipant.setOnAction(participant -> showParticipant(q.getValue()));
            toParticipant.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    recentParticipants.getItems().remove(q.getValue());
                    showParticipant(q.getValue());
                    recentParticipants.refresh();
                }
            });
            return new SimpleObjectProperty<>(toParticipant);
        });
        recentParticipants.setRowFactory(participant -> {
            TableRow<Participant> row = new TableRow<>();
            row.setOnMouseClicked(q -> {
                if (q.getClickCount() == 2 && (!row.isEmpty())) {
                    Participant participantRow = row.getItem();
                    showParticipant(participantRow);
                }
            });

            return row;
        });
    }

    public void addParticipantToBox(Participant participant) {
        recentParticipants.getItems().add(participant);
    }

    private void showParticipant(Participant participant) {
        mainCtrl.showAddParticipant(event.getInvitationID(), participant);
    }

    private void deleteParticipantFromDb(Participant participant) {
            recentParticipants.getItems().remove(participant);
            server.deleteParticipant(participant.getId());


    }
    private void deleteParticipantFromTable(Participant participant) {
        recentParticipants.getItems().remove(participant);
    }

    public void refresh() {
        var participants = server.getParticipantsByInvitationId(event.getInvitationID());
        data = FXCollections.observableList(participants);
        recentParticipants.setItems(data);
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
    /*
    public void addRandomParticipant() {
        Participant participant = new Participant(event, new ArrayList<Debt>(),
                new ArrayList<Debt>(), "name", "email", "iban", "bic");
        AddedParticipant addedParticipant = new AddedParticipant(participant, this);
        addedParticipants.add(participant);
        HBox hBox = addedParticipant.getNode();
        displayParticipants.getChildren().add(hBox);
    }*/

    /**
     * method to add participant
     */
    public void addAllParticipants() {
        recentParticipants.getItems().clear();
        List<Participant> pList = server.getParticipantsByInvitationId(event.getInvitationID());
        for (Participant participant : pList) {
            addParticipantToBox(participant);
        }
    }

    public void addNewParticipant(Participant participant) {
        if (participant == null) return;
        if (participant.getId() != null) {
            addEditedParticipant(participant);
        } else {
            addAddedParticipant(participant);
        }
        addParticipantToBox(participant);
    }
    public void showAddParticipant() {
        mainCtrl.showAddParticipant(event.getInvitationID());
    }

    public void showEditParticipant(Participant participant) {
        deleteParticipantFromTable(participant);
        mainCtrl.showAddParticipant(event.getInvitationID(), participant);
    }

    public void setEvent(String id) {
        event = server.getEventByInvitationId(id);
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
