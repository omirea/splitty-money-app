package client.nodes;

import javax.inject.Inject;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class ThemeService {
    private Properties prop;
    private String fileName;

    /**
     * theme service constructor
     */
    @Inject
    public ThemeService(){
        prop=new Properties();
        fileName="src/main/resources/config.properties";
        try(FileInputStream fileInputStream=new FileInputStream(fileName)){
            prop.load(fileInputStream);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

//    /**
//     * set the theme for all the pages
//     * @param theme the theme to set
//     */
//    public void setTheme(String theme){
//        System.out.println(scenes.size());
//        for(Scene scene : scenes){
//            System.out.println(1);;
//            scene.getStylesheets().add(theme);
//        }
//    }

    /**
     * save the preferred theme to the config file
     * @param theme theme to set
     */
    public void saveToConfig(String theme){
        prop.setProperty("theme", theme);
        try(OutputStream outputStream=new FileOutputStream(fileName)){
            prop.store(outputStream, "Updated theme");
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public String getTheme(){
        return prop.getProperty("theme");
    }

}
