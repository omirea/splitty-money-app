package client.scenes;

import client.utils.RefServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.text.Text;

public class EventOverviewCtrl {

    private final RefServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    Text participantList;
    @FXML
    MenuButton expensesPersons;

    @Inject
    public EventOverviewCtrl(RefServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @FXML
    public void onSendInvitesClick(){

    }

    @FXML
    public void onAddExpenseClick() {

    }

    @FXML
    public void onSettleDebtsClick() {

    }
}
