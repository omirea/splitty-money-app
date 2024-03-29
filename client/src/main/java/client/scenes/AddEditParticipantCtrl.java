package client.scenes;

import client.Main;
import client.utils.ServerUtils;
import commons.Event;
import commons.Participant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.inject.Inject;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static client.Main.locale;

public class AddEditParticipantCtrl implements Main.LanguageSwitch{

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private Event event;
    @FXML
    private TextField bicTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField ibanTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label addEditParticipantLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label ibanLabel;
    @FXML
    private Label bicLabel;


    @Inject
    public AddEditParticipantCtrl(ServerUtils server, MainCtrl mainCtrl){
        this.server=server;
        this.mainCtrl=mainCtrl;
    }
    @FXML
    private void onClickDeleteAll(ActionEvent event) {
        nameTextField.clear();
        emailTextField.clear();
        ibanTextField.clear();
        bicTextField.clear();
        mainCtrl.showEventOverview(this.event.getInvitationID());
    }

    public void setEvent(String id) {
        event = server.getEventById(id);
    }

    @FXML
    void onClickOk(ActionEvent event) {
        if(checkEmpty() && validateEmail() && isIbanValid()){
        // TODO: Add to database
            String name= nameTextField.getText();
            String email=emailTextField.getText();
            String iban=ibanTextField.getText();
            String bic=bicTextField.getText();

            Participant p= new Participant(name,email,iban,bic);

           server.createParticipant(p);

            Alert alert=new Alert(Alert.AlertType.INFORMATION);

            switch(locale.getLanguage()) {
                case "nl":
                    alert.setTitle("Succesvol toevoegen");
                    alert.setContentText("Deelnemer succesvol toegevoegd");
                    break;
                case "en":
                    alert.setTitle("Adding Successful");
                    alert.setContentText("Participant Added Successfully");
                    break;
                default:
                    break;
            }
            alert.setHeaderText(null);
            alert.showAndWait();

            mainCtrl.showEventOverview(this.event.getInvitationID());
        }
    }

    public ServerUtils getServer() {
        return server;
    }

    public MainCtrl getMainCtrl() {
        return mainCtrl;
    }

    public TextField getBicTextField() {
        return bicTextField;
    }

    public void setBicTextField(TextField bicTextField) {
        this.bicTextField = bicTextField;
    }

    public TextField getEmailTextField() {
        return emailTextField;
    }

    public void setEmailTextField(TextField emailTextField) {
        this.emailTextField = emailTextField;
    }

    public TextField getIbanTextField() {
        return ibanTextField;
    }

    public void setIbanTextField(TextField ibanTextField) {
        this.ibanTextField = ibanTextField;
    }

    public TextField getNameTextField() {
        return nameTextField;
    }

    public void setNameTextField(TextField nameTextField) {
        this.nameTextField = nameTextField;
    }

    public Button getOkButton() {
        return okButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddEditParticipantCtrl that = (AddEditParticipantCtrl) o;
        return Objects.equals(server, that.server) && Objects.equals(mainCtrl, that.mainCtrl)
            && Objects.equals(bicTextField, that.bicTextField)
            && Objects.equals(emailTextField, that.emailTextField)
            && Objects.equals(ibanTextField, that.ibanTextField)
            && Objects.equals(nameTextField, that.nameTextField)
            && Objects.equals(okButton, that.okButton)
            && Objects.equals(cancelButton, that.cancelButton);
    }

    @Override
    public int hashCode() {
        return Objects.hash(server, mainCtrl, bicTextField, emailTextField, ibanTextField,
            nameTextField, okButton, cancelButton);
    }

