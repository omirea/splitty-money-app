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
    private AddEditParticipant addEditParticipant;
    private Scene participant;

    private InvitationCtrl invitationCtrl;
    private Scene invitation;

    private StartCtrl startCtrl;
    private Scene start;

    public void initialize(Stage primaryStage,
                           Pair<StartCtrl, Parent> start,
                           Pair<InvitationCtrl, Parent> invitation,
                           Pair<AddEditParticipant, Parent> participant) {
        this.primaryStage = primaryStage;
        this.startCtrl = start.getKey();
        this.start = new Scene(start.getValue());

        this.addEditParticipant = participant.getKey();
        this.participant = new Scene(participant.getValue());

        this.invitationCtrl = invitation.getKey();
        this.invitation = new Scene(invitation.getValue());


        //showStartScreen();
        //showParticipant();
        showInvitation();
        primaryStage.show();
    }

    public void showParticipant() {
        primaryStage.setTitle("Add/Edit Participant");
        primaryStage.setScene(participant);
    }

    public void showInvitation() {
        primaryStage.setTitle("Send Invites");
        primaryStage.setScene(invitation);
    }


    public void showStartScreen() {
        primaryStage.setTitle("Splitty: Start");
        primaryStage.setScene(start);

    }
}