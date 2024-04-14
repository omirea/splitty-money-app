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
import client.nodes.ConnectionSetup;
import client.nodes.ThemeService;
import com.google.inject.Inject;
import commons.Expense;
import commons.Participant;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

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
    private ThemeService themeService;

    private List<Scene> scenes;

    private Scene addEditParticipant, openDebts, invitation,
        expense, overview, manageParticipants, start,
        logInAdmin, closedDebts, eventsAdmin, settingsPage;
    private ConnectionSetup cs;
    @Inject
    public MainCtrl(ConnectionSetup cs) {
        this.cs = cs;
    }

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
        this.anotherStage = new Stage();
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
        this.themeService=new ThemeService();
        Main.switchLocale("translations","en");

        initializeScenes();
        changeTheme(themeService.getTheme());
        cs.setUpConnection();

        showStartScreen();
        primaryStage.show();
    }

    public Stage getAnotherStage() {
        return anotherStage;
    }

    /**
     * method to show participant page
     */
    public void showAddParticipant(String id) {
        addEditParticipantCtrl.setEvent(id);
        anotherStage.setTitle("Splitty: Add/Edit Participant");
        anotherStage.setScene(addEditParticipant);
        anotherStage.show();

    }

    public void showAddParticipant(String id, Participant participant) {
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
        openDebtsCtrl.addDebtsToList();
        openDebtsCtrl.addParticipantsToChoiceBox();
        primaryStage.setTitle("Splitty: Open Debts");
        primaryStage.setScene(openDebts);
    }

    /**
     * method to show invitation page
     */
    public void showInvitation(String id) {
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
        startCtrl.setUpLanguage();
    }

    /**
     * method to show expense page
     */
    public void showAddExpense(String id) {
        primaryStage.setTitle("Splitty: Add/Edit Expense");
        addEditExpenseCtrl.setEvent(id);
        primaryStage.setScene(expense);
        addEditExpenseCtrl.clearBoxes();
        addEditExpenseCtrl.addAllRelevantParticipants();
    }

    /**
     * method to show expense page
     */
    public void showAddExpense(String id, Expense e) {
        primaryStage.setTitle("Splitty: Add/Edit Expense");
        primaryStage.setScene(expense);
        addEditExpenseCtrl.clearBoxes();
        addEditExpenseCtrl.setEvent(id);
        addEditExpenseCtrl.addAllRelevantParticipants();
        addEditExpenseCtrl.setExpense(e);
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
        overviewCtrl.loadExpenses();
        overview.setOnKeyPressed(e -> overviewCtrl.keyPressed(e));
        overviewCtrl.setVisibleAdmin(false);
    }

    /**
     * method to show manage participant page
     */
    public void showManageParticipants(String invitationId, Participant participantToAdd) {
        anotherStage.close();
        manageParticipantsCtrl.setEvent(invitationId);
        manageParticipantsCtrl.addNewParticipant(participantToAdd);
        primaryStage.setTitle("Splitty: Manage Participants");
        primaryStage.setScene(manageParticipants);
        manageParticipants.setOnKeyPressed(e -> manageParticipantsCtrl.keyPressed(e));
    }

    public void showManageParticipants(String invitationId) {
        showManageParticipants(invitationId, null);
        manageParticipantsCtrl.setPreUpdatedParticipants();
        manageParticipantsCtrl.addAllParticipants();
        manageParticipants.setOnKeyPressed(e -> manageParticipantsCtrl.keyPressed(e));
    }

    /**
     * method to show admin log in page
     */
    public void showAdminLogIn() {
        primaryStage.setTitle("Splitty: Admin Log In");
        primaryStage.setScene(logInAdmin);
        adminLogInCtrl.generatePassword();
        logInAdmin.setOnKeyPressed(e -> adminLogInCtrl.enterKeyPressed(e));
    }

    /**
     * method to show admin events overview page
     */
    public void showEventsAdmin(){
        manageEventsAdminCtrl.refresh();
        primaryStage.setTitle("Splitty: Admin events overview");
        primaryStage.setScene(eventsAdmin);
        manageEventsAdminCtrl.pollUpdates();
        eventsAdmin.setOnKeyPressed(e -> manageEventsAdminCtrl.enterKeyPressed(e));
    }


    /**
     * method to show the Settings page
     */
    public void showSettingsPage() {
        if(anotherStage.isShowing() && anotherStage != null){
            anotherStage.toFront();
        } else {
            anotherStage=new Stage();
            anotherStage.setTitle("Splitty: Settings Page");
            anotherStage.setScene(settingsPage);
            anotherStage.initOwner(primaryStage);
            anotherStage.show();
        }
        if(anotherStage.getOwner() == null){
            anotherStage.close();
        }
    }

    /**
     * method to show closed debts page
     */
    public void showClosedDebts(String id) {
        closedDebtsCtrl.setEvent(id);
        closedDebtsCtrl.addDebtsToList();
        closedDebtsCtrl.addParticipantsToChoiceBox();
        primaryStage.setTitle("Closed Debts");
        primaryStage.setScene(closedDebts);
    }

    /**
     * method to make a list of all the scenes
     */
    public void initializeScenes(){
        scenes=new ArrayList<>();
        scenes.add(settingsPage);
        scenes.add(addEditParticipant);
        scenes.add(expense);
        scenes.add(eventsAdmin);
        scenes.add(logInAdmin);
        scenes.add(closedDebts);
        scenes.add(openDebts);
        scenes.add(invitation);
        scenes.add(manageParticipants);
        scenes.add(start);
        scenes.add(overview);
    }

    /**
     * method to set the scene
     * @param theme selected theme
     */
    public void changeTheme(String theme){
        for(Scene scene:scenes) {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(theme);
        }
    }


    public Scene getInvitation() {
        return invitation;
    }

    public Scene getExpense() {
        return expense;
    }

}