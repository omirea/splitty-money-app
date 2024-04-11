package client.scenes;

import client.Main;
import client.nodes.ConnectionSetup;
import client.nodes.LanguageSwitch;
import client.nodes.ThemeService;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Objects;


public class SettingsPageCtrl implements Main.LanguageSwitch {


    private ConnectionSetup connectionSetup;
    private final MainCtrl mainCtrl;
    private LanguageSwitch languageSwitch;
    private ThemeService themeService;

    @FXML
    private RadioMenuItem englishButton;
    @FXML
    private ImageView englishView;
    @FXML
    private RadioMenuItem dutchButton;
    @FXML
    private ImageView dutchView;
    @FXML
    private MenuButton languageMenuButton;
    @FXML
    private MenuButton themeButton;
    @FXML
    private RadioMenuItem lightModeButton;
    @FXML
    private RadioMenuItem darkModeButton;


    @Inject
    public SettingsPageCtrl(MainCtrl mainCtrl, ConnectionSetup cs, LanguageSwitch ls, ThemeService ts){
        connectionSetup = cs;
        languageSwitch = ls;
        this.mainCtrl=mainCtrl;
        this.themeService=ts;
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
    }

    public void onEnglishSwitchClick() {
        dutchButton.setSelected(false);
        languageSwitch.saveToConfig("translations_en");
        Main.switchLocale("translations", "en");
    }
    public void onDutchSwitchClick() {
        englishButton.setSelected(false);
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
    public void darkMode()  {
        lightModeButton.setSelected(false);
        darkModeButton.setSelected(true);
        themeService.saveToConfig("stylesheets/darkMode.css");
        mainCtrl.changeTheme("stylesheets/darkMode.css");
    }


    public void lightMode() {
        darkModeButton.setSelected(false);
        lightModeButton.setSelected(true);
        themeService.saveToConfig("stylesheets/whiteMode.css");
        mainCtrl.changeTheme("stylesheets/whiteMode.css");
    }

}
