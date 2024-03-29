package client.scenes;

import client.Main;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import commons.Expense;
import commons.Participant;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    @FXML
    private Text participantsListText, eventTitleText;
    @FXML
    private ChoiceBox<Participant> participantsMenu;
    @FXML
    private TabPane expensesTabs;
    @FXML
    private ListView<Expense> listViewAll, listViewFrom, listViewWith;
    @FXML
    private Button homeButton;
    @FXML
    private ImageView homeView;

    @FXML
    private Button editTitleButton;

    @FXML
    private Button sendInvitesButton;

    @FXML
    private Text participantsText;

    @FXML
    private Button editParticipantsButton;

    @FXML
    private Text expensesText;

    @FXML
    private Button addExpenseButton;

    @FXML
    private Tab allTab;

    @FXML
    private Tab fromPersonTab;

    @FXML
    private Tab toPersonTab;
    @FXML
    private Button settleDebtsButton;


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
        //set home button
        homeView.setFitHeight(25);
        homeView.setFitWidth(22);
        Image setting=new Image(Objects.requireNonNull
                (getClass().getResourceAsStream("/icons/home.png")));
        homeView.setImage(setting);
        homeButton.setGraphic(homeView);
    }


    public void setEvent(String invitationID) {
        event = server.getEventById(invitationID);
        eventTitleText.setText(event.getName());
    }

    public ListView<Expense> getListViewAll() {
        return listViewAll;
    }

    public ListView<Expense> getListViewFrom(){
        return listViewFrom;
    }

    public ListView<Expense> getListViewWith(){
        return  listViewWith;
    }

    /**
     * method to open send invite page
     */
    public void onSendInvitesClick(){
        //will do the following code snippet once implemented:
        mainCtrl.showInvitation();
    }

    /**
     * method to add expense
     */
    public void onAddExpenseClick() {
        //will do the following code snippet once implemented:
        //mainCtrl.showAddExpense();
        mainCtrl.showAddExpense();
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
        mainCtrl.showOpenDebts();
    }

    /**
     * method to refresh the page
     */
    public void onMenuChange() {
        System.out.println(participantsMenu.getValue());
        listViewAll.getItems().clear();
        listViewFrom.getItems().clear();
        listViewWith.getItems().clear();

    }

    /**
     * method to edit participant
     */
    public void onParticipantEditClick() {
        mainCtrl.showAddParticipant();
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
        List<Participant> pList = event.getParticipants();
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

