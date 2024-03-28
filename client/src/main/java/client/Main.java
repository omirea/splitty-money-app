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

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);
    private static ResourceBundle resourceBundle;
    public static Locale locale;
    public static void main(String[] args) {
        launch(args);
    }

    @Override

    public void start(Stage stage) {
        var participant = FXML.load(AddEditParticipantCtrl.class, "client", "scenes",
            "AddEditParticipant.fxml");
        var start = FXML.load(StartCtrl.class, "client", "scenes", "StartScreen.fxml");

        var invitation = FXML.load(InvitationCtrl.class, "client", "scenes",
            "Invitation.fxml");
        var openDebts = FXML.load(OpenDebtsCtrl.class, "client", "scenes",
            "OpenDebts.fxml");

        var expense = FXML.load(AddEditExpenseCtrl.class, "client", "scenes",
            "AddEditExpense.fxml");
        var overview = FXML.load(EventOverviewCtrl.class, "client", "scenes",
            "EventOverview.fxml");
        var manageParticipants = FXML.load(ManageParticipantsCtrl.class, "client", "scenes",
            "ManageParticipants.fxml");
        var mainCtrl = INJECTOR.getInstance(MainCtrl.class);
        var logInAdmin = FXML.load(AdminLogInCtrl.class, "client", "scenes",
            "AdminLogIn.fxml");
        var closedDebts=FXML.load(ClosedDebtsCtrl.class, "client", "scenes",
            "ClosedDebts.fxml");
        var eventsAdmin = FXML.load(ManageEventsAdminCtrl.class, "client", "scenes",
            "ManageEventsAdmin.fxml");
        mainCtrl.initialize(stage, start, overview, invitation, participant, expense, openDebts,
            manageParticipants, logInAdmin, closedDebts, eventsAdmin);

    }

    public interface LanguageSwitch {
        void LanguageSwitch();
    }

    public static String getLocalizedString(String key) {
        if(resourceBundle != null && resourceBundle.containsKey(key)) {
            return resourceBundle.getString(key);
        } else {
            return "MISSING KEY: " + key;
        }
    }

    public static void LanguageSwitching() {
        List<LanguageSwitch> controllers = Arrays.asList(
                INJECTOR.getInstance(AddEditExpenseCtrl.class),
                INJECTOR.getInstance(AddEditParticipantCtrl.class),
                INJECTOR.getInstance(AdminLogInCtrl.class),
                INJECTOR.getInstance(ClosedDebtsCtrl.class),
                INJECTOR.getInstance(EventOverviewCtrl.class),
                INJECTOR.getInstance(InvitationCtrl.class),
                INJECTOR.getInstance(ManageEventsAdminCtrl.class),
                INJECTOR.getInstance(ManageParticipantsCtrl.class),
                INJECTOR.getInstance(OpenDebtsCtrl.class),
                INJECTOR.getInstance(StartCtrl.class));
        for(LanguageSwitch controller : controllers) {
            controller.LanguageSwitch();
        }
    }
    public static void swtichLocale(String baseName, String languageCode) {
        if(languageCode != null) {
            locale = new Locale(languageCode);
            resourceBundle = ResourceBundle.getBundle(baseName, locale);
        } else {
            resourceBundle = ResourceBundle.getBundle(baseName, Locale.getDefault());
        }
        LanguageSwitching();
    }
}