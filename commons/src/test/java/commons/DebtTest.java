package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

class DebtTest {

    private Debt debt;
    private Debt debtAll;

    @BeforeEach
    void setupDebt() {
        Participant from = new Participant("Person1", "abc@xyz.com",
                "Bob", "NL123", "123");
        Participant to = new Participant("Person2", "def@xyz.com",
                "Bob", "NL456", "456");
        debt = new Debt(from, to, 10.04);

        Expense ex = new Expense("This is a description",
            2.09,"This is a type",
            LocalDate.of(2005, 12, 1),
            Currency.getInstance("BAM"));
        Event e = new Event("Party", "DFR45");
        debtAll = new Debt(e, ex, from , to, 10.04);
        debtAll.setId((long) 1);

        Debt d = new Debt();
    }

    @Test
    void getIDTest(){
        Long id = (long) 1;
        assertEquals(id, debtAll.getId());
    }

    @Test
    void getEventTest(){
        Event e = new Event("Party", "DFR45");
        assertEquals(e, debtAll.getEvent());
    }

    @Test
    void setEventTest(){
        Event e = new Event("Food", "DFR45");
        debtAll.setEvent(e);
        assertEquals(e, debtAll.getEvent());
    }

    @Test
    void getExpenseTest(){
        Expense ex = new Expense("This is a description",
            2.09,"This is a type",
            LocalDate.of(2005, 12, 1),
            Currency.getInstance("BAM"));
        assertEquals(ex, debtAll.getExpense());
    }

    @Test
    void setExpenseTest(){
        Expense ex = new Expense("This is a description",
            2.09,"food",
            LocalDate.of(2005, 8, 1),
            Currency.getInstance("BAM"));
        debtAll.setExpense(ex);
        assertEquals(ex, debtAll.getExpense());
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
        Participant from2 = new Participant("Person1", "abc@xyz.com","Bob",
                "NL123",
            "123");
        assertEquals(from2, debt.getFrom());
    }

    @Test
    void setFrom() {
        Participant from3 = new Participant("Person3", "abc@xyz.com", "Bob",
                "NL123",
            "123");
        debt.setFrom(from3);
        assertEquals(from3, debt.getFrom());
    }

    @Test
    void getTo() {
        Participant to2 = new Participant("Person2", "def@xyz.com", "Bob",
                "NL456",
            "456");
        assertEquals(to2, debt.getTo());
    }

    @Test
    void setTo() {
        Participant to3 = new Participant("Person3", "abc@xyz.com", "Bob",
                "NL123",
            "123");
        debt.setTo(to3);
        assertEquals(to3, debt.getTo());
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
        Participant from = new Participant("Person1", "abc@xyz.com",
                "Bob", "NL123", "123");
        Participant to = new Participant("Person2", "def@xyz.com",
                "Bob", "NL456", "456");
        Debt debt1 = new Debt(from, to, 10.04);
        assertEquals(debt, debt1);
    }

    @Test
    void testNotEquals() {
        Participant from = new Participant("Person1", "abc@xyz.com",
                "Bob", "NL123", "123");
        Participant to = new Participant("Person3", "def@xyz.com",
                "Bob", "NL456", "456");
        Debt debt1 = new Debt(from, to, 10.04);
        assertNotEquals(debt, debt1);
    }

    @Test
    void testHashCode() {
        Participant from = new Participant("Person1", "abc@xyz.com",
                "Bob", "NL123", "123");
        Participant to = new Participant("Person2", "def@xyz.com",
                "Bob","NL456", "456");
        Debt debt1 = new Debt(from, to, 10.04);
        assertEquals(debt.hashCode(), debt1.hashCode());
    }

    @Test
    void toStringTest(){
        String s = "Debt: settled = false, from = Person1, to = Person2, amount = 10.04";
        assertEquals(s, debt.toString());
    }
}