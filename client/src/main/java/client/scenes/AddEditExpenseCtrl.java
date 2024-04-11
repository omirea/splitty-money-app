package client.scenes;

import client.Main;
import client.nodes.ParticipantStringConverter;
import client.nodes.PersonAmount;
import client.utils.ServerUtils;
import commons.Debt;
import commons.Event;
import commons.Expense;
import commons.Participant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.*;

import static client.Main.locale;

public class AddEditExpenseCtrl implements Main.LanguageSwitch {
    private ObservableList<String> currencyList =
        FXCollections.observableArrayList("EUR", "USD", "GBP");
    // Add expense
    private ObservableList<Participant> participants;
    private ObservableList<PersonAmount> personAmounts;
    private MainCtrl mainCtrl;
    private final ServerUtils server;

    @FXML
    private ChoiceBox<Participant> whoPaidField;
    @FXML
    private TextField whatForField, howMuchField;
    @FXML
    private DatePicker whenField;
    @FXML
    private ChoiceBox<String> currencyField;

    @FXML
    private Text addExpenseText, whoPaidText, whatForText, howMuchText, whenText, howToSplitText;
    @FXML
    private Button cancelButton, addExpenseButton, autoDivideButton;

    // How to split
    @FXML
    private VBox peopleVBoxField;
    @FXML
    private RadioButton onlySomePeopleField, allPeopleField;

    @FXML
    private TableColumn<PersonAmount, CheckBox> checkBoxColumn;
    @FXML
    private TableColumn<PersonAmount, String> participantColumn;
    @FXML
    private TableColumn<PersonAmount, TextField> amountColumn;
    @FXML
    private TableView<PersonAmount> tableView;
    Event event;
    Expense expense;

    /**
     * AddEditExpenseCtrl
     * @param serverUtils server
     * @param mainCtrl main
     */
    @Inject
    public AddEditExpenseCtrl(ServerUtils serverUtils, MainCtrl mainCtrl){
        this.server=serverUtils;
        this.mainCtrl=mainCtrl;
        this.participants = FXCollections.observableArrayList();
        this.personAmounts = FXCollections.observableArrayList();
    }

    public TableView<PersonAmount> getTableView(){
        return tableView;
    }

