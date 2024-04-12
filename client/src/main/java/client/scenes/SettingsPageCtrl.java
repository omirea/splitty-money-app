package client.scenes;

import client.Main;
import client.nodes.ConnectionSetup;
import client.nodes.LanguageSwitch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.inject.Inject;
import java.io.File;
import java.net.MalformedURLException;
import java.util.Objects;


public class SettingsPageCtrl implements Main.LanguageSwitch {


    private ConnectionSetup connectionSetup;
    private final MainCtrl mainCtrl;

    private LanguageSwitch languageSwitch;

    @FXML
    private RadioMenuItem englishButton;
    @FXML
    private ImageView englishView;
    @FXML
    private RadioMenuItem dutchButton;
    @FXML
    private ImageView dutchView;
    @FXML
    private RadioMenuItem turkishButton;
    @FXML
    private ImageView turkishView;
    @FXML
    private MenuButton languageMenuButton;
    @FXML
    private MenuButton themeButton;
    @FXML
    private RadioMenuItem lightModeButton;
    @FXML
    private RadioMenuItem darkModeButton;



    @Inject
    public SettingsPageCtrl(ConnectionSetup cs, LanguageSwitch ls, MainCtrl mainCtrl){
        connectionSetup = cs;
        languageSwitch = ls;
        this.mainCtrl=mainCtrl;
    }

    @FXML
    public void initialize(){
        //set english flag
        englishView.setFitHeight(25);
        englishView.setFitWidth(22);
        Image setting=new Image(Objects.requireNonNull
                (getClass().getResourceAsStream("/icons/english.png")));
        englishView.setImage(setting);
        englishButton.setGraphic(englishView);

        //set dutch flag
        dutchView.setFitHeight(25);
        dutchView.setFitWidth(22);
        setting=new Image(Objects.requireNonNull
                (getClass().getResourceAsStream("/icons/dutch.png")));
        dutchView.setImage(setting);
        dutchButton.setGraphic(dutchView);

        turkishView.setFitHeight(25);
        turkishView.setFitWidth(22);
        setting=new Image(Objects.requireNonNull
                (getClass().getResourceAsStream("/icons/turkish.png")));
        turkishView.setImage(setting);
        turkishButton.setGraphic(turkishView);
    }

    public void onEnglishSwitchClick() {
        dutchButton.setSelected(false);
        turkishButton.setSelected(false);
        languageSwitch.saveToConfig("translations_en");
        Main.switchLocale("translations", "en");
    }
    public void onDutchSwitchClick() {
        englishButton.setSelected(false);
        turkishButton.setSelected(false);
        languageSwitch.saveToConfig("translations_nl");
        Main.switchLocale("translations", "nl");
    }

    public void onChangeServerClick() {
        connectionSetup.promptUser();
    }

    @Override
    public void LanguageSwitch() {
        languageMenuButton.setText(Main.getLocalizedString("Language"));
        themeButton.setText(Main.getLocalizedString("Theme"));
        englishButton.setText(Main.getLocalizedString("English"));
        dutchButton.setText(Main.getLocalizedString("Dutch"));
        lightModeButton.setText(Main.getLocalizedString("lightMode"));
        darkModeButton.setText(Main.getLocalizedString("darkMode"));

    }
    public void darkMode(ActionEvent event) throws MalformedURLException {
        lightModeButton.setSelected(false);
        File style = new File("./src/main/resources/style.css");
        mainCtrl.getSettingsPage().getStylesheets().
                add(style.toURI().toURL().toExternalForm());
        mainCtrl.getClosedDebts().getStylesheets().
                add(style.toURI().toURL().toExternalForm());
        mainCtrl.getExpense().getStylesheets().
                add(style.toURI().toURL().toExternalForm());
        mainCtrl.getInvitation().getStylesheets().
                add(style.toURI().toURL().toExternalForm());
        mainCtrl.getStart().getStylesheets().
                add(style.toURI().toURL().toExternalForm());
        mainCtrl.getOverview().getStylesheets().
                add(style.toURI().toURL().toExternalForm());
        mainCtrl.getManageParticipants().getStylesheets().
                add(style.toURI().toURL().toExternalForm());
        mainCtrl.getEventsAdmin().getStylesheets().
                add(style.toURI().toURL().toExternalForm());
        mainCtrl.getLogInAdmin().getStylesheets().
                add(style.toURI().toURL().toExternalForm());
        mainCtrl.getOpenDebts().getStylesheets().
                add(style.toURI().toURL().toExternalForm());
        mainCtrl.getAddEditParticipant().getStylesheets().
                add(style.toURI().toURL().toExternalForm());
        mainCtrl.getAddEditParticipant().getStylesheets().
                add(style.toURI().toURL().toExternalForm());
    }


    public void lightMode(ActionEvent actionEvent) throws MalformedURLException {
        darkModeButton.setSelected(false);
        File style = new File("./src/main/resources/style.css");
        mainCtrl.getSettingsPage().getStylesheets().
                remove(style.toURI().toURL().toExternalForm());
        mainCtrl.getClosedDebts().getStylesheets().
                remove(style.toURI().toURL().toExternalForm());
        mainCtrl.getExpense().getStylesheets().
                remove(style.toURI().toURL().toExternalForm());
        mainCtrl.getInvitation().getStylesheets().
                remove(style.toURI().toURL().toExternalForm());
        mainCtrl.getStart().getStylesheets().
                remove(style.toURI().toURL().toExternalForm());;
        mainCtrl.getOverview().getStylesheets().
                remove(style.toURI().toURL().toExternalForm());
        mainCtrl.getManageParticipants().getStylesheets().
                remove(style.toURI().toURL().toExternalForm());
        mainCtrl.getEventsAdmin().getStylesheets().
                remove(style.toURI().toURL().toExternalForm());
        mainCtrl.getLogInAdmin().getStylesheets().
                remove(style.toURI().toURL().toExternalForm());
        mainCtrl.getOpenDebts().getStylesheets().
                remove(style.toURI().toURL().toExternalForm());
        mainCtrl.getAddEditParticipant().getStylesheets().
                remove(style.toURI().toURL().toExternalForm());
        mainCtrl.getAddEditParticipant().getStylesheets().
                remove(style.toURI().toURL().toExternalForm());

    }


    public void onTurkishSwitchClick(ActionEvent event) {
        englishButton.setSelected(false);
        dutchButton.setSelected(false);
        languageSwitch.saveToConfig("translations_tr");
        Main.switchLocale("translations", "tr");
    }
}
