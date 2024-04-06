package client.scenes;

import client.Main;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import java.util.Objects;

import static client.Main.locale;

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
    private Text createNewEventText;
    @FXML
    private Button createButton;
    @FXML
    private Text joinEventText;
    @FXML
    private Button joinButton;
    @FXML
    private Text recentEventsText;
    @FXML
    private TableView<Event> recentEvents;
    @FXML
    private TableColumn<Event, String> titleColumn;
    @FXML
    private TableColumn<Event, Button> deleteColumn;
    @FXML
    private TableColumn<Event, Button> openColumn;

    @Inject
    public StartCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
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
            Button toEvent = new Button("Open");
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
        } else {
            Alert alertNameEmpty = new Alert(Alert.AlertType.WARNING);
            switch(locale.getLanguage()){
                case "nl":
                    alertNameEmpty.setTitle("Geen Evenement Naam");
                    alertNameEmpty.setContentText("Vul een naam voor het evenement in AUB");
                    break;
                case "en":
                    alertNameEmpty.setTitle("Empty Event Title Field");
                    alertNameEmpty.setContentText("Please fill in the event title field");
                    break;
                default:
                    break;
            }
            alertNameEmpty.setHeaderText(null);
            alertNameEmpty.showAndWait();
        }
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
        } catch (Exception e) {
            Alert alert=new Alert(Alert.AlertType.WARNING);
            switch(locale.getLanguage()) {
                case "nl":
                    alert.setTitle("Uitnodigingscode niet gevonden");
                    alert.setContentText("Check je uitnodigingscode opnieuw AUB");
                    break;
                case "en":
                    alert.setTitle("Invitation code not found");
                    alert.setContentText("Please check your invitation code again");
                    break;
                default:
                    break;
            }
            alert.setHeaderText(null);
            alert.show();
            throw e;
        }
    }

    /**
     * method to add event to table view
     */
    public void addEventToBox(Event event) {
        recentEvents.getItems().add(event);
    }

    private void showEvent(String invID) {
        mainCtrl.showEventOverview(invID);
    }

    private void deleteEventFromTable(Event event) {
        recentEvents.getItems().remove(event);
    }

    /**
     * method to go to adming log in page
     */
    public void onAdminClick(){
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
