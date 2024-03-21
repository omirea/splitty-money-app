package client.nodes;

import javafx.scene.control.TextField;

public class PersonAmount {

    private String name;

    private TextField textField;

    public PersonAmount(String name, TextField textField) {
        this.name = name;
        this.textField = textField;
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

    @Override
    public String toString() {
        return name;
    }
}
