package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParticipantTest {

    @Test
    public void getNameTest(){
        Participant p=new Participant("Bob", "bob@gmail.com", "12345",
            "123");
        assertEquals(p.getName(), "Bob");
    }

    @Test
    public void setNameTest(){
        Participant p=new Participant("Bob", "bob@gmail.com", "12345",
            "123");
        assertEquals(p.getName(), "Bob");
        p.setName("Stephany");
        assertEquals(p.getName(), "Stephany");
    }

    @Test
    public void getEmailTest(){
        Participant p=new Participant("Bob", "bob@gmail.com", "12345",
            "123");
        assertEquals(p.getEmail(), "bob@gmail.com");
    }

    @Test
    public void setEmailTest(){
        Participant p=new Participant("Bob", "bob@gmail.com", "12345",
            "123");
        assertEquals(p.getEmail(), "bob@gmail.com");
        p.setEmail("steph@gmail.com");
        assertEquals(p.getEmail(), "steph@gmail.com");
    }

    @Test
    public void getIBANTest(){
        Participant p=new Participant("Bob", "bob@gmail.com", "12345",
            "123");
        assertEquals(p.getIBAN(), "12345");
    }

    @Test
    public void setIBANTest(){
        Participant p=new Participant("Bob", "bob@gmail.com", "12345",
            "123");
        assertEquals(p.getIBAN(), "12345");
        p.setIBAN("00000");
        assertEquals(p.getIBAN(), "00000");
    }

    @Test
    public void getBICTest(){
        Participant p=new Participant("Bob", "bob@gmail.com", "12345",
            "123");
        assertEquals(p.getBIC(), "123");
    }

    @Test
    public void setBICTest(){
        Participant p=new Participant("Bob", "bob@gmail.com", "12345",
            "123");
        assertEquals(p.getBIC(), "123");
        p.setBIC("000");
        assertEquals(p.getBIC(), "000");
    }

    @Test
    public void nullTest(){
        Participant p1=new Participant("Bob", "bob@gmail.com", "12345",
            "123");
        Participant p2=null;
        assertNotEquals(p1, p2);
    }

    @Test
    public void equalTest(){
        Participant p1=new Participant("Bob", "bob@gmail.com", "12345",
            "123");
        Participant p2=new Participant("Bob", "bob@gmail.com", "12345",
            "123");
        assertEquals(p1, p2);
    }

    @Test
    public void difNameTest(){
        Participant p1=new Participant("Bob", "bob@gmail.com", "12345",
            "123");
        Participant p2=new Participant("Sasha", "bob@gmail.com", "12345",
            "123");
        assertNotEquals(p1, p2);
    }

    @Test
    public void difEmailTest(){
        Participant p1=new Participant("Bob", "bob@gmail.com", "12345",
            "123");
        Participant p2=new Participant("Bob", "sasha@gmail.com", "12345",
            "123");
        assertNotEquals(p1, p2);
    }

    @Test
    public void difIBANTest(){
        Participant p1=new Participant("Bob", "bob@gmail.com", "12345",
            "123");
        Participant p2=new Participant("Bob", "bob@gmail.com", "00000",
            "123");
        assertNotEquals(p1, p2);
    }

    @Test
    public void difBICTest(){
        Participant p1=new Participant("Bob", "bob@gmail.com", "12345",
            "123");
        Participant p2=new Participant("Bob", "bob@gmail.com", "12345",
            "000");
        assertNotEquals(p1, p2);
    }

    @Test
    public void hashCodeTest(){
        Participant p=new Participant("Bob", "bob@gmail.com", "12345",
            "123");
        assertEquals(p.hashCode(), -1224606191);
    }

    @Test
    public void toStringTest(){
        Participant p=new Participant("Bob", "bob@gmail.com", "12345",
            "123");
        assertEquals(p.toString(), "Participant has name: 'Bob', email: 'bob@gmail.com', " +
            "IBAN: '12345', BIC: '123';");
    }
}
