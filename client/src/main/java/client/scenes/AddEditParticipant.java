package client.scenes;
import client.utils.ServerUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


import javax.inject.Inject;
import javafx.event.ActionEvent;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddEditParticipant {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
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
    private Button abortButton;

    @Inject
    public AddEditParticipant(ServerUtils server, MainCtrl mainCtrl){
        this.server=server;
        this.mainCtrl=mainCtrl;
    }
    @FXML
    private void onClickDeleteAll(ActionEvent event) {
        nameTextField.clear();
        emailTextField.clear();
        ibanTextField.clear();
        bicTextField.clear();
    }

    @FXML
    void onClickOk(ActionEvent event) {
        if(checkEmpty() && validateEmail() && isIbanValid()){
        // TODO: Add to database

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

    public void setOkButton(Button okButton) {
        this.okButton = okButton;
    }

    public Button getAbortButton() {
        return abortButton;
    }

    public void setAbortButton(Button abortButton) {
        this.abortButton = abortButton;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddEditParticipant that = (AddEditParticipant) o;
        return Objects.equals(server, that.server) && Objects.equals(mainCtrl, that.mainCtrl) && Objects.equals(bicTextField, that.bicTextField) && Objects.equals(emailTextField, that.emailTextField) && Objects.equals(ibanTextField, that.ibanTextField) && Objects.equals(nameTextField, that.nameTextField) && Objects.equals(okButton, that.okButton) && Objects.equals(abortButton, that.abortButton);
    }

    @Override
    public int hashCode() {
        return Objects.hash(server, mainCtrl, bicTextField, emailTextField, ibanTextField, nameTextField, okButton, abortButton);
    }

    public boolean validateEmail(){
        String regex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])" +
                "|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p= Pattern.compile(regex);
        //System.out.println(emailTextField.getText().trim());
        Matcher m= p.matcher(emailTextField.getText().trim());
        if(m.find() && m.group().equals(emailTextField.getText().trim())){
            return true;
        }else{
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Email");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter A Valid Email");
            alert.showAndWait();
            return false;
        }
    }

    public boolean checkEmpty(){
        boolean name= nameTextField.getText().trim().isEmpty();
        boolean email= emailTextField.getText().trim().isEmpty();
        boolean iban= ibanTextField.getText().trim().isEmpty();
        boolean bic= bicTextField.getText().trim().isEmpty();

        if(!(name || email || iban || bic)){
            return true;
        }
        else{
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty Field");
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All The Fields");
            alert.showAndWait();
            return false;
        }


    }

    private boolean isIbanValid() {
        int IBAN_MIN_SIZE = 15;
        int IBAN_MAX_SIZE = 34;
        long IBAN_MAX = 999999999;
        long IBAN_MODULUS = 97;
        String trimmed = ibanTextField.getText().trim();
        //System.out.println(ibanTextField.getText().trim());
        if (trimmed.length() < IBAN_MIN_SIZE || trimmed.length() > IBAN_MAX_SIZE) {
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Non-valid IBAN");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter A Valid IBAN");
            alert.showAndWait();
            return false;
        }
        String reformat = trimmed.substring(4) + trimmed.substring(0, 4);

        long total = 0;
        for (int i = 0; i < reformat.length(); i++) {
            int charValue = Character.getNumericValue(reformat.charAt(i));
            if (charValue < 0 || charValue > 35) {
                Alert alert=new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Non-valid IBAN");
                alert.setHeaderText(null);
                alert.setContentText("Please Enter A Valid IBAN");
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
            alert.setTitle("Non-valid IBAN");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter A Valid IBAN");
            alert.showAndWait();
            return false;
        }


    }
}
