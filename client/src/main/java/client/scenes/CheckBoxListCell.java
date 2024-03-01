package client.scenes;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class CheckBoxListCell<T> extends ListCell<T> {

    private final CheckBox checkBox = new CheckBox();

    /**
     * CheckboxListCell constructor
     */
    public CheckBoxListCell() {
        checkBox.setOnAction(event -> {
            getListView().getSelectionModel().select(getIndex());
        });
    }

    /**
     * update item method
     * @param item The new item for the cell.
     * @param empty whether or not this cell represents data from the list. If it
     *        is empty, then it does not represent any domain data, but is a cell
     *        being used to render an "empty" row.
     */
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

    /**
     * forListView method
     * @return Callback<ListView<T>, ListVell<T>>
     * @param <T> smth
     */
    public static <T> Callback<ListView<T>, ListCell<T>> forListView() {
        return param -> new CheckBoxListCell<>();
    }
}