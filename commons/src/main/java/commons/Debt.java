package commons;

import java.util.Objects;

public class Debt {
    private boolean settled;
    private Participant from;
    private Participant to;
    private double value;

    /**
     * Constructs a new debt object
     * @param from participant who pays debt
     * @param to participant who gets paid
     * @param value value of the debt
     */
    public Debt(Participant from, Participant to, double value) {
        this.from = from;
        this.to = to;
        this.value = value;
        settled = false;
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
    public double getValue() {
        return value;
    }

    /**
     * setter for value of debt
     * @param value value of the debt
     */
    public void setValue(double value) {
        this.value = value;
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
        return settled == debt.settled && Double.compare(value, debt.value) == 0 && Objects.equals(from, debt.from) && Objects.equals(to, debt.to);
    }

    /**
     * hashcode method for debt
     * @return the hashcode of this debt
     */
    @Override
    public int hashCode() {
        return Objects.hash(settled, from, to, value);
    }
}