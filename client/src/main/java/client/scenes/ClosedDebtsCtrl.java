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

import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    private TableColumn<DebtsTable, CheckBox> checkBoxCol;
    @FXML
    private TableColumn<DebtsTable, TreeView<String>> informationCol;
    @FXML
    private TableColumn<DebtsTable, Button> emailCol;
    @FXML
    private TableColumn<DebtsTable, Button> IBANCol;
    @FXML
    private TableColumn<DebtsTable, Button> receivedCol;
    @FXML
    private Label closedDebtsLabel, fromLabel, toLabel;
    @FXML
    private Button seeOpenDebtsButton, searchButton, reopenSelectedDebtsButton;


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
        checkBoxCol.
                setCellValueFactory(new PropertyValueFactory<>("checkBox"));
        informationCol.
                setCellValueFactory(new PropertyValueFactory<>("treeView"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        IBANCol.setCellValueFactory(new PropertyValueFactory<>("IBAN"));
        receivedCol.setCellValueFactory(new PropertyValueFactory<>("closeDebt"));

        //set choice boxes
        fromParticipantCB.setItems(allParticipants);
        fromParticipantCB.setConverter(new ParticipantStringConverter());
        toParticipantCB.setItems(allParticipants);
        toParticipantCB.setConverter(new ParticipantStringConverter());

        //set button color
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        //getReopenAllDebts()
                //.setStyle("-fx-background-color:
        // linear-gradient(to top right, #c7dde7, #32c2fd)");

        //set button to go home page
        homeView.setFitHeight(25);
        homeView.setFitWidth(22);
        Image setting = new Image(Objects.requireNonNull
                (getClass().getResourceAsStream("/icons/left-arrow.png")));
        homeView.setImage(setting);
        homeButton.setGraphic(homeView);
    }

    public void setEvent(String id) {
        event = server.getEventByInvitationId(id);
    }

    /**
     * add debts to the table view
     */
    public void addDebtsToList() {
        calculateAllDebts();
        createDebtsTable();
        tableView.setItems(debtsTables);
    }

    /**
     * calculates all debts of the event that were closed
     */
    private void calculateAllDebts() {

        allDebts.clear();
        newDebts.clear();

        List<Debt> debts = server.getDebtsByInvitationId(event.getInvitationID());
        debts.removeIf(debt -> !debt.isSettled());
        allDebts.addAll(debts);

        for (int i = 0; i < debts.size(); i++) {

            Debt debt1 = new Debt(debts.get(i).getFrom(),
                    debts.get(i).getTo(), debts.get(i).getAmount());
            newDebts.add(debt1);

            Long fromID = debts.get(i).getFrom().getId();
            Long toID = debts.get(i).getTo().getId();

            for (int j = i + 1; j < debts.size(); j++) {

                Debt debt2 = debts.get(j);

                if (Objects.equals(debt2.getFrom().getId(), fromID)
                        && Objects.equals(debt2.getTo().getId(), toID)) {
                    debt1.setAmount(debt1.getAmount() + debt2.getAmount());
                    debts.remove(debt2);
                    j--;
                } else if (Objects.equals(debt2.getFrom().getId(), toID)
                        && Objects.equals(debt2.getTo().getId(), fromID)) {
                    debt1.setAmount(debt1.getAmount() - debt2.getAmount());
                    debts.remove(debt2);
                    j--;
                }
            }

            if (debt1.getAmount() < 0) {
                Participant newFrom = debt1.getTo();
                debt1.setTo(debt1.getFrom());
                debt1.setFrom(newFrom);
                debt1.setAmount(-debt1.getAmount());
            } else if (debt1.getAmount() == 0) {
                debts.remove(i);
                i--;
                newDebts.remove(debt1);
            }
        }
    }

    /**
     * create the debts to be added to the Open Debts page
     */
    public void createDebtsTable() {
        debtsTables.clear();
        for(Debt debt : newDebts){
            //set tree view-debts info + text flow (bold text)
            TreeView<TextFlow> treeView = new TreeView<>();
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
            Text currency=new Text("EUR");
            Text to=new Text(debt.getTo().getName());

            makeTextBold(from, howMuch, currency, to);
            textFlow.getChildren().addAll(from, new Text(" " +
                            Main.getLocalizedString("needsToPay") + " "),
                    howMuch, new Text(" "), currency,
                    new Text(" " + Main.getLocalizedString("toPerson") + " "), to);
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

            //set open debts button
            Button openDebtButton=new Button(Main.getLocalizedString("openDebt"));
            openDebtButton.setAlignment(Pos.CENTER);
            openDebtButton.setOnAction(x -> {
                for (Debt existingDebt : allDebts) {
                    if (existingDebt.getFrom().equals(debt.getFrom())
                            && existingDebt.getTo().equals(debt.getTo())) {
                        existingDebt.setSettled(false);
                        server.updateDebt(existingDebt, existingDebt.getId());
                    }
                }
                addDebtsToList();
                String oldName = event.getName();
                event.setName("A");
                event = server.updateEvent(event, event.getID());
                event.setName(oldName);
                event = server.updateEvent(event, event.getID());
            });
            //create debt
            DebtsTable newDebt=new DebtsTable(new CheckBox(), treeView, viewEmailButton,
                    viewIBANButton, openDebtButton, debt);
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
            return new Text(Main.getLocalizedString("noBankInformation"));
        String info=Main.getLocalizedString("bankInformationAvailable") +
                Main.getLocalizedString("accountHolder") +  participant.getAccountHolder()  + "\n" +
                "IBAN: " + participant.getIBAN() + "\n";
        if(participant.getBIC().isEmpty())
            info=info + Main.getLocalizedString("BICUnknown");
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
            viewEmailButton.setTooltip(new Tooltip(Main.getLocalizedString("toolTipEmail")));
            return;
        }
        Image image= new Image(Objects.requireNonNull
                (getClass().getResourceAsStream("/icons/email.png")));
        ImageView mailView=new ImageView();
        mailView.setFitWidth(15);
        mailView.setFitHeight(15);
        mailView.setImage(image);
        viewEmailButton.setGraphic(mailView);
        viewEmailButton.setTooltip(new Tooltip(Main.getLocalizedString("payerEmail") +
                participant.getEmail()));
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
            viewIBANButton.setTooltip(new Tooltip(Main.getLocalizedString("toolTipIBAN")));
            return;
        }
        Image IBAN= new Image(Objects.requireNonNull
                (getClass().getResourceAsStream("/icons/iban.png")));
        ImageView IBANView=new ImageView();
        IBANView.setImage(IBAN);
        IBANView.setFitWidth(15);
        IBANView.setFitHeight(15);
        viewIBANButton.setGraphic(IBANView);
        viewIBANButton.setTooltip(new Tooltip(Main.getLocalizedString("payerIBAN") +
                participant.getIBAN()));
    }

    /**
     * method to reopen all the closed debts
     */
    public void reopenAllDebts(){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(Main.getLocalizedString("alertReOpenDebtsTitle"));
        alert.setContentText(Main.getLocalizedString("alertReOpenDebtsContent"));
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            for (Debt debt : allDebts) {
                debt.setSettled(false);
                server.updateDebt(debt, debt.getId());
            }
            addDebtsToList();
            String oldName = event.getName();
            event.setName("A");
            event = server.updateEvent(event, event.getID());
            event.setName(oldName);
            event = server.updateEvent(event, event.getID());
        }
    }

    /**
     * method to reopen only the selected debts
     */
    public void reopenSelectedDebts(){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(Main.getLocalizedString("alertReOpenSelectedDebtsTitle"));
        alert.setContentText(Main.getLocalizedString("alertReOpenSelectedDebtsContent"));
        alert.setHeaderText(null);
        Optional<ButtonType> result=alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            for (DebtsTable debtRow : debtsTables){
                if (debtRow.getCheckBox().isSelected()){
                    Debt debt = debtRow.getDebt();
                    for (Debt existingDebt : allDebts) {
                        if (existingDebt.getFrom().equals(debt.getFrom())
                                && existingDebt.getTo().equals(debt.getTo())) {
                            existingDebt.setSettled(false);
                            server.updateDebt(existingDebt, existingDebt.getId());
                        }
                    }
                }
            }
            addDebtsToList();
            String oldName = event.getName();
            event.setName("A");
            event = server.updateEvent(event, event.getID());
            event.setName(oldName);
            event = server.updateEvent(event, event.getID());
        }
    }

    /**
     * method to search based on the participant filters chosen
     */
    public void searchByParticipant(){
        Participant to = toParticipantCB.getSelectionModel().getSelectedItem();
        Participant from = fromParticipantCB.getSelectionModel().getSelectedItem();
        List<DebtsTable> filteredList = new ArrayList<>(debtsTables);
        if (from != null && !from.getName().isEmpty()) {
            filteredList = filteredList.stream()
                    .filter(debtsTable -> Objects.equals(debtsTable.getDebt().getFrom().getId(),
                            from.getId())).toList();
        }
        if (to != null && !to.getName().isEmpty()) {
            filteredList = filteredList.stream()
                    .filter(debtsTable -> Objects.equals(debtsTable.getDebt().getTo().getId(),
                            to.getId())).toList();
        }
        ObservableList<DebtsTable> filtered = FXCollections.observableArrayList(filteredList);
        tableView.setItems(filtered);
    }

    @Override
    public void LanguageSwitch() {
        homeButton.setText(Main.getLocalizedString("Back"));
        closedDebtsLabel.setText(Main.getLocalizedString("closedDebts"));
        seeOpenDebtsButton.setText(Main.getLocalizedString("seeOpenDebts"));
        toLabel.setText(Main.getLocalizedString("toPerson"));
        fromLabel.setText(Main.getLocalizedString("fromPerson"));
        searchButton.setText(Main.getLocalizedString("Search"));
        emailCol.setText(Main.getLocalizedString("Email"));
        IBANCol.setText(Main.getLocalizedString("IBAN"));
        informationCol.setText(Main.getLocalizedString("Information"));
        reopenSelectedDebtsButton.setText(Main.getLocalizedString("reopenSelectedDebts"));
        reopenAllDebts.setText(Main.getLocalizedString("reopenAllDebts"));
    }

    /**
     * add the event participants to the choice box
     */
    public void addParticipantsToChoiceBox() {
        allParticipants.clear();
        List<Participant> pList = server.getParticipantsByInvitationId(event.getInvitationID());
        allParticipants.addAll(pList);
        allParticipants.add(new Participant("", null, null, null, null));
    }

    /**
     * method to show open debts page
     */
    public void showOpenDebts(){
        mainCtrl.showOpenDebts(event.getInvitationID());
    }

    /**
     * method to show start page
     */
    public void showHome(){mainCtrl.showStartScreen();}
}
