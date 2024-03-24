package client.scenes;

import client.nodes.RecentExpense;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import commons.Expense;
import commons.Participant;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Objects;

public class EventOverviewCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    Event event;
    @FXML
    private Text participantsList, eventTitleText;
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
        mainCtrl.showExpense();
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
    public void onEditClick() {
        mainCtrl.showParticipant();
    }

    /**
     * method to change between the list tabs
     */
    public void onTabSwitch() {
//        int tabIndex = expensesTabs.getSelectionModel().getSelectedIndex();
    }

    public void onBackClick() {
        mainCtrl.showStartScreen();
    }

    public void keyPressed(KeyEvent e) {
        if (Objects.requireNonNull(e.getCode()) == KeyCode.ESCAPE) {
            onBackClick();
        }
    }
}

