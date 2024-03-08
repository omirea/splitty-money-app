package client.scenes;

import client.nodes.RecentEvent;
import client.utils.RefServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class StartCtrl {

    private final RefServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private TextField createEventField;

    @FXML
    private TextField joinEventField;

    @FXML
    private VBox recentEventsBox;

    @Inject
    public StartCtrl(RefServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
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
}
