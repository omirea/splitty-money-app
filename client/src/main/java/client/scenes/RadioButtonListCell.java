package client.scenes;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.util.Callback;

public class RadioButtonListCell<T> extends ListCell<T> {

    private final RadioButton radioButton = new RadioButton();

    public RadioButtonListCell() {
        radioButton.setOnAction(event -> {
            getListView().getSelectionModel().select(getIndex());
        });
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(null);
        } else {
            setText(item.toString());
            setGraphic(radioButton);
        }
    }

    public static <T> Callback<ListView<T>, ListCell<T>> forListView() {
        return param -> new RadioButtonListCell<>();
    }
}