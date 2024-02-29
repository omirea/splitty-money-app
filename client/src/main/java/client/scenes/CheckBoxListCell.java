package client.scenes;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class CheckBoxListCell<T> extends ListCell<T> {

    private final CheckBox checkBox = new CheckBox();

    public CheckBoxListCell() {
        checkBox.setOnAction(event -> {
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
            setGraphic(checkBox);
        }
    }

    public static <T> Callback<ListView<T>, ListCell<T>> forListView() {
        return param -> new CheckBoxListCell<>();
    }
}