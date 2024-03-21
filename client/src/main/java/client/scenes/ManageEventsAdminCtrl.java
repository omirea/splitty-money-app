package client.scenes;

import client.utils.RefServerUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javax.inject.Inject;

public class ManageEventsAdminCtrl {

    private final RefServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private Button overview;

    @FXML
    private TextField eventName;

    @FXML
    private Button search;

    @Inject
    public ManageEventsAdminCtrl (RefServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * method to show start screen when admin wants to log out
     */
    public void  onLogOutClick(){
        mainCtrl.showStartScreen();
    }

    /**
     * method for button so search with string happens when clicking it
     */
    public void onSearchClick(){
        String eventNameS = eventName.getText().trim();
        //TODO: implement search in database.
        //TODO: implement the FXML file so it shows them.
    }
}
