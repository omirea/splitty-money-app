package client.scenes;

import client.utils.RefServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class EventOverviewCtrl {

    private final RefServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    Text participantsList, eventTitleText;
    @FXML
    ChoiceBox<String> participantsMenu;
    @FXML
    VBox allTab, withTab, fromTab;

    @Inject
    public EventOverviewCtrl(RefServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @FXML
    public void onSendInvitesClick(){
        //will do the following code snippet once implemented:
        //mainCtrl.showInvite();
        System.out.println("Sending Invite...");
    }

    @FXML
    public void onAddExpenseClick() {
        //will do the following code snippet once implemented:
        //mainCtrl.showAddExpense();
        System.out.println("Adding Expense...");
        addExpense();
    }

    @FXML
    public void onSettleDebtsClick() {
        //will do the following code snippet once implemented:
        //mainCtrl.showOpenDebts();
        System.out.println("Settling debts...");
    }

    @FXML
    public void onRefreshClick() {
        System.out.println(participantsMenu.getValue());
        allTab.getChildren().clear();
        fromTab.getChildren().clear();
        withTab.getChildren().clear();
        //it would call the method to get the list of expenses from the db
        //assumption is that the right person is filtered
        //and loop the following code:
        addExpense();
    }

    @FXML
    public void onAddClick() {
        System.out.println("opening add page...[temp]");
        addParticipant();
    }

    @FXML
    public void onEditClick() {
        System.out.println("opening edit page... [temp]");
        mainCtrl.showParticipant();
    }

    private void addExpense() {
        RecentExpense re = new RecentExpense(/*expense*/);
        boolean isFrom = Math.random() > 0.5;
        if (isFrom) {
            fromTab.getChildren().add(re.getNode());
            System.out.println("added to from");
        } else {
            withTab.getChildren().add(re.getNode());
            System.out.println("added to with");
        }
//        HBox allBox =
        allTab.getChildren().add(re.getNode());
    }

    private void addParticipant() {
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

    public void refresh() {
        addParticipant();

    }
}
