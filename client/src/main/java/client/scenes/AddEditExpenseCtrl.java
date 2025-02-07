package client.scenes;

import client.Main;
import client.nodes.ParticipantStringConverter;
import client.nodes.PersonAmount;
import client.utils.ServerUtils;
import commons.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import com.google.inject.Inject;
import java.time.LocalDate;
import java.util.*;
import java.util.List;


public class AddEditExpenseCtrl implements Main.LanguageSwitch {
    private ObservableList<String> currencyList =
        FXCollections.observableArrayList("EUR");
    // Add expense

    private ObservableList<String> tags=
            FXCollections.observableArrayList();
    private ObservableList<Participant> participants;
    private ObservableList<PersonAmount> personAmounts;
    private MainCtrl mainCtrl;
    private final ServerUtils server;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private ChoiceBox<Participant> whoPaidField;
    @FXML
    private TextField whatForField, howMuchField, tagField;
    @FXML
    private DatePicker whenField;
    @FXML
    private ChoiceBox<String> currencyField;

    @FXML
    private ChoiceBox<String> tagChoiceBox, deleteTagChoiceBox;

    @FXML
    private Label addExpenseText, whoPaidText, whatForText, howMuchText, whenText, howToSplitText;
    @FXML
    private Button cancelButton, addExpenseButton, autoDivideButton;

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

    @FXML
    private Button addTagButton;

    @FXML
    private Button deleteTagButton;
    private Event event;

    private Event eventWithTheTags;
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

//       if(event!=null)
//           tags.setAll(event.getTags());
//       tagChoiceBox.setItems(tags);

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
            if (sum > total && onlySomePeopleField.isSelected())
                sumIsLarger();
            else {
                autoDivideMethod();
                Expense expense1 = createExpense();
                expense = null;

                String oldName = event.getName();
                event.setName("A");
                event = server.updateEvent(event, event.getID());
                event.setName(oldName);
                event = server.updateEvent(event, event.getID());

                mainCtrl.showEventOverview(event.getInvitationID());
            }
        } else {
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle(Main.getLocalizedString("alertEmptyFieldsTitle"));
            alert.setContentText(Main.getLocalizedString("alertEmptyFieldsContent"));
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

    /**
     * sumisLarger Method
     */
    public void sumIsLarger(){
        Alert exceededAmount=new Alert(Alert.AlertType.ERROR);
        exceededAmount.setTitle(Main.getLocalizedString("alertExceededAmountTitle"));
        exceededAmount.setContentText(Main.getLocalizedString("alertExceededAmountContent"));
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
        String type=tagChoiceBox.getSelectionModel().getSelectedItem();

        if (expense == null) {
            expense = new Expense(event, whatFor, amount, type, date, currency);
            expense = server.createExpense(expense);
        } else {
            expense.setDateSent(date);
            expense.setAmount(amount);
            expense.setDescription(whatFor);
            //expense.setCurrency(currency);
            expense.setType(type);

            expense.setEvent(event);
            expense = server.updateExpense(expense, expense.getId());
        }
        updateDebts(amount, whoPaid);
        return expense;
    }

    /**
     * adds/updates the debts in the server
     * @param amount amount
     * @param whoPaid whoPaid
     */
    private void updateDebts(Double amount, Participant whoPaid) {

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
        addTagButton.setText(Main.getLocalizedString("addTag"));
        deleteTagButton.setText(Main.getLocalizedString("deleteTag"));
    }

    /**
     * sets the event
     * @param invitationId invitationid
     */
    public void setEvent(String invitationId) {
        this.event = server.getEventByInvitationId(invitationId);
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
        fillTableView();
    }

    /**
     * clear all the boxes on the page
     */
    public void clearBoxes() {
        whoPaidField.getItems().clear();
        whoPaidField.setValue(null);
        whatForField.setText("");
        howMuchField.setText("");
        whenField.setValue(null);
        onlySomePeopleField.selectedProperty().setValue(false);
        allPeopleField.selectedProperty().setValue(false);
    }

    /**
     * add all items to the tableView
     */
    public void fillTableView() {
        if (participants == null || participants.isEmpty()) return;

        for (Participant participant : participants) {
            if (participant.equals(whoPaidField.getValue())) continue;
            personAmounts.add(new PersonAmount(participant));
        }
    }

    public void setExpense(Expense e) {
        expense = e;
        List<Debt> debts = server.getDebtsByExpense(e.getId());
        whoPaidField.setValue(debts.getFirst().getTo());
        whatForField.setText(e.getDescription());
        howMuchField.setText(e.getAmount().toString());
        whenField.setValue(e.getDateSent());
        tagChoiceBox.setValue(e.getType());
        onlySomePeopleField.selectedProperty().setValue(true);
        loadDebts(debts);

    }

    private void loadDebts(List<Debt> debts) {
        List<PersonAmount> pas = debts.stream()
            .map(d -> new PersonAmount(d.getFrom()))
            .toList();
        tableView.getItems().removeAll(pas);

        for (Debt d : debts) {
            PersonAmount pa = new PersonAmount(d.getFrom());
            pa.getTextField().setText(d.getAmount().toString());
            pa.getCheckBox().selectedProperty().setValue(true);
            tableView.getItems().add(pa);
        }
    }

    public void setTags(){
        List<TagsClass> tc=eventWithTheTags.getTags();
        tags.clear();
        System.out.println("boofy is " + tc.size());
        for(TagsClass tag : tc){
            System.out.println(tag.getName());
            tags.add(tag.getName());
        }
        tagChoiceBox.setItems(tags);
        deleteTagChoiceBox.setItems(tags);
    }

    public void addTag(){
        if(!tagField.getText().isEmpty()){
            eventWithTheTags.addTag(tagField.getText(),
                    colorPicker.getValue().toString().substring(2));
            String color= colorPicker.getValue().toString().substring(2);
            System.out.println(color);
            tags.clear();
            for(TagsClass tg : eventWithTheTags.getTags()){
                tags.add(tg.getName());
            }
            //tags.add(tagField.getText());
            mainCtrl.setEventWithTagsForEventOverview(eventWithTheTags);
            tagField.clear();
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(Main.getLocalizedString("tagCreated"));
            alert.setContentText(Main.getLocalizedString("tagSucc"));
            alert.showAndWait();
        }
    }

    public void deleteTag(){
        if(!deleteTagChoiceBox.getSelectionModel().getSelectedItem().isEmpty()){
            List<TagsClass> eventTags=eventWithTheTags.getTags();
            for(TagsClass tc: eventTags){
                if(tc.getName().equals(deleteTagChoiceBox.getSelectionModel().getSelectedItem()))
                    eventTags.remove(tc);
            }
            eventWithTheTags.setTags(eventTags);
            tags.clear();
            for(TagsClass tg : eventWithTheTags.getTags()){
                tags.add(tg.getName());
            }
            //tags.add(tagField.getText());
            mainCtrl.setEventWithTagsForEventOverview(eventWithTheTags);
            deleteTagChoiceBox.getSelectionModel().clearSelection();
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(Main.getLocalizedString("tagDeleted"));
            alert.setContentText(Main.getLocalizedString("tagDel"));
            alert.showAndWait();
        }
    }

    public void setEventWithTags(Event eventWithTheTags) {
        this.eventWithTheTags=eventWithTheTags;
    }
}
