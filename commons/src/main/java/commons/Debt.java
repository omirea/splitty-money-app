package commons;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "debt")
public class Debt {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private boolean settled;
    @ManyToOne
    @JoinColumn(name = "ex_id")
    private Expense expense;

    @ManyToOne
    @JoinColumn(name = "from_id", nullable = false)
    private Participant from;

    @ManyToOne
    @JoinColumn(name = "to_id", nullable = false)
    private Participant to;

    private Double amount;

    /**
     * Constructs a new debt object
     * @param from participant who pays debt
     * @param to participant who gets paid
     * @param amount value of the debt
     */
    public Debt(Participant from, Participant to, Double amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        settled = false;
    }

    public Debt(Expense expense, Participant from, Participant to, Double amount) {
        this.expense = expense;
        this.from = from;
        this.to = to;
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

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    /**
     * getter for settled
     * @return whether the debt has been settled
     */
    public boolean isSettled() {
        return settled;
    }

    /**
     * setter for whether the debt is settled
     * @param settled value to be assigned to settled
     */
    public void setSettled(boolean settled) {
        this.settled = settled;
    }

    /**
     * getter for participant paying the debt
     * @return participant who is to pay the debt
     */
    public Participant getFrom() {
        return from;
    }


    /**
     * setter for participant paying the debt
     * @param from participant who is to pay the debt
     */
    public void setFrom(Participant from) {
        this.from = from;
    }

    /**
     * getter for receiver of debt
     * @return participant receiving the debt
     */
    public Participant getTo() {
        return to;
    }

    /**
     * setter for receiver of debt
     * @param to participant receiving the debt
     */
    public void setTo(Participant to) {
        this.to = to;
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
            && Objects.equals(from, debt.from)
                && Objects.equals(to, debt.to);
    }

    /**
     * hashcode method for debt
     * @return the hashcode of this debt
     */
    @Override
    public int hashCode() {
        return Objects.hash(settled, from, to, amount);
    }
}
