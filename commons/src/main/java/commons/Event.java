package commons;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.Instant;
import java.util.*;


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
     * Instant with the date an object of event is created
     */
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Instant createDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Instant lastModified;



    /**
     * Constructor for an Event object
     * @param participants List of participants which are part of the event
     * @param expenses List of expenses that are part of the event
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id == event.id && Objects.equals(name, event.name) && Objects.equals(invitationID, event.invitationID) && Objects.equals(createDate, event.createDate) && Objects.equals(lastModified, event.lastModified);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, invitationID, createDate, lastModified);
    }

    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", invitationID='" + invitationID + '\'' +
                ", createDate=" + createDate +
                ", lastModified=" + lastModified +
                '}';
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public Instant getLastModified() {
        return lastModified;
    }
}
