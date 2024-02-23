package commons;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    @Test
    void addParticipantTest() {
        Participant p1 = new Participant("tom", "123", "23RABO", "45rt6");
        Participant p2 = new Participant("jerry", "345", "34ABN", "op90");
        Participant p3 = new Participant("cat", "567", "PO23", "ddf34");
        ArrayList<Participant> pl1 = new ArrayList<>(List.of(p1, p2));
        ArrayList<Participant> pl2 = new ArrayList<>(List.of(p1, p2, p3));
        List<Participant> paidFor = new ArrayList<>(List.of(p2));

        Expense e1 = new Expense(p1, "dinner", 18.00, "food", paidFor,
            LocalDate.of(2005, 12, 1),
            Currency.getInstance("BAM") );
        Expense e2 = new Expense(p1, "lunch", 14.00, "food", paidFor,
            LocalDate.of(2006, 12, 1),
            Currency.getInstance("BAM") );
        ArrayList<Expense> el1 = new ArrayList<>(List.of(e1, e2));

        Event event = new Event(pl1, el1, "Party", "DER45");
        event.addParticipant(p3);
        assertEquals(event.getParticipants(), pl2);
    }

    @Test
    void removeParticipantTest() {
        Participant p1 = new Participant("tom", "123", "23RABO", "45rt6");
        Participant p2 = new Participant("jerry", "345", "34ABN", "op90");
        Participant p3 = new Participant("cat", "567", "PO23", "ddf34");
        ArrayList<Participant> pl1 = new ArrayList<>(List.of(p1, p2, p3));
        List<Participant> paidFor = new ArrayList<>(List.of(p2));

        Expense e1 = new Expense(p1, "dinner", 18.00, "food", paidFor,
            LocalDate.of(2005, 12, 1),
            Currency.getInstance("BAM") );
        Expense e2 = new Expense(p1, "lunch", 14.00, "food", paidFor,
            LocalDate.of(2006, 12, 1),
            Currency.getInstance("BAM") );
        ArrayList<Expense> el1 = new ArrayList<>(List.of(e1, e2));

        Event event = new Event(pl1, el1, "Party", "DER45");

        assertEquals(event.removeParticipant(p2), true);
        assertEquals(event.removeParticipant(p2), false);
    }


    @Test
    void addExpense() {
        Participant p1 = new Participant("tom", "123", "23RABO", "45rt6");
        Participant p2 = new Participant("jerry", "345", "34ABN", "op90");
        Participant p3 = new Participant("cat", "567", "PO23", "ddf34");
        ArrayList<Participant> pl1 = new ArrayList<>(List.of(p1, p2, p3));
        ArrayList<Participant> paidFor = new ArrayList<>(List.of(p2));

        Expense e1 = new Expense(p1, "dinner", 18.00, "food", paidFor,
            LocalDate.of(2005, 12, 1),
            Currency.getInstance("BAM") );
        Expense e2 = new Expense(p1, "lunch", 14.00, "food", paidFor,
            LocalDate.of(2006, 12, 1),
            Currency.getInstance("BAM") );
        ArrayList<Expense> el1 = new ArrayList<>(List.of(e1));
        ArrayList<Expense> el2 = new ArrayList<>(List.of(e1, e2));

        Event event = new Event(pl1, el1, "Party", "DER45");
        event.addExpense(e2);
        assertEquals(event.getExpenses(), el2);

    }

    @Test
    void removeExpense() {
        Participant p1 = new Participant("tom", "123", "23RABO", "45rt6");
        Participant p2 = new Participant("jerry", "345", "34ABN", "op90");
        Participant p3 = new Participant("cat", "567", "PO23", "ddf34");
        ArrayList<Participant> pl1 = new ArrayList<>(List.of(p1, p2, p3));
        List<Participant> paidFor = new ArrayList<>(List.of(p2));

        Expense e1 = new Expense(p1, "dinner", 18.00, "food", paidFor,
            LocalDate.of(2005, 12, 1),
            Currency.getInstance("BAM") );
        Expense e2 = new Expense(p1, "lunch", 14.00, "food", paidFor,
            LocalDate.of(2006, 12, 1),
            Currency.getInstance("BAM") );
        ArrayList<Expense> el1 = new ArrayList<>(List.of(e1, e2));

        Event event = new Event(pl1, el1, "Party", "DER45");

        assertEquals(event.removeExpense(e2), true);
        assertEquals(event.removeExpense(e2), false);
    }

    @Test
    void testEquals() {
        Participant p1 = new Participant("tom", "123", "23RABO", "45rt6");
        Participant p2 = new Participant("jerry", "345", "34ABN", "op90");
        Participant p3 = new Participant("cat", "567", "PO23", "ddf34");
        ArrayList<Participant> pl1 = new ArrayList<>(List.of(p1, p2, p3));
        ArrayList<Participant> pl2 = new ArrayList<>(List.of(p1, p2));
        List<Participant> paidFor = new ArrayList<>(List.of(p2));

        Expense e1 = new Expense(p1, "dinner", 18.00, "food", paidFor,
            LocalDate.of(2005, 12, 1),
            Currency.getInstance("BAM") );
        Expense e2 = new Expense(p1, "lunch", 14.00, "food", paidFor,
            LocalDate.of(2006, 12, 1),
            Currency.getInstance("BAM") );
        ArrayList<Expense> el1 = new ArrayList<>(List.of(e1, e2));

        Event event = new Event(pl1, el1, "Party", "DER45");
        Event event2 = new Event(pl2, el1, "Party", "DER45");
        Event event3 = new Event(pl1, el1, "Party", "DER45");
        assertFalse(event.equals(event2));
        assertTrue(event.equals(event3));
    }

    @Test
    void testHashCode() {
        Participant p1 = new Participant("tom", "123", "23RABO", "45rt6");
        Participant p2 = new Participant("jerry", "345", "34ABN", "op90");
        Participant p3 = new Participant("cat", "567", "PO23", "ddf34");
        ArrayList<Participant> pl1 = new ArrayList<>(List.of(p1, p2, p3));
        ArrayList<Participant> pl2 = new ArrayList<>(List.of(p1, p2));
        List<Participant> paidFor = new ArrayList<>(List.of(p2));

        Expense e1 = new Expense(p1, "dinner", 18.00, "food", paidFor,
            LocalDate.of(2005, 12, 1),
            Currency.getInstance("BAM") );
        Expense e2 = new Expense(p1, "lunch", 14.00, "food", paidFor,
            LocalDate.of(2006, 12, 1),
            Currency.getInstance("BAM") );
        ArrayList<Expense> el1 = new ArrayList<>(List.of(e1, e2));

        Event event = new Event(pl1, el1, "Party", "DER45");
        Event event2 = new Event(pl2, el1, "Party", "DER45");
        Event event3 = new Event(pl1, el1, "Party", "DER45");
        assertNotEquals(event.hashCode(), event2.hashCode());
        assertEquals(event.hashCode(), event3.hashCode());
    }
}