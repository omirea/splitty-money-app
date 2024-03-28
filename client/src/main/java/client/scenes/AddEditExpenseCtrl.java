package client.scenes;

import client.Main;
import client.nodes.PersonAmount;
import client.utils.ServerUtils;
import commons.Expense;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javax.inject.Inject;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

import static client.Main.locale;

public class AddEditExpenseCtrl implements Main.LanguageSwitch {

    ObservableList<String> currencyList =
            FXCollections.observableArrayList("EUR", "USD", "GBP");
    // Add expense
    private MainCtrl mainCtrl;
    private final ServerUtils serverUtils;

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

    @FXML
    private Text addExpenseText;
    @FXML
    private Text whoPaidText;
    @FXML
    private Text whatForText;
    @FXML
    private Text howMuchText;
    @FXML
    private Text whenText;
    @FXML
    private Button cancelButton;
    @FXML
    private Button addExpenseButton;
    @FXML
    private Text howToSplitText;

    // How to split
    @FXML
    private VBox peopleVBoxField;
    @FXML
    private RadioButton onlySomePeopleField;
    @FXML
    private RadioButton allPeopleField;

    @FXML
    private Button autoDivideButton;
    @FXML
    private TableColumn<PersonAmount, CheckBox> checkBoxColumn;
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

    public TableView<PersonAmount> getTableView(){
        return tableView;
    }


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
        //initialize table view
        tableView.visibleProperty().bind(onlySomePeopleField.selectedProperty());
        autoDivideButton.visibleProperty().bind(onlySomePeopleField.selectedProperty());
        checkBoxColumn.
                setCellValueFactory(new PropertyValueFactory<PersonAmount, CheckBox>("checkBox"));
        participantColumn.
                setCellValueFactory(new PropertyValueFactory<PersonAmount, String>("name"));
        amountColumn.
                setCellValueFactory(new PropertyValueFactory<PersonAmount, TextField>("textField"));
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // currency initialiser
        howMuchField.setText("0");
        currencyField.setValue("EUR");
        currencyField.setItems(currencyList);
    }

    public ChoiceBox<String> getWhoPaidField(){return whoPaidField;}

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

            //check if the amount people have to pay back is greater than the total amount
            double total= Double.parseDouble(howMuchField.getText());
            double sum=0;
            List<PersonAmount> selectedPeople=tableView.getItems();
            for(PersonAmount pa : selectedPeople) {
                if (pa.getCheckBox().isSelected() &&
                        !pa.getTextField().getText().isEmpty()) {
                    sum += Double.parseDouble(pa.getTextField().getText());
                }
            }

            if(sum>total)
                sumIsLarger();
            else {
                mainCtrl.addExpenseToEvent(createExpense());
                mainCtrl.showEventOverview("123");
            }
        }else{
            Alert alert=new Alert(Alert.AlertType.ERROR);
            switch(locale.getLanguage()) {
                case "nl":
                        alert.setTitle("Niet ingevulde velden");
                        alert.setContentText("Alle velden moeten ingevuld " +
                                "worden voor het maken van een uitgave");
                        break;
                case "en":
                        alert.setTitle("Empty fields");
                        alert.setContentText("You need to fill in all fields to create an expense");
                        break;
                default:
                    break;
            }
            alert.setHeaderText(null);
            alert.showAndWait();
        }

    }

    public void sumIsLarger(){
        Alert exceededAmount=new Alert(Alert.AlertType.ERROR);
        exceededAmount.setTitle("Exceeded amount");
        exceededAmount.setContentText("The amount of money people are paying back " +
                "is larger than the value of the expense");
        exceededAmount.showAndWait();
    }

    /**
     * onAbortClick method
     * @param event - click event
     * @throws IOException - if class not found
     */
    @FXML
    public void onAbortClick(ActionEvent event) throws IOException {
        mainCtrl.showEventOverview("123");
    }

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

    /**
     * method to auto divide money between selected payers
     */
    public void autoDivideMethod(){
        double total= Double.parseDouble(howMuchField.getText());
        int peopleCounter=0;
        List<PersonAmount> selectedPeople=tableView.getItems();
        for(PersonAmount pa : selectedPeople)
            if(pa.getCheckBox().isSelected()) {
                if(!pa.getTextField().getText().isEmpty())
                    total=total-Double.parseDouble(pa.getTextField().getText());
                else
                    peopleCounter++;
            }
        if(total<0)
            sumIsLarger();
        for(PersonAmount pa : selectedPeople){
            if(pa.getCheckBox().isSelected()){
                if(pa.getTextField().getText().isEmpty()){
                    double price=total/peopleCounter;
                    if(price== (int) price)
                        pa.getTextField().setText(String.valueOf((int) price));
                    else
                        pa.getTextField().setText(String.valueOf(price));
                }
            }
        }
    }

    @Override
    public void LanguageSwitch() {
        addExpenseText.setText(Main.getLocalizedString("addEditExpense"));
        whoPaidText.setText(Main.getLocalizedString("whoPaid"));
        howMuchText.setText(Main.getLocalizedString("howMuch"));
        whatForText.setText(Main.getLocalizedString("whatFor"));
        whenText.setText(Main.getLocalizedString("When"));
        howToSplitText.setText(Main.getLocalizedString("howToSplit"));
        allPeopleField.setText(Main.getLocalizedString("equallyBetweenEverybody"));
        onlySomePeopleField.setText(Main.getLocalizedString("onlySomePeople"));
        participantColumn.setText(Main.getLocalizedString("Participant"));
        amountColumn.setText(Main.getLocalizedString("Amount"));
        autoDivideButton.setText(Main.getLocalizedString("Auto-Divide"));
        cancelButton.setText(Main.getLocalizedString("Cancel"));
        addExpenseButton.setText(Main.getLocalizedString("addExpense"));
    }
}
