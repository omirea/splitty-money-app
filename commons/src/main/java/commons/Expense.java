package commons;
import java.time.LocalDate;
import java.util.*;
public class Expense {
    private Participant whoPaid; // The participant that paid this instance
    private String description; // Description by the participant
    private double value; // Value of the expense
    private String type; // Type of expense
    private List<Participant> participants; // list of participant

    private LocalDate dateSent; //date the expense is sent

    private Currency currency; //currency used

    /**
     * Constructor for expense
     * @param whoPaid participant that paid the instance
     * @param description description of the participant
     * @param value value of the expense
     * @param type type of the expense
     * @param participants List of participants involved in the expense
     * @param dateSent date when the request for the expense has been made
     * @param currency currency of the expense
     */
    public Expense(Participant whoPaid, String description, double value, String type, List<Participant> participants,
                   LocalDate dateSent, Currency currency) {
        this.whoPaid = whoPaid;
        this.description = description;
        this.value = value;
        this.type = type;
        this.participants = participants;
        this.dateSent = dateSent;
        this.currency = currency;
    }
    /**
     * Getter for the person that paid
     * @return The participant that paid
     */
    public Participant getWhoPaid() {
        return whoPaid;
    }

    /**
     * Getter for description
     * @return the description of the expense
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter for the value
     * @return the value of the expense
     */
    public double getValue() {
        return value;
    }

    /**
     * Getter for the type
     * @return the type of the expense
     */
    public String getType() {
        return type;
    }

    /**
     * Getter for the list of participants
     * @return A list of participants relevant to the expense
     */
    public List<Participant> getParticipants() {
        return participants;
    }

    /**
     * Getter for the date
     * @return Date the expense has been made
     */
    public LocalDate getDateSent() {
        return dateSent;
    }

    /**
     * Getter for the currency
     * @return Currency that has been used
     */
    public Currency getCurrency() {
        return currency;
    }
    /**
     * Setter for the Participant who has pad
     * @param whoPaid the person who paid
     */
    public void setWhoPaid(Participant whoPaid) {
        this.whoPaid = whoPaid;
    }

    /**
     * Setter for the description of the expense
     * @param description the description of the expense
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Setter for the value of the expense
     * @param value value of the expense
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * Setter for the type of the expense
     * @param type type of the expense
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Setter for the participants involved
     * @param participants the participants involved
     */
    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    /**
     * Setter for the date sent
     * @param dateSent date the expense has been made
     */
    public void setDateSent(LocalDate dateSent) {
        this.dateSent = dateSent;
    }

    /**
     * Setter for the currency used in the expense
     * @param currency currency used in the expense
     */
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    /**
     * Equals method for Expense
     <<<<<<< HEAD
     * @param o other Expense
     * @return boolean true or false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expense expense = (Expense) o;
        return Double.compare(value, expense.value) == 0 &&
                Objects.equals(whoPaid, expense.whoPaid) &&
                Objects.equals(description, expense.description) &&
                Objects.equals(type, expense.type) &&
                Objects.equals(participants, expense.participants) &&
                Objects.equals(dateSent, expense.dateSent) && Objects.equals(currency, expense.currency);
    }

    /**
     * Hash method for Expense
     * @return int hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(whoPaid, description, value, type, participants, dateSent, currency);
    }

    /**
     * Adds a participant
     * @param participant to be added
     */
    public void addParticipant(Participant participant) {
        participants.add(participant);
    }

    /**
     * Removes a participant
     * @param participant to be removed
     */
    public void removeParticipant(Participant participant) {
        participants.remove(participant);
    }
}
