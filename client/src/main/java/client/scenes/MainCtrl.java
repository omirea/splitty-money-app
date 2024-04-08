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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MainCtrl {
    private Stage primaryStage;

    private Stage anotherStage;
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


    public void initialize(Stage primaryStage, Stage anotherStage,
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
        this.anotherStage = anotherStage;
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
        addEditParticipant.getStylesheets().add("stylesheets/addEditParticipant.css");
        anotherStage.setTitle("Splitty: Add/Edit Participant");
        anotherStage.setScene(addEditParticipant);
        anotherStage.show();
    }

    public void showAddParticipant(String id, Participant participant) {
        addEditParticipant.getStylesheets().add("stylesheets/addEditParticipant.css");
        addEditParticipantCtrl.setEvent(id);
        addEditParticipantCtrl.setParticipant(participant);
        anotherStage.setTitle("Splitty: Add/Edit Participant");
        anotherStage.setScene(addEditParticipant);
        anotherStage.show();
    }

    /**
     * method to show open debts page
     */
    public void showOpenDebts(String id) {
        openDebtsCtrl.setEvent(id);
        primaryStage.setTitle("Splitty: Open Debts");
        primaryStage.setScene(openDebts);
        openDebts.getStylesheets().add("stylesheets/debts.css");
        openDebtsCtrl.addDebtsToList(id);
        openDebtsCtrl.addParticipantsToChoiceBox(id);
    }

    /**
     * method to show invitation page
     */
    public void showInvitation(String id) {
        invitation.getStylesheets().add("stylesheets/invitation.css");
        invitationCtrl.setEvent(id);
        anotherStage.setTitle("Splitty: Send Invites");
        anotherStage.setScene(invitation);
        anotherStage.show();
    }

    /**
     * method to show start screen page
     */
    public void showStartScreen() {
        primaryStage.setTitle("Splitty: Start");
        primaryStage.setScene(start);
        start.getStylesheets().add("stylesheets/startPage.css");
        startCtrl.setUpConnection();
        startCtrl.setUpLanguage();
    }

    /**
     * method to show expense page
     */
    public void showAddExpense(String id) {
        primaryStage.setTitle("Splitty: Add/Edit Expense");
        addEditExpenseCtrl.setEvent(id);
        expense.getStylesheets().add("stylesheets/addEditExpense.css");
        primaryStage.setScene(expense);
        addEditExpenseCtrl.addAllRelevantParticipants();
        addEditExpenseCtrl.clearBoxes();
    }

    /**
     * method to show events page
     * @param invitationId id of the event
     */
    public void showEventOverview(String invitationId) {
        overviewCtrl.setEvent(invitationId);
        overview.getStylesheets().add("stylesheets/eventOverview.css");
        primaryStage.setTitle("Splitty: Event overview");
        primaryStage.setScene(overview);
        overviewCtrl.addAllParticipants();
        overview.setOnKeyPressed(e -> overviewCtrl.keyPressed(e));
    }

    /**
     * method to show manage participant page
     */
    public void showManageParticipants(String invitationId, Participant participantToAdd) {
        anotherStage.close();
        manageParticipantsCtrl.setEvent(invitationId);
        manageParticipantsCtrl.addNewParticipant(participantToAdd);manageParticipants.getStylesheets().add("stylesheets/manageParticipants.css");
        primaryStage.setTitle("Splitty: Manage Participants");
        primaryStage.setScene(manageParticipants);
    }
    public void showManageParticipants(String invitationId) {
        showManageParticipants(invitationId, null);
        manageParticipants.getStylesheets().add("stylesheets/manageParticipants.css");
        manageParticipantsCtrl.addAllParticipants();
    }

    /**
     * method to show admin log in page
     */
    public void showAdminLogIn() {
        primaryStage.setTitle("Splitty: Admin Log In");
        primaryStage.setScene(logInAdmin);
        logInAdmin.getStylesheets().add("stylesheets/adminLogin.css");
        adminLogInCtrl.generatePassword();
    }

    /**
     * method to show admin events overview page
     */
    public void showEventsAdmin(){
        manageEventsAdminCtrl.refresh();
        eventsAdmin.getStylesheets().add("stylesheets/adminOverview.css");
        primaryStage.setTitle("Splitty: Admin events overview");
        primaryStage.setScene(eventsAdmin);
        eventsAdmin.setOnKeyPressed(e -> manageEventsAdminCtrl.enterKeyPressed(e));
    }

    /**
     * method to show the Settings page
     */
    public void showSettingsPage() {
        Stage anotherStage=new Stage();
        settingsPage.getStylesheets().add("stylesheets/settingsPage.fxml.css");
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
        closedDebts.getStylesheets().add("stylesheets/debts.css");
        closedDebtsCtrl.addDebtsToList(id);
        closedDebtsCtrl.addParticipantsToChoiceBox(id);
        primaryStage.setScene(closedDebts);
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

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Scene getAddEditParticipant() {
        return addEditParticipant;
    }

    public Scene getOpenDebts() {
        return openDebts;
    }

    public Scene getInvitation() {
        return invitation;
    }

    public Scene getExpense() {
        return expense;
    }

    public Scene getOverview() {
        return overview;
    }

    public Scene getManageParticipants() {
        return manageParticipants;
    }

    public Scene getStart() {
        return start;
    }

    public Scene getLogInAdmin() {
        return logInAdmin;
    }

    public Scene getClosedDebts() {
        return closedDebts;
    }

    public Scene getEventsAdmin() {
        return eventsAdmin;
    }

    public Scene getSettingsPage() {
        return settingsPage;
    }
}