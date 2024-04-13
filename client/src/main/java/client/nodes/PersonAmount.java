package client.nodes;

import commons.Participant;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonAmount that = (PersonAmount) o;
        return Objects.equals(getParticipant(), that.getParticipant()) &&
            Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCheckBox(), getParticipant(), getName(), getTextField());
    }
}
