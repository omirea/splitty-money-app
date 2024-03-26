package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import commons.Expense;
import commons.Participant;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Objects;

public class EventOverviewCtrl {

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

    @Inject
    public EventOverviewCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void setEvent(String id) {
        event = server.getEventById(id);
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
        tid.setHeaderText("Input the new event title");
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
     * occurs when the back button is clicked.
     * shows the start screen.
     */
    public void onBackClick() {
        mainCtrl.showStartScreen();
    }

    /**
     * maps the keyboard shortcuts to this controller/scene
     * @param e KeyEvent inputted
     */
    public void keyPressed(KeyEvent e) {
        if (Objects.requireNonNull(e.getCode()) == KeyCode.ESCAPE) {
            onBackClick();
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

//    public void addParticipantToMenu(Participant p) {
//
//    }
//
//    public void addParticipantToText() {
//
//    }
}

