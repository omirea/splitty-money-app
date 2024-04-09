package client.scenes;

import client.Main;
import client.utils.ServerUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Objects;

import static client.Main.locale;

public class AdminLogInCtrl implements Main.LanguageSwitch {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private PasswordField passwordField;
    @FXML
    private Button homeButton;
    @FXML
    private ImageView homeView;
    @FXML
    private Text passwordText;
    @FXML
    private Button logInButton;


    @Inject
    public AdminLogInCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * method to initialize the page
     */
    @FXML
    public void initialize() {
        //set home button
        homeView.setFitHeight(25);
        homeView.setFitWidth(22);
        Image setting=new Image(Objects.requireNonNull
                (getClass().getResourceAsStream("/icons/home.png")));
        homeView.setImage(setting);
        homeButton.setGraphic(homeView);
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
    @FXML protected void onClickHome() throws IOException {
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
            Alert alert=new Alert(Alert.AlertType.WARNING);
            switch(locale.getLanguage()) {
                case "nl":
                    alert.setTitle("Niet ingevulde velden");
                    alert.setContentText("Velden invullen AUB");
                    break;
                case "en":
                    alert.setTitle("Empty fields");
                    alert.setContentText("Please fill in all the fields>");
                    break;
                default:
                    break;
            }
            alert.setHeaderText(null);
            alert.showAndWait();
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
            switch(locale.getLanguage()) {
                case "nl":
                    alertPassword.setTitle("Ongeldig wachtwoord");
                    alertPassword.setContentText("Wachtwoord is fout, probeer opnieuw");
                    break;
                case "en":
                    alertPassword.setTitle("Password is incorrect");
                    alertPassword.setContentText("Try Password again");
                    break;
                default:
                    break;
            }
            alertPassword.setHeaderText(null);
            alertPassword.showAndWait();
            return false;
        }
    }

    public void generatePassword() {
        server.generatePassword();
    }

    @Override
    public void LanguageSwitch() {
        homeButton.setText(Main.getLocalizedString("Home"));
        passwordText.setText(Main.getLocalizedString("Password"));
        logInButton.setText(Main.getLocalizedString("logIn"));
    }
}
