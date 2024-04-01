package client.nodes;

import client.scenes.MainCtrl;
import commons.Event;
import jakarta.inject.Inject;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Objects;

public class RecentEvent {
    HBox hbox;
     Event event;

     MainCtrl mainCtrl;
    Button link;
    Button remove;

    public RecentEvent(/*Event event*/) {
        // this.event = event;
        link = new Button("Open");
        // might need to put the url from event into the param
        // link.setOnAction(mainCtrl.ShowEventOverview(event.getId()));
        remove = new Button("rem");
        remove.setOnAction(e -> removeRecentEvent());
        hbox = new HBox();
        int num = (int) (Math.random() * 10);
        hbox.getChildren().add(new Text("[test: " + num + "]"));
        hbox.getChildren().add(link);
        hbox.getChildren().add(remove);
        hbox.setAlignment(Pos.CENTER_LEFT);

    }

    @Inject
    public RecentEvent(Event event, MainCtrl mainCtrl) {
        this.event = event;
        this.mainCtrl=mainCtrl;
        link = new Button("Open ");
        // might need to put the url from event into the param
        link.setOnAction(e -> openRecentEvent());
        remove = new Button();
        ImageView imageView = new ImageView();
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        Image trash =new Image(Objects.requireNonNull
            (getClass().getResourceAsStream("/icons/trash.png")));
        imageView.setImage(trash);
        remove.setGraphic(imageView);
        remove.setOnAction(e -> removeRecentEvent());
        hbox = new HBox();
        hbox.getChildren().add(new Text(this.event.getName() +"  "));
        hbox.getChildren().add(link);
        hbox.getChildren().add(remove);
        hbox.setMargin(remove, new Insets(0, 0, 0, 10));
        hbox.setAlignment(Pos.CENTER_LEFT);

    }

//
//
//
//
//        switch (locale.getLanguage()){
//            case "nl": link = new Button("Open ");
//                link.setOnAction(e -> openRecentEvent());
//                remove = new Button("Verwijder ");
//                remove.setOnAction(e -> removeRecentEvent());
//                break;
//            case "en":
//                link = new Button("Open ");
//                link.setOnAction(e -> openRecentEvent());
//                remove = new Button("Remove ");
//                remove.setOnAction(e -> removeRecentEvent());
//                break;
//            default: break;






    public HBox getNode(){
        return hbox;
    }

    private void removeRecentEvent() {
        VBox parent = (VBox) hbox.getParent();
        parent.getChildren().remove(hbox);
    }

    private void openRecentEvent() {
        mainCtrl.showEventOverview(event.getInvitationID());
    }
}
