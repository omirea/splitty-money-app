package client.scenes;

import client.utils.RefServerUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javax.inject.Inject;

public class AdminLogInCtrl {

    private final RefServerUtils server;
    private final MainCtrl mainCtrl;
    @FXML
    private VBox vboxContainer;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Text textUsername;

    @FXML
    private Text textPassword;

    @FXML
    private Button homeButton;

    @FXML
    private Button logInButton;


    @Inject
    public AdminLogInCtrl(RefServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @FXML
    public boolean onClickLogIn(){
        if(checkPassword() && checkEmptyFields() && checkUsername() ){
            //mainCtrl.showAdminLogIn();
            // TODO: connect to admin overview events;
            return true;
        } else return false;
    }

    @FXML
    public void onClickHome(){
        mainCtrl.showStartScreen();
    }


    public boolean checkEmptyFields(){
        boolean usernameField = username.getText().trim().isEmpty();
        boolean passwordField = password.getText().trim().isEmpty();

        if(!(usernameField || passwordField)){
            return true;
        } else {
            Alert alertEmpty = new Alert(Alert.AlertType.WARNING);
            alertEmpty.setTitle("Empty Field(s)");
            alertEmpty.setHeaderText(null);
            alertEmpty.setContentText("Please Fill In All The Fields");
            alertEmpty.showAndWait();
            return false;
        }
    }

    public boolean checkPassword(){
        String passwordAdmin = "AdminPassword123!";
        String passwordFromField = password.getText().trim();
        if(passwordFromField.equals(passwordAdmin)){
            return true;
        } else {
            Alert alertPassword = new Alert(Alert.AlertType.WARNING);
            alertPassword.setTitle("Password is incorrect");
            alertPassword.setHeaderText(null);
            alertPassword.setContentText("Try password again");
            alertPassword.showAndWait();
            return false;
        }
    }

    public boolean checkUsername(){
        String usernameAdmin = "AdminSplitty";
        String usernameFromField = username.getText().trim();
        if(usernameAdmin.equals(usernameFromField)){
            return true;
        } else {
            Alert alertUsername = new Alert(Alert.AlertType.WARNING);
            alertUsername.setTitle("Username is incorrect");
            alertUsername.setHeaderText(null);
            alertUsername.setContentText("Try username again");
            alertUsername.showAndWait();
            return false;
        }
    }






}
