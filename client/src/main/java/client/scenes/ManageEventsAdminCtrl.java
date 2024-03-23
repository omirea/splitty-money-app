package client.scenes;

import client.nodes.EventsInList;
import client.utils.RefServerUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import javax.inject.Inject;



public class ManageEventsAdminCtrl {

    private final RefServerUtils server;
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
    private VBox eventList;

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
        //TODO: commented out code below is method to show all events gotten from search in DB.
//        List<Event> eventsFromSearch = new ArrayList<>();
//        for(int x = 0; x < eventsFromSearch.size(); x++ ){
//            String n = eventsFromSearch.get(x).getName();
//            EventsInList eil = new EventsInList(n);
//            eventList.getChildren().add(eil.getNode());
//        }
        //TODO: delete this below once above is implemented.
        EventsInList el = new EventsInList("hello");
        eventList.getChildren().add(el.getNode());
    }

    public void showEventDetails(){
        //TODO: show specific event with id
        mainCtrl.showEventOverview("");
    }


    public void onDateOrderClick(){
//        tableView.setSortPolicy();
    }
}
