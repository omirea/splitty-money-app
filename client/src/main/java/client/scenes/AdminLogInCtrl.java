package client.scenes;

import client.utils.ServerUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;

import javax.inject.Inject;

public class AdminLogInCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private PasswordField passwordField;


    @Inject public AdminLogInCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * method to connect into admin account
     * @return true or false if login was successful
     */
    @FXML protected boolean onClickLogIn(){
        if(hasNoEmptyFields() && checkPassword()){
            mainCtrl.showEventsAdmin();
            passwordField.setText("");
            return true;
        } else return false;
    }

    /**
     * method to display the Start Screen
     */
    @FXML protected void onClickHome(){
        mainCtrl.showStartScreen();
    }

    /**
     * method to check if the text filled are empty
     * @return true if the fields are both filled, else false
     */
    public boolean hasNoEmptyFields(){
        boolean passwordInput = passwordField.getText().trim().isEmpty();
        if(!passwordInput){
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
        String passwordInput = passwordField.getText().trim();
        if(server.checkPassword(passwordInput)){
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

    public void generatePassword() {
        server.generatePassword();
    }
}
