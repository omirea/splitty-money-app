package client.nodes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.scene.control.TextInputDialog;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

public class ConnectionSetup {
    ServerUtils serverUtils;

    @Inject
    public ConnectionSetup(ServerUtils serverUtils) {
        this.serverUtils = serverUtils;
    }

    /**
     * sets the URL for the server
     * @param server URL of the server
     * @return true if it worked, false otherwise.
     */
    private void setServer(String server) {
        Properties prop = new Properties();
        String fileName = "config.properties";
        try (FileInputStream fis = new FileInputStream(fileName)) {
            prop.load(fis);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        prop.setProperty("server", server);
        serverUtils.setServer(prop.getProperty("server"));
    }

    /**
     * checks if the config file has a server configured
     * @return true if there is a server, false otherwise.
     */
    public String getConfiguredServer() {
        Properties prop = new Properties();
        String fileName = "config.properties";
        try (FileInputStream fis = new FileInputStream(fileName)) {
            prop.load(fis);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        String server = prop.getProperty("server");
        if (server.isEmpty()) {
            System.out.println("Configuration is currently empty");
            return null;
        }
        System.out.println("Current server: " + server);
        return server;
    }

    public boolean hasConfiguredServer() {
        return getConfiguredServer() != null;
    }

    public void promptUser() {
        if (!hasConfiguredServer()) {
            promptUser("");
        }
        promptUser(getConfiguredServer());
    }

    public void promptUser(String s) {
        boolean done = false;
        while (!done) {
            TextInputDialog tid = new TextInputDialog(s);
            tid.setHeaderText("Enter the server you would like to connect to:");
            Optional<String> result = tid.showAndWait();
            if (result.isPresent()){
                if (!serverUtils.testConnection(s)) continue;
                setServer(result.get());
                break;
            }
            if (hasConfiguredServer()) {
                break;
            }
        }
    }
}
