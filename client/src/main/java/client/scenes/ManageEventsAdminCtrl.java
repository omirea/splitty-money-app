package client.scenes;

import client.nodes.EventsInList;
import client.utils.ServerUtils;
import commons.Event;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;


public class ManageEventsAdminCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private TextField eventName;
    @FXML
    private Button search;
    @FXML
    private Button title;
    @FXML
    private Button date;
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
    private TableColumn<Event, Button> buttons;



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

        String eventNameS = eventName.getText().trim();
        //TODO: implement search in database.
        //TODO: commented out code below is method to show all events gotten from search in DB.
//        List<Event> eventsFromSearch = new ArrayList<>();
//        for(int x = 0; x < eventsFromSearch.size(); x++ ){
//            String n = eventsFromSearch.get(x).getName();
//            EventsInList eil = new EventsInList(n);
//            eventList.getChildren().add(eil.getNode());
//        }
        //TODO: delete this below once above is implemented.
        EventsInList el = new EventsInList("hello");
//        eventList.getChildren().add(el.getNode());
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

    public void refresh(){
        var events = server.getAllEvents();
        allEvents = FXCollections.observableList(events);
        table.setItems(allEvents);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colEventTitle.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getName()));
        colInvitationID.setCellValueFactory(q -> new SimpleStringProperty(
            q.getValue().getInvitationID()));
    }
}
