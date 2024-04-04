package commons;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.*;


@Entity
@Table(name = "expense")
 public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

//    @OneToMany(mappedBy = "expense")
//    private List<Debt> debts;

    private String name; // Description by the participant

    private Double amount; // Value of the expense

    private String type; // Type of expense

    private LocalDate dateSent; //date the expense is sent

    private Currency currency; //currency used

    /**
     * Constructor for expense
     * @param name description of the participant
     * @param amount value of the expense
     * @param type type of the expense
     * @param dateSent date when the request for the expense has been made
     * @param currency currency of the expense
     */
    public Expense(String name, Double amount, String type,
                   LocalDate dateSent, Currency currency) {
        this.name = name;
        this.amount = amount;
        this.type = type;
        this.dateSent = dateSent;
        this.currency = currency;
    }

    public Expense(Event event, List<Debt> debts, String name,
                   Double amount, String type, LocalDate dateSent, Currency currency) {
        this.event = event;
        //this.debts = debts;
        this.name = name;
        this.amount = amount;
        this.type = type;
        this.dateSent = dateSent;
        this.currency = currency;
    }

    public Expense() {}


    /**
     * Getter for description
     * @return the description of the expense
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the value
     * @return the value of the expense
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * Getter for the type
     * @return the type of the expense
     */
    public String getType() {
        return type;
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
     * Setter for the description of the expense
     * @param description the description of the expense
     */
    public void setName(String description) {
        this.name = description;
    }

    /**
     * Setter for the value of the expense
     * @param value value of the expense
     */
    public void setAmount(Double value) {
        this.amount = value;
    }

    /**
     * Setter for the type of the expense
     * @param type type of the expense
     */
    public void setType(String type) {
        this.type = type;
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
     * @param o other Expense
     * @return boolean true or false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expense expense = (Expense) o;
        return Double.compare(amount, expense.amount) == 0 &&
                Objects.equals(name, expense.name) &&
                Objects.equals(type, expense.type) &&
                Objects.equals(dateSent, expense.dateSent) &&
                Objects.equals(currency, expense.currency);
    }

//    public void setDebts(List<Debt> debts) {
//        this.debts = debts;
//    }

    /**
     * Hash method for Expense
     * @return int hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, amount, type, dateSent, currency);
    }

    /**
     * toString method
     * @return string version of expense
     */
    @Override
    public String toString() {
        return "Expense{" +
                "ex_id=" + id +
                ", event_id=" + event +
//                ", debts=" + debts +
                ", description='" + name + '\'' +
                ", value=" + amount +
                ", type='" + type + '\'' +
                ", dateSent=" + dateSent +
                ", currency=" + currency +
                '}';
    }

    /**
     * Getter for id
     * @return the id of an expense
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id){this.id=id;};

    public void setEvent(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }
}
