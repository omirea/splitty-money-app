package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ParticipantTest {

    private Participant p;
    private Participant pEvent;
    private Participant pDebts;

    @BeforeEach
    void setupExpense() {
        p = new Participant("Bob", "bob@gmail.com", "Bob LastName",
            "12345", "123");
        pEvent = new Participant("Bob", "bob@gmail.com", "Bob LastName",
            "12345", "123", new Event());

        pDebts = new Participant(new Event(),List.of(new Debt()), List.of(new Debt()),
            "Bob", "bob@gmail.com", "Bob LastName",
            "12345", "123");
        pDebts.setId((long) 1);
        Participant pa = new Participant();
    }

    @Test
    void getIdTest(){
        Long id = (long) 1;
        assertEquals(id, pDebts.getId());
    }

    @Test
    void getEventTest(){
        Event e = new Event();
        assertEquals(e, pDebts.getEvent());
    }

    @Test
    void setEventTest(){
        Event e = new Event("help");
        pDebts.setEvent(e);
        assertEquals(e, pDebts.getEvent());
    }

    @Test
    public void setNameTest(){
        assertEquals(p.getName(), "Bob");
        p.setName("Stephany");
        assertEquals(p.getName(), "Stephany");
    }

    @Test
    public void getEmailTest(){
        assertEquals(p.getEmail(), "bob@gmail.com");
    }

    @Test
    public void setEmailTest(){
        assertEquals(p.getEmail(), "bob@gmail.com");
        p.setEmail("steph@gmail.com");
        assertEquals(p.getEmail(), "steph@gmail.com");
    }

    @Test
    public void getIBANTest(){
        assertEquals(p.getIBAN(), "12345");
    }

    @Test
    public void setIBANTest(){
        assertEquals(p.getIBAN(), "12345");
        p.setIBAN("00000");
        assertEquals(p.getIBAN(), "00000");
    }

    @Test
    public void getBICTest(){
        assertEquals(p.getBIC(), "123");
    }

    @Test
    public void setBICTest(){
        assertEquals(p.getBIC(), "123");
        p.setBIC("000");
        assertEquals(p.getBIC(), "000");
    }

    @Test
    public void getAccTest(){
        assertEquals(p.getAccountHolder(), "Bob LastName");
    }

    @Test
    public void setAccTest(){
        assertEquals(p.getAccountHolder(), "Bob LastName");
        p.setAccountHolder("Bob LName");
        assertEquals(p.getAccountHolder(), "Bob LName");
    }

    @Test
    public void nullTest(){
        Participant p2=null;
        assertNotEquals(p, p2);
    }

    @Test
    public void equalTest(){
        Participant p=new Participant("Bob", "bob@gmail.com", "12345",
            "Bob LastName","123");
        Participant p2=new Participant("Bob", "bob@gmail.com", "12345",
                "Bob LastName","123");
        assertEquals(p, p2);
    }

    @Test
    public void difNameTest(){
        Participant p2=new Participant("Sasha", "bob@gmail.com", "12345",
                "Bob LastName","123");
        assertNotEquals(p, p2);
    }

    @Test
    public void difEmailTest(){
        Participant p2=new Participant("Bob", "sasha@gmail.com", "12345",
                "Bob LastName","123");
        assertNotEquals(p, p2);
    }

    @Test
    public void difIBANTest(){
        Participant p2=new Participant("Bob", "bob@gmail.com", "00000",
                "Bob LastName","123");
        assertNotEquals(p, p2);
    }

    @Test
    public void difBICTest(){
        Participant p2=new Participant("Bob", "bob@gmail.com", "12345",
                "Bob LastName","000");
        assertNotEquals(p, p2);
    }

    @Test
    public void difAccTest(){
        Participant p2=new Participant("Bob", "bob@gmail.com", "12345",
                "Bob FirstName","123");
        assertNotEquals(p, p2);
    }

    @Test
    public void hashCodeTest(){
        Participant p=new Participant("Bob", "bob@gmail.com", "12345",
                "Bob LastName","123");
        assertEquals(p.hashCode(), 1948978983);
    }

    @Test
    public void toStringTest(){
        Participant p=new Participant("Bob", "bob@gmail.com", "12345",
                "Bob LastName","123");
        assertEquals(p.toString(), "Participant has name: 'Bob', email: 'bob@gmail.com', " +
                "account holder name: 12345', IBAN: 'Bob LastName', BIC: '123';");
    }
}
