package client.scenes;

import client.Main;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import commons.Participant;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ManageParticipantsCtrl implements Main.LanguageSwitch {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private List<Participant> addedParticipants, editedParticipants, deletedParticipants,
            preUpdatedParticipants;

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
    private Button finishButton, addButton;

    @Inject
    public ManageParticipantsCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        addedParticipants = new ArrayList<>();
        editedParticipants = new ArrayList<>();
        deletedParticipants = new ArrayList<>();
        preUpdatedParticipants = new ArrayList<>();
    }

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getName()));
        deleteColumn.setCellValueFactory(q -> {
            Button deleteParticipant = new Button();
            deleteParticipant.setAlignment(Pos.CENTER);
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
                    server.send("/app/remParticipants",q.getValue());
                    deletedParticipants.add(q.getValue());
                    deleteParticipantFromTable(q.getValue());


                }
            });

            return new SimpleObjectProperty<>(deleteParticipant);
        });
        editColumn.setCellValueFactory(q -> {
            Button toParticipant = new Button(Main.getLocalizedString("Edit"));
            toParticipant.setAlignment(Pos.CENTER);
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

    public void setPreUpdatedParticipants() {
        preUpdatedParticipants = server.getParticipantsByInvitationId(event.getInvitationID());

        server.registerForMessages("/topic/participants", p ->{
            Platform.runLater(() ->{
                addAllParticipants();
                refresh();
            });
        });

        server.registerForMessages("/topic/remParticipants", p ->{
            Platform.runLater(() ->{
                deleteParticipantFromTable(p);
                refresh();
            });
        });
    }

    public void addParticipantToBox(Participant participant) {
        List<Participant> participantsInBox=recentParticipants.getItems();
        if(participantsInBox.contains(participant))
            return;
        Participant p = server.createParticipant(participant);
        participant.setId(p.getId());
        recentParticipants.getItems().add(participant);
        recentParticipants.refresh();
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

//    /**
//     * method to cancel action
//     */
//    public void onCancelClick() {
//        System.out.println("Going back to event");
//        if (hasConfirmed()) {
//            for(Participant participant : addedParticipants) {
//                server.deleteParticipant(participant.getId());
//            }
//            for(Participant participant : preUpdatedParticipants) {
//                server.createParticipant(participant);
//            }
//            for (Participant participant : deletedParticipants) {
//                server.createParticipant(participant);
//            }
//            mainCtrl.showEventOverview(event.getInvitationID());
//            addedParticipants = new ArrayList<>();
//            editedParticipants = new ArrayList<>();
//            deletedParticipants = new ArrayList<>();
//        }
//    }

    private static boolean hasConfirmed() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(Main.getLocalizedString("alertRevertChangesTitle"));
        alert.setContentText(Main.getLocalizedString("alertRevertChangesContent"));
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    /**
     * method to open event page
     */
    public void onFinishClick() {
        System.out.println("Complete changes and return to event");
        /*
        for (Participant participant : addedParticipants) {
            Participant p = server.createParticipant(participant);
            participant.setId(p.getId());
        }
        for (Participant participant : editedParticipants) {
            server.updateParticipant(participant, participant.getId());
        }*/
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
            //server.createParticipant(participant);
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
        //server.createParticipant(participant);
        addedParticipants.add(participant);
    }

    public void addEditedParticipant(Participant participant) {
        server.updateParticipant(participant, participant.getId());
        editedParticipants.add(participant);
    }

    public void addRemovedParticipant(Participant participant) {
        deletedParticipants.add(participant);
    }

    @Override
    public void LanguageSwitch() {
        manageParticipantsLabel.setText(Main.getLocalizedString("manageParticipants"));
//        cancelButton.setText(Main.getLocalizedString("Cancel"));
        addButton.setText(Main.getLocalizedString("Add"));
    }

    public ServerUtils getServer() {
        return server;
    }
}
