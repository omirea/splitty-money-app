package commons;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "debt")
public class Debt {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Boolean settled;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "from_id", nullable = false)
    private Participant hasToPay;

    @ManyToOne
    @JoinColumn(name = "to_id", nullable = false)
    private Participant whoPaid;

    private Double amount;

    /**
     * Constructs a new debt object
     * @param hasToPay participant who pays debt
     * @param whoPaid participant who gets paid
     * @param amount value of the debt
     */
    public Debt(Participant hasToPay, Participant whoPaid, Double amount) {
        this.hasToPay = hasToPay;
        this.whoPaid = whoPaid;
        this.amount = amount;
        settled = false;
    }

    public Debt(Event event, Participant hasToPay, Participant whoPaid, Double amount) {
        this.event = event;
        this.hasToPay = hasToPay;
        this.whoPaid = whoPaid;
        this.amount = amount;
        settled = false;
    }

    public Debt() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     * getter for settled
     * @return whether the debt has been settled
     */
    public Boolean isSettled() {
        return settled;
    }

    /**
     * setter for whether the debt is settled
     * @param settled value to be assigned to settled
     */
    public void setSettled(Boolean settled) {
        this.settled = settled;
    }

    /**
     * getter for participant paying the debt
     * @return participant who is to pay the debt
     */
    public Participant getHasToPay() {
        return hasToPay;
    }

    /**
     * setter for participant paying the debt
     * @param from participant who is to pay the debt
     */
    public void setHasToPay(Participant from) {
        this.hasToPay = from;
    }

    /**
     * getter for receiver of debt
     * @return participant receiving the debt
     */
    public Participant getWhoPaid() {
        return whoPaid;
    }

    /**
     * setter for receiver of debt
     * @param to participant receiving the debt
     */
    public void setWhoPaid(Participant to) {
        this.whoPaid = to;
    }

    /**
     * getter for value of debt
     * @return value of the debt
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * setter for value of debt
     * @param value value of the debt
     */
    public void setAmount(Double value) {
        this.amount = value;
    }

    @Override
    public String toString() {
        return "Debt" +
                ", from=" + hasToPay +
                ", to=" + whoPaid +
                ", amount=" + amount +
                '}';
    }

    /**
     * equals method to compare debts
     * @param o to be compared to this for equality
     * @return whether parameter has same values as this debt
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Debt debt = (Debt) o;
        return settled == debt.settled && Double.compare(amount, debt.amount) == 0
            && Objects.equals(hasToPay, debt.hasToPay)
                && Objects.equals(whoPaid, debt.whoPaid);
    }

    /**
     * hashcode method for debt
     * @return the hashcode of this debt
     */
    @Override
    public int hashCode() {
        return Objects.hash(settled, hasToPay, whoPaid, amount);
    }
}
