package commons;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
public class Event {

    /**
     * List with all the participants which are part of this event
     */
    @OneToMany(mappedBy = "event")
    private List<Participant> participants;

    /**
     * List with all the expenses which are part of this event
     */
    @OneToMany(mappedBy = "event")
    private List<Expense> expenses;

    /**
     * String with the name of the event
     */
    private String name;

    /**
     * String with invitation ID, which the participants can invite others with
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long invitationID;

    /**
     * Constructor for an Event object
     * @param participants List of participants which are part of the event
     * @param expenses List of expenses that are part of the event
     * @param name String with name of the event
     * @param invitationID String with invitation ID of the event
     */
    public Event(List<Participant> participants, List<Expense> expenses, String name,
                 long invitationID) {
        this.participants = participants;
        this.expenses = expenses;
        this.name = name;
        this.invitationID = invitationID;
    }

    public Event(String name) {
        participants = new ArrayList<>();
        expenses = new ArrayList<>();
        this.name = name;
//        invitationID = "p" + Math.floor(Math.random() * 20);
    }

    public Event() {
    }

    /**
     * Getter for the list of participants
     * @return List with all participants
     */
    public List<Participant> getParticipants() {
        return participants;
    }

    /**
     * Getter for the list of participants
     * @param participants List with all the participants
     */
    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    /**
     * Add method to add a new participant to the list of participants of the event
     * @param newParticipant Participant object that will be added to the list
     */
    public void addParticipant(Participant newParticipant){
        participants.add(newParticipant);
    }

    /**
     * Remove method to remove a participant from the list of participants of the event
     * @param participant Participant object that will be added to the list
     * @return Boolean if it was successful or not
     */
    public Boolean removeParticipant(Participant participant){

        return participants.remove(participant);
    }

    /**
     * Getter for the list of expenses
     * @return List with all the expenses of the event
     */
    public List<Expense> getExpenses() {
        return expenses;
    }

    /**
     * Setter for the list of expenses
     * @param expenses List with expenses of the event
     */
    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    /**
     * Add method to add a new expense to the list of expenses of the event
     * @param newExpense Expense object that will be added to the list
     */
    public void addExpense (Expense newExpense){expenses.add(newExpense);
    }


    /**
     * Remove method for the list of expenses, to remove an expense in the list
     * @param expense The expense that is to be removed
     * @return Boolean if it was successful or not
     */
    public Boolean removeExpense(Expense expense){

        return expenses.remove(expense);
    }

    /**
     * Getter for the name of the event
     * @return String with name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name of the event
     * @param name String with name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the invitation ID of the event
     * @return String with invitation ID
     */
    public long getInvitationID() {
        return invitationID;
    }

    /**
     * Setter for the invitation ID of the event
     * @param invitationID String with invitation ID
     */
    public void setInvitationID(long invitationID) {
        this.invitationID = invitationID;
    }

    /**
     * Equals method for the Event class
     * @param object Object that will be compared to
     * @return boolean true or false if equal or not
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Event event = (Event) object;
        return Objects.equals(participants, event.participants) &&
            Objects.equals(expenses, event.expenses) &&
            Objects.equals(name, event.name) &&
            Objects.equals(invitationID, event.invitationID);
    }

    /**
     * Hash method for the Event class
     * @return Integer with hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(participants, expenses, name, invitationID);
    }

    @Override
    public String toString() {
        return "Event{" +
            "participants=" + participants +
            ", expenses=" + expenses +
            ", name='" + name + '\'' +
            ", invitationID='" + invitationID + '\'' +
            '}';
    }
}
