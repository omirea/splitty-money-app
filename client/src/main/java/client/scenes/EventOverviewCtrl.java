package client.scenes;

import client.nodes.RecentExpense;
import client.utils.RefServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.util.ArrayList;

public class EventOverviewCtrl {

    private final RefServerUtils server;
    private final MainCtrl mainCtrl;
    private ArrayList<RecentExpense> recentExpenses;
    @FXML
    private Text participantsList, eventTitleText;
    @FXML
    private ChoiceBox<String> participantsMenu;
    @FXML
    private VBox allBox, withBox, fromBox;
    @FXML
    private TabPane expensesTabs;

    @Inject
    public EventOverviewCtrl(RefServerUtils server, MainCtrl mainCtrl) {
        recentExpenses = new ArrayList<>();
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * method to open send invite page
     */
    public void onSendInvitesClick(){
        //will do the following code snippet once implemented:
        mainCtrl.showInvitation();
        System.out.println("Sending Invite...");
    }

    /**
     * method to add expense
     */
    public void onAddExpenseClick() {
        //will do the following code snippet once implemented:
        //mainCtrl.showAddExpense();
        System.out.println("Adding Expense...");
        addExpense();
    }

    /**
     * method to open open debts page
     */
    public void onSettleDebtsClick() {
        //will do the following code snippet once implemented:
        mainCtrl.showOpenDebts();
        System.out.println("Settling debts...");
    }

    /**
     * method to refresh the page
     */
    public void onRefreshClick() {
        System.out.println(participantsMenu.getValue());
        allBox.getChildren().clear();
        fromBox.getChildren().clear();
        withBox.getChildren().clear();
        //it would call the method to get the list of expenses from the db
        //assumption is that the right person is filtered
        //and loop the following code:
        onTabSwitch();
    }

    /**
     * method to add participant
     */
    public void onAddClick() {
        System.out.println("opening add page...[temp]");
        addParticipantToBox();
    }

    /**
     * method to edit participant
     */
    public void onEditClick() {
        System.out.println("opening edit page... [temp]");
        mainCtrl.showParticipant();
    }

    /**
     * method to change between the list tabs
     */
    public void onTabSwitch() {
        int tabIndex = expensesTabs.getSelectionModel().getSelectedIndex();
        for (RecentExpense re : recentExpenses) {
            switchTab(tabIndex, re);
        }
    }

    private void addExpense() {
        RecentExpense re = new RecentExpense();
        recentExpenses.add(re);
        int tabIndex = expensesTabs.getSelectionModel().getSelectedIndex();
        switchTab(tabIndex, re);
    }

    private void switchTab(int index, RecentExpense re) {
        if (re.isFrom() && index == 1) {
            addToTab(fromBox, re);

        } else if (!re.isFrom() && index == 2) {
            addToTab(withBox, re);

        } else {
            addToTab(allBox, re);
        }
    }

    private void addToTab(VBox box, RecentExpense re) {
        if (box.getChildren().contains(re.getNode())) return;
        box.getChildren().add(re.getNode());
    }

    private void addParticipantToBox() {
        StringBuilder pString = new StringBuilder(participantsList.getText());
        //it would call the method to get the list of participants from the db
        //and loop through the following code
        String participant = "Person" + (int) (Math.random() * 10);
        if (!pString.isEmpty()) {
            pString.append(", ");
        }
        pString.append(participant);
        participantsMenu.getItems().add(participant);

        //after the loop:
        participantsList.setText(pString.toString());
    }

    public void setEventTitleText() {
        eventTitleText.setText("new title");
    }
}
