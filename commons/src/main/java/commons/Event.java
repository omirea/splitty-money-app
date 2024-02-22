package commons;

import java.util.ArrayList;
import java.util.Objects;

public class Event {
    private ArrayList<Participant> Participants;
    private ArrayList<Expense> Expenses;
    private String name;
    private String invitationID;

    /**
     * Constructor for an Event object
     * @param participants ArrayList of participants which are part of the event
     * @param expenses ArrayList of expenses that are part of the event
     * @param name String with name of the event
     * @param invitationID String with invitation ID of the event
     */
    public Event(ArrayList<Participant> participants, ArrayList<Expense> expenses, String name,
                 String invitationID) {
        this.Participants = participants;
        this.Expenses = expenses;
        this.name = name;
        this.invitationID = invitationID;
    }

    public ArrayList<Participant> getParticipants() {
        return Participants;
    }

    public void setParticipants(ArrayList<Participant> participants) {
        this.Participants = participants;
    }

    public void addParticipant(ArrayList<Participant> participants, Participant newParticipant){
        participants.add(newParticipant);
    }

    public void removeParticipant(ArrayList<Participant> participants, Participant participant){
        participants.remove(participant);
    }

    public ArrayList<Expense> getExpenses() {
        return Expenses;
    }

    public void setExpenses(ArrayList<Expense> expenses) {
        this.Expenses = expenses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInvitationID() {
        return invitationID;
    }

    public void setInvitationID(String invitationID) {
        this.invitationID = invitationID;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Event event = (Event) object;
        return Objects.equals(Participants, event.Participants) &&
            Objects.equals(Expenses, event.Expenses) &&
            Objects.equals(name, event.name) &&
            Objects.equals(invitationID, event.invitationID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Participants, Expenses, name, invitationID);
    }
}
