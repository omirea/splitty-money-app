package commons;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "p_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @OneToMany(mappedBy = "from", cascade = CascadeType.REMOVE)
    private List<Debt> payDebts;

    @OneToMany(mappedBy = "to", cascade = CascadeType.REMOVE)
    private List<Debt> receiveDebts;

    private String name; // name of participant
    private String email; // email of participant
    private String accountHolder;
    private String IBAN; // IBAN of participant
    private String BIC; // BIC of participant

    /**
     * constructor for class Participant
     * @param name name of the participant
     * @param email email of the participant
     * @param IBAN IBAN of the participant
     * @param BIC BIC of the participant
     */
    public Participant(String name, String email, String accountHolder,
                       String IBAN, String BIC, Event event){
        this.name=name;
        this.email=email;
        this.accountHolder=accountHolder;
        this.IBAN=IBAN;
        this.BIC=BIC;
        this.event = event;
        payDebts = new ArrayList<>();
        receiveDebts = new ArrayList<>();
    }

    public Participant(String name, String email, String accountHolder, String IBAN, String BIC){
        this.name=name;
        this.email=email;
        this.accountHolder=accountHolder;
        this.IBAN=IBAN;
        this.BIC=BIC;
        payDebts = new ArrayList<>();
        receiveDebts = new ArrayList<>();
    }

    public Participant(Event event, List<Debt> payDebts, List<Debt> receiveDebts,
                       String name, String email, String accountHolder, String IBAN, String BIC) {
        this.event = event;
        this.payDebts = payDebts;
        this.receiveDebts = receiveDebts;
        this.name = name;
        this.email = email;
        this.accountHolder=accountHolder;
        this.IBAN = IBAN;
        this.BIC = BIC;
    }

    public Participant() {}

    /**
     * method to get the name of a participant
     * @return name of the participant
     */
    public String getName(){
        return name;
    }

    /**
     * method to get the email of a participant
     * @return email of the participant
     */
    public String getEmail(){
        return email;
    }

    /**
     * method to get the account holder of a participant
     * @return account holder of the participant
     */
    public String getAccountHolder() {
        return accountHolder;
    }

    /**
     * method to change the account holder of a participant
     * @param accountHolder the new account holder of the participant
     */
    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    /**
     * method to get the IBAN of a participant
     * @return IBAN of the participant
     */
    public String getIBAN(){
        return IBAN;
    }

    /**
     * method to get the BIC of a participant
     * @return BIC of the participant
     */
    public String getBIC(){
        return BIC;
    }

    /**
     * method to change the name of a participant
     * @param newName the new name of the participant
     */
    public void setName(String newName){
        this.name=newName;
    }

    /**
     * method to change the email of a participant
     * @param newEmail the new email of the participant
     */
    public void setEmail(String newEmail){
        this.email=newEmail;
    }

    /**
     * method to change the IBAN of a participant
     * @param newIBAN the new IBAN of the participant
     */
    public void setIBAN(String newIBAN){
        this.IBAN=newIBAN;
    }

    /**
     * method to change the BIC of a participant
     * @param newBIC the new BIC of the participant
     */
    public void setBIC(String newBIC){
        this.BIC=newBIC;
    }

    public Long getId() {
        return id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     * method that checks if 2 participants are equal
     * @param o the participant to compare to
     * @return true or false if the participants are or not equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Participant that)) return false;
        return Objects.equals(name, that.name) && Objects.equals(email, that.email)
                && Objects.equals(accountHolder, that.accountHolder)
            && Objects.equals(IBAN, that.IBAN) && Objects.equals(BIC, that.BIC);
    }

    /**
     * method that created hashcode for a participant
     * @return hashcode of an object of class participant
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, email, accountHolder, IBAN, BIC);
    }

    @Override
    public String toString() {
        return "Participant has " +
                "name: '" + name + '\'' +
                ", email: '" + email + '\'' +
                ", account holder name: '" + accountHolder + '\'' +
                ", IBAN: '" + IBAN + '\'' +
                ", BIC: '" + BIC + '\'' +
                ';';
    }

    public void setId(Long id) {
        this.id = id;
    }


}
