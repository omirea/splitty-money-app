package client.nodes;

import client.utils.ServerUtils;
import com.google.inject.Inject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class LanguageSwitch {

    ServerUtils serverUtils;
    Properties prop;
    String fileName;

    @Inject
    public LanguageSwitch(ServerUtils serverUtils) {
        this.serverUtils = serverUtils;

        prop = new Properties();
        fileName = "src/main/resources/config.properties";
        try (FileInputStream fileInputStream = new FileInputStream(fileName)) {
            prop.load(fileInputStream);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * sets the URL for the server
     * @param language URL of the server
     * @return true if it worked, false otherwise.
     */
    public void saveToConfig(String language) {
        prop.setProperty("preferredLanguage", language);
        try (OutputStream outputStream = new FileOutputStream(fileName)) {
            prop.store(outputStream, "Updated configuration");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * checks if the config file has a server configured
     * @return true if there is a server, false otherwise.
     */
    public String getLanguage() {
        //prop = new Properties();
        fileName = "src/main/resources/config.properties";
        try (FileInputStream fileInputStream = new FileInputStream(fileName)) {
            prop.load(fileInputStream);
            String language = prop.getProperty("preferredLanguage");
            if (language.isEmpty()) {
                return null;
            }
            return language;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
