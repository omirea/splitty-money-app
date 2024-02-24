package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParticipantTest {

    @Test
    public void getNameTest(){
        Participant p=new Participant("Bob", "bob@gmail.com", "12345", "123");
        assertEquals(p.getName(), "Bob");
    }

    @Test
    public void setNameTest(){
        Participant p=new Participant("Bob", "bob@gmail.com",
                "12345", "123");
        assertEquals(p.getName(), "Bob");
        p.setName("Stephany");
        assertEquals(p.getName(), "Stephany");
    }

    @Test
    public void getEmailTest(){
        Participant p=new Participant("Bob", "bob@gmail.com",
                "12345", "123");
        assertEquals(p.getEmail(), "bob@gmail.com");
    }

    @Test
    public void setEmailTest(){
        Participant p=new Participant("Bob", "bob@gmail.com",
                "12345", "123");
        assertEquals(p.getEmail(), "bob@gmail.com");
        p.setEmail("steph@gmail.com");
        assertEquals(p.getEmail(), "steph@gmail.com");
    }

    @Test
    public void getIBANTest(){
        Participant p=new Participant("Bob", "bob@gmail.com",
                "12345", "123");
        assertEquals(p.getIBAN(), "12345");
    }

    @Test
    public void setIBANTest(){
        Participant p=new Participant("Bob", "bob@gmail.com",
                "12345", "123");
        assertEquals(p.getIBAN(), "12345");
        p.setIBAN("00000");
        assertEquals(p.getIBAN(), "00000");
    }

    @Test
    public void getBICTest(){
        Participant p=new Participant("Bob", "bob@gmail.com",
                "12345", "123");
        assertEquals(p.getBIC(), "123");
    }

    @Test
    public void setBICTest(){
        Participant p=new Participant("Bob", "bob@gmail.com",
                "12345", "123");
        assertEquals(p.getBIC(), "123");
        p.setBIC("000");
        assertEquals(p.getBIC(), "000");
    }
}
