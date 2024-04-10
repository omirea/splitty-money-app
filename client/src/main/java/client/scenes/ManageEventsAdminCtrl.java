package client.scenes;

import client.Main;
import client.utils.ServerUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import commons.Debt;
import commons.Event;
import commons.Expense;
import commons.Participant;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

import javax.inject.Inject;
import java.io.*;
import java.net.URL;
import java.util.*;

import static client.Main.locale;


public class ManageEventsAdminCtrl implements Initializable, Main.LanguageSwitch {


    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private final StartCtrl startCtrl;
    @FXML
    private TextField eventNameTextField;
    @FXML
    private Button searchButton;
    @FXML
    private Button logOutButton;
    @FXML
    private Button refreshButton;
    @FXML
    private Button importButton;
    @FXML
    private ObservableList<Event> allEvents;
    @FXML
    private TableView<Event> table;
    @FXML
    private TableColumn<Event, String> colEventTitle;
    @FXML
    private TableColumn<Event, String> colInvitationID;
    @FXML
    private TableColumn<Event, Button> colDelete;
    @FXML
    private TableColumn<Event, String> colDateCreated;
    @FXML
    private TableColumn<Event, String> colLastModified;
    @FXML
    private TableColumn<Event, Button> colJSON;


    @Inject
    public ManageEventsAdminCtrl (ServerUtils server, MainCtrl mainCtrl, StartCtrl startCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.startCtrl = startCtrl;
        this.allEvents = FXCollections.observableArrayList();
    }
    
