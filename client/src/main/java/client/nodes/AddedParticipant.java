package client.nodes;

import client.scenes.ManageParticipantsCtrl;
import commons.Participant;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class AddedParticipant {
    private HBox hbox;
    private Text name;
    private Participant participant;
    private Button rename;
    private Button save;
    private Button remove;
    private Button edit;
    ManageParticipantsCtrl manageParticipantsCtrl;

    public AddedParticipant(Participant participant,
                            ManageParticipantsCtrl manageParticipantsCtrl) {
        this.participant = participant;
        name = new Text(participant.getName());

        rename = new Button("rename");
        rename.setOnAction(e -> renameParticipant());

        remove = new Button("delete");
        remove.setOnAction(e -> removeParticipant());

        save = new Button("save");
        save.setOnAction(e -> saveParticipant());

        edit = new Button("edit");
        edit.setOnAction(e -> editParticipant());

        hbox = new HBox();
        hbox.getChildren().add(name);
        hbox.getChildren().add(rename);
        hbox.getChildren().add(edit);
        hbox.getChildren().add(remove);

        this.manageParticipantsCtrl = manageParticipantsCtrl;
    }

    public HBox getNode(){
        return hbox;
    }

    private void removeParticipant() {
        VBox parent = (VBox) hbox.getParent();
        parent.getChildren().remove(hbox);
        manageParticipantsCtrl.addRemovedParticipant(participant);
    }

    private void renameParticipant() {
        System.out.println("Renaming participant");

        Node node = hbox.getChildren().removeFirst();
        name = (Text) node;
        TextField field = new TextField(name.getText());
        hbox.getChildren().add(0, field);

        hbox.getChildren().remove(1);
        hbox.getChildren().add(1, save);
    }

    private void saveParticipant() {
        System.out.println("Saving changes to participant");

        Node node = hbox.getChildren().removeFirst();
        TextField field = (TextField) node;
        name = new Text(field.getText());
        hbox.getChildren().add(0, name);

        hbox.getChildren().remove(1);
        hbox.getChildren().add(1, rename);

        participant.setName(name.getText());
        manageParticipantsCtrl.addEditedParticipant(participant);
    }

    private void editParticipant() {
        VBox parent = (VBox) hbox.getParent();
        parent.getChildren().remove(hbox);
        manageParticipantsCtrl.showEditParticipant(participant);
    }
}
