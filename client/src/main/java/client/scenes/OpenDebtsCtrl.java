package client.scenes;
import client.utils.ServerUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

import javax.inject.Inject;
import java.util.Objects;

public class OpenDebtsCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private Button arrowButton;

    @FXML
    private HBox hboxContainer;

    @FXML
    private Button markButton;

    @FXML
    private Button payAllDebts;

    @FXML
    private Button back;

    @Inject
    public OpenDebtsCtrl(ServerUtils server, MainCtrl mainCtrl){
        this.server=server;
        this.mainCtrl=mainCtrl;
    }

    public ServerUtils getServer() {
        return server;
    }

    public MainCtrl getMainCtrl() {
        return mainCtrl;
    }

    public Button getArrowButton() {
        return arrowButton;
    }

    public void setArrowButton(Button arrowButton) {
        this.arrowButton = arrowButton;
    }

    public Button getMarkButton() {
        return markButton;
    }

    public void setMarkButton(Button markButton) {
        this.markButton = markButton;
    }

    public void areYouSure(){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Select all debts as paid");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to mark all debts as settled?");
        alert.showAndWait();
    }

    public void goBackToEvent(){
        mainCtrl.showEventOverview("1023");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OpenDebtsCtrl that = (OpenDebtsCtrl) o;
        return Objects.equals(server, that.server) && Objects.equals(mainCtrl, that.mainCtrl) && Objects.equals(arrowButton, that.arrowButton) && Objects.equals(markButton, that.markButton);
    }

    @Override
    public String toString() {
        return "OpenDebtsCtrl{" +
                "server=" + server +
                ", mainCtrl=" + mainCtrl +
                ", arrowButton=" + arrowButton +
                ", markButton=" + markButton +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(server, mainCtrl, arrowButton, markButton);
    }

    @FXML
    void markReceived(ActionEvent event) {
    }

    @FXML
    void openTextField(ActionEvent event) {
        TextArea txt= new TextArea("aaa");
        hboxContainer.getChildren().add(txt);
    }




}
