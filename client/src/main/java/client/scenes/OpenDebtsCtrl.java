package client.scenes;

import client.Main;
import client.nodes.DebtsTable;
import client.nodes.ParticipantStringConverter;
import client.nodes.PersonAmount;
import client.utils.ServerUtils;
import commons.Debt;
import commons.Event;
import commons.Expense;
import commons.Participant;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static client.Main.locale;

public class OpenDebtsCtrl implements Main.LanguageSwitch {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private  Event event;
    private ObservableList<Debt> allDebts;
    private ObservableList<Participant> allParticipants;

    private ObservableList<DebtsTable> debtsTables;
    private ObservableList<String> debtsString;
    @FXML
    private Button payAllDebtsButton;
    @FXML
    private Button seeClosedDebtsButton;
    @FXML
    private Button paySelectedDebtsButton;
    @FXML
    private Button homeButton;
    @FXML
    private ChoiceBox<Participant> selectedParticipant;
    @FXML
    private ImageView homeView;
    @FXML
    private Label yourCurrentDebtsLabel;
    @FXML
    private TableView<DebtsTable> tableView;
    @FXML
    private TableColumn<DebtsTable, String> informationCol;
    @FXML
    private TableColumn<DebtsTable, Button> emailCol;
    @FXML
    private TableColumn<DebtsTable, Button> IBANCol;
    @FXML
    private TableColumn<DebtsTable, Button> receivedCol;

    @Inject
    public OpenDebtsCtrl(ServerUtils server, MainCtrl mainCtrl){
        this.server=server;
        this.mainCtrl=mainCtrl;
        this.allParticipants = FXCollections.observableArrayList();
        this.allDebts=FXCollections.observableArrayList();
        this.debtsString=FXCollections.observableArrayList();
        this.debtsTables=FXCollections.observableArrayList();
    }

    /**
     * method to initialize the open debts page
     */
    @FXML
    public void initialize(){
        //initialize table view
        //initialize table view
//        tableView.visibleProperty().bind(onlySomePeopleField.selectedProperty());
//        autoDivideButton.visibleProperty().bind(onlySomePeopleField.selectedProperty());
//        checkBoxColumn.
//                setCellValueFactory(new PropertyValueFactory<PersonAmount, CheckBox>("checkBox"));
//        participantColumn.
//                setCellValueFactory(new PropertyValueFactory<PersonAmount, String>("name"));
//        amountColumn.
//                setCellValueFactory(new PropertyValueFactory<PersonAmount, TextField>("textField"));
//        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        informationCol.
                setCellValueFactory(new PropertyValueFactory<DebtsTable, String>("debtInfo"));
        emailCol.setCellValueFactory(new PropertyValueFactory<DebtsTable, Button>("email"));
        IBANCol.setCellValueFactory(new PropertyValueFactory<DebtsTable, Button>("IBAN"));
        receivedCol.setCellValueFactory(new PropertyValueFactory<DebtsTable, Button>("closeDebt"));

        //initialize choice box
        selectedParticipant.setItems(allParticipants);
        selectedParticipant.setConverter(new ParticipantStringConverter());
        //listView.setItems(debtsString);

        //initialize button colours
        //listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        getPayAllDebts()
                .setStyle("-fx-background-color: linear-gradient(to top right, #f5dce7, #e781c9)");

        //set home button
        homeView.setFitHeight(25);
        homeView.setFitWidth(22);
        Image setting=new Image(Objects.requireNonNull
                (getClass().getResourceAsStream("/icons/home.png")));
        homeView.setImage(setting);
        homeButton.setGraphic(homeView);
    }

    public ServerUtils getServer() {
        return server;
    }

    public MainCtrl getMainCtrl() {
        return mainCtrl;
    }

    public Button getSeeClosedDebtsButton(){return seeClosedDebtsButton;}

    public Button getSelectedDebts(){return paySelectedDebtsButton;}

//    public ListView<String> getListView(){return listView;}

    public Button getPayAllDebts(){return payAllDebtsButton;}

    /**
     * method to mark all debts as paid
     */
    public void markAllDebtsAsPaid(){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        switch(locale.getLanguage()) {
            case "nl":
                alert.setTitle("Markeer alle kosten als betaald");
                alert.setContentText("Weet je zeker dat je alle kosten als betaald wilt markeren");
                break;
            case "en":
                alert.setTitle("Mark all debts as paid");
                alert.setContentText("Are you sure you want to mark all debts as settled?");
                break;
            default:
                break;
        }
        alert.setHeaderText(null);
        Optional<ButtonType> result=alert.showAndWait();
        if(result.get()==ButtonType.OK) {
            //mainCtrl.addItemsToClosedDebts(this.listView);
            //listView.getItems().clear();
        }
    }
    public void setEvent(String id) {
        event = server.getEventByInvitationId(id);
    }

    /**
     * method to go back to event page
     */
    public void goBackToEvent(){
        mainCtrl.showEventOverview(event.getInvitationID());
    }

    /**
     * method to open closed debts page
     */
    public void seeClosedDebts(){mainCtrl.showClosedDebts(event.getInvitationID());}

