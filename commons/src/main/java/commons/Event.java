package commons;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;


@Entity
public class Event {

    /**
     * String with the name of the event
     */
    private String name;

    /**
     * id used for the database to identify the object
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * String with invitation ID, which the participants can invite others with
     */
    private String invitationID;

    /**
     * Constructor for an Event object
     * @param name String with name of the event
     * @param invitationID String with invitation ID of the event
     */
    public Event(String name,
                 String invitationID) {
        this.name = name;
        this.invitationID = invitationID;
    }

    public Event(String name) {
        this.name = name;
        invitationID = generateInvitationID();
    }

    /**
     * Generates a random invitation id
     * @return random invitation id
     */
    private String generateInvitationID() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 90; // letter 'z'
        int targetStringLength = 8;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
            .filter(i -> (i <= 57 || i >= 65))
            .limit(targetStringLength)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
    }

    public Event() {
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
    public String getInvitationID() {
        return invitationID;
    }

    /**
     * Setter for the invitation ID of the event
     * @param invitationID String with invitation ID
     */
    public void setInvitationID(String invitationID) {
        this.invitationID = invitationID;
    }


    /**
     * Getter for the ID of the event
     * @return long with ID
     */
    public long getID() {
        return id;
    }

    /**
     * Setter for the ID of the event
     * @param id long with ID
     */
    public void setID(long id) {
        this.id = id;
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
        return Objects.equals(name, event.name) &&
            Objects.equals(invitationID, event.invitationID);
    }

    /**
     * Hash method for the Event class
     * @return Integer with hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, invitationID);
    }

    @Override
    public String toString() {
        return "Event{" +
            "name='" + name + '\'' +
            ", invitationID='" + invitationID + '\'' +
            '}';
    }
}
