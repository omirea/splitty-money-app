package client.scenes;

import client.Main;
import client.nodes.LanguageSwitch;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class StartCtrl implements  Main.LanguageSwitch {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    @FXML
    private TextField createEventField;
    @FXML
    private TextField joinEventField;
    @FXML
    private Button settingsButton;
    @FXML
    private Button adminButton;
    @FXML
    private ImageView settingsView;
    @FXML
    private ImageView adminView;
    @FXML
    private Label createNewEventText;
    @FXML
    private Button createButton;
    @FXML
    private Label joinEventText;
    @FXML
    private Button joinButton;
    @FXML
    private Label recentEventsText;
    @FXML
    private TableView<Event> recentEvents;
    @FXML
    private TableColumn<Event, String> titleColumn;
    @FXML
    private TableColumn<Event, Button> deleteColumn;
    @FXML
    private TableColumn<Event, Button> openColumn;

    private LanguageSwitch languageSwitch;



    @Inject
    public StartCtrl(ServerUtils server, MainCtrl mainCtrl, LanguageSwitch languageSwitch) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.languageSwitch=languageSwitch;
    }

    public void setUpLanguage(){
        String[] lg=languageSwitch.getLanguage().split("_");
        Main.switchLocale(lg[0], lg[1]);
    }

    /**
     * method to initialize the page view (table, settings icon and admin icon)
     */
    @FXML
    public void initialize() {
        //set settings button
        settingsView.setFitHeight(25);
        settingsView.setFitWidth(22);
        Image setting=new Image(Objects.requireNonNull
                (getClass().getResourceAsStream("/icons/settings.png")));
        settingsView.setImage(setting);
        settingsButton.setGraphic(settingsView);

        //set admin button
        adminView.setFitHeight(25);
        adminView.setFitWidth(22);
        Image admin=new Image(Objects.requireNonNull
                (getClass().getResourceAsStream("/icons/systemadministratormale_1.png")));
        adminView.setImage(admin);
        adminButton.setGraphic(adminView);

        //sets the table columns
        titleColumn.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getName()));
        deleteColumn.setCellValueFactory(q -> {
            Button deleteEvent = new Button();
            ImageView trashCan = new ImageView();
            trashCan.setFitWidth(20);
            trashCan.setFitHeight(20);
            Image trash =new Image(Objects.requireNonNull
                (getClass().getResourceAsStream("/icons/trash.png")));
            trashCan.setImage(trash);
            deleteEvent.setGraphic(trashCan);
            deleteEvent.setOnAction(event -> deleteEventFromTable(q.getValue()));
            return new SimpleObjectProperty<>(deleteEvent);
        });
        openColumn.setCellValueFactory(q -> {
            Button toEvent = new Button(Main.getLocalizedString("Open"));
            toEvent.setOnAction(event -> showEvent(q.getValue().getInvitationID()));
            return new SimpleObjectProperty<>(toEvent);
        });
        recentEvents.setRowFactory(event -> {
            TableRow<Event> row = new TableRow<>();
            row.setOnMouseClicked(q -> {
                if (q.getClickCount() == 2 && (!row.isEmpty())) {
                    Event eventRow = row.getItem();
                    String invID = eventRow.getInvitationID();
                    showEvent(invID);
                }
            });
            return row;
        });
    }

    /**
     * method to create event
     */
    public void onCreateClick() {
        boolean nameEmpty = createEventField.getText().trim().isEmpty();
        if(!nameEmpty) {
            System.out.println("Create " + createEventField.getText());
            Event e = new Event(createEventField.getText());
            addEventToBox(e);
            e = server.createEvent(e);
            mainCtrl.showEventOverview(e.getInvitationID());
            createEventField.setText("");
            joinEventField.setText(" ");
        } else {
            Alert alertNameEmpty = getAlertNameEmpty();
            alertNameEmpty.showAndWait();
        }
    }

    private static Alert getAlertNameEmpty() {
        Alert alertNameEmpty = new Alert(Alert.AlertType.WARNING);
        alertNameEmpty.setTitle(Main.getLocalizedString("alertEmptyEventNameTitle"));
        alertNameEmpty.setContentText(Main.getLocalizedString("alertEmptyEventNameContent"));
        alertNameEmpty.setHeaderText(null);
        return alertNameEmpty;
    }

    /**
     * method to join event
     */
    public void onJoinClick() {
        System.out.println("Join: " + joinEventField.getText());
        // TODO: connect to database, open new window
        try {
            mainCtrl.showEventOverview(joinEventField.getText());
            Event e =  server.getEventByInvitationId(joinEventField.getText());
            addEventToBox(e);
            joinEventField.setText("");
            createEventField.setText(" ");
        } catch (Exception e) {
            Alert alert = getAlertIncorrectInvitationId();
            alert.show();
            throw e;
        }
    }

    private static Alert getAlertIncorrectInvitationId() {
        Alert alert=new Alert(Alert.AlertType.WARNING);
        alert.setTitle(Main.getLocalizedString("alertNoInvitationCodeTitle"));
        alert.setContentText(Main.getLocalizedString("alertNoInvitationCodeContent"));
        alert.setHeaderText(null);
        return alert;
    }


    /**
     * method to add event to table view
     */
    public void addEventToBox(Event event) {
        if(!recentEvents.getItems().contains(event)){
            recentEvents.getItems().add(event);
        }
    }

    private void showEvent(String invID) {
        mainCtrl.showEventOverview(invID);
    }

    public void deleteEventFromTable(Event event) {
        recentEvents.getItems().remove(event);
        recentEvents.refresh();
    }

    /**
     * method to go to adming log in page
     */
    public void onAdminClick(){
        joinEventField.clear();
        createEventField.clear();
        mainCtrl.showAdminLogIn();
    }

    /**
     * method to show the Settings Page
     */
    public void showSettingsPage(){
        mainCtrl.showSettingsPage();
    }

    @Override
    public void LanguageSwitch() {
        adminButton.setText(Main.getLocalizedString("Admin"));
        createNewEventText.setText(Main.getLocalizedString("createNewEvent"));
        createButton.setText(Main.getLocalizedString("Create"));
        joinEventText.setText(Main.getLocalizedString("joinEvent"));
        joinButton.setText(Main.getLocalizedString("Join"));
        recentEventsText.setText(Main.getLocalizedString("recentlyViewedEvents"));
        settingsButton.setText(Main.getLocalizedString("Settings"));
        joinEventField.setPromptText(Main.getLocalizedString("invitationID"));
        createEventField.setPromptText(Main.getLocalizedString("Title"));
        titleColumn.setText(Main.getLocalizedString("Title"));
    }
}
