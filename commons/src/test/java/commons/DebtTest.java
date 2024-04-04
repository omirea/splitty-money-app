package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DebtTest {

    private Debt debt;

    @BeforeEach
    void setupDebt() {
        Participant from = new Participant("Person1", "abc@xyz.com", "NL123",
            "123");
        Participant to = new Participant("Person2", "def@xyz.com", "NL456",
            "456");
        debt = new Debt(from, to, 10.04);
    }

    @Test
    void isSettled() {
        assertFalse(debt.isSettled());
    }

    @Test
    void setSettled() {
        debt.setSettled(true);
        assertTrue(debt.isSettled());
        debt.setSettled(false);
        assertFalse(debt.isSettled());
    }

    @Test
    void getFrom() {
        Participant from2 = new Participant("Person1", "abc@xyz.com", "NL123",
            "123");
        assertEquals(from2, debt.getHasToPay());
    }

    @Test
    void setFrom() {
        Participant from3 = new Participant("Person3", "abc@xyz.com", "NL123",
            "123");
        debt.setHasToPay(from3);
        assertEquals(from3, debt.getHasToPay());
    }

    @Test
    void getTo() {
        Participant to2 = new Participant("Person2", "def@xyz.com", "NL456",
            "456");
        assertEquals(to2, debt.getWhoPaid());
    }

    @Test
    void setTo() {
        Participant to3 = new Participant("Person3", "abc@xyz.com", "NL123",
            "123");
        debt.setWhoPaid(to3);
        assertEquals(to3, debt.getWhoPaid());
    }

    @Test
    void getValue() {
        assertEquals(10.04, debt.getAmount());
    }

    @Test
    void setValue() {
        debt.setAmount(20.04);
        assertEquals(20.04, debt.getAmount());
    }

    @Test
    void testEqualsNull() {
        assertNotEquals(debt, null);
    }

    @Test
    void testEqualsSame() {
        assertEquals(debt, debt);
    }

    @Test
    void testEquals() {
        Participant from = new Participant("Person1", "abc@xyz.com", "NL123",
            "123");
        Participant to = new Participant("Person2", "def@xyz.com", "NL456",
            "456");
        Debt debt1 = new Debt(from, to, 10.04);
        assertEquals(debt, debt1);
    }

    @Test
    void testNotEquals() {
        Participant from = new Participant("Person1", "abc@xyz.com", "NL123",
            "123");
        Participant to = new Participant("Person3", "def@xyz.com", "NL456",
            "456");
        Debt debt1 = new Debt(from, to, 10.04);
        assertNotEquals(debt, debt1);
    }

    @Test
    void testHashCode() {
        Participant from = new Participant("Person1", "abc@xyz.com", "NL123",
            "123");
        Participant to = new Participant("Person2", "def@xyz.com", "NL456",
            "456");
        Debt debt1 = new Debt(from, to, 10.04);
        assertEquals(debt.hashCode(), debt1.hashCode());
    }
}