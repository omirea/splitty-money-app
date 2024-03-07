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
package client;

import static com.google.inject.Guice.createInjector;

import client.scenes.*;

import com.google.inject.Injector;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    public static void main(String[] args) {
        launch(args);
    }

    @Override

    public void start(Stage stage) {
        var participant = FXML.load(AddEditParticipantCtrl.class, "client", "scenes", "AddEditParticipant.fxml");
        var start = FXML.load(StartCtrl.class, "client", "scenes", "StartScreen.fxml");
        var invitation = FXML.load(InvitationCtrl.class, "client", "scenes", "Invitation.fxml");
        var openDebts = FXML.load(OpenDebtsCtrl.class, "client", "scenes", "OpenDebts.fxml");

        var expense = FXML.load(AddEditExpenseCtrl.class, "client", "scenes", "AddEditExpense.fxml");
        var overview = FXML.load(EventOverviewCtrl.class, "client", "scenes", "EventOverview.fxml");
        var manageParticipants = FXML.load(ManageParticipantsCtrl.class, "client", "scenes", "ManageParticipants.fxml");
        var mainCtrl = INJECTOR.getInstance(MainCtrl.class);
        var logInAdmin = FXML.load(AdminLogInCtrl.class, "client", "scenes", "AdminLogIn.fxml");
        mainCtrl.initialize(stage, start, overview, invitation, participant, expense, openDebts, manageParticipants, logInAdmin);

    }
}