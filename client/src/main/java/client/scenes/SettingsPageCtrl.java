package client.scenes;

import client.Main;
import client.utils.ServerUtils;
import javafx.fxml.FXML;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.inject.Inject;
import java.util.Objects;

public class SettingsPageCtrl implements Main.LanguageSwitch {

    private ServerUtils serverUtils;
    private MainCtrl mainCtrl;

    @FXML
    private RadioMenuItem englishButton;
    @FXML
    private ImageView englishView;
    @FXML
    private RadioMenuItem dutchButton;
    @FXML
    private ImageView dutchView;

    @Inject
    public SettingsPageCtrl(ServerUtils serverUtils, MainCtrl mainCtrl){
        this.serverUtils=serverUtils;
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
    }

    public void onEnglishSwitchClick() {
        dutchButton.setSelected(false);
        Main.switchLocale("translations", "en");
    }
    public void onDutchSwitchClick() {
        englishButton.setSelected(false);
        Main.switchLocale("translations", "nl");
    }


    @Override
    public void LanguageSwitch() {
    }
}
