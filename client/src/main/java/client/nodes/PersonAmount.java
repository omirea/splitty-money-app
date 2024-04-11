package client.nodes;

import commons.Participant;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class PersonAmount {

    private CheckBox checkBox=new CheckBox();
    private final Participant participant;
    private String name;
    private TextField textField = new TextField();

    public PersonAmount(Participant participant) {
        this.participant = participant;
        this.name = participant.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TextField getTextField() {
        return textField;
    }

    public void setTextField(TextField textField) {
        this.textField = textField;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public Participant getParticipant() {
        return participant;
    }

    @Override
    public String toString() {
        return name;
    }
}
