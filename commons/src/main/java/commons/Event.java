package commons;

import java.util.ArrayList;

public class Event {
    private ArrayList<Participant> Participants;
    private ArrayList<Expense> Expenses;
    private String name;
    private String inventationID;

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
        participants.remove(participant);b
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

    public String getInventationID() {
        return inventationID;
    }

    public void setInventationID(String inventationID) {
        this.inventationID = inventationID;
    }
}
