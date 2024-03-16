package client.nodes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class RecentExpense {
    // Event event;
    private HBox hbox;
    private Button edit;
    private boolean isFrom;

    public RecentExpense() {
        // this.expense = expense;
        edit = new Button("edit");
        edit.setOnAction(e -> removeExpense());
        hbox = new HBox();

        //placeholder text, will take from expense eventually
        int num = (int) (Math.random() * 10);
        isFrom = num < 5;
        Text text = new Text("[expense: " + num + "]");

        hbox.getChildren().add(text);
        hbox.getChildren().add(edit);
        hbox.setAlignment(Pos.CENTER_LEFT);

    }

    public HBox getNode(){
        return hbox;
    }

    private void removeExpense() {
        VBox parent = (VBox) hbox.getParent();
        parent.getChildren().remove(hbox);
    }

    public boolean isFrom() {
        return isFrom;
    }
}
