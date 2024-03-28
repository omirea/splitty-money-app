package client.nodes;

import javafx.scene.control.Button;

public class EventListButtons {

    private Button delete;

    public void EventsInList (){
        this.delete = new Button("Delete");
    }

    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }
}
