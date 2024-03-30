package client.scenes;

import client.Main;
import client.utils.ServerUtils;
import commons.Event;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;


import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import static client.Main.locale;


public class ManageEventsAdminCtrl implements Initializable, Main.LanguageSwitch {


    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private TextField eventNameTextField;
    @FXML
    private Button searchButton;
    @FXML
    private Button titleButton;
    @FXML
    private Button dateButton;
    @FXML
    private Button activityButton;
    @FXML
    private Button logOutButton;
    @FXML
    private Button refreshButton;
    @FXML
    private Text orderByText;
    @FXML
    ObservableList<Event> allEvents;
    @FXML
    private TableView<Event> table;
    @FXML
    private TableColumn<Event, String> colEventTitle;
    @FXML
    private TableColumn<Event, String> colInvitationID;
    @FXML
    private TableColumn<Event, Button> colDelete;





    @Inject
    public ManageEventsAdminCtrl (ServerUtils server, MainCtrl mainCtrl) {
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
        boolean searchEmpty = eventNameTextField.getText().trim().isEmpty();
        var events = server.getAllEvents();
        allEvents = FXCollections.observableList(events);
        if(!searchEmpty) {
            String eventNameS = eventNameTextField.getText().trim();
            List<Event> eventsWithName =
                allEvents.stream().filter(e -> e.getName().contains(eventNameS)).toList();
            ObservableList<Event> eventsWithN = FXCollections.observableList(eventsWithName);
            table.setItems(eventsWithN);
        } else {
            Alert alertEmpty = new Alert(Alert.AlertType.WARNING);
            switch(locale.getLanguage()){
                case "nl":
                    alertEmpty.setTitle("Lege Velden");
                    alertEmpty.setContentText("Vul het zoek veld in AUB");
                    break;
                case "en":
                    alertEmpty.setTitle("Empty Field");
                    alertEmpty.setContentText("Please Fill In The Search Field");
                    break;
                default:
                    break;
            }
            alertEmpty.setHeaderText(null);
            alertEmpty.showAndWait();
        }
    }


    /**
     * shows the event details
     */
    public void showEventDetails(){
        //TODO: show specific event with id
        mainCtrl.showEventOverview("123");
    }

    /**
     * should order the event list of search on date made
     */
    public void onDateOrderClick(){
//        tableView.setSortPolicy();
    }

    public void onTitleOrderClick(){
//        colEventTitle.setSortType(TableColumn.SortType.ASCENDING);
//        refresh();
        var events = server.getAllEvents();
        List<Event> sortedEvents = events.stream().sorted().toList();
        ObservableList<Event> eventsSorted = FXCollections.observableList(sortedEvents);
        table.setItems(eventsSorted);

    }

    public void refresh(){
        var events = server.getAllEvents();
        allEvents = FXCollections.observableList(events);
        table.setItems(allEvents);
    }

    public void onDeleteClick(TableColumn.CellDataFeatures<Event, Button> q){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        switch(locale.getLanguage()) {
            case "nl":
                alert.setTitle("Evenement Verwijderen");
                alert.setContentText("Weet je zeker dat je het evenement wilt verwijderen?");
                break;
            case "en":
                alert.setTitle("Delete Event");
                alert.setContentText("Are you sure you want to delete the event?");
                break;
            default:
                break;
        }
        alert.setHeaderText(null);
        Optional<ButtonType> result=alert.showAndWait();
        if(result.get()==ButtonType.OK){
            server.deleteEvent(q.getValue().getID());
            refresh();
        }
    }

    /**
     * initializes the columns of the table view with string values of events
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colEventTitle.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getName()));
        colInvitationID.setCellValueFactory(q -> new SimpleStringProperty(
            q.getValue().getInvitationID()));
        colDelete.setCellValueFactory(q -> {
            Button delete = new Button();
            ImageView imageView = new ImageView();
            imageView.setFitHeight(20);
            imageView.setFitWidth(20);
            Image trash =new Image(Objects.requireNonNull
                (getClass().getResourceAsStream("/icons/trash.png")));
            imageView.setImage(trash);
            delete.setGraphic(imageView);
            delete.setOnAction(event -> onDeleteClick(q));
            return new SimpleObjectProperty<>(delete);
            });
        }

    @Override
    public void LanguageSwitch() {
        orderByText.setText(Main.getLocalizedString("orderBy"));
        logOutButton.setText(Main.getLocalizedString("logOut"));
        eventNameTextField.setText(Main.getLocalizedString("searchEvent"));
        searchButton.setText(Main.getLocalizedString("Search"));
        titleButton.setText(Main.getLocalizedString("Title"));
        dateButton.setText(Main.getLocalizedString("Date"));
        activityButton.setText(Main.getLocalizedString("Activity"));
        refreshButton.setText(Main.getLocalizedString("Refresh"));
        colEventTitle.setText(Main.getLocalizedString("Title"));
        colInvitationID.setText(Main.getLocalizedString("invitationID"));
        //delete.setText(Main.getLocalizedString("Delete"));
    }

}
