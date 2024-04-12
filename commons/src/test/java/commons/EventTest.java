package commons;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    private Event event;
    private Event eventAll;


    @BeforeEach
    void setupEvent() {
        event = new Event("Party", "DER45");
        Event eventName = new Event("Brunch");
        Event eventNull = new Event();

        Participant from = new Participant("Person1", "abc@xyz.com",
            "Bob", "NL123", "123");
        Participant to = new Participant("Person2", "def@xyz.com",
            "Bob", "NL456", "456");

        List<Participant> p = new ArrayList<>(List.of(from, to));
        List<Expense> e = new ArrayList<>(List.of(new Expense( "This is a description",
            2.09,"This is a type", LocalDate.of(2005, 12, 1),
            Currency.getInstance("BAM"))));
        List<Debt> d = new ArrayList<>(List.of(new Debt(from, to, 10.04)));
        eventAll = new Event("Trip", "DGR45", e, p, d);
        eventAll.setID((long) 1);
        eventAll.setLastModified(new Date(2004, 04, 11));
        eventAll.setCreateDate(new Date(2000, 06, 21));
    }

    @Test
    void getNameTest(){
        assertEquals("Party", event.getName());
    }

    @Test
    void setNameTest(){
        event.setName("Dinner");
        assertEquals("Dinner", event.getName());
    }

    @Test
    void getInvitationIDTest(){
        assertEquals("DER45", event.getInvitationID());
    }

    @Test
    void setInvitationTest(){
        event.setInvitationID("ABC12");
        assertEquals("ABC12", event.getInvitationID());
    }

    @Test
    void getExpensesTest(){
        List<Expense> ex = new ArrayList<>(List.of(new Expense( "This is a description",
            2.09,"This is a type", LocalDate.of(2005, 12, 1),
            Currency.getInstance("BAM"))));
        assertEquals(ex, eventAll.getExpenses());
    }

    @Test
    void setExpensesTest(){
        List<Expense> ex = new ArrayList<>(List.of(new Expense( "This is a description",
            10.04,"This is a type", LocalDate.of(2005, 10, 1),
            Currency.getInstance("BAM"))));
        eventAll.setExpenses(ex);
        assertEquals(ex, eventAll.getExpenses());
    }

    @Test
    void getParticipantsTest(){
        Participant to = new Participant("Person2", "def@xyz.com",
            "Bob", "NL456", "456");
        Participant from = new Participant("Person1", "abc@xyz.com",
            "Bob", "NL123", "123");
        List<Participant> pa = new ArrayList<>(List.of(from, to));
        assertEquals(pa, eventAll.getParticipants());
    }

    @Test
    void setParticipantsTest(){
        Participant to = new Participant("Bob", "def@xyz.com",
            "tina", "NL456", "456");
        Participant from = new Participant("flo", "abc@xyz.com",
            "Fred", "NL123", "123");
        List<Participant> pa = new ArrayList<>(List.of(from, to));
        eventAll.setParticipants(pa);
        assertEquals(pa, eventAll.getParticipants());
    }

    @Test
    void getDebtsTest(){
        Participant to = new Participant("Person2", "def@xyz.com",
            "Bob", "NL456", "456");
        Participant from = new Participant("Person1", "abc@xyz.com",
            "Bob", "NL123", "123");
        List<Debt> de = new ArrayList<>(List.of(new Debt(from, to, 10.04)));
        assertEquals(de, eventAll.getDebts());
    }

    @Test
    void toStringTest(){
        String s = "Event{name='Party', invitationID='DER45'}";
        assertEquals(s, event.toString());
    }

    @Test
    void getIDTest(){
        Long id = (long)1;
        assertEquals(id, eventAll.getID());
    }

    @Test
    void setIDTest(){
        Long id = (long) 2;
        eventAll.setID(id);
        assertEquals(id, eventAll.getID());
    }

    @Test
    void getLastModifiedTest(){
        Date d = new Date(2004, 04, 11);
        assertEquals(d, eventAll.getLastModified());
    }

    @Test
    void setLastModifiedTest(){
        Date d = new Date(2004, 03, 23);
        eventAll.setLastModified(d);
        assertEquals(d, eventAll.getLastModified());
    }

    @Test
    void getCreateDateTest(){
        Date d = new Date(2000, 06, 21);
        assertEquals(d, eventAll.getCreateDate());
    }

    @Test
    void setCreateDateTest(){
        Date d = new Date(2006, 06, 21);
        eventAll.setCreateDate(d);
        assertEquals(d, eventAll.getCreateDate());
    }


    @Test
    void testEqualsTrue() {
        Event event3 = new Event("Party", "DER45");
        assertEquals(event, event3);
    }

    @Test
    void testEqualsFalse(){
        Event event2 = new Event("P", "DER45");
        assertNotEquals(event, event2);
    }

    @Test
    void testHashCodeTrue() {
        Event event3 = new Event("Party", "DER45");
        assertEquals(event.hashCode(), event3.hashCode());
    }

    @Test
    void testHashCodeFalse() {
        Event event2 = new Event("P", "DER45");
        assertNotEquals(event.hashCode(), event2.hashCode());
    }
}