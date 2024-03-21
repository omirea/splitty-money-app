package client.scenes;

import client.nodes.CheckBoxListCell;
import client.nodes.PersonAmount;
import client.utils.ServerUtils;
import com.fasterxml.jackson.databind.deser.impl.PropertyValue;
import commons.Expense;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javax.inject.Inject;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Currency;

public class AddEditExpenseCtrl {

    ObservableList<String> currencyList =
            FXCollections.observableArrayList("EUR", "USD", "GBP");
    // Add expense
    private MainCtrl mainCtrl;

    private ServerUtils serverUtils;

    @FXML
    private ChoiceBox<String> whoPaidField;
    @FXML
    private TextField whatForField;
    @FXML
    private TextField howMuchField;
    @FXML
    private DatePicker whenField;
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

    @FXML
    private TableColumn<PersonAmount, String> participantColumn;
    @FXML
    private TableColumn<PersonAmount, TextField> amountColumn;
    @FXML
    private TableView<PersonAmount> tableView;

    @Inject
    public AddEditExpenseCtrl(ServerUtils serverUtils, MainCtrl mainCtrl){
        this.serverUtils=serverUtils;
        this.mainCtrl=mainCtrl;
    }

    public TableColumn<PersonAmount, String> getParticipantColumn(){return participantColumn;}
    public TableColumn<PersonAmount, TextField> getAmountColumn(){return amountColumn;}
    public TableView<PersonAmount> getTableView(){return tableView;}


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
        participantColumn.setCellValueFactory(new PropertyValueFactory<PersonAmount, String>("name"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<PersonAmount, TextField>("textField"));
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // only some people initialiser
        peopleVBoxField.visibleProperty().bind(onlySomePeopleField.selectedProperty());
        //peopleListViewField.setItems(currencyList); // for testing only
        peopleListViewField.setCellFactory(CheckBoxListCell.forListView());
        peopleListViewField.getSelectionModel().getSelectedItems()
            .addListener((ListChangeListener<String>) c -> {
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

    public ChoiceBox<String> getWhoPaidField(){return whoPaidField;}

    public ListView<String> getPeopleListViewField(){return peopleListViewField;}

    /**
     * onAddClick method
     * @param event - click event
     * @throws IOException - IOException
     */
    @FXML
    public void onAddClick(ActionEvent event) throws IOException {

        if(whatForField.getText() != null && whenField.getValue() != null
          && howMuchField.getText() != null && (allPeopleField.getText() != null
          || onlySomePeopleField.getText() != null)) {

            mainCtrl.addExpenseToEvent(createExpense());
            mainCtrl.showEventOverview("123");
//            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass()
//            .getResource("StartScreen.fxml")));
//            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
//            Scene scene = new Scene(root);
//            stage.setScene(scene);
//            stage.show();
            // TO DO: DATABASE STUFF
        }else{
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Empty fields");
            alert.setContentText("You need to fill in all fields to create an expense");
            alert.showAndWait();
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
//        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass()
//        .getResource("EventOverview.fxml")));
//        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
        mainCtrl.showEventOverview("123");
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

    public Expense createExpense(){
        String whoPaid=whoPaidField.getSelectionModel().getSelectedItem();
        String whatFor=whatForField.getText();
        Double amount= Double.valueOf(howMuchField.getText());
        Currency currency= Currency.getInstance(currencyField.getSelectionModel()
            .getSelectedItem());
        LocalDate date=whenField.getValue();
        Expense expense=new Expense(whatFor, amount, null, date, currency);
        return expense;
    }
}
