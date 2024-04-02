package client.nodes;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class PersonAmount {


    private long id;
    private CheckBox checkBox=new CheckBox();
    private String name;
    private TextField textField=new TextField();

    public PersonAmount(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return name;
    }
}
