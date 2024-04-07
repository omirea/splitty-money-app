package client.nodes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.scene.control.TextInputDialog;

import java.io.*;
import java.util.Optional;
import java.util.Properties;

public class ConnectionSetup {
    ServerUtils serverUtils;
    Properties prop;
    String fileName;

    @Inject
    public ConnectionSetup(ServerUtils serverUtils) {
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
     * @param server URL of the server
     * @return true if it worked, false otherwise.
     */
    private void setServer(String server) {
        saveToConfig(server);
        serverUtils.setServer(prop.getProperty("server"));
    }

    private void saveToConfig(String server) {
        prop.setProperty("server", server);
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
    public String getConfiguredServer() {
        String server = prop.getProperty("server");
        if (server.isEmpty()) {
            return null;
        }
        return server;
    }

    public boolean hasConfiguredServer() {
        return getConfiguredServer() != null;
    }

    public void promptUser() {
        if (!hasConfiguredServer()) {
            promptUser("");
        } else {
            promptUser(getConfiguredServer());
        }
    }

    public void promptUser(String prompt) {
        boolean done = false;
        while (!done) {
            TextInputDialog tid = new TextInputDialog(prompt);
            tid.setHeaderText("Enter the server you would like to connect to:");
            Optional<String> result = tid.showAndWait();
            if (result.isPresent()){
                String res = result.get();
                if (!serverUtils.testConnection(res)) continue;
                done = true;
                setServer(res);
                break;
            }
            if (hasConfiguredServer()) break;
        }
    }
}
