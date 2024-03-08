/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client.scenes;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MainCtrl {
    private Stage primaryStage;
    private AddEditParticipantCtrl addEditParticipantCtrl;
    private Scene participant;
    private OpenDebtsCtrl openDebtsCtrl;
    private Scene openDebts;
    private InvitationCtrl invitationCtrl;
    private Scene invitation;
    private AddEditExpenseCtrl addEditExpenseCtrl;
    private Scene expense;
    private EventOverviewCtrl overviewCtrl;
    private Scene overview;
    private ManageParticipantsCtrl manageParticipantsCtrl;
    private Scene editParticipants;
    private StartCtrl startCtrl;
    private Scene start;
    private Scene logInAdmin;
    private AdminLogInCtrl adminLogInCtrl;
    private ClosedDebtsCtrl closedDebtsCtrl;
    private Scene closedDebts;

    public void initialize(Stage primaryStage,
                           Pair<StartCtrl, Parent> start,
                           Pair<EventOverviewCtrl, Parent> overview,
                           Pair<InvitationCtrl, Parent> invitation,
                           Pair<AddEditParticipantCtrl, Parent> participant,
                           Pair<AddEditExpenseCtrl, Parent> expense,
                           Pair<OpenDebtsCtrl, Parent> openDebts,
                           Pair<ManageParticipantsCtrl, Parent> editParticipants,
                           Pair<AdminLogInCtrl, Parent> logInAdminA,
                           Pair<ClosedDebtsCtrl, Parent> closedDebts) {
        this.primaryStage = primaryStage;
        this.startCtrl = start.getKey();
        this.start = new Scene(start.getValue());
        this.overviewCtrl = overview.getKey();
        this.overview = new Scene(overview.getValue());
        this.addEditParticipantCtrl = participant.getKey();
        this.participant = new Scene(participant.getValue());
        this.openDebtsCtrl = openDebts.getKey();
        this.openDebts = new Scene(openDebts.getValue());
        this.invitationCtrl = invitation.getKey();
        this.invitation = new Scene(invitation.getValue());
        this.addEditExpenseCtrl = expense.getKey();
        this.expense = new Scene(expense.getValue());
        this.manageParticipantsCtrl = editParticipants.getKey();
        this.editParticipants = new Scene(editParticipants.getValue());
        this.adminLogInCtrl = logInAdminA.getKey();
        this.logInAdmin = new Scene(logInAdminA.getValue());
        this.closedDebtsCtrl = closedDebts.getKey();
        this.closedDebts = new Scene(closedDebts.getValue());

        initializeAspectOpenDebts();
        initializeAspectClosedDebts();
        openDebtsCtrl.getListView().getItems().addAll("Debt 1", "Debt 2", "Debt 3");
        openDebtsCtrl.getListView().getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        closedDebtsCtrl.getListView().getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //showStartScreen();
        //showEditParticipants();
        showOpenDebts();
        //showExpense();
        //showEditParticipants();
        //showAdminLogIn();
        primaryStage.show();
    }

    private void initializeAspectOpenDebts() {
        openDebtsCtrl.getPayAllDebts().setStyle("-fx-background-color: linear-gradient(to top right, #f5dce7, #e781c9)");
    }

    private void initializeAspectClosedDebts() {
        closedDebtsCtrl.getReopenAllDebts().setStyle("-fx-background-color: #4dbaff");
    }

    /**
     * method to show participant page
     */
    public void showParticipant() {
        primaryStage.setTitle("Splitty: Add/Edit Participant");
        primaryStage.setScene(participant);
    }

    /**
     * method to show open debts page
     */
    public void showOpenDebts() {
        primaryStage.setTitle("Splitty: Open Debts");
        primaryStage.setScene(openDebts);
    }

    /**
     * method to show invitation page
     */
    public void showInvitation() {
        primaryStage.setTitle("Splitty: Send Invites");
        primaryStage.setScene(invitation);
    }

    /**
     * method to show start screen page
     */
    public void showStartScreen() {
        primaryStage.setTitle("Splitty: Start");
        primaryStage.setScene(start);
    }

    /**
     * method to show expense page
     */
    public void showExpense() {
        primaryStage.setTitle("Splitty: Add/Edit Expense");
        primaryStage.setScene(expense);
    }

    /**
     * method to show events page
     * @param id id of the event
     */
    public void showEventOverview(String id) {
        primaryStage.setTitle("Splitty: Event overview");
        primaryStage.setScene(overview);
        overviewCtrl.setEventTitleText();
    }

    /**
     * method to show edit participant page
     */
    public void showEditParticipants() {
        primaryStage.setTitle("Splitty: Edit Participants");
        primaryStage.setScene(editParticipants);
    }

    /**
     * method to show admin log in page
     */
    public void showAdminLogIn() {
        primaryStage.setTitle("Splitty: Admin Log In");
        primaryStage.setScene(logInAdmin);
    }

    /**
     * method to show closed debts page
     */
    public void showClosedDebts() {
        primaryStage.setTitle("Closed Debts");
        primaryStage.setScene(closedDebts);
    }

    /**
     * method to add closed debts to the list view
     * @param listView list view that needs to be added
     */
    public void addItemsToClosedDebts(ListView<String> listView) {
        for(String s: listView.getItems())
            closedDebtsCtrl.getListView().getItems().add(s);
    }

    /**
     * method to add open debts to the list view
     * @param listView list view that needs to be added
     */
    public void addItemsToOpenDebts(ListView<String> listView) {
        for(String s: listView.getItems())
            openDebtsCtrl.getListView().getItems().add(s);
    }
}