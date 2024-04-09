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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javax.inject.Inject;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static client.Main.locale;

public class AddEditExpenseCtrl implements Main.LanguageSwitch {
    private ObservableList<String> currencyList =
            FXCollections.observableArrayList("EUR", "USD", "GBP");
    // Add expense
    private ObservableList<Participant> participants;
    private ObservableList<PersonAmount> personAmounts;
    private Map<String, Participant> personAmountMap;
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
        this.personAmountMap = new HashMap<>();
    }

    public TableView<PersonAmount> getTableView(){
        return tableView;
    }

    public void setExpense(Expense e) {
        expense = e;
        List<Debt> debts = server.getAllDebts();
        whoPaidField.setValue(debts.getFirst().getWhoPaid());
        whatForField.setText(e.getName());
        howMuchField.setText(String.valueOf(e.getAmount()));
        currencyField.setValue(e.getCurrency().getDisplayName());
        whenField.setValue(e.getDateSent());
        //fill in all the expense stuff

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
        if(participants == null) return;
        for (Participant participant : participants) {
            if (!Objects.equals(participant.getName(), whoPaidField.getValue().getName())) {
                personAmounts.add(new PersonAmount(participant.getName()));
                personAmountMap.put(participant.getName(), participant);
            }
        }
    }

    public ChoiceBox<Participant> getWhoPaidField(){return whoPaidField;}

    /**
     * onAddClick method
     * @param event2 - click event
     * @throws IOException - IOException
     */
    @FXML
    public void onAddClick(ActionEvent event2) throws IOException {

        if(whatForField.getText() != null && whenField.getValue() != null
            && howMuchField.getText() != null && (allPeopleField.getText() != null
            || onlySomePeopleField.getText() != null)) {

            //check if the amount people have to pay back is greater than the total amount
            Double total= Double.parseDouble(howMuchField.getText());
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
                Expense expense1 = createExpense();
                expense = null;
                mainCtrl.showEventOverview(event.getInvitationID());
            }
        }else{
            Alert alert = getAlert();
            alert.showAndWait();
        }

    }

    private static Alert getAlert() {
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
        return alert;
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
     * @param event2 - click event
     * @throws IOException - if class not found
     */
    @FXML
    public void onAbortClick(ActionEvent event2) throws IOException {
        mainCtrl.showEventOverview(event.getInvitationID());
    }

    /**
     * creates an expense
     * @return create Expense
     */
    public Expense createExpense(){
        Participant whoPaid=whoPaidField.getSelectionModel().getSelectedItem();
        String whatFor=whatForField.getText();
        Double amount= Double.parseDouble(howMuchField.getText());
        Currency currency= Currency.getInstance(currencyField.getSelectionModel()
            .getSelectedItem());
        LocalDate date=whenField.getValue();
        List<Debt> debtList = new ArrayList<>();
        fillDebtList(amount, debtList, whoPaid);
        if (expense == null) {
            //expense = new Expense(event, debtList, whatFor, amount, null, date, currency);
            expense = new Expense(event, whatFor, amount, null, date, currency);
            expense.setEvent(event);
            expense=server.createExpense(expense);
        } else {
            //check if the debt already exists between these 2 participants
            //if it exists, update it
            //if it doesn't exist, create it
            List<Debt> debtsDB=server.getDebtsByInvitationId(event.getInvitationID());
            for(Debt debt : debtList){
                Participant from=debt.getHasToPay();
                Participant to=debt.getWhoPaid();
                boolean exists=false;
                for(Debt debtDB : debtsDB){
                    if(debtDB.getHasToPay().equals(from) && debtDB.getWhoPaid().equals(to)){
                        exists=true;
                        Double amountDB=debtDB.getAmount();
                        Double newAmount=debt.getAmount();
                        debtDB.setAmount(amountDB+newAmount);
                        server.updateDebt(debtDB, debtDB.getId());
                    }
                }
                if(!exists){
                    debt.setEvent(event);
                    server.createDebt(debt);
                }
            }
            expense.setDateSent(date);
            expense.setAmount(amount);
            expense.setName(whatFor);
            expense.setCurrency(currency);
            expense.setType(null);
            //expense.setDebts(debtList);
            expense.setEvent(event);
            expense=server.updateExpense(expense, expense.getId());
        }
        return expense;
    }

    /**
     * fills debtList for expense object
     * @param amount amount
     * @param debtList debtList
     * @param whoPaid whoPaid
     */
    private void fillDebtList(Double amount, List<Debt> debtList, Participant whoPaid) {
        if(allPeopleField.isSelected()) {
            for(PersonAmount personAmount : personAmounts) {
                personAmount.getCheckBox().selectedProperty().set(true);
            }

            for(PersonAmount personAmount : personAmounts) {
                dividePerPerson(personAmount, amount, personAmounts.size());
                Debt debt = new Debt(personAmountMap.get(personAmount.getName()), whoPaid,
                    Double.parseDouble(personAmount.getTextField().getText()));
                debtList.add(debt);
            }
        } else {
            for (PersonAmount personAmount : tableView.getItems()) {
                if (personAmount.getCheckBox().isSelected()) {
                    Debt debt = new Debt(personAmountMap.get(personAmount.getName()), whoPaid,
                        Double.parseDouble(personAmount.getTextField().getText()));

                    debt=server.createDebt(debt);
                    debtList.add(debt);
                }
            }
        }
        //check if the debt already exists between these 2 participants
        //if it exists, update it
        //if it doesn't exist, create it
        List<Debt> debtsDB=server.getDebtsByInvitationId(event.getInvitationID());
        for(Debt debt : debtList){
            Participant from=debt.getHasToPay();
            Participant to=debt.getWhoPaid();
            boolean exists=false;
            for(Debt debtDB : debtsDB){
                if(debtDB.getHasToPay().equals(from) && debtDB.getWhoPaid().equals(to)){
                    exists=true;
                    Double amountDB=debtDB.getAmount();
                    Double newAmount=debt.getAmount();
                    debtDB.setAmount(amountDB+newAmount);
                    server.updateDebt(debtDB, debtDB.getId());
                }
            }
            if(!exists){
                debt.setEvent(event);
                server.createDebt(debt);
            }
        }
    }

    /**
     * method to auto divide money between selected payers
     */
    public void autoDivideMethod(){
        Double total= Double.parseDouble(howMuchField.getText());
        int peopleCounter=0;
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
        if (!pa.getCheckBox().isSelected()) return;
        if(pa.getCheckBox().isSelected()){
            if(pa.getTextField().getText().isEmpty()){
                Double price= total / peopleCounter;
//                if( (double) price== (int) price)
//                    pa.getTextField().setText(String.valueOf((int) price));
//                else
                pa.getTextField().setText(String.valueOf(price));
            }
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
