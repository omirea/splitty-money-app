package client.scenes;

import client.Main;
import client.nodes.DebtsTable;
import client.nodes.ParticipantStringConverter;
import client.utils.ServerUtils;
import commons.Debt;
import commons.Event;
import commons.Participant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static client.Main.locale;

public class ClosedDebtsCtrl implements Main.LanguageSwitch {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final ObservableList<Debt> allDebts;
    private final ObservableList<Participant> allParticipants;
    private final ObservableList<DebtsTable> debtsTables;
    private final ObservableList<Debt> newDebts;
    Event event;
    @FXML
    private TableView<DebtsTable> tableView;
    @FXML
    private Button reopenAllDebts;

    @FXML
    private Button homeButton;
    @FXML
    private ImageView homeView;
    @FXML
    private ChoiceBox<Participant> fromParticipantCB;
    @FXML
    private ChoiceBox<Participant> toParticipantCB;
    @FXML
    private TableColumn<DebtsTable, TreeView<String>> informationCol;
    @FXML
    private TableColumn<DebtsTable, Button> emailCol;
    @FXML
    private TableColumn<DebtsTable, Button> IBANCol;
    @FXML
    private TableColumn<DebtsTable, Button> receivedCol;


    @Inject
    public ClosedDebtsCtrl(ServerUtils server,MainCtrl mainCtrl){
        this.server=server;
        this.mainCtrl=mainCtrl;
        allDebts= FXCollections.observableArrayList();
        allParticipants=FXCollections.observableArrayList();
        debtsTables=FXCollections.observableArrayList();
        newDebts=FXCollections.observableArrayList();
    }

