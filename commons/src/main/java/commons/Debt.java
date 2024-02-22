package commons;

import java.util.Objects;

public class Debt {
    private boolean settled;
    private Participant from;
    private Participant to;
    private double value;

    public Debt(Participant from, Participant to, double value) {
        this.from = from;
        this.to = to;
        this.value = value;
        settled = false;
    }

    public boolean isSettled() {
        return settled;
    }

    public void setSettled(boolean settled) {
        this.settled = settled;
    }

    public Participant getFrom() {
        return from;
    }

    public void setFrom(Participant from) {
        this.from = from;
    }

    public Participant getTo() {
        return to;
    }

    public void setTo(Participant to) {
        this.to = to;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Debt debt = (Debt) o;
        return settled == debt.settled && Double.compare(value, debt.value) == 0 && Objects.equals(from, debt.from) && Objects.equals(to, debt.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(settled, from, to, value);
    }
}
