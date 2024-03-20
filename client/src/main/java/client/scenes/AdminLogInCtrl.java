package client.scenes;

import client.utils.RefServerUtils;
import javafx.fxml.FXML;
//import javafx.scene.control.Alert;
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
    private TextField textUsername;

    @FXML
    private PasswordField textPassword;


    @Inject
    public AdminLogInCtrl(RefServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * method to connect into admin account
     * @return true or false if login was successful
     */
    public boolean onClickLogIn(){
        if(checkEmptyFields() && checkPassword() && checkUsername() ){
            mainCtrl.showEventsAdmin();
            textPassword.setText("");
            textUsername.setText("");
            return true;
        } else return false;
    }

    /**
     * method to display the Start Screen
     */
    public void onClickHome(){
        mainCtrl.showStartScreen();
    }

    /**
     * method to check if the text filled are empty
     * @return true if the fields are both filled, else false
     */
    public boolean checkEmptyFields(){
        boolean usernameField = textUsername.getText().trim().isEmpty();
        boolean passwordField = textPassword.getText().trim().isEmpty();

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

    /**
     * method to check if the password is correct
     * @return true if the password is correct, else false
     */
    public boolean checkPassword(){
        //TODO: Figure out how to do specific username/password combinations
        String passwordAdmin = "AdminPassword123!";
        String passwordFromField = textPassword.getText().trim();
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

    /**
     * method to check if the username is correct
     * @return true if the username is correct, else false
     */
    public boolean checkUsername(){
        //TODO: Figure out how to do specific username/password combinations
        String usernameAdmin = "AdminSplitty";
        String usernameFromField = textUsername.getText().trim();
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
