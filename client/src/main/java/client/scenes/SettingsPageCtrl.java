package client.scenes;

import client.Main;
import client.utils.ServerUtils;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;

import javax.inject.Inject;

public class SettingsPageCtrl implements Main.LanguageSwitch {

    private ServerUtils serverUtils;
    private MainCtrl mainCtrl;

    @FXML
    private MenuButton themeButton;

    @Inject
    public SettingsPageCtrl(ServerUtils serverUtils, MainCtrl mainCtrl){
        this.serverUtils=serverUtils;
        this.mainCtrl=mainCtrl;
    }

    public void onEnglishSwitchClick() {
        Main.switchLocale("translations", "en");
    }
    public void onDutchSwitchClick() {
        Main.switchLocale("translations", "nl");
    }

    public void closeWindow(){
        mainCtrl.showStartScreen();
    }

    @Override
    public void LanguageSwitch() {
    }
}