    /**
     * method to mark all selected debts as paid
     */
    public void paySelectedDebts(){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        switch(locale.getLanguage()) {
            case "nl":
                alert.setTitle("Markeer geselecteerde kosten als betaald");
                alert.setContentText("Weet je zeker dat je " +
                        "gemarkeerkde kosten als betaalt wilt markeren");
                break;
            case "en":
                alert.setTitle("Mark selected debts as paid");
                alert.setContentText("Are you sure you want to " +
                        "mark the selected debts as settled?");
                break;
            default:
                break;
        }
        alert.setHeaderText(null);
        Optional<ButtonType> result=alert.showAndWait();
        if(result.get()==ButtonType.OK) {
            ListView<String> selected = new ListView<>();
//            for (Object o : listView.getSelectionModel().getSelectedItems())
//                selected.getItems().add((String) o);
            mainCtrl.addItemsToClosedDebts(selected);
            //listView.getItems().removeAll(listView.getSelectionModel().getSelectedItems());
        }
    }

    @Override
    public void LanguageSwitch() {
        homeButton.setText(Main.getLocalizedString("Home"));
        yourCurrentDebtsLabel.setText(Main.getLocalizedString("yourCurrentDebts"));
        //youShouldPayToLabel.setText(Main.getLocalizedString("youShouldPayTo"));
        //eventLabel.setText(Main.getLocalizedString("Event"));
        //amountLabel.setText(Main.getLocalizedString("Amount"));
        paySelectedDebtsButton.setText(Main.getLocalizedString("paySelectedDebts"));
        payAllDebtsButton.setText(Main.getLocalizedString("payAllDebts"));
        seeClosedDebtsButton.setText(Main.getLocalizedString("seeClosedDebts"));
    }

    public void addDebtsToList(String id) {
        allDebts.clear();
        debtsTables.clear();
        List<Expense> expenses=server.getExpensesByInvitationId(id);
        for(Expense expense : expenses){
            List<Debt> debts=server.getDebtsByExpenseId(expense.getId());
            allDebts.addAll(debts);
        }
        createDebtsTable(allDebts);
        tableView.setItems(debtsTables);
    }

    /**
     * create the debts to be added to the Open Debts page
     * @param allDebts list of debts of the event
     */
    private void createDebtsTable(ObservableList<Debt> allDebts) {
        for(Debt debt : allDebts){
            String info=debt.getFrom().getName() + " needs to pay " + debt.getAmount() + " to " + debt.getTo().getName();
            Button viewEmailButton=new Button();
            viewEmailButton.setAlignment(Pos.CENTER);
            Button viewIBANButton=new Button();
            viewIBANButton.setAlignment(Pos.CENTER);
            setupEmailPicture(debt.getTo(), viewEmailButton);
            setupIBANPicture(debt.getTo(), viewIBANButton);

            Button closeDebtButton=new Button("Close Debt");
            closeDebtButton.setAlignment(Pos.CENTER);
            DebtsTable newDebt=new DebtsTable(info, viewEmailButton, viewIBANButton, closeDebtButton);
            debtsTables.add(newDebt);
        }
    }

    /**
     * method to check if payer has email
     * @param to payer id
     */
    private void setupEmailPicture(Participant to, Button viewEmailButton) {
        Participant participant=server.getParticipantById(to.getId());
        if(participant.getEmail().isEmpty()) {
            Image image= new Image(Objects.requireNonNull
                    (getClass().getResourceAsStream("/icons/no-email.png")));
            ImageView mailView=new ImageView();
            mailView.setFitWidth(15);
            mailView.setFitHeight(15);
            mailView.setImage(image);
            viewEmailButton.setGraphic(mailView);
            viewEmailButton.setTooltip(new Tooltip("This participant has no email specified"));
            return;
        }
        Image image= new Image(Objects.requireNonNull
                (getClass().getResourceAsStream("/icons/email.png")));
        ImageView mailView=new ImageView();
        mailView.setFitWidth(15);
        mailView.setFitHeight(15);
        mailView.setImage(image);
        viewEmailButton.setGraphic(mailView);
        viewEmailButton.setTooltip(new Tooltip("Payer's email: " + participant.getEmail()));
    }

    /**
     * method to check if payer has IBAN
     * @param to payer id
     */
    private void setupIBANPicture(Participant to, Button viewIBANButton) {
        Participant participant=server.getParticipantById(to.getId());
        System.out.println(1);
        System.out.println(participant.getIBAN());
        if(participant.getIBAN().isEmpty()){
            Image IBAN= new Image(Objects.requireNonNull
                    (getClass().getResourceAsStream("/icons/no-iban.png")));
            ImageView IBANView=new ImageView();
            IBANView.setImage(IBAN);
            IBANView.setFitWidth(15);
            IBANView.setFitHeight(15);
            viewIBANButton.setGraphic(IBANView);
            viewIBANButton.setTooltip(new Tooltip("This participant has no IBAN specified"));
            return;
        }
        Image IBAN= new Image(Objects.requireNonNull
                (getClass().getResourceAsStream("/icons/iban.png")));
        ImageView IBANView=new ImageView();
        IBANView.setImage(IBAN);
        IBANView.setFitWidth(15);
        IBANView.setFitHeight(15);
        viewIBANButton.setGraphic(IBANView);
        viewIBANButton.setTooltip(new Tooltip("Payer's IBAN: " + participant.getIBAN()));
    }

    /**
     * add the event participants to the choice box
     * @param id of the event
     */
    public void addParticipantsToChoiceBox(String id) {
        allParticipants.clear();
        List<Participant> pList = server.getParticipantsByInvitationId(id);
        allParticipants.addAll(pList);
    }
}