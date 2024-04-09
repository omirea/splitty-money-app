package client.nodes;

import client.scenes.MainCtrl;
import javafx.scene.Scene;

import javax.inject.Inject;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ThemeService {

    private Properties prop;
    private String fileName;
    private MainCtrl mainCtrl;
    private List<Scene> scenes;
    /**
     * theme service constructor
     */
    @Inject
    public ThemeService(MainCtrl mainCtrl){
        this.mainCtrl=mainCtrl;
        prop=new Properties();
        scenes=new ArrayList<>();
        fileName="src/main/resources/config.properties";
        try(FileInputStream fileInputStream=new FileInputStream(fileName)){
            prop.load(fileInputStream);
        }catch(IOException e){
            e.printStackTrace();
        }
        scenes.add(mainCtrl.getStart());
        scenes.add(mainCtrl.getSettingsPage());
        scenes.add(mainCtrl.getOverview());
        scenes.add(mainCtrl.getInvitation());
        scenes.add(mainCtrl.getManageParticipants());
        scenes.add(mainCtrl.getAddEditParticipant());
        scenes.add(mainCtrl.getExpense());
        scenes.add(mainCtrl.getOpenDebts());
        scenes.add(mainCtrl.getClosedDebts());
        scenes.add(mainCtrl.getLogInAdmin());
        scenes.add(mainCtrl.getEventsAdmin());
;    }

    /**
     * set the theme for all the pages
     * @param theme the theme to set
     */
    public void setTheme(String theme){
        System.out.println(scenes.size());
        for(Scene scene : scenes){
            System.out.println(1);;
            scene.getStylesheets().add(theme);
        }
    }

    public void saveToConfig(String theme){
        prop.setProperty("theme", theme);
        try(OutputStream outputStream=new FileOutputStream(fileName)){
            prop.store(outputStream, "Updated theme");
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

}
