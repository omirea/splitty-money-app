package client.scenes;

import client.nodes.RecentEvent;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.Objects;

public class StartCtrl {

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
        Image setting=new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/settings.png")));
        settingsView.setImage(setting);
        settingsButton.setGraphic(settingsView);

        //set admin button
        adminView.setFitHeight(25);
        adminView.setFitWidth(22);
        Image admin=new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/systemadministratormale_1.png")));
        adminView.setImage(admin);
        adminButton.setGraphic(adminView);
    }

    /**
     * method to create event
     */
    public void onCreateClick() {
        System.out.println("Create" + createEventField.getText());
        addEventToBox();
        // TODO: open new window
    }

    /**
     * method to join event
     */
    public void onJoinClick() {
        System.out.println("Join: " + joinEventField.getText());
        // TODO: connect to database, open new window
        mainCtrl.showEventOverview(joinEventField.getText());
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
}
