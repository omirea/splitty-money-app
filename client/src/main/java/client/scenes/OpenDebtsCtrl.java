package client.scenes;

import client.Main;
import client.utils.ServerUtils;
import commons.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.inject.Inject;
import java.util.Objects;
import java.util.Optional;

import static client.Main.locale;

public class OpenDebtsCtrl implements Main.LanguageSwitch {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    Event event;

    @FXML
    private Button payAllDebtsButton;

    @FXML
    private Button seeClosedDebtsButton;

    @FXML
    private Button paySelectedDebtsButton;

    @FXML
    private ListView<String> listView;

    @FXML
    private Button homeButton;

    @FXML
    private ImageView homeView;
    @FXML
    private Label yourCurrentDebtsLabel;
    @FXML
    private Label youShouldPayToLabel;
    @FXML
    private Label eventLabel;
    @FXML
    private Label amountLabel;


    @Inject
    public OpenDebtsCtrl(ServerUtils server, MainCtrl mainCtrl){
        this.server=server;
        this.mainCtrl=mainCtrl;
    }

    /**
     * method to initialize the open debts page
     */
    @FXML
    public void initialize(){
        //initialize button colours
        getListView().getItems().addAll("Debt 1", "Debt 2", "Debt 3");
        getListView().getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        getPayAllDebts()
                .setStyle("-fx-background-color: linear-gradient(to top right, #f5dce7, #e781c9)");

        //set home button
        homeView.setFitHeight(25);
        homeView.setFitWidth(22);
        Image setting=new Image(Objects.requireNonNull
                (getClass().getResourceAsStream("/icons/home.png")));
        homeView.setImage(setting);
        homeButton.setGraphic(homeView);
    }

    public ServerUtils getServer() {
        return server;
    }

    public MainCtrl getMainCtrl() {
        return mainCtrl;
    }

    public Button getSeeClosedDebtsButton(){return seeClosedDebtsButton;}

    public Button getSelectedDebts(){return paySelectedDebtsButton;}

    public ListView<String> getListView(){return listView;}

    public Button getPayAllDebts(){return payAllDebtsButton;}

    /**
     * method to mark all debts as paid
     */
    public void markAllDebtsAsPaid(){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        switch(locale.getLanguage()) {
            case "nl":
                alert.setTitle("Markeer alle kosten als betaald");
                alert.setContentText("Weet je zeker dat je alle kosten als betaald wilt markeren");
                break;
            case "en":
                alert.setTitle("Mark all debts as paid");
                alert.setContentText("Are you sure you want to mark all debts as settled?");
                break;
            default:
                break;
        }
        alert.setHeaderText(null);
        Optional<ButtonType> result=alert.showAndWait();
        if(result.get()==ButtonType.OK) {
            mainCtrl.addItemsToClosedDebts(this.listView);
            listView.getItems().clear();
        }
    }
    public void setEvent(String id) {
        event = server.getEventByInvitationId(id);
    }

    /**
     * method to go back to event page
     */
    public void goBackToEvent(){
        mainCtrl.showEventOverview(event.getInvitationID());
    }

    /**
     * method to open closed debts page
     */
    public void seeClosedDebts(){mainCtrl.showClosedDebts(event.getInvitationID());}

    /**
     * method to mark all selected debts as paid
     */
    public void paySelectedDebts(){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        switch(locale.getLanguage()) {
            case "nl":
                alert.setTitle("Markeer geselecteerde kosten als betaald");
                alert.setContentText("Weet je zeker dat je " +
                        "gemarkeerkde kosten als betaalt wilt markeren");
                break;
            case "en":
                alert.setTitle("Mark selected debts as paid");
                alert.setContentText("Are you sure you want to " +
                        "mark the selected debts as settled?");
                break;
            default:
                break;
        }
        alert.setHeaderText(null);
        Optional<ButtonType> result=alert.showAndWait();
        if(result.get()==ButtonType.OK) {
            ListView<String> selected = new ListView<>();
            for (Object o : listView.getSelectionModel().getSelectedItems())
                selected.getItems().add((String) o);
            mainCtrl.addItemsToClosedDebts(selected);
            listView.getItems().removeAll(listView.getSelectionModel().getSelectedItems());
        }
    }

    @Override
    public void LanguageSwitch() {
        homeButton.setText(Main.getLocalizedString("Home"));
        yourCurrentDebtsLabel.setText(Main.getLocalizedString("yourCurrentDebts"));
        youShouldPayToLabel.setText(Main.getLocalizedString("youShouldPayTo"));
        eventLabel.setText(Main.getLocalizedString("Event"));
        amountLabel.setText(Main.getLocalizedString("Amount"));
        paySelectedDebtsButton.setText(Main.getLocalizedString("paySelectedDebts"));
        payAllDebtsButton.setText(Main.getLocalizedString("payAllDebts"));
        seeClosedDebtsButton.setText(Main.getLocalizedString("seeClosedDebts"));
    }
}