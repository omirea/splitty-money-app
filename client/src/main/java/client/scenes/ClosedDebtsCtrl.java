package client.scenes;

import client.Main;
import client.utils.ServerUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.inject.Inject;
import java.util.Objects;
import java.util.Optional;

public class ClosedDebtsCtrl implements Main.LanguageSwitch {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private Button seeOpenDebts;

    @FXML
    private Button reopenAllDebts;

    @FXML
    private Button reopenSelectedDebts;

    @FXML
    private ListView<String> listView=new ListView<>();
    @FXML
    private Button homeButton;
    @FXML
    private ImageView homeView;
    @FXML
    private Label closedDebtsLabel;

    @FXML
    private Label youPaidToLabel;

    @FXML
    private Label eventLabel;
    @FXML
    private Label amountLabel;


    @Inject
    public ClosedDebtsCtrl(ServerUtils server,MainCtrl mainCtrl){
        this.server=server;
        this.mainCtrl=mainCtrl;
    }

    public ListView<String> getListView(){return listView;}
    public void goBackToOpenDebts(){
        mainCtrl.showOpenDebts();
    }

    public Button getSeeOpenDebts(){return seeOpenDebts;}

    public Button getReopenAllDebts() {return reopenAllDebts;}

    public Button getReopenSelectedDebts(){return reopenSelectedDebts;}

    /**
     * method to initialize close debts page
     */
    public void initialize(){
        //set button color
        getListView().getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        getReopenAllDebts()
                .setStyle("-fx-background-color: linear-gradient(to top right, #c7dde7, #32c2fd)");

        //set button to go home page
        homeView.setFitHeight(25);
        homeView.setFitWidth(22);
        Image setting=new Image(Objects.requireNonNull
                (getClass().getResourceAsStream("/icons/home.png")));
        homeView.setImage(setting);
        homeButton.setGraphic(homeView);
    }

    /**
     * method to reopen all the closed debts
     */
    public void reopenAllDebts(){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Reopen all debts");
        alert.setContentText("Are you sure you want to reopen all debts?");
        Optional<ButtonType> result=alert.showAndWait();
        if(result.get()==ButtonType.OK){
            mainCtrl.addItemsToOpenDebts(listView);
            listView.getItems().clear();
        }
    }

    /**
     * method to reopen only the selected debts
     */
    public void reopenSelectedDebts(){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Reopen selected debts");
        alert.setContentText("Are you sure you want to reopen the selected debts?");
        Optional<ButtonType> result=alert.showAndWait();
        if(result.get()==ButtonType.OK){
            ListView<String> selected=new ListView<>();
            for(String s: listView.getSelectionModel().getSelectedItems())
                selected.getItems().add(s);
            mainCtrl.addItemsToOpenDebts(selected);
            listView.getItems().removeAll(listView.getSelectionModel().getSelectedItems());
        }
    }

    @Override
    public void LanguageSwtich() {
        homeButton.setText(Main.getLocalizedString("Home"));
        closedDebtsLabel.setText(Main.getLocalizedString("closedDebts"));
        seeOpenDebts.setText(Main.getLocalizedString("seeOpenDebts"));
        youPaidToLabel.setText(Main.getLocalizedString("youPaidTo"));
        eventLabel.setText(Main.getLocalizedString("Event"));
        amountLabel.setText(Main.getLocalizedString("Amount"));
        reopenAllDebts.setText(Main.getLocalizedString("reopenSelectedDebts"));
        reopenAllDebts.setText(Main.getLocalizedString("reopenAllDebts"));
    }
}