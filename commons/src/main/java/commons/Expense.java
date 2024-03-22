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
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @OneToMany(mappedBy = "expense")
    private List<Debt> debts;

    private String description; // Description by the participant

    private double amount; // Value of the expense

    private String type; // Type of expense

    private LocalDate dateSent; //date the expense is sent

    private Currency currency; //currency used

    /**
     * Constructor for expense
     * @param description description of the participant
     * @param amount value of the expense
     * @param type type of the expense
     * @param dateSent date when the request for the expense has been made
     * @param currency currency of the expense
     */
    public Expense(String description, double amount, String type,
                   LocalDate dateSent, Currency currency) {
        this.description = description;
        this.amount = amount;
        this.type = type;
        this.dateSent = dateSent;
        this.currency = currency;
    }

    public Expense(Long id, Event event, List<Debt> debts, String description, double amount, String type,
                   LocalDate dateSent, Currency currency) {
        this.id = id;
        this.event = event;
        this.debts = debts;
        this.description = description;
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
    public String getDescription() {
        return description;
    }

    /**
     * Getter for the value
     * @return the value of the expense
     */
    public double getAmount() {
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
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Setter for the value of the expense
     * @param value value of the expense
     */
    public void setAmount(double value) {
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
     <<<<<<< HEAD
     * @param o other Expense
     * @return boolean true or false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expense expense = (Expense) o;
        return Double.compare(amount, expense.amount) == 0 &&
                Objects.equals(description, expense.description) &&
                Objects.equals(type, expense.type) &&
                Objects.equals(dateSent, expense.dateSent) &&
                Objects.equals(currency, expense.currency);
    }

    /**
     * Hash method for Expense
     * @return int hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(description, amount, type, dateSent, currency);
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
                ", debts=" + debts +
                ", description='" + description + '\'' +
                ", value=" + amount +
                ", type='" + type + '\'' +
                ", dateSent=" + dateSent +
                ", currency=" + currency +
                '}';
    }
}
