package commons;


import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {





    @Test
    void getNameTest(){
        Event event = new Event("Party", "DER45");
        assertEquals("Party", event.getName());
    }

    @Test
    void setNameTest(){
        Event event = new Event("Party", "DER45");
        event.setName("Dinner");
        assertEquals("Dinner", event.getName());
    }

    @Test
    void getInvitationIDTest(){
        Event event = new Event("Party", "DER45");
        assertEquals("DER45", event.getInvitationID());
    }

    @Test
    void setInvitationTest(){

        Event event = new Event("Party", "DER45");
        event.setInvitationID("ABC12");
        assertEquals("ABC12", event.getInvitationID());
    }





    @Test
    void testEqualsTrue() {


        Event event = new Event("Party", "DER45");
        Event event3 = new Event("Party", "DER45");
        assertEquals(event, event3);
    }

    @Test
    void testEqualsFalse(){


        Event event = new Event("Party", "DER45");
        Event event2 = new Event("Party", "DER45");
        assertNotEquals(event, event2);
    }

    @Test
    void testHashCodeTrue() {


        Event event = new Event("Party", "DER45");
        Event event3 = new Event("Party", "DER45");
        assertEquals(event.hashCode(), event3.hashCode());
    }

    @Test
    void testHashCodeFalse() {

        Event event = new Event("Party", "DER45");
        Event event2 = new Event("P", "DER45");
        assertNotEquals(event.hashCode(), event2.hashCode());
    }
}