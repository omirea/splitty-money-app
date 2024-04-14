package commons;

import jakarta.persistence.*;
import java.util.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Event{

    /**
     * String with the name of the event
     */
    private String name;

    /**
     * id used for the database to identify the object
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * String with invitation ID, which the participants can invite others with
     */
    private String invitationID;

    /**
     * Instant with the date an object of event is created
     */
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModified;

    @Transient
    private List<Expense> expenses = new ArrayList<>();
    @Transient
    private List<Participant> participants = new ArrayList<>();

    @Transient
    private List<Debt> debts = new ArrayList<>();

    /**
     * List of tags of an event
     */
    @Transient
    private List<TagsClass> tags=new ArrayList<>();

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

    public Event(String name,
                 String invitationID,
                 List<Expense> expenses,
                 List<Participant> participants,
                 List<Debt> debts){
        this.name = name;
        this.invitationID = invitationID;
        this.expenses = expenses;
        this.participants = participants;
        this.debts = debts;
    }

    public Event(String name) {
        this.name = name;
        invitationID = generateInvitationID();
    }

    public Event() {
    }


    public List<Expense> getExpenses() {
        return expenses;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public List<Debt> getDebts() {
        return debts;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
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
    public Long getID() {
        return id;
    }
    public void setID(Long id){
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date date){
        this.lastModified = date;
    }

    public void setCreateDate(Date date){
        this.createDate = date;
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

    public List<TagsClass> getTags(){
//        for(String s:tags)
//            System.out.println(s);
        return tags;
    }

    public void setTags(List<TagsClass> tg){
        tags=tg;
        for(TagsClass t : tags)
            System.out.println(t.getName());
    }

    public void addTag(String tg, String hexCode){
        TagsClass tag=new TagsClass(tg, hexCode);
        tags.add(tag);
    }
}
