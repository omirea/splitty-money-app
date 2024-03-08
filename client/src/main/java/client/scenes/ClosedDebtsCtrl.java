package client.scenes;

import client.utils.ServerUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.inject.Inject;

public class ClosedDebtsCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private Button back;

    @FXML
    private ListView<String> listView;

    @Inject
    public ClosedDebtsCtrl(ServerUtils server,MainCtrl mainCtrl){
        this.server=server;
        this.mainCtrl=mainCtrl;
    }


    public void goBackToOpenDebts(){
        mainCtrl.showOpenDebts();
    }

    public Button getBackButton(){return back;}

    public ListView<String> getListView(){return listView;}

    public void addItemsToListView(String item){
        this.listView.getItems().add(item);
    }

}