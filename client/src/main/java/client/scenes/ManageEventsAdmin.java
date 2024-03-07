package client.scenes;

import client.utils.RefServerUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.inject.Inject;

public class ManageEventsAdmin {

    private final RefServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private Button overview;

    @FXML
    private TextField eventName;

    @FXML
    private Button search;

    @Inject
    public ManageEventsAdmin(RefServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void  onOverviewClick(){
        mainCtrl.showStartScreen();
    }

    public void onSearchClick(){
        String eventNameS = eventName.getText().trim();
        //TODO: implement search in database.
    }
}
