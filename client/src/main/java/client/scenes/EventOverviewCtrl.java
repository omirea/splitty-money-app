package client.scenes;

import client.Main;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import commons.Expense;
import commons.Participant;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Objects;

import static client.Main.locale;

public class EventOverviewCtrl implements Main.LanguageSwitch {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    Event event;

    @FXML private Text participantsListText, eventTitleText;
    @FXML private ChoiceBox<Participant> participantsMenu;
    @FXML private TabPane expensesTabs;
    @FXML private Tab allTab, fromPersonTab, toPersonTab;

    @FXML private Text participantsText, expensesText;
    @FXML private Button homeButton, editTitleButton, sendInvitesButton,
        editParticipantsButton, addExpenseButton, settleDebtsButton;

    //I'm gonna hate myself for doing this...
    @FXML private TableView<Expense> expenseTableViewAll, expenseTableViewFrom, expenseTableViewTo;
    @FXML private TableColumn<Expense, String> nameColumnAll, nameColumnFrom, nameColumnTo;
    @FXML private TableColumn<Expense, Double> amountColumnAll, amountColumnFrom, amountColumnTo;
    @FXML private TableColumn<Expense, Button> editColumnAll, editColumnFrom, editColumnTo,
                                            deleteColumnAll, deleteColumnFrom, deleteColumnTo;
    TableView<Expense>[] expenseTVs =
        new TableView[]{expenseTableViewAll,expenseTableViewFrom,expenseTableViewTo};
    TableColumn<Expense, String>[] nameColumns =
        new TableColumn[]{nameColumnAll, nameColumnFrom, nameColumnTo};
    TableColumn<Expense, Double>[] amountColumns =
        new TableColumn[]{amountColumnAll, amountColumnFrom, amountColumnTo};
    TableColumn<Expense, Button>[] editColumns =
        new TableColumn[]{editColumnAll, editColumnFrom, editColumnTo};
    TableColumn<Expense, Button>[] deleteColumns =
        new TableColumn[]{deleteColumnAll, deleteColumnFrom, deleteColumnTo};



    @Inject
    public EventOverviewCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * method to initialize the page
     */
    @FXML
    public void initialize() {

        setupTableViews();
        setUpImages();

    }

    private void setupTableViews() {
        nameColumnAll.setCellValueFactory(new PropertyValueFactory<Expense, String>("description"));
        amountColumnAll.setCellValueFactory(new PropertyValueFactory<Expense, Double>("amount"));
        editColumnAll.setCellValueFactory(this::createEditButton);
        deleteColumnAll.setCellValueFactory(this::createDeleteButton);
//        loadExpenses();
    }

//    private void loadExpenses() {
//
//    }
//
//    private void addExpense() {
//
//    }

    private SimpleObjectProperty<Button> createEditButton(
        TableColumn.CellDataFeatures<Expense, Button> q) {
        Button editButton = new Button();
        editButton.setOnAction(event -> onEditExpenseClick(q.getValue()));
        return new SimpleObjectProperty<>(editButton);
    }

    private SimpleObjectProperty<Button> createDeleteButton(
        TableColumn.CellDataFeatures<Expense, Button> q) {
        Button deleteButton = new Button();
        deleteButton.setOnAction(event -> onDeleteExpenseClick(q.getValue()));
        setImage(deleteButton, "icons/trash.png");
        return new SimpleObjectProperty<>(deleteButton);
    }

    public void onEditExpenseClick(Expense e) {
        mainCtrl.showAddExpense(event.getInvitationID(), e);
    }

    public void onDeleteExpenseClick(Expense e) {
        server.deleteExpense(e.getId());
        expenseTableViewAll.getItems().remove(e);
    }

    private void setUpImages() {
        setImage(homeButton, "/icons/home.png");
    }

    private void setImage(Button b, String link) {
        ImageView iv = new ImageView();
        iv.setFitWidth(20);
        iv.setFitHeight(20);
        Image image =new Image(Objects.requireNonNull
            (getClass().getResourceAsStream(link)));
        iv.setImage(image);
        b.setGraphic(iv);
    }


    public void setEvent(String invitationID) {
        event = server.getEventByInvitationId(invitationID);
        eventTitleText.setText(event.getName());
    }

    /**
     * method to open send invite page
     */
    public void onSendInvitesClick(){
        //will do the following code snippet once implemented:
        mainCtrl.showInvitation(event.getInvitationID());
    }

    /**
     * method to add expense
     */
    public void onAddExpenseClick() {
        //will do the following code snippet once implemented:
        //mainCtrl.showAddExpense();
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
     * method to refresh the page
     */
    public void onMenuChange() {
        System.out.println(participantsMenu.getValue());
    }

    /**
     * method to edit participant
     */
    public void onParticipantEditClick() {
        mainCtrl.showManageParticipants(event.getInvitationID());
    }

    public void onTitleEditClick() {
        TextInputDialog tid = new TextInputDialog(eventTitleText.getText());
        switch (locale.getLanguage()) {
            case "nl":
                tid.setHeaderText("Vul de naam van het evenement in");
                break;
            case "en":
                tid.setHeaderText("Input the new event title");
                break;
            default: break;
        }
        tid.showAndWait();
        String title = tid.getEditor().getText();
        event.setName(title);
        event = server.updateEvent(event, event.getID());
        eventTitleText.setText(title);
    }

    /**
     * method to change between the list tabs
     */
    public void onTabSwitch() {
//        int tabIndex = expensesTabs.getSelectionModel().getSelectedIndex();
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
        List<Participant> pList = server.getParticipantsByInvitationId(event.getInvitationID());
        participantsMenu.getItems().addAll(pList);
        String pListString = pList.stream().map(Participant::getName).toList().toString();
        pListString = pListString.substring(1, pListString.length()-1);
        participantsListText.setText(pListString);
    }

    @Override
    public void LanguageSwitch() {
        homeButton.setText(Main.getLocalizedString("Home"));
        editTitleButton.setText(Main.getLocalizedString("editTitle"));
        sendInvitesButton.setText(Main.getLocalizedString("sendInvites"));
        participantsText.setText(Main.getLocalizedString("Participants"));
        editParticipantsButton.setText(Main.getLocalizedString("editParticipants"));
        expensesText.setText(Main.getLocalizedString("Expenses"));
        addExpenseButton.setText(Main.getLocalizedString("addExpense"));
        allTab.setText(Main.getLocalizedString("All"));
        fromPersonTab.setText(Main.getLocalizedString("fromPerson"));
        toPersonTab.setText(Main.getLocalizedString("toPerson"));
        settleDebtsButton.setText(Main.getLocalizedString("settleDebts"));

    }

//    public void addParticipantToMenu(Participant p) {
//
//    }
//
//    public void addParticipantToText() {
//
//    }
}

