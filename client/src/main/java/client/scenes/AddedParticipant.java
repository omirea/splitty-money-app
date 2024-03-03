package client.scenes;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class AddedParticipant {
    HBox hbox;
    Text name;
    Button edit;
    Button save;
    Button remove;

    public AddedParticipant() {
        name = new Text("Hello World!");

        edit = new Button("E");
        edit.setOnAction(e -> editParticipant());
        remove = new Button("R");
        remove.setOnAction(e -> removeParticipant());
        save = new Button("S");
        save.setOnAction(e -> saveParticipant());

        hbox = new HBox();
        hbox.getChildren().add(name);
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

    private void editParticipant() {
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
        hbox.getChildren().add(1, edit);
    }
}
