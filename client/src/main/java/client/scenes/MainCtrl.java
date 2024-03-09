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
    private Scene manageParticipants;

    private StartCtrl startCtrl;
    private Scene start;

    public void initialize(Stage primaryStage,
                           Pair<StartCtrl, Parent> start,
                           Pair<EventOverviewCtrl, Parent> overview,
                           Pair<InvitationCtrl, Parent> invitation,
                           Pair<AddEditParticipantCtrl, Parent> participant,
                           Pair<AddEditExpenseCtrl, Parent> expense,
                           Pair<OpenDebtsCtrl, Parent> openDebts,
                           Pair<ManageParticipantsCtrl, Parent> manageParticipants) {
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

        this.manageParticipantsCtrl = manageParticipants.getKey();
        this.manageParticipants = new Scene(manageParticipants.getValue());

        showStartScreen();
//        showExpense();
//        showEditParticipants();
        primaryStage.show();
    }

    public void showParticipant() {
        primaryStage.setTitle("Add/Edit Participant");
        primaryStage.setScene(participant);
    }

    public void showOpenDebts() {
        primaryStage.setTitle("Open Debts");
        primaryStage.setScene(openDebts);
    }

    public void showInvitation() {
        primaryStage.setTitle("Send Invites");
        primaryStage.setScene(invitation);
    }


    public void showStartScreen() {
        primaryStage.setTitle("Splitty: Start");
        primaryStage.setScene(start);

    }
    public void showExpense() {
        primaryStage.setTitle("Add/Edit Expense");
        primaryStage.setScene(expense);
    }

    public void showEventOverview(String id) {
        primaryStage.setTitle("Splitty: Event overview");
        primaryStage.setScene(overview);
        overviewCtrl.setEventTitleText();
    }

    public void showManageParticipants() {
        primaryStage.setTitle("Splitty: Manage Participants");
        primaryStage.setScene(manageParticipants);
    }
}