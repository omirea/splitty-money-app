package client.scenes;

import client.Main;
import client.nodes.ConnectionSetup;
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


    ConnectionSetup connectionSetup;

    private final MainCtrl mainCtrl;

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
    public SettingsPageCtrl(ConnectionSetup cs, MainCtrl mainCtrl){
        connectionSetup = cs;
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

    public void onChangeServerClick() {
        connectionSetup.promptUser();
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

    /**
     *
     */
    @Override
    public void LanguageSwitch() {

    }
}
