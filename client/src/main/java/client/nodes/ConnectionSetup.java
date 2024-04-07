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
        fileName = "config.properties";
        try (InputStream inputStream =
                 ConnectionSetup.class
                     .getClassLoader()
                     .getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new FileNotFoundException("file not found in the classpath");
            }
            prop.load(inputStream);
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
        prop.setProperty("server", server);
        try (OutputStream outputStream = new FileOutputStream(fileName)) {
            prop.store(outputStream, "Updated configuration");
            System.out.println("[SET] stored");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        prop = new Properties();
        // Reload properties from the file to reflect the changes
        try (InputStream inputStream = new FileInputStream(fileName)) {
            prop.load(inputStream);
            System.out.println("[SET] Reloaded properties:");
            System.out.println("[SET] " + prop.getProperty("server"));
        } catch (IOException e) {
            throw new RuntimeException("Error loading properties file", e);
        }

        serverUtils.setServer(prop.getProperty("server"));
    }

    /**
     * checks if the config file has a server configured
     * @return true if there is a server, false otherwise.
     */
    public String getConfiguredServer() {
        String server = prop.getProperty("server");
        if (server.isEmpty()) {
            System.out.println("[GET] Configuration is currently empty");
            return null;
        }
        System.out.println("[GET] Current server: " + server);
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
                System.out.println("[PU] connection established");
                setServer(res);
                System.out.println("[PU] server has been set to: " + getConfiguredServer());
                break;
            }
            if (hasConfiguredServer()) break;
        }
    }
}
