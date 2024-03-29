package client.scenes;

import client.Main;
import client.nodes.RecentEvent;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Objects;

import static client.Main.locale;

public class StartCtrl implements Main.LanguageSwitch {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private TextField createEventField;

    @FXML
    private TextField joinEventField;

    @FXML
    private VBox recentEventsBox;

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
    private Button englishButton;
    @FXML
    private Button dutchButton;

    @Inject
    public StartCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * method to initialize the page
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
    }

    /**
     * method to create event
     */
    public void onCreateClick() {
        System.out.println("Create" + createEventField.getText());
        Event e = new Event(createEventField.getText());
        e= server.createEvent(e);
        mainCtrl.showEventOverview(e.getInvitationID());
    }

    /**
     * method to join event
     */
    public void onJoinClick() {
        System.out.println("Join: " + joinEventField.getText());
        // TODO: connect to database, open new window
        try {
            mainCtrl.showEventOverview(joinEventField.getText());
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
     * method to add event to box
     */
    public void addEventToBox() {
        RecentEvent re = new RecentEvent();
        recentEventsBox.getChildren().add(re.getNode());
    }

    /**
     * method to go to adming log in page
     */
    public void onAdminClick(){
        mainCtrl.showAdminLogIn();
    }

    public void onEnglishSwitchClick() {
       Main.switchLocale("translations", "en");
    }
    public void onDutchSwitchClick() {
        Main.switchLocale("translations", "nl");
    }

    @Override
    public void LanguageSwitch() {
        adminButton.setText(Main.getLocalizedString("Admin"));
        createNewEventText.setText(Main.getLocalizedString("createNewEvent"));
        createButton.setText(Main.getLocalizedString("Create"));
        joinEventText.setText(Main.getLocalizedString("joinEvent"));
        joinButton.setText(Main.getLocalizedString("Join"));
        recentEventsText.setText(Main.getLocalizedString("recentlyViewedEvents"));
    }
}
