package client.scenes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class RecentEvent {
    HBox hbox;
    // Event event;
    Button link;
    Button remove;

    public RecentEvent() {
        // this.event = event;
        link = new Button("Open");
        // might need to put the url from event into the param
        // link.setOnAction(MainCtrl.ShowEventOverview());
        remove = new Button("rem");
        remove.setOnAction(e -> removeRecentEvent());
        hbox = new HBox();
        int num = (int) (Math.random() * 10);
        hbox.getChildren().add(new Text("[test: " + num + "]"));
        hbox.getChildren().add(link);
        hbox.getChildren().add(remove);
        hbox.setAlignment(Pos.CENTER_LEFT);

    }

    public HBox getNode(){
        return hbox;
    }

    private void removeRecentEvent() {
        VBox parent = (VBox) hbox.getParent();
        parent.getChildren().remove(hbox);
    }
}
