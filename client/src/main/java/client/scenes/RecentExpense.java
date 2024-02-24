package client.scenes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class RecentExpense {
    HBox hbox;
    // Event event;
    Button edit;

    public RecentExpense(/*Expense expense*/) {
        // this.expense = expense;
        edit = new Button("edit");
        edit.setOnAction(e -> editExpense());
        hbox = new HBox();

        //placeholder text, will take from expense eventually
        int num = (int) (Math.random() * 10);
        Text text = new Text("[expense: " + num + "]");

        hbox.getChildren().add(text);
        hbox.getChildren().add(edit);
        hbox.setAlignment(Pos.CENTER_LEFT);

    }

    public HBox getNode(){
        return hbox;
    }

    private void editExpense() {
        VBox parent = (VBox) hbox.getParent();
        parent.getChildren().remove(hbox);
    }
}
