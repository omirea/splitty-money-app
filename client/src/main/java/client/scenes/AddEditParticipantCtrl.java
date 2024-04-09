package client.scenes;

import client.Main;
import client.utils.ServerUtils;
import commons.Event;
import commons.Participant;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static client.Main.locale;

public class AddEditParticipantCtrl implements Main.LanguageSwitch{

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    @FXML
    private TextField bicTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField accHolderTextField;
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


    Event event;
    Participant participant;

    @FXML
    public void initialize(){
        nameTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER)) {
                    emailTextField.requestFocus();
                }
            }
        });
        emailTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER)) {
                    accHolderTextField.requestFocus();
                }
            }
        });
        accHolderTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER)) {
                    ibanTextField.requestFocus();
                }
            }
        });
        ibanTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER)) {
                    bicTextField.requestFocus();
                }
            }
        });
        bicTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER)) {
                    onClickOk();
                }
            }
        });
    }

    @Inject
    public AddEditParticipantCtrl(ServerUtils server, MainCtrl mainCtrl){
        this.server=server;
        this.mainCtrl=mainCtrl;
    }
    @FXML
    private void onClickDeleteAll() {
        nameTextField.clear();
        emailTextField.clear();
        ibanTextField.clear();
        bicTextField.clear();
        accHolderTextField.clear();
        mainCtrl.showManageParticipants(event.getInvitationID(), participant);
        participant = null;
    }

    @FXML
    void onClickOk() {
        if(checkEmpty() && validateEmail(emailTextField.getText())
                && isIbanValid(ibanTextField.getText())) {
            String name = nameTextField.getText();
            String email = emailTextField.getText();
            String accHolder = accHolderTextField.getText();
            String iban = ibanTextField.getText();
            String bic = bicTextField.getText();

            if (!hasUniqueName(name)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                switch (locale.getLanguage()) {
                    case "nl":
                        alert.setTitle("Naam is al bezet");
                        alert.setContentText("Deze naam is al bezet, kies een andere naam.");
                        break;
                    case "en":
                        alert.setTitle("Name is already chose");
                        alert.setContentText("This name is already chosen, fill in another name");
                        break;
                    default:
                        break;
                }
                alert.setHeaderText(null);
                alert.showAndWait();
            } else {
                if (participant == null) {
                    participant = new Participant(name, email, accHolder, iban, bic, event);
                } else {
                    participant.setName(name);
                    participant.setEmail(email);
                    participant.setAccountHolder(accHolder);
                    participant.setIBAN(iban);
                    participant.setBIC(bic);
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                switch (locale.getLanguage()) {
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

                mainCtrl.showManageParticipants(this.event.getInvitationID(), participant);
                participant = null;
                nameTextField.clear();
                emailTextField.clear();
                accHolderTextField.clear();
                ibanTextField.clear();
                bicTextField.clear();
            }
        }
    }

    private boolean hasUniqueName(String name) {
        List<Participant> allP = server.getParticipantsByInvitationId(event.getInvitationID());
        boolean bool = true;
        for(Participant p : allP){
            if(p.getName().equals(name)){
                bool = false;
                return bool;
            }
        }
        return bool;
    }

    public ServerUtils getServer() {
        return server;
    }

    public MainCtrl getMainCtrl() {
        return mainCtrl;
    }

    public void setEvent(String id) {
        event = server.getEventByInvitationId(id);
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
        nameTextField.setText(participant.getName());
        emailTextField.setText(participant.getEmail());
        accHolderTextField.setText(participant.getAccountHolder());
        bicTextField.setText(participant.getBIC());
        ibanTextField.setText(participant.getIBAN());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddEditParticipantCtrl that = (AddEditParticipantCtrl) o;
        return Objects.equals(server, that.server) && Objects.equals(mainCtrl, that.mainCtrl)
                && Objects.equals(bicTextField, that.bicTextField)
                && Objects.equals(emailTextField, that.emailTextField)
                && Objects.equals(accHolderTextField, that.accHolderTextField)
                && Objects.equals(ibanTextField, that.ibanTextField)
                && Objects.equals(nameTextField, that.nameTextField)
                && Objects.equals(okButton, that.okButton)
                && Objects.equals(cancelButton, that.cancelButton);
    }

    @Override
    public int hashCode() {
        return Objects.hash(server, mainCtrl, bicTextField, emailTextField,
                accHolderTextField, ibanTextField, nameTextField, okButton, cancelButton);
    }

    /**
     * method to check if the email input is valid
     * @return true or false if the email is valid
     */
    public boolean validateEmail(String email){
        String regex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\." +
                "[0-9]{1,3}\\.[0-9]{1,3}\\])" +
                "|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p= Pattern.compile(regex);
        Matcher m= p.matcher(email.trim());
        if(email.equals("")) {
            return true;
        }
        if(m.find() && m.group().equals(email.trim())){
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
    public boolean isIbanValid(String IBAN) {
        int IBAN_MIN_SIZE = 15;
        int IBAN_MAX_SIZE = 34;
        long IBAN_MAX = 999999999;
        long IBAN_MODULUS = 97;
        String trimmed=IBAN.trim();
        //String trimmed = ibanTextField.getText().trim();
        //System.out.println(ibanTextField.getText().trim());
        if(IBAN.equals("")) {
            return true;
        }
        if (trimmed.length() < IBAN_MIN_SIZE || trimmed.length() > IBAN_MAX_SIZE) {
            Alert alert = getAlert();
            alert.showAndWait();
            return false;
        }
        String reformat = trimmed.substring(4) + trimmed.substring(0, 4);

        long total = 0;
        for (int i = 0; i < reformat.length(); i++) {
            int charValue = Character.getNumericValue(reformat.charAt(i));
            if (charValue < 0 || charValue > 35) {
                Alert alert = getAlert();
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
            Alert alert = getAlert();
            alert.showAndWait();
            return false;
        }
    }

    private Alert getAlert() {
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
        return alert;
    }

    /**
     * method to check if any text box is empty
     * @return true or false if any text boxes are empty
     */
    public boolean checkEmpty(){
        boolean name= nameTextField.getText().trim().isEmpty();
//        boolean email= emailTextField.getText().trim().isEmpty();
//        boolean iban= ibanTextField.getText().trim().isEmpty();
//        boolean bic= bicTextField.getText().trim().isEmpty();

        if(!(name)){
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
