package commons;


import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {

//    @Test
//    void getParticipantsTest(){
//        Participant p1 = new Participant("tom", "123", "23RABO", "45rt6");
//        Participant p2 = new Participant("jerry", "345", "34ABN", "op90");
//        ArrayList<Participant> pl1 = new ArrayList<>(List.of(p1, p2));
//        List<Participant> paidFor = new ArrayList<>(List.of(p2));
//
//        Expense e1 = new Expense(p1, "dinner", 18.00, "food", paidFor,
//            LocalDate.of(2005, 12, 1),
//            Currency.getInstance("BAM") );
//        Expense e2 = new Expense(p1, "lunch", 14.00, "food", paidFor,
//            LocalDate.of(2006, 12, 1),
//            Currency.getInstance("BAM") );
//        ArrayList<Expense> el1 = new ArrayList<>(List.of(e1, e2));
//        Event event = new Event(pl1, el1, "Party", "DER45");
//        assertEquals(pl1, event.getParticipants());
//    }
//
//    @Test
//    void setParticipantsTest(){
//        Participant p1 = new Participant("tom", "123", "23RABO", "45rt6");
//        Participant p2 = new Participant("jerry", "345", "34ABN", "op90");
//        Participant p3 = new Participant("cat", "567", "PO23", "ddf34");
//        ArrayList<Participant> pl1 = new ArrayList<>(List.of(p1, p2));
//        ArrayList<Participant> pl2 = new ArrayList<>(List.of(p1, p2, p3));
//        List<Participant> paidFor = new ArrayList<>(List.of(p2));
//
//        Expense e1 = new Expense(p1, "dinner", 18.00, "food", paidFor,
//            LocalDate.of(2005, 12, 1),
//            Currency.getInstance("BAM") );
//        Expense e2 = new Expense(p1, "lunch", 14.00, "food", paidFor,
//            LocalDate.of(2006, 12, 1),
//            Currency.getInstance("BAM") );
//        ArrayList<Expense> el1 = new ArrayList<>(List.of(e1, e2));
//        Event event = new Event(pl1, el1, "Party", "DER45");
//        event.setParticipants(pl2);
//        assertEquals(pl2, event.getParticipants());
//    }
//
//    @Test
//    void getExpensesTest(){
//        Participant p1 = new Participant("tom", "123", "23RABO", "45rt6");
//        Participant p2 = new Participant("jerry", "345", "34ABN", "op90");
//        ArrayList<Participant> pl1 = new ArrayList<>(List.of(p1, p2));
//        List<Participant> paidFor = new ArrayList<>(List.of(p2));
//
//        Expense e1 = new Expense(p1, "dinner", 18.00, "food", paidFor,
//            LocalDate.of(2005, 12, 1),
//            Currency.getInstance("BAM") );
//        Expense e2 = new Expense(p1, "lunch", 14.00, "food", paidFor,
//            LocalDate.of(2006, 12, 1),
//            Currency.getInstance("BAM") );
//        ArrayList<Expense> el1 = new ArrayList<>(List.of(e1, e2));
//        Event event = new Event(pl1, el1, "Party", "DER45");
//        assertEquals(el1, event.getExpenses());
//    }
//
//    @Test
//    void setExpensesTest(){
//        Participant p1 = new Participant("tom", "123", "23RABO", "45rt6");
//        Participant p2 = new Participant("jerry", "345", "34ABN", "op90");
//        ArrayList<Participant> pl1 = new ArrayList<>(List.of(p1, p2));
//        List<Participant> paidFor = new ArrayList<>(List.of(p2));
//
//        Expense e1 = new Expense(p1, "dinner", 18.00, "food", paidFor,
//            LocalDate.of(2005, 12, 1),
//            Currency.getInstance("BAM") );
//        Expense e2 = new Expense(p1, "lunch", 14.00, "food", paidFor,
//            LocalDate.of(2006, 12, 1),
//            Currency.getInstance("BAM") );
//        ArrayList<Expense> el1 = new ArrayList<>(List.of(e1, e2));
//        ArrayList<Expense> el2 = new ArrayList<>(List.of(e1));
//        Event event = new Event(pl1, el1, "Party", "DER45");
//        event.setExpenses(el2);
//        assertEquals(el2, event.getExpenses());
//    }
//
//    @Test
//    void getNameTest(){
//        Participant p1 = new Participant("tom", "123", "23RABO", "45rt6");
//        Participant p2 = new Participant("jerry", "345", "34ABN", "op90");
//        ArrayList<Participant> pl1 = new ArrayList<>(List.of(p1, p2));
//        List<Participant> paidFor = new ArrayList<>(List.of(p2));
//
//        Expense e1 = new Expense(p1, "dinner", 18.00, "food", paidFor,
//            LocalDate.of(2005, 12, 1),
//            Currency.getInstance("BAM") );
//        Expense e2 = new Expense(p1, "lunch", 14.00, "food", paidFor,
//            LocalDate.of(2006, 12, 1),
//            Currency.getInstance("BAM") );
//        ArrayList<Expense> el1 = new ArrayList<>(List.of(e1, e2));
//        Event event = new Event(pl1, el1, "Party", "DER45");
//        assertEquals("Party", event.getName());
//    }
//
//    @Test
//    void setNameTest(){
//        Participant p1 = new Participant("tom", "123", "23RABO", "45rt6");
//        Participant p2 = new Participant("jerry", "345", "34ABN", "op90");
//        ArrayList<Participant> pl1 = new ArrayList<>(List.of(p1, p2));
//        List<Participant> paidFor = new ArrayList<>(List.of(p2));
//
//        Expense e1 = new Expense(p1, "dinner", 18.00, "food", paidFor,
//            LocalDate.of(2005, 12, 1),
//            Currency.getInstance("BAM") );
//        Expense e2 = new Expense(p1, "lunch", 14.00, "food", paidFor,
//            LocalDate.of(2006, 12, 1),
//            Currency.getInstance("BAM") );
//        ArrayList<Expense> el1 = new ArrayList<>(List.of(e1, e2));
//        Event event = new Event(pl1, el1, "Party", "DER45");
//        event.setName("Dinner");
//        assertEquals("Dinner", event.getName());
//    }
//
//    @Test
//    void getInvitationIDTest(){
//        Participant p1 = new Participant("tom", "123", "23RABO", "45rt6");
//        Participant p2 = new Participant("jerry", "345", "34ABN", "op90");
//        ArrayList<Participant> pl1 = new ArrayList<>(List.of(p1, p2));
//        List<Participant> paidFor = new ArrayList<>(List.of(p2));
//
//        Expense e1 = new Expense(p1, "dinner", 18.00, "food", paidFor,
//            LocalDate.of(2005, 12, 1),
//            Currency.getInstance("BAM") );
//        Expense e2 = new Expense(p1, "lunch", 14.00, "food", paidFor,
//            LocalDate.of(2006, 12, 1),
//            Currency.getInstance("BAM") );
//        ArrayList<Expense> el1 = new ArrayList<>(List.of(e1, e2));
//        Event event = new Event(pl1, el1, "Party", "DER45");
//        assertEquals("DER45", event.getInvitationID());
//    }
//
//    @Test
//    void setInvitationTest(){
//        Participant p1 = new Participant("tom", "123", "23RABO", "45rt6");
//        Participant p2 = new Participant("jerry", "345", "34ABN", "op90");
//        ArrayList<Participant> pl1 = new ArrayList<>(List.of(p1, p2));
//        List<Participant> paidFor = new ArrayList<>(List.of(p2));
//
//        Expense e1 = new Expense(p1, "dinner", 18.00, "food", paidFor,
//            LocalDate.of(2005, 12, 1),
//            Currency.getInstance("BAM") );
//        Expense e2 = new Expense(p1, "lunch", 14.00, "food", paidFor,
//            LocalDate.of(2006, 12, 1),
//            Currency.getInstance("BAM") );
//        ArrayList<Expense> el1 = new ArrayList<>(List.of(e1, e2));
//        Event event = new Event(pl1, el1, "Party", "DER45");
//        event.setInvitationID("ABC12");
//        assertEquals("ABC12", event.getInvitationID());
//    }
//
//    @Test
//    void addParticipantTest() {
//        Participant p1 = new Participant("tom", "123", "23RABO", "45rt6");
//        Participant p2 = new Participant("jerry", "345", "34ABN", "op90");
//        Participant p3 = new Participant("cat", "567", "PO23", "ddf34");
//        ArrayList<Participant> pl1 = new ArrayList<>(List.of(p1, p2));
//        ArrayList<Participant> pl2 = new ArrayList<>(List.of(p1, p2, p3));
//        List<Participant> paidFor = new ArrayList<>(List.of(p2));
//
//        Expense e1 = new Expense(p1, "dinner", 18.00, "food", paidFor,
//            LocalDate.of(2005, 12, 1),
//            Currency.getInstance("BAM") );
//        Expense e2 = new Expense(p1, "lunch", 14.00, "food", paidFor,
//            LocalDate.of(2006, 12, 1),
//            Currency.getInstance("BAM") );
//        ArrayList<Expense> el1 = new ArrayList<>(List.of(e1, e2));
//
//        Event event = new Event(pl1, el1, "Party", "DER45");
//        event.addParticipant(p3);
//        assertEquals(event.getParticipants(), pl2);
//    }
//
//    @Test
//    void removeParticipantTest() {
//        Participant p1 = new Participant("tom", "123", "23RABO", "45rt6");
//        Participant p2 = new Participant("jerry", "345", "34ABN", "op90");
//        Participant p3 = new Participant("cat", "567", "PO23", "ddf34");
//        ArrayList<Participant> pl1 = new ArrayList<>(List.of(p1, p2, p3));
//        List<Participant> paidFor = new ArrayList<>(List.of(p2));
//
//        Expense e1 = new Expense(p1, "dinner", 18.00, "food", paidFor,
//            LocalDate.of(2005, 12, 1),
//            Currency.getInstance("BAM") );
//        Expense e2 = new Expense(p1, "lunch", 14.00, "food", paidFor,
//            LocalDate.of(2006, 12, 1),
//            Currency.getInstance("BAM") );
//        ArrayList<Expense> el1 = new ArrayList<>(List.of(e1, e2));
//
//        Event event = new Event(pl1, el1, "Party", "DER45");
//
//        assertEquals(event.removeParticipant(p2), true);
//        assertEquals(event.removeParticipant(p2), false);
//    }
//
//    @Test
//    void addExpense() {
//        Participant p1 = new Participant("tom", "123", "23RABO", "45rt6");
//        Participant p2 = new Participant("jerry", "345", "34ABN", "op90");
//        Participant p3 = new Participant("cat", "567", "PO23", "ddf34");
//        ArrayList<Participant> pl1 = new ArrayList<>(List.of(p1, p2, p3));
//        ArrayList<Participant> paidFor = new ArrayList<>(List.of(p2));
//
//        Expense e1 = new Expense(p1, "dinner", 18.00, "food", paidFor,
//            LocalDate.of(2005, 12, 1),
//            Currency.getInstance("BAM") );
//        Expense e2 = new Expense(p1, "lunch", 14.00, "food", paidFor,
//            LocalDate.of(2006, 12, 1),
//            Currency.getInstance("BAM") );
//        ArrayList<Expense> el1 = new ArrayList<>(List.of(e1));
//        ArrayList<Expense> el2 = new ArrayList<>(List.of(e1, e2));
//
//        Event event = new Event(pl1, el1, "Party", "DER45");
//        event.addExpense(e2);
//        assertEquals(event.getExpenses(), el2);
//
//    }
//
//    @Test
//    void removeExpense() {
//        Participant p1 = new Participant("tom", "123", "23RABO", "45rt6");
//        Participant p2 = new Participant("jerry", "345", "34ABN", "op90");
//        Participant p3 = new Participant("cat", "567", "PO23", "ddf34");
//        ArrayList<Participant> pl1 = new ArrayList<>(List.of(p1, p2, p3));
//        List<Participant> paidFor = new ArrayList<>(List.of(p2));
//
//        Expense e1 = new Expense(p1, "dinner", 18.00, "food", paidFor,
//            LocalDate.of(2005, 12, 1),
//            Currency.getInstance("BAM") );
//        Expense e2 = new Expense(p1, "lunch", 14.00, "food", paidFor,
//            LocalDate.of(2006, 12, 1),
//            Currency.getInstance("BAM") );
//        ArrayList<Expense> el1 = new ArrayList<>(List.of(e1, e2));
//
//        Event event = new Event(pl1, el1, "Party", "DER45");
//
//        assertEquals(event.removeExpense(e2), true);
//        assertEquals(event.removeExpense(e2), false);
//    }
//
//    @Test
//    void testEqualsTrue() {
//        Participant p1 = new Participant("tom", "123", "23RABO", "45rt6");
//        Participant p2 = new Participant("jerry", "345", "34ABN", "op90");
//        Participant p3 = new Participant("cat", "567", "PO23", "ddf34");
//        ArrayList<Participant> pl1 = new ArrayList<>(List.of(p1, p2, p3));
//        List<Participant> paidFor = new ArrayList<>(List.of(p2));
//
//        Expense e1 = new Expense(p1, "dinner", 18.00, "food", paidFor,
//            LocalDate.of(2005, 12, 1),
//            Currency.getInstance("BAM") );
//        Expense e2 = new Expense(p1, "lunch", 14.00, "food", paidFor,
//            LocalDate.of(2006, 12, 1),
//            Currency.getInstance("BAM") );
//        ArrayList<Expense> el1 = new ArrayList<>(List.of(e1, e2));
//
//        Event event = new Event(pl1, el1, "Party", "DER45");
//        Event event3 = new Event(pl1, el1, "Party", "DER45");
//        assertEquals(event, event3);
//    }
//
//    @Test
//    void testEqualsFalse(){
//        Participant p1 = new Participant("tom", "123", "23RABO", "45rt6");
//        Participant p2 = new Participant("jerry", "345", "34ABN", "op90");
//        Participant p3 = new Participant("cat", "567", "PO23", "ddf34");
//        ArrayList<Participant> pl1 = new ArrayList<>(List.of(p1, p2, p3));
//        ArrayList<Participant> pl2 = new ArrayList<>(List.of(p1, p2));
//        List<Participant> paidFor = new ArrayList<>(List.of(p2));
//
//        Expense e1 = new Expense(p1, "dinner", 18.00, "food", paidFor,
//            LocalDate.of(2005, 12, 1),
//            Currency.getInstance("BAM") );
//        Expense e2 = new Expense(p1, "lunch", 14.00, "food", paidFor,
//            LocalDate.of(2006, 12, 1),
//            Currency.getInstance("BAM") );
//        ArrayList<Expense> el1 = new ArrayList<>(List.of(e1, e2));
//
//        Event event = new Event(pl1, el1, "Party", "DER45");
//        Event event2 = new Event(pl2, el1, "Party", "DER45");
//        assertNotEquals(event, event2);
//    }
//
//    @Test
//    void testHashCodeTrue() {
//        Participant p1 = new Participant("tom", "123", "23RABO", "45rt6");
//        Participant p2 = new Participant("jerry", "345", "34ABN", "op90");
//        Participant p3 = new Participant("cat", "567", "PO23", "ddf34");
//        ArrayList<Participant> pl1 = new ArrayList<>(List.of(p1, p2, p3));
//        List<Participant> paidFor = new ArrayList<>(List.of(p2));
//
//        Expense e1 = new Expense(p1, "dinner", 18.00, "food", paidFor,
//            LocalDate.of(2005, 12, 1),
//            Currency.getInstance("BAM") );
//        Expense e2 = new Expense(p1, "lunch", 14.00, "food", paidFor,
//            LocalDate.of(2006, 12, 1),
//            Currency.getInstance("BAM") );
//        ArrayList<Expense> el1 = new ArrayList<>(List.of(e1, e2));
//
//        Event event = new Event(pl1, el1, "Party", "DER45");
//        Event event3 = new Event(pl1, el1, "Party", "DER45");
//        assertEquals(event.hashCode(), event3.hashCode());
//    }
//
//    @Test
//    void testHashCodeFalse() {
//        Participant p1 = new Participant("tom", "123", "23RABO", "45rt6");
//        Participant p2 = new Participant("jerry", "345", "34ABN", "op90");
//        Participant p3 = new Participant("cat", "567", "PO23", "ddf34");
//        ArrayList<Participant> pl1 = new ArrayList<>(List.of(p1, p2, p3));
//        ArrayList<Participant> pl2 = new ArrayList<>(List.of(p1, p2));
//        List<Participant> paidFor = new ArrayList<>(List.of(p2));
//
//        Expense e1 = new Expense(p1, "dinner", 18.00, "food", paidFor,
//            LocalDate.of(2005, 12, 1),
//            Currency.getInstance("BAM") );
//        Expense e2 = new Expense(p1, "lunch", 14.00, "food", paidFor,
//            LocalDate.of(2006, 12, 1),
//            Currency.getInstance("BAM") );
//        ArrayList<Expense> el1 = new ArrayList<>(List.of(e1, e2));
//
//        Event event = new Event(pl1, el1, "Party", "DER45");
//        Event event2 = new Event(pl2, el1, "Party", "DER45");
//        assertNotEquals(event.hashCode(), event2.hashCode());
//    }
}