    public void onImportClick(){
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        try {
            TypeReference<Event> typeReference = new TypeReference<>() {};
            Event eventJson = om.readValue(selectedFile, typeReference);
            server.createEvent(eventJson);
            List<Participant> parts = eventJson.getParticipants();
            List<Expense> expenses = eventJson.getExpenses();
            Event between = server.getEventByInvitationId(eventJson.getInvitationID());
            for (Participant p : parts){
                p.setEvent(between);
                server.createParticipant(p);
            }
            for (Expense e : expenses){
                e.setEvent(between);
                Expense ex = server.createExpense(e);
                List<Debt> debts = server.getDebtsByExpense(e.getId());
                for(Debt d : debts){
                    d.setExpense(ex);
                    d.setEvent(between);
                    List<Participant> participantsinEvent =
                        server.getParticipantsByInvitationId(eventJson.getInvitationID());
                    for(Participant p : participantsinEvent){
                        if(p.equals(d.getTo())){
                            d.setTo(p);
                        }
                        if(p.equals(d.getFrom())){
                            d.setFrom(p);
                        }
                    }
                }
                mainCtrl.addExpenseToEvent(e);
            }
            refresh();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * method to show start screen when admin wants to log out
     */
    public void  onLogOutClick(){
        mainCtrl.showStartScreen();
    }

    /**
     * method for button so search with string happens when clicking it
     */
    public void onSearchClick(){
        boolean searchEmpty = eventNameTextField.getText().trim().isEmpty();
        var events = server.getAllEvents();
        allEvents = FXCollections.observableList(events);
        if(!searchEmpty) {
            String eventNameS = eventNameTextField.getText().trim();
            List<Event> eventsWithName =
                allEvents.stream().filter(e -> e.getName().contains(eventNameS)).toList();
            ObservableList<Event> eventsWithN = FXCollections.observableList(eventsWithName);
            table.setItems(eventsWithN);
        } else {
            table.setItems(allEvents);
        }

    }


    /**
     * maps the keyboard shortcuts to this controller/scene
     * @param e KeyEvent inputted
     */
    public void enterKeyPressed(KeyEvent e) {
        if (Objects.requireNonNull(e.getCode()) == KeyCode.ENTER) {
            onSearchClick();
        }
    }


    /**
     * shows the event details
     */
    public void showEventDetails(String invitationID){
        mainCtrl.showEventOverview(invitationID);
    }


    /**
     * refreshes the table view with recent added events
     */
    public void refresh(){
        var events = server.getAllEvents();
        allEvents = FXCollections.observableList(events);
        table.setItems(allEvents);
    }

    /**
     * Deletes the Event from the database when clicked on the button
     * @param q The button
     */
    public void onDeleteClick(TableColumn.CellDataFeatures<Event, Button> q){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        switch(locale.getLanguage()) {
            case "nl":
                alert.setTitle("Evenement Verwijderen");
                alert.setContentText("Weet je zeker dat je het evenement wilt verwijderen?");
                break;
            case "en":
                alert.setTitle("Delete Event");
                alert.setContentText("Are you sure you want to delete the event?");
                break;
            default:
                break;
        }
        alert.setHeaderText(null);
        Optional<ButtonType> result=alert.showAndWait();
        if(result.get()==ButtonType.OK){
            List<Expense> expenses =
                server.getExpensesByInvitationId(q.getValue().getInvitationID());
            for(Expense expense : expenses){
                List<Debt> debts = server.getDebtsByInvitationId(q.getValue().getInvitationID());
                for (Debt debt : debts){
                    server.deleteDebt(debt.getId());
                }
                server.deleteExpense(expense.getId());
            }
            List<Participant> participants =
                server.getParticipantsByInvitationId(q.getValue().getInvitationID());
            for(Participant p : participants){
                server.deleteParticipant(p.getId());
            }
            server.deleteEvent(q.getValue().getID());
            startCtrl.deleteEventFromTable(q.getValue());
            refresh();
        }
    }

    /**
     * initializes the rows of the table view with all the columns and the attributes
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colEventTitle.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getName()));
        colInvitationID.setCellValueFactory(q -> new SimpleStringProperty(
            q.getValue().getInvitationID()));
        colDelete.setCellValueFactory(q -> {
            Button delete = new Button();
            ImageView imageView = new ImageView();
            imageView.setFitHeight(20);
            imageView.setFitWidth(20);
            Image trash =new Image(Objects.requireNonNull
                (getClass().getResourceAsStream("/icons/trash.png")));
            imageView.setImage(trash);
            delete.setGraphic(imageView);
            delete.setOnAction(event -> onDeleteClick(q));
            return new SimpleObjectProperty<>(delete);
        });
        colDateCreated.setCellValueFactory(q ->
        new SimpleStringProperty(q.getValue().getCreateDate().toString()));
        colLastModified.setCellValueFactory(q ->
            new SimpleStringProperty(q.getValue().getLastModified().toString()));
        colJSON.setCellValueFactory(q -> {
            Button json = new Button();
            json.setText("JSON");
            json.setOnAction(event -> {
                try {
                    onJSONClick(q.getValue());
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            });
            return new SimpleObjectProperty<>(json);
        });
        table.setRowFactory(event -> {
           TableRow<Event> row = new TableRow<>();
           row.setOnMouseClicked(q -> {
               if(q.getClickCount() == 2 && (!row.isEmpty())) {
                   Event eventRow = row.getItem();
                   String invID = eventRow.getInvitationID();
                   showEventDetails(invID);
               }
           });
           return row;
        });
//        pollUpdates();
    }

    public void pollUpdates() {
        server.registerForUpdates(q-> {
            allEvents.add(q);
        });
    }

    public void stop() {
        server.stop();
    }


    /**
     * Makes an JSON file with all the information of the event
     * @param event which needs to be exported
     */
    private void onJSONClick(Event event) throws JsonProcessingException {
        try {
            Writer writer = new BufferedWriter(new FileWriter(
                event.getInvitationID() + ".json"));
            System.out.println(server.getParticipantsByInvitationId(event.getInvitationID()));
            System.out.println(server.getExpensesByInvitationId(event.getInvitationID()));
            String string = server.getEventByInvitationIdJSON(event.getInvitationID());
            writer.write(string);
            writer.flush();
            writer.close();
            System.out.println("JSON made with event invitation ID: " + event.getInvitationID());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            switch(locale.getLanguage()) {
                case "nl":
                    alert.setTitle("JSON Download Succesvol");
                    alert.setContentText("JSON document succesvol toegevoegd");
                    break;
                case "en":
                    alert.setTitle("JSON Download Successful");
                    alert.setContentText("JSON document added successfully");
                    break;
                default:
                    break;
            }
            alert.setHeaderText(null);
            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * sets the keys on each text included FXML object for the language switch
     */
    @Override
    public void LanguageSwitch() {
        logOutButton.setText(Main.getLocalizedString("logOut"));
        eventNameTextField.setPromptText(Main.getLocalizedString("searchEvent"));
        searchButton.setText(Main.getLocalizedString("Search"));
        refreshButton.setText(Main.getLocalizedString("Refresh"));
        colEventTitle.setText(Main.getLocalizedString("Title"));
        colInvitationID.setText(Main.getLocalizedString("invitationID"));
        colLastModified.setText(Main.getLocalizedString("lastModified"));
        colDateCreated.setText(Main.getLocalizedString("dateCreated"));
        importButton.setText(Main.getLocalizedString("Import"));
    }

}