    /**
     * method to check if the email input is valid
     * @return true or false if the email is valid
     */
    public boolean validateEmail(){
        String regex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\." +
            "[0-9]{1,3}\\.[0-9]{1,3}\\])" +
                "|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p= Pattern.compile(regex);
        Matcher m= p.matcher(emailTextField.getText().trim());
        if(m.find() && m.group().equals(emailTextField.getText().trim())){
            return true;
        }else{
            Alert alert=new Alert(Alert.AlertType.WARNING);
            switch(locale.getLanguage()) {
                case "nl":
                    alert.setTitle("Ongeldige E-Mail");
                    alert.setContentText("Vul een geldig E-Mail adres in");
                    break;
                case "en":
                    alert.setTitle("Validate E-Mail");
                    alert.setContentText("Please enter a valid E-Mail");
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
     * method to check if IBAN is valid
     * @return true or false if IBAN is valid
     */
    private boolean isIbanValid() {
        int IBAN_MIN_SIZE = 15;
        int IBAN_MAX_SIZE = 34;
        long IBAN_MAX = 999999999;
        long IBAN_MODULUS = 97;
        String trimmed = ibanTextField.getText().trim();
        //System.out.println(ibanTextField.getText().trim());
        if (trimmed.length() < IBAN_MIN_SIZE || trimmed.length() > IBAN_MAX_SIZE) {
            Alert alert=new Alert(Alert.AlertType.WARNING);
            switch(locale.getLanguage()) {
                case "nl":
                    alert.setTitle("Ongeldige IBAN");
                    alert.setContentText("Vul een geldige IBAN in");
                    break;
                case "en":
                    alert.setTitle("Non-Valid IBAN");
                    alert.setContentText("Please enter a valid IBAN");
                    break;
                default:
                    break;
            }
            alert.setHeaderText(null);
            alert.showAndWait();
            return false;
        }
        String reformat = trimmed.substring(4) + trimmed.substring(0, 4);

        long total = 0;
        for (int i = 0; i < reformat.length(); i++) {
            int charValue = Character.getNumericValue(reformat.charAt(i));
            if (charValue < 0 || charValue > 35) {
                Alert alert=new Alert(Alert.AlertType.WARNING);
                switch(locale.getLanguage()) {
                    case "nl":
                        alert.setTitle("Ongeldige IBAN");
                        alert.setContentText("Vul een geldige IBAN in");
                        break;
                    case "en":
                        alert.setTitle("Non-Valid IBAN");
                        alert.setContentText("Please enter a valid IBAN");
                        break;
                    default:
                        break;
                }
                alert.setHeaderText(null);
                alert.showAndWait();
                return false;

            }
            total = (charValue > 9 ? total * 100 : total * 10) + charValue;
            if (total > IBAN_MAX) {
                total = (total % IBAN_MODULUS);
            }
        }
        if((total % IBAN_MODULUS) == 1){
            return true;
        }
        else{
            Alert alert=new Alert(Alert.AlertType.WARNING);
            switch(locale.getLanguage()) {
                case "nl":
                    alert.setTitle("Ongeldige IBAN");
                    alert.setContentText("Vul een geldige IBAN in");
                    break;
                case "en":
                    alert.setTitle("Non-Valid IBAN");
                    alert.setContentText("Please enter a valid IBAN");
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
     * method to check if any text box is empty
     * @return true or false if any text boxes are empty
     */
    public boolean checkEmpty(){
        boolean name= nameTextField.getText().trim().isEmpty();
        boolean email= emailTextField.getText().trim().isEmpty();
        boolean iban= ibanTextField.getText().trim().isEmpty();
        boolean bic= bicTextField.getText().trim().isEmpty();

        if(!(name || email || iban || bic)){
            return true;
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            switch(locale.getLanguage()) {
                case "nl":
                    alert.setTitle("Niet ingevulde velden");
                    alert.setContentText("Alle velden invullen AUB");
                    break;
                case "en":
                    alert.setTitle("Empty fields");
                    alert.setContentText("Please fill in all the fields");
                    break;
                default:
                    break;
            }
            alert.setHeaderText(null);
            alert.showAndWait();
            return false;
        }
    }

    @Override
    public void LanguageSwitch() {
        addEditParticipantLabel.setText(Main.getLocalizedString("addEditParticipant"));
        nameLabel.setText(Main.getLocalizedString("Name"));
        ibanLabel.setText(Main.getLocalizedString("IBAN"));
        emailLabel.setText(Main.getLocalizedString("Email"));
        bicLabel.setText(Main.getLocalizedString("BIC"));
        cancelButton.setText(Main.getLocalizedString("Cancel"));
        okButton.setText(Main.getLocalizedString("Ok"));
    }
}