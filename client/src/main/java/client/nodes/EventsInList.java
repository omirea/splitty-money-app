package client.nodes;

import client.scenes.MainCtrl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class EventsInList {
    private HBox hbox;
    private MainCtrl mainCtrl;
    private String eventName;
    private Button delete;
    private Button JSON;
    private Button details;

    public EventsInList(String eventName){
        this.eventName = eventName;
        Text name = new Text(eventName);
        delete = new Button("Delete");
        delete.setOnAction(e -> deleteEventFromDB());

        JSON = new Button("JSON");
        JSON.setOnAction(e -> getJSONFile());

        details = new Button("Details");
//        details.setOnAction(e -> ManageEventsAdminCtrl.showEventDetails());
        hbox = new HBox();
        hbox.getChildren().add(name);
        hbox.setMargin(name, new Insets(10, 0, 0, 0));
        hbox.getChildren().add(details);
        hbox.setMargin(details, new Insets(10, 15, 0, 40));
        hbox.getChildren().add(JSON);
        hbox.setMargin(JSON, new Insets(10, 15, 0, 0));
        hbox.getChildren().add(delete);
        hbox.setMargin(delete, new Insets(10, 15, 0, 0));
        hbox.setAlignment(Pos.CENTER_LEFT);

    }
    public HBox getNode(){
        return hbox;
    }

    private void getJSONFile() {
        //TODO: get JSON file from specific event
    }

    private void deleteEventFromDB() {
        //TODO: delete event with id from DB
        VBox parent = (VBox) hbox.getParent();
        parent.getChildren().remove(hbox);

    }

    private void showEventDetails(){
        //TODO: show specific event with id
//        ManageEventsAdminCtrl.showEventDetails();
    }

    @Override
    public String toString() {
        return eventName;
    }
}
