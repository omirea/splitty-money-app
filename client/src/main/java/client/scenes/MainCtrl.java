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

import client.Main;
import client.nodes.PersonAmount;
import commons.Expense;
import commons.Participant;
import jakarta.mail.Part;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MainCtrl {
    private Stage primaryStage;
    private AddEditParticipantCtrl addEditParticipantCtrl;
    private OpenDebtsCtrl openDebtsCtrl;
    private InvitationCtrl invitationCtrl;
    private AddEditExpenseCtrl addEditExpenseCtrl;
    private EventOverviewCtrl overviewCtrl;
    private ManageParticipantsCtrl manageParticipantsCtrl;
    private StartCtrl startCtrl;
    private AdminLogInCtrl adminLogInCtrl;
    private ClosedDebtsCtrl closedDebtsCtrl;
    private ManageEventsAdminCtrl manageEventsAdminCtrl;
    private SettingsPageCtrl settingsPageCtrl;

    private Scene addEditParticipant, openDebts, invitation,
        expense, overview, manageParticipants, start,
        logInAdmin, closedDebts, eventsAdmin, settingsPage;


    public void initialize(Stage primaryStage,
                           Pair<StartCtrl, Parent> start,
                           Pair<EventOverviewCtrl, Parent> overview,
                           Pair<InvitationCtrl, Parent> invitation,
                           Pair<AddEditParticipantCtrl, Parent> participant,
                           Pair<AddEditExpenseCtrl, Parent> expense,
                           Pair<OpenDebtsCtrl, Parent> openDebts,
                           Pair<ManageParticipantsCtrl, Parent> manageParticipants,
                           Pair<AdminLogInCtrl, Parent> logInAdminA,
                           Pair<ClosedDebtsCtrl, Parent> closedDebts,
                           Pair<ManageEventsAdminCtrl, Parent> eventsAdmin,
                            Pair<SettingsPageCtrl, Parent> settingsPage) {
        this.primaryStage = primaryStage;
        this.startCtrl = start.getKey();
        this.start = new Scene(start.getValue());
        this.overviewCtrl = overview.getKey();
        this.overview = new Scene(overview.getValue());
        this.addEditParticipantCtrl = participant.getKey();
        this.addEditParticipant = new Scene(participant.getValue());
        this.openDebtsCtrl = openDebts.getKey();
        this.openDebts = new Scene(openDebts.getValue());
        this.invitationCtrl = invitation.getKey();
        this.invitation = new Scene(invitation.getValue());
        this.addEditExpenseCtrl = expense.getKey();
        this.expense = new Scene(expense.getValue());
        this.manageParticipantsCtrl = manageParticipants.getKey();
        this.manageParticipants = new Scene(manageParticipants.getValue());
        this.adminLogInCtrl = logInAdminA.getKey();
        this.logInAdmin = new Scene(logInAdminA.getValue());
        this.closedDebtsCtrl = closedDebts.getKey();
        this.closedDebts = new Scene(closedDebts.getValue());
        this.manageEventsAdminCtrl = eventsAdmin.getKey();
        this.eventsAdmin = new Scene(eventsAdmin.getValue());
        this.settingsPageCtrl = settingsPage.getKey();
        this.settingsPage=new Scene(settingsPage.getValue());
        Main.switchLocale("translations","en");


        //showEventOverview("123");
        showStartScreen();
        //showSettingsPage();
        //showEventsAdmin();
        //showEditParticipants();
        //showOpenDebts();
        //showEditParticipants();
        //showAdminLogIn();
        primaryStage.show();
    }

    /**
     * method to show participant page
     */
    public void showAddParticipant(String id) {
        addEditParticipantCtrl.setEvent(id);
        primaryStage.setTitle("Splitty: Add/Edit Participant");
        primaryStage.setScene(addEditParticipant);
    }

    public void showAddParticipant(String id, Participant participant) {
        addEditParticipantCtrl.setEvent(id);
        addEditParticipantCtrl.setParticipant(participant);
        primaryStage.setTitle("Splitty: Add/Edit Participant");
        primaryStage.setScene(addEditParticipant);
    }

    /**
     * method to show open debts page
     */
    public void showOpenDebts(String id) {
        openDebtsCtrl.setEvent(id);
        primaryStage.setTitle("Splitty: Open Debts");
        primaryStage.setScene(openDebts);
    }

    /**
     * method to show invitation page
     */
    public void showInvitation(String id) {
        invitationCtrl.setEvent(id);
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
    public void showAddExpense(String invitationId) {
        primaryStage.setTitle("Splitty: Add/Edit Expense");
        addEditExpenseCtrl.setEvent(invitationId);
        primaryStage.setScene(expense);
        addEditExpenseCtrl.addAllRelevantParticipants();
        addEditExpenseCtrl.addAllItems();
    }

    /**
     * method to show events page
     * @param invitationId id of the event
     */
    public void showEventOverview(String invitationId) {
        overviewCtrl.setEvent(invitationId);
        primaryStage.setTitle("Splitty: Event overview");
        primaryStage.setScene(overview);
        overviewCtrl.addAllParticipants();
        overview.setOnKeyPressed(e -> overviewCtrl.keyPressed(e));
    }

    /**
     * method to show manage participant page
     */
    public void showManageParticipants(String invitationId, Participant participantToAdd) {
        manageParticipantsCtrl.setEvent(invitationId);
        manageParticipantsCtrl.addNewParticipant(participantToAdd);
        primaryStage.setTitle("Splitty: Manage Participants");
        primaryStage.setScene(manageParticipants);
    }
    public void showManageParticipants(String invitationId) {
        showManageParticipants(invitationId, null);
        manageParticipantsCtrl.addAllParticipants();
    }

    /**
     * method to show admin log in page
     */
    public void showAdminLogIn() {

        primaryStage.setTitle("Splitty: Admin Log In");
        primaryStage.setScene(logInAdmin);
        adminLogInCtrl.generatePassword();
    }

    /**
     * method to show admin events overview page
     */
    public void showEventsAdmin(){
        manageEventsAdminCtrl.refresh();
        primaryStage.setTitle("Splitty: Admin events overview");
        primaryStage.setScene(eventsAdmin);
    }

    /**
     * method to show the Settings page
     */
    public void showSettingsPage() {
        Stage anotherStage=new Stage();
        anotherStage.setTitle("Splitty: Settings Page");
        anotherStage.setScene(settingsPage);
        anotherStage.show();
    }

    /**
     * method to show closed debts page
     */
    public void showClosedDebts(String id) {
        closedDebtsCtrl.setEvent(id);
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

    /**
     * when a participant is added to the event it becomes a possible option
     * for "who paid" for an expense
     * @param participant the participant that has been added
     */
    public void addParticipantToExpenseOption(Participant participant) {
        addEditExpenseCtrl.getWhoPaidField().getItems().add(participant);
    }

    /**
     * when an expense gets created it is added to the list of all events
     * @param expense the expense that has been created
     */
    public void addExpenseToEvent(Expense expense) {
        overviewCtrl.getListViewAll().getItems().add(expense);
    }

    /**
     * when a participant is added to the event it becomes a possible option
     * for who should pay for an expense
     * @param participant the participant that has been added
     */
    public void addParticipantToWhoShouldPayOption(String participant) {
        PersonAmount pa=new PersonAmount(participant);
        addEditExpenseCtrl.getTableView().getItems().add(pa);
    }
}