    /**
     * method to initialize close debts page
     */
    public void initialize(){
        //set table view
        //initialize table view
        informationCol.
                setCellValueFactory(new PropertyValueFactory<>("treeView"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        IBANCol.setCellValueFactory(new PropertyValueFactory<>("IBAN"));
        receivedCol.setCellValueFactory(new PropertyValueFactory<>("closeDebt"));
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //set choice boxes
        fromParticipantCB.setItems(allParticipants);
        fromParticipantCB.setConverter(new ParticipantStringConverter());
        toParticipantCB.setItems(allParticipants);
        toParticipantCB.setConverter(new ParticipantStringConverter());

        //set button color
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        getReopenAllDebts()
                .setStyle("-fx-background-color: linear-gradient(to top right, #c7dde7, #32c2fd)");

        //set button to go home page
        homeView.setFitHeight(25);
        homeView.setFitWidth(22);
        Image setting = new Image(Objects.requireNonNull
                (getClass().getResourceAsStream("/icons/home.png")));
        homeView.setImage(setting);
        homeButton.setGraphic(homeView);
    }

    public void setEvent(String id) {
        event = server.getEventByInvitationId(id);
    }

    public Button getReopenAllDebts() {return reopenAllDebts;}

    public void setAllDebts(ObservableList<Debt> newDebts){
        this.allDebts.clear();
        this.allDebts.addAll(newDebts);
    }

    /**
     * add debts to the table view
     */
    public void addDebtsToList() {
        createDebtsTable(allDebts);
        System.out.println("dsfds");
        for(DebtsTable debtsTable : debtsTables)
            System.out.println(debtsTable);
        tableView.setItems(debtsTables);
    }

    /**
     * create the debts to be added to the Open Debts page
     * @param allDebts list of debts of the event
     */
    private void createDebtsTable(ObservableList<Debt> allDebts) {
        debtsTables.clear();
        for(Debt debt : allDebts){
            //set tree view-debts info + text flow (bold text)
            TreeView<TextFlow> treeView=new TreeView<>();
            treeView.setMaxHeight(30);
            treeView.setOnMouseClicked(event -> {
                if(treeView.getMaxHeight()==30)
                    treeView.setMaxHeight(100);
                else
                    treeView.setMaxHeight(30);
            });
            //set text flow
            TextFlow textFlow=new TextFlow();
            Text from=new Text(debt.getFrom().getName());
            Text howMuch=new Text(String.valueOf(debt.getAmount()));
            Text currency=new Text(String.valueOf(debt.getExpense().getCurrency()));
            Text to=new Text(debt.getTo().getName());

            makeTextBold(from, howMuch, currency, to);
            textFlow.getChildren().addAll(from, new Text(" needs to pay "),
                    howMuch, new Text(" "), currency, new Text(" to "), to);
            TreeItem<TextFlow> treeItemRoot=new TreeItem<>(textFlow);
            TextFlow detailsFlow=new TextFlow();
            Text details=getExtraDetails(debt);
            detailsFlow.getChildren().add(details);
            TreeItem<TextFlow> treeItemDetails=new TreeItem<>(detailsFlow);
            treeView.setRoot(treeItemRoot);
            treeItemRoot.getChildren().add(treeItemDetails);

            //set email and iban buttons
            Button viewEmailButton=new Button();
            viewEmailButton.setAlignment(Pos.CENTER);
            Button viewIBANButton=new Button();
            viewIBANButton.setAlignment(Pos.CENTER);
            setupEmailPicture(debt.getTo(), viewEmailButton);
            setupIBANPicture(debt.getTo(), viewIBANButton);

            //set closed debts button
            Button closeDebtButton=new Button("Close Debt");
            closeDebtButton.setAlignment(Pos.CENTER);

            //create debt
            DebtsTable newDebt=new DebtsTable(treeView, viewEmailButton,
                    viewIBANButton, closeDebtButton);
            debtsTables.add(newDebt);
        }
    }

    /**
     * add drop down details for the debt
     * @param debt the debt we need details for
     * @return text with the details
     */
    private Text getExtraDetails(Debt debt) {
        Participant participant=debt.getTo();
        if(participant.getIBAN().isEmpty())
            return new Text("No bank information is available for");
        String info="Bank information available: Transfer money to:\n" +
                "Account Holder: " + "name \n" +
                "IBAN: " + participant.getIBAN() + "\n";
        if(participant.getBIC().isEmpty())
            info=info + "BIC: unknown";
        else
            info=info + "BIC: " + participant.getBIC();
        return new Text(info);
    }

    /**
     * make some text bold
     * @param from who needs to pay
     * @param howMuch how much
     * @param currency currency
     * @param to who to pay to
     */
    private void makeTextBold(Text from, Text howMuch, Text currency, Text to) {
        from.setStyle("-fx-font-weight: bold");
        howMuch.setStyle("-fx-font-weight: bold");
        currency.setStyle("-fx-font-weight: bold");
        to.setStyle("-fx-font-weight: bold");
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
     * method to reopen all the closed debts
     */
    public void reopenAllDebts(){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        switch(locale.getLanguage()) {
            case "nl":
                alert.setTitle("Heropenen alle kosten");
                alert.setContentText("Weet je zeker dat je alle kosten wilt heropenen?");
                break;
            case "en":
                alert.setTitle("Re-open all debts");
                alert.setContentText("Are you sure you want to re-open all debts?");
                break;
            default:
                break;
        }
        alert.setHeaderText(null);
        Optional<ButtonType> result=alert.showAndWait();
        if(result.get()==ButtonType.OK){
            //mainCtrl.addItemsToOpenDebts(listView);
        }
    }

    /**
     * method to reopen only the selected debts
     */
    public void reopenSelectedDebts(){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        switch(locale.getLanguage()) {
            case "nl":
                alert.setTitle("Heropen bepaalde kosten");
                alert.setContentText("Weet je zeker dat je " +
                        "de geselecteerde kosten wilt heropenen?");
                break;
            case "en":
                alert.setTitle("Re-open selected debts");
                alert.setContentText("Are you sure that you " +
                        "want to re-open the selected debts?");
                break;
            default:
                break;
        }
        alert.setHeaderText(null);
        Optional<ButtonType> result=alert.showAndWait();
        if(result.get()==ButtonType.OK){
            ListView<String> selected=new ListView<>();
//            for(String s: listView.getSelectionModel().getSelectedItems())
//                selected.getItems().add(s);
//            //mainCtrl.addItemsToOpenDebts(selected);
//            listView.getItems().removeAll(listView.getSelectionModel().getSelectedItems());
        }
    }

    @Override
    public void LanguageSwitch() {
        homeButton.setText(Main.getLocalizedString("Home"));
        //closedDebtsLabel.setText(Main.getLocalizedString("closedDebts"));
        //seeOpenDebts.setText(Main.getLocalizedString("seeOpenDebts"));
        //youPaidToLabel.setText(Main.getLocalizedString("youPaidTo"));
        //eventLabel.setText(Main.getLocalizedString("Event"));
        //amountLabel.setText(Main.getLocalizedString("Amount"));
        //reopenSelectedDebts.setText(Main.getLocalizedString("reopenSelectedDebts"));
        reopenAllDebts.setText(Main.getLocalizedString("reopenAllDebts"));
    }

    public void goBackToEvent() {
        mainCtrl.showEventOverview(event.getInvitationID());
    }

    /**
     * add the event participants to the choice box
     * @param id of the event
     */
    public void addParticipantsToChoiceBox(String id) {
        allParticipants.clear();
        List<Participant> pList = server.getParticipantsByInvitationId(id);
        allParticipants.addAll(pList);
        allParticipants.add(new Participant("", null, null, null));
    }

    /**
     * method to show open debts page
     */
    public void showOpenDebts(){
        mainCtrl.showOpenDebts(event.getInvitationID());
    }
}
