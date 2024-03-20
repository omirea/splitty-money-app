package commons;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "debt")
public class Debt {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long d_id;
    private boolean settled;
    @ManyToOne
    @JoinColumn(name = "exp_id", nullable = false)
    private Expense expense;
    @ManyToOne
    @JoinColumn(name = "debt_id", nullable = false)
    private Participant from;
    @OneToOne
    @JoinColumn(name = "p_id", referencedColumnName = "p_id")
    private Participant to;
    private double amount;

    /**
     * Constructs a new debt object
     * @param from participant who pays debt
     * @param to participant who gets paid
     * @param amount value of the debt
     */
    public Debt(Participant from, Participant to, double amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        settled = false;
    }

    public Debt(Long d_id, Expense expense, Participant from, Participant to, double amount) {
        this.d_id = d_id;
        this.expense = expense;
        this.from = from;
        this.to = to;
        this.amount = amount;
        settled = false;
    }

    public Long getD_id() {
        return d_id;
    }

    public void setD_id(Long d_id) {
        this.d_id = d_id;
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    public Debt() {}

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
    public double getAmount() {
        return amount;
    }

    /**
     * setter for value of debt
     * @param value value of the debt
     */
    public void setAmount(double value) {
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
        return settled == debt.settled && Double.compare(amount, debt.amount) == 0 && Objects.equals(from, debt.from)
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
