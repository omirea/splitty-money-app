package client.scenes;

import client.Main;
import client.nodes.EventsInList;
import client.utils.ServerUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import javax.inject.Inject;



public class ManageEventsAdminCtrl implements Main.LanguageSwitch {

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

        String eventNameS = eventNameTextField.getText().trim();
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

    @Override
    public void LanguageSwtich() {
        orderByText.setText(Main.getLocalizedString("orderBy"));
        logOutButton.setText(Main.getLocalizedString("logOut"));
        eventNameTextField.setText(Main.getLocalizedString("searchEvent"));
        searchButton.setText(Main.getLocalizedString("Search"));
        titleButton.setText(Main.getLocalizedString("Title"));
        dateButton.setText(Main.getLocalizedString("Date"));
        activityButton.setText(Main.getLocalizedString("Activity"));
    }
}
