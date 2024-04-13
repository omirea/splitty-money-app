package client.scenes;

import client.Main;
import client.nodes.ConnectionSetup;
import client.nodes.LanguageSwitch;
import client.nodes.ThemeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import javax.inject.Inject;
import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;


public class SettingsPageCtrl implements Main.LanguageSwitch {


    private ConnectionSetup connectionSetup;
    private final MainCtrl mainCtrl;

    private LanguageSwitch languageSwitch;

    private ThemeService themeService;

    @FXML
    private RadioMenuItem englishButton;

    @FXML
    private Button changeServerButton;

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
    private RadioMenuItem downloadButton;
    @FXML
    private ImageView downloadView;
    @FXML
    private MenuButton languageMenuButton;
    @FXML
    private MenuButton themeButton;
    @FXML
    private RadioMenuItem lightModeButton;
    @FXML
    private RadioMenuItem darkModeButton;



    @Inject
    public SettingsPageCtrl(ConnectionSetup cs, LanguageSwitch ls,
                            MainCtrl mainCtrl, ThemeService ts){
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

        turkishView.setFitHeight(25);
        turkishView.setFitWidth(22);
        setting=new Image(Objects.requireNonNull
                (getClass().getResourceAsStream("/icons/turkish.png")));
        turkishView.setImage(setting);
        turkishButton.setGraphic(turkishView);

        downloadView.setFitHeight(25);
        downloadView.setFitWidth(22);
        setting=new Image(Objects.requireNonNull
                (getClass().getResourceAsStream("/icons/download_icon_143099.png")));
        downloadView.setImage(setting);
        downloadButton.setGraphic(downloadView);

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

    public void onDownloadClick() {
        String fileUrl = "/translations.properties";
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(Main.getLocalizedString("chooseDirectory"));
        File selectedDirectory = directoryChooser.showDialog(new Stage());
        if (selectedDirectory == null) {return;}
            try {
                InputStream inputStream = getClass().getResourceAsStream(fileUrl);
                if (inputStream != null) {
                    String fileName = new File(fileUrl).getName();
                    File destinationFile = new File(selectedDirectory, fileName);
                    fileWriting(destinationFile, inputStream);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(Main.getLocalizedString("alertResourceNotFound"));
                    alert.setContentText(Main.getLocalizedString("alertResourceNotFound"));
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private static void fileWriting(File destinationFile, InputStream inputStream)
            throws IOException {
        try (inputStream; FileOutputStream outputStream = new FileOutputStream(destinationFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(Main.getLocalizedString("alertFileDownloadedSuccesfullyTitle"));
            alert.setContentText(Main.getLocalizedString("alertFileDownloadedSuccesfully"));
            alert.setHeaderText(null);
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(Main.getLocalizedString("alertWritingFile"));
            alert.setContentText(Main.getLocalizedString("alertWritingFile"));
            alert.setHeaderText(null);
            alert.showAndWait();
        }
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
        turkishButton.setText(Main.getLocalizedString("Turkish"));
        downloadButton.setText(Main.getLocalizedString("addLanguage"));
        lightModeButton.setText(Main.getLocalizedString("lightMode"));
        darkModeButton.setText(Main.getLocalizedString("darkMode"));
        changeServerButton.setText(Main.getLocalizedString("changeServer"));

    }

    /**
     * change to dark mode
     */
    public void darkMode() {
        lightModeButton.setSelected(false);
        darkModeButton.setSelected(true);
        themeService.saveToConfig("stylesheets/darkMode.css");
        mainCtrl.changeTheme("stylesheets/darkMode.css");
    }

    /**
     * change to white mode
     */
    public void lightMode(){
        darkModeButton.setSelected(false);
        lightModeButton.setSelected(true);
        themeService.saveToConfig("stylesheets/whiteMode.css");
        mainCtrl.changeTheme("stylesheets/whiteMode.css");

    }


    public void onTurkishSwitchClick(ActionEvent event) {
        englishButton.setSelected(false);
        dutchButton.setSelected(false);
        languageSwitch.saveToConfig("translations_tr");
        Main.switchLocale("translations", "tr");
    }
}
