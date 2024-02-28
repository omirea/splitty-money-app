package client.scenes;

// import client.utils.RefServerUtils;
// import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class StartCtrl {

//    private final RefServerUtils server;
//    private final MainCtrl mainCtrl;

    @FXML
    private TextField createEventField;

    @FXML
    private TextField joinEventField;

    @FXML
    private VBox recentEventsBox;

//    @Inject
//    public StartCtrl(RefServerUtils server, MainCtrl mainCtrl) {
//        this.server = server;
//        this.mainCtrl = mainCtrl;
//    }

    @FXML
    public void onCreateClick() {
        System.out.println("Create" + createEventField.getText());
        refreshRecentEvents();
        // TODO: open new window


    }
    @FXML
    public void onJoinClick() {
        System.out.println("Join: " + joinEventField.getText());
        // TODO: connect to database, open new window

    }

    public void refreshRecentEvents() {
        RecentEvent re = new RecentEvent();
        recentEventsBox.getChildren().add(re.getNode());
    }
}