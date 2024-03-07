package client.nodes;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class AddedParticipant {
    HBox hbox;
    Text name;
    Button rename;
    Button save;
    Button remove;
    Button edit;

    public AddedParticipant() {
        name = new Text("Hello World!");

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
    }

    public HBox getNode(){
        return hbox;
    }

    private void removeParticipant() {
        VBox parent = (VBox) hbox.getParent();
        parent.getChildren().remove(hbox);
    }

    private void renameParticipant() {
        System.out.println("Renaming participant");

        Node node = hbox.getChildren().remove(0);
        name = (Text) node;
        TextField field = new TextField(name.getText());
        hbox.getChildren().add(0, field);

        hbox.getChildren().remove(1);
        hbox.getChildren().add(1, save);
    }

    private void saveParticipant() {
        System.out.println("Saving changes to participant");

        Node node = hbox.getChildren().remove(0);
        TextField field = (TextField) node;
        name = new Text(field.getText());
        hbox.getChildren().add(0, name);

        hbox.getChildren().remove(1);
        hbox.getChildren().add(1, rename);
    }

    private void editParticipant() {
        // TODO: navigate to add/edit participant page
    }
}
