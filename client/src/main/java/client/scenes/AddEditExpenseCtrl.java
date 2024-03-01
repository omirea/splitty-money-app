package client.scenes;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.security.auth.callback.ChoiceCallback;
import java.io.IOException;
import java.util.Objects;


public class AddEditExpenseCtrl {

    ObservableList<String> currencyList =
            FXCollections.observableArrayList("EUR", "USD", "GBP");
    // Add expense
    @FXML
    private ChoiceBox<String> whoPaidField;
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
    private RadioButton onlySomePeopleField;
    @FXML
    private RadioButton allPeopleField;


    // expense type tag bar
//    @FXML
//    private HBox tagBoxField;
//    @FXML
//    private HBox tagBarField;
//
//    @FXML
//    private TextField tagBarEnterField;

    /**
     * initialise method
     */
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

    /**
     * onAddClick method
     * @param event
     * @throws IOException
     */
    @FXML
    public void onAddClick(ActionEvent event) throws IOException {
//        if(whoPaidField.getValue() != null
        if(whatForField.getText() != null
                && whenField.getValue() != null
                && howMuchField.getText() != null &&
                (allPeopleField.getText() != null
                        || onlySomePeopleField.getText() != null)) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("StartScreen.fxml")));
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            // TO DO: DATABASE STUFF
        }
        System.out.println("Add " + whatForField.getText());
        System.out.println("Picked date " + whenField.getValue());
        System.out.println(howMuchField.getText() + " "
                + currencyField.getValue());
    }

    /**
     * onAbortClick method
     * @param event - click event
     * @throws IOException - if class not found
     */
    @FXML
    public void onAbortClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("StartScreen.fxml")));
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

//    @FXML
//    public void onXTagClick(ActionEvent event) {
//        Button deleteButton = (Button) event.getSource();
//        tagBoxField = (HBox) deleteButton.getParent();
//        tagBarField = (HBox) tagBoxField.getParent();
//
//        tagBarField.getChildren().remove(tagBoxField);
//    }
//
//    @FXML
//    public void onAddTagClick() {
//        String newTagName = tagBarEnterField.getText();
//
//        if (!newTagName.isEmpty()) {
//            // Create a new Button for the tag
//            Button newTagButton = new Button(newTagName);
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
