package client.scenes;

import client.Main;
import client.nodes.ParticipantStringConverter;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Debt;
import commons.Event;
import commons.Expense;
import commons.Participant;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class EventOverviewCtrl implements Main.LanguageSwitch {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private final ObservableList<Participant> allParticipants;
    Event event;
    List<Expense> expenses;
    List<Debt> debts;

    @FXML private Text participantsListText, eventTitleText;
    @FXML private ChoiceBox<Participant> participantsMenu;
    @FXML private Tab allTab, fromPersonTab, toPersonTab;

    @FXML private Text participantsText, expensesText;
    @FXML private Button homeButton, editTitleButton, sendInvitesButton,
        editParticipantsButton, addExpenseButton, settleDebtsButton;

    //I'm going to hate myself for doing this...
    @FXML private TableView<Expense> expenseTableViewAll,
        expenseTableViewHasToPay, expenseTableViewPaidFor;
    @FXML private TableColumn<Expense, Date> dateColAll, dateColFrom, dateColTo;
    @FXML private TableColumn<Expense, String> nameColAll, nameColFrom, nameColTo;
    @FXML private TableColumn<Expense, Double> amountColAll, amountColFrom, amountColTo;
    @FXML private TableColumn<Expense, Button> editColAll, editColFrom, editColTo,
                                            deleteColAll, deleteColFrom, deleteColTo;

    private final StartCtrl start;

    @Inject
    public EventOverviewCtrl(ServerUtils server, MainCtrl mainCtrl, StartCtrl start) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.start = start;
        expenses = new ArrayList<>();
        this.allParticipants= FXCollections.observableArrayList();
    }

    /**
     * method to initialize the page
     */
    @FXML
    public void initialize() {
        //set choice box
        participantsMenu.setItems(allParticipants);
        participantsMenu.setConverter(new ParticipantStringConverter());

        setupColumns();
        setUpImages();
    }

    private void setupColumns() {
        setTable(dateColAll, nameColAll, amountColAll, editColAll, deleteColAll);
        setTable(dateColTo, nameColTo, amountColTo, editColTo, deleteColTo);
        setTable(dateColFrom, nameColFrom, amountColFrom, editColFrom, deleteColFrom);
    }

    private void setTable(TableColumn<Expense, Date> dateCol,
                          TableColumn<Expense, String> nameCol,
                          TableColumn<Expense, Double> amountCol,
                          TableColumn<Expense, Button> editCol,
                          TableColumn<Expense, Button> deleteCol) {

        dateCol.setCellValueFactory(new PropertyValueFactory<>("dateSent"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        editCol.setCellValueFactory(this::createEditButton);
        deleteCol.setCellValueFactory(this::createDeleteButton);

        dateCol.setText(dateText);
        nameCol.setText(whatForText);
        amountCol.setText(amountText);
        editCol.setText(editText);
        deleteCol.setText(deleteText);
    }

    public void loadExpenses() {
        if (event == null) return;
        expenses = server.getExpensesByInvitationId(event.getInvitationID());
        setDebts();
        onMenuChange();
    }

    private SimpleObjectProperty<Button> createEditButton(
        TableColumn.CellDataFeatures<Expense, Button> q) {

        Button editButton = new Button();
        editButton.setOnAction(event -> onEditExpenseClick(q.getValue()));
         setImage(editButton, "/icons/pencil.png");
        return new SimpleObjectProperty<>(editButton);
    }

    private SimpleObjectProperty<Button> createDeleteButton(
        TableColumn.CellDataFeatures<Expense, Button> q) {

        Button deleteButton = new Button();
        deleteButton.setOnAction(event -> onDeleteExpenseClick(q.getValue()));
        setImage(deleteButton, "/icons/trash.png");
        return new SimpleObjectProperty<>(deleteButton);
    }

    public void onEditExpenseClick(Expense e) {
        mainCtrl.showAddExpense(event.getInvitationID(), e);
        System.out.println("Editing: " + e);
    }

    public void onDeleteExpenseClick(Expense e) {
        expenses.remove(e);
        expenseTableViewPaidFor.getItems().remove(e);
        expenseTableViewPaidFor.getItems().remove(e);
        expenseTableViewAll.getItems().remove(e);
        server.deleteExpense(e.getId());

    }

    private void setUpImages() {
        setImage(homeButton, "/icons/home.png");
//        setImage(editParticipantsButton, "icons/pencil.png");
    }

    private void setImage(Button b, String link) {
        ImageView iv = new ImageView();
        iv.setFitWidth(20);
        iv.setFitHeight(20);
        Image image = new Image(Objects.requireNonNull
            (getClass().getResourceAsStream(link)));
        iv.setImage(image);
        b.setGraphic(iv);
    }


    public void setEvent(String invitationID) {
        event = server.getEventByInvitationId(invitationID);
        eventTitleText.setText(event.getName());
    }

    public void setDebts() {
        debts = server.getDebtsByInvitationId(event.getInvitationID());
        System.out.println(debts);
    }

    /**
     * method to open send invite page
     */
    public void onSendInvitesClick(){
        mainCtrl.showInvitation(event.getInvitationID());
    }

    /**
     * method to add expense
     */
    public void onAddExpenseClick() {
        mainCtrl.showAddExpense(event.getInvitationID());
    }

    /**
     * method to go back to the Home page
     */
    public void goBackHome(){
        mainCtrl.showStartScreen();
    }

    /**
     * method to open the open debts page
     */
    public void onSettleDebtsClick() {
        mainCtrl.showOpenDebts(event.getInvitationID());
    }

    /**
     * method to change the contents of the expense table view
     * should probably be encapsulated/abstracted
     */
    public void onMenuChange() {
        Participant p = participantsMenu.getValue();
        System.out.println(p);
        expenseTableViewAll.getItems().clear();
        expenseTableViewPaidFor.getItems().clear();
        expenseTableViewHasToPay.getItems().clear();
        if (p == null) {
            expenseTableViewAll.getItems().addAll(expenses);
            setTotalText();
            return;
        }

        List<Debt> paidForList = debts.stream()
            .filter(debt -> debt.getTo().equals(p)).toList();
        List<Debt> hasToPayList = debts.stream()
            .filter(debt -> debt.getFrom().equals(p)).toList();

        setPersonText(paidForList, hasToPayList);

        List<Expense> paidForExpenses = paidForList.stream()
                .map(Debt::getExpense).toList();
        List<Expense> hasToPayExpenses = hasToPayList.stream()
            .map(Debt::getExpense).toList();

        expenseTableViewPaidFor.getItems().addAll(paidForExpenses);
        expenseTableViewHasToPay.getItems().addAll(hasToPayExpenses);
        expenseTableViewAll.getItems().addAll(paidForExpenses);
        expenseTableViewAll.getItems().addAll(hasToPayExpenses);

    }

    private void setTotalText() {
        double total = getDebtsSum(debts);
        allTab.setText(allTabText + ": " + total);
        toPersonTab.setText(toTabText);
        fromPersonTab.setText(fromTabText);
    }

    private void setPersonText(List<Debt> paidForDebts, List<Debt> hasToPayDebts) {
        double totalTo = getDebtsSum(hasToPayDebts);
        double totalFrom = getDebtsSum(paidForDebts);
        allTab.setText(allTabText);
        toPersonTab.setText(toTabText + ": " + totalTo);
        fromPersonTab.setText(fromTabText + ": " + totalFrom);
    }

    private double getDebtsSum(List<Debt> debts) {
        return debts.stream()
            .filter(d -> !d.isSettled())
            .map(Debt::getAmount)
            .mapToDouble(d -> d)
            .sum();
    }


    /**
     * method to edit participant
     */
    public void onParticipantEditClick() {
        mainCtrl.showManageParticipants(event.getInvitationID());
    }

    public void onTitleEditClick() {
        TextInputDialog tid = new TextInputDialog(eventTitleText.getText());
        tid.setHeaderText(Main.getLocalizedString("tidTitleEditHeader"));
        tid.showAndWait();
        String title = tid.getEditor().getText();
        start.deleteEventFromTable(event);
        event.setName(title);
        event = server.updateEvent(event, event.getID());
        eventTitleText.setText(title);
        start.addEventToBox(event);
    }

    /**
     * maps the keyboard shortcuts to this controller/scene
     * @param e KeyEvent inputted
     */
    public void keyPressed(KeyEvent e) {
        if (Objects.requireNonNull(e.getCode()) == KeyCode.ESCAPE) {
            goBackHome();
        }
    }

    /**
     * method to be called to add
     * you will probably change this to use the methods commented out below
     */
    public void addAllParticipants() {
        allParticipants.clear();
        allParticipants.add(null);
        List<Participant> pList = server.getParticipantsByInvitationId(event.getInvitationID());
        allParticipants.addAll(pList);

        String pListString = pList.stream().map(Participant::getName).toList().toString();
        pListString = pListString.substring(1, pListString.length()-1);
        participantsListText.setText(pListString);
    }

    private String allTabText, fromTabText, toTabText,
    dateText, amountText, whatForText, editText, deleteText;


    @Override
    public void LanguageSwitch() {
        homeButton.setText(Main.getLocalizedString("Home"));
        editTitleButton.setText(Main.getLocalizedString("editTitle"));
        sendInvitesButton.setText(Main.getLocalizedString("sendInvites"));
        participantsText.setText(Main.getLocalizedString("Participants"));
        editParticipantsButton.setText(Main.getLocalizedString("editParticipants"));
        expensesText.setText(Main.getLocalizedString("Expenses"));
        addExpenseButton.setText(Main.getLocalizedString("addExpense"));
        allTabText = Main.getLocalizedString("All");
        fromTabText = Main.getLocalizedString("fromPerson");
        toTabText = Main.getLocalizedString("toPerson");
        settleDebtsButton.setText(Main.getLocalizedString("settleDebts"));
        dateText = Main.getLocalizedString("expenseDate");
        amountText = Main.getLocalizedString("Amount");
        whatForText = Main.getLocalizedString("whatFor");
        editText = Main.getLocalizedString("editExpense");
        deleteText = Main.getLocalizedString("deleteExpense");
        setupColumns();
    }

}