    /**
     * initialise method
     */
    @FXML
    private void initialize() {
        whoPaidField.setItems(participants);
        tableView.setItems(personAmounts);
        whoPaidField.setConverter(new ParticipantStringConverter());

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

        whatForField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER)) {
                    howMuchField.requestFocus();
                }
            }
        });

        howMuchField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER)) {
                    whenField.requestFocus();
                }
            }
        });
    }

    /**
     * add all items to the tableView
     */
    public void addAllItems() {
        if (participants == null) return;
        for (Participant participant : participants) {
            if (!Objects.equals(participant.getName(), whoPaidField.getValue().getName())) {
                personAmounts.add(new PersonAmount(participant));
            }
        }
    }

    public ChoiceBox<Participant> getWhoPaidField(){return whoPaidField;}

    /**
     * onAddClick method
     */
    @FXML
    public void onAddClick() {

        if (whatForField.getText() != null && whenField.getValue() != null
            && howMuchField.getText() != null && (allPeopleField.getText() != null
            || onlySomePeopleField.getText() != null)) {

            //check if the amount people have to pay back is greater than the total amount
            Double total = Double.parseDouble(howMuchField.getText());
            double sum = 0;
            List<PersonAmount> selectedPeople = tableView.getItems();
            for(PersonAmount pa : selectedPeople) {
                if (pa.getCheckBox().isSelected() &&
                    !pa.getTextField().getText().isEmpty()) {
                    sum += Double.parseDouble(pa.getTextField().getText());
                }
            }
            if (sum > total)
                sumIsLarger();
            else {
                Expense expense1 = createExpense();
                expense = null;
                mainCtrl.showEventOverview(event.getInvitationID());
            }
        } else {
            Alert alert=new Alert(Alert.AlertType.WARNING);
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

    /**
     * sumisLarger Method
     */
    public void sumIsLarger(){
        Alert exceededAmount=new Alert(Alert.AlertType.ERROR);
        exceededAmount.setTitle("Exceeded amount");
        exceededAmount.setContentText("The amount of money people are paying back " +
            "is larger than the value of the expense");
        exceededAmount.showAndWait();
    }

    /**
     * onAbortClick method
     */
    @FXML
    public void onAbortClick() {
        expense = null;
        mainCtrl.showEventOverview(event.getInvitationID());
    }

    /**
     * creates an expense
     * @return create Expense
     */
    public Expense createExpense(){

        Participant whoPaid = whoPaidField.getSelectionModel().getSelectedItem();
        String whatFor = whatForField.getText();
        Double amount = Double.parseDouble(howMuchField.getText());
        Currency currency = Currency.getInstance(currencyField.getSelectionModel()
            .getSelectedItem());
        LocalDate date = whenField.getValue();

        if (expense == null) {
            expense = new Expense(event, whatFor, amount, null, date, currency);
            expense = server.createExpense(expense);
        } else {
            expense.setDateSent(date);
            expense.setAmount(amount);
            expense.setDescription(whatFor);
            expense.setCurrency(currency);
            expense.setType(null);
            expense.setEvent(event);
            expense = server.updateExpense(expense, expense.getId());
        }
        fillDebtList(amount, whoPaid);
        return expense;
    }

    /**
     * fills debtList for expense object
     * @param amount amount
     * @param whoPaid whoPaid
     */
    private void fillDebtList(Double amount, Participant whoPaid) {

        List<Debt> existingDebts = server.getDebtsByExpense(expense.getId());
        if (!existingDebts.isEmpty() && !existingDebts.get(0).getTo().equals(whoPaid)) {
            for (Debt debt : existingDebts) {
                server.deleteDebt(debt.getId());
            }
            existingDebts.clear();
        }

        if (allPeopleField.isSelected()) {

            for (PersonAmount personAmount : personAmounts) {
                personAmount.getCheckBox().selectedProperty().set(true);
                personAmount.getTextField().setText("");
                dividePerPerson(personAmount, amount, personAmounts.size());
                createOrUpdateDebt(whoPaid, existingDebts, personAmount);
            }

            for (Debt debt : existingDebts) {
                server.deleteDebt(debt.getId());
            }

        } else {

            for (PersonAmount personAmount : tableView.getItems()) {
                if (personAmount.getCheckBox().isSelected()) {
                    createOrUpdateDebt(whoPaid, existingDebts, personAmount);
                }
            }

            for (Debt debt : existingDebts) {
                server.deleteDebt(debt.getId());
            }
        }
    }

    private void createOrUpdateDebt(Participant whoPaid, List<Debt> existingDebts,
                                    PersonAmount personAmount) {
        Debt debt = new Debt(event, expense, personAmount.getParticipant(),
                whoPaid, Double.parseDouble(personAmount.getTextField().getText()));
        List<Debt> sameDebts = existingDebts.stream()
                .filter(existingDebt -> existingDebt.getFrom()
                        .equals(personAmount.getParticipant()))
                .toList();
        if (sameDebts.isEmpty()) {
            server.createDebt(debt);
        } else {
            server.updateDebt(debt, sameDebts.get(0).getId());
            existingDebts.remove(sameDebts.get(0));
        }
    }

    /**
     * method to auto divide money between selected payers
     */
    public void autoDivideMethod(){
        Double total = Double.parseDouble(howMuchField.getText());
        int peopleCounter = 0;
        List<PersonAmount> selectedPeople=tableView.getItems();
        for(PersonAmount pa : selectedPeople)
            if(pa.getCheckBox().isSelected()) {
                if(!pa.getTextField().getText().isEmpty())
                    total=total-Double.parseDouble(pa.getTextField().getText());
                else
                    peopleCounter++;
            }
        if(total<0) sumIsLarger();
        for(PersonAmount pa : selectedPeople){
            dividePerPerson(pa, total, peopleCounter);
        }
    }

    /**
     * dividePerPerson Method
     * @param pa personAmounts
     * @param total Total
     * @param peopleCounter PeopleCounter
     */
    private void dividePerPerson(PersonAmount pa, Double total, int peopleCounter) {
        if (pa.getCheckBox().isSelected() && pa.getTextField().getText().isEmpty()){
            Double price = total / peopleCounter;
            pa.getTextField().setText(String.valueOf(price));
        }
    }

    /**
     * LanguageSwitch
     */
    @Override
    public void LanguageSwitch() {
        addExpenseText.setText(Main.getLocalizedString("addEditExpense"));
        whoPaidText.setText(Main.getLocalizedString("whoPaid"));
        howMuchText.setText(Main.getLocalizedString("Amount"));
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

    /**
     * sets the event
     * @param invitationId invitationid
     */
    public void setEvent(String invitationId) {
        event = server.getEventByInvitationId(invitationId);
    }

    /**
     * adds all to the choicebox
     */
    public void addAllRelevantParticipants() {
        participants.clear();
        List<Participant> pList = server.getParticipantsByInvitationId(event.getInvitationID());
        participants.addAll(pList);
    }

    /**
     * deletes the one that is selected in whoPaidField
     */
    public void onWhoPaidChange() {
        tableView.getItems().clear();
        addAllItems();
    }

    /**
     * clear all the boxes on the page
     */
    public void clearBoxes() {
        whoPaidField.setValue(null);
        whatForField.setText("");
        howMuchField.setText("0");
        whenField.setValue(null);
        onlySomePeopleField.selectedProperty().setValue(false);
        allPeopleField.selectedProperty().setValue(false);
    }
}
