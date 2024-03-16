package client.scenes;

import client.utils.ServerUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javax.inject.Inject;
import java.util.Optional;

public class OpenDebtsCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private Button payAllDebts;

    @FXML
    private Button back;

    @FXML
    private Button seeClosedDebts;

    @FXML
    private Button selectedDebts;

    @FXML
    private ListView<String> listView=new ListView<>();

    @Inject
    public OpenDebtsCtrl(ServerUtils server, MainCtrl mainCtrl){
        this.server=server;
        this.mainCtrl=mainCtrl;
    }

    public ServerUtils getServer() {
        return server;
    }

    public MainCtrl getMainCtrl() {
        return mainCtrl;
    }

    public Button getSeeClosedDebts(){return seeClosedDebts;}

    public Button getBack(){return back;}

    public Button getSelectedDebts(){return selectedDebts;}

    public ListView<String> getListView(){return listView;}

    public Button getPayAllDebts(){return payAllDebts;}

    /**
     * method to mark all debts as paid
     */
    public void markAllDebtsAsPaid(){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Mark all debts as paid");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to mark all debts as settled?");
        Optional<ButtonType> result=alert.showAndWait();
        if(result.get()==ButtonType.OK) {
            mainCtrl.addItemsToClosedDebts(this.listView);
            listView.getItems().clear();
        }
    }

    /**
     * method to go back to event page
     */
    public void goBackToEvent(){
        mainCtrl.showEventOverview("1023");
    }

    /**
     * method to open closed debts page
     */
    public void seeClosedDebts(){mainCtrl.showClosedDebts();}

    /**
     * method to mark all selected debts as paid
     */
    public void paySelectedDebts(){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Mark selected debts as paid");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to mark the selected debts as settled?");
        Optional<ButtonType> result=alert.showAndWait();
        if(result.get()==ButtonType.OK) {
            ListView<String> selected = new ListView<>();
            for (Object o : listView.getSelectionModel().getSelectedItems())
                selected.getItems().add((String) o);
            mainCtrl.addItemsToClosedDebts(selected);
            listView.getItems().removeAll(listView.getSelectionModel().getSelectedItems());
        }
    }
}