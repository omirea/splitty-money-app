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
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;


import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


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
    private Button activity;


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



    @FXML
    private Button activityButton;
    @FXML
    private VBox eventList;
    @FXML
    private Button logOutButton;
    @FXML
    private Text orderByText;



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
            alertEmpty.setTitle("Empty Field");
            alertEmpty.setHeaderText(null);
            alertEmpty.setContentText("Please Fill In The Search Field");
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
        server.deleteEvent(q.getValue().getID());
        refresh();
    }

    /**
     * initializes the columns of the table view with string values of events
     * @param url location
     * @param resourceBundle resources
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colEventTitle.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getName()));
        colInvitationID.setCellValueFactory(q -> new SimpleStringProperty(
            q.getValue().getInvitationID()));
        colDelete.setCellValueFactory(q -> {
            Button delete = new Button("Delete");
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
    }

}
