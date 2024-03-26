package client.scenes;

import client.nodes.RecentExpense;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class EventOverviewCtrl {

    private Stage primaryStage;
    private final ServerUtils server;
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
    @FXML
    private ListView<String> listViewAll;
    @FXML
    private ListView<String> listViewFrom;
    @FXML
    private ListView<String> listViewWith;
    @FXML
    private Button goHomeButton;
    @FXML
    private ImageView homeView;

    @Inject
    public EventOverviewCtrl(Stage primaryStage, ServerUtils server, MainCtrl mainCtrl) {
        this.primaryStage=primaryStage;
        recentExpenses = new ArrayList<>();
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * method to initialize the page
     */
    @FXML
    public void initialize() {
        homeView.setFitHeight(25);
        homeView.setFitWidth(22);
        homeView.setImage(new Image(new File("client/src/main/resources/icons/home.png").toURI().toString()));
        goHomeButton.setGraphic(homeView);
    }

    public ListView<String> getListViewAll() {return listViewAll;}

    public ListView<String> getListViewFrom(){return listViewFrom;}

    public ListView<String> getListViewWith(){return  listViewWith;}

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
        mainCtrl.showExpense();
        addExpense();
    }
    /**
     * method to go back to the Home page
     */
    public void goBackHome(){
        mainCtrl.showStartScreen();
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
        mainCtrl.showExpense();
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
        mainCtrl.addParticipantToExpenseOption(participant);
        mainCtrl.addParticipantToWhoShouldPayOption(participant);
    }

    public void setEventTitleText() {
        eventTitleText.setText("new title");
    }
}
