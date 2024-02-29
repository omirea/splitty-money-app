package client.scenes;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;



public class AddEditExpenseCtrl {

    ObservableList<String> currencyList =
            FXCollections.observableArrayList("EUR", "USD", "GBP");
    // Add expense
    @FXML
    private TextField whatForField;
    @FXML
    private DatePicker whenField;
    @FXML
    private TextField howMuchField;
    @FXML
    private ChoiceBox<String> currencyField;

    // How to split
    @FXML
    private ListView<String> peopleListViewField;
    @FXML
    private VBox peopleVBoxField;
    @FXML
    private CheckBox onlySomePeopleField;

    // expense type tag bar
    @FXML
    private HBox tagBoxField;
    @FXML
    private HBox tagBarField;

    @FXML
    private TextField tagBarEnterField;

//    @FXML
//    private TagBar tagBarField2;

    @FXML
    private void initialize() {
        // only some people initialiser
        peopleVBoxField.visibleProperty().bind(onlySomePeopleField.selectedProperty());
        peopleListViewField.setItems(currencyList); // for testing only
        peopleListViewField.setCellFactory(CheckBoxListCell.forListView());
        peopleListViewField.getSelectionModel().getSelectedItems().addListener((ListChangeListener<String>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    System.out.println("Selected: " + c.getAddedSubList());
                } else if (c.wasRemoved()) {
                    System.out.println("Deselected: " + c.getRemoved());
                }
            }
        });
        // currency initialiser
        currencyField.setValue("EUR");
        currencyField.setItems(currencyList);
    }

    @FXML
    public void onAddClick() {
        System.out.println("Add " + whatForField.getText());
        System.out.println("Picked date " + whenField.getValue());
        System.out.println(howMuchField.getText() + " "
                + currencyField.getValue());
    }

    @FXML
    public void onAbortClick() {
        // TO DO
    }

//    @FXML
//    public void onXTagClick(ActionEvent event) {
//        Button deleteButton = (Button) event.getSource();
//        tagBoxField = (HBox) deleteButton.getParent();
//        tagBarField = (HBox) tagBoxField.getParent();
//
//        tagBarField.getChildren().remove(tagBoxField);
//    }

//    @FXML
//    public void onAddTagClick() {
//        String newTagName = tagBarEnterField.getText();
//
//        if (!newTagName.isEmpty()) {
//            // Create a new Button for the tag
//            Button newTagButton = new Button(newTagName);
//            newTagButton.getStyleClass().add("tag-button"); // Add a CSS class for styling if needed
//            // Set the action for the delete button in the tag
//            newTagButton.setOnAction(this::onXTagClick);
//            // Create a new HBox for the tag
//            HBox newTagBox = new HBox();
//            newTagBox.getChildren().add(newTagButton);
//            // Add the new tag HBox to the existing tagBarField
//            tagBarField.getChildren().add(newTagBox);
//            // Clear the tagBarEnterField for the next input
//            tagBarEnterField.clear();
//        }
//    }
}
