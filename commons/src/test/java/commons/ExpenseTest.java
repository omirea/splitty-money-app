package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseTest {

    private Expense expense;
    private Expense e;

    @BeforeEach
    void setupExpense() {
        expense = new Expense("This is a description",
            2.09, "This is a type",
            LocalDate.of(2005, 12, 1),
            Currency.getInstance("BAM"));

        e = new Expense(new Event(),"This is a description",
            2.09, "This is a type",
            LocalDate.of(2005, 12, 1),
            Currency.getInstance("BAM"));
        e.setID((long) 1);
    }

    @Test
    void getIdTest(){
        Long id = (long) 1;
        assertEquals(id, e.getId());
    }

    @Test
    void getEventTest(){
        Event ev = new Event();
        assertEquals(ev, e.getEvent());
    }

    @Test
    void setEventTest(){
        Event ev = new Event("hello");
        e.setEvent(ev);
        assertEquals(ev, e.getEvent());
    }

    @Test
    public void getDescriptionTest() {
        assertEquals("This is a description", expense.getDescription());
    }
    @Test
    public void getValueTest() {
        assertEquals(2.09, expense.getAmount());
    }
    @Test
    public void getTypeTest() {
        assertEquals("This is a type", expense.getType());
    }


    @Test
    public void getDateSent() {
        assertEquals(LocalDate.of(2005, 12, 1), expense.getDateSent());
    }

    @Test
    public void setDescriptionTest() {
        expense.setDescription("abcdefg");
        assertEquals("abcdefg", expense.getDescription());
    }

    @Test
    public void setValueTest() {
        expense.setAmount(3.04);
        assertEquals(3.04, expense.getAmount());
    }

    @Test
    public void setTypeTest() {
        expense.setType("key");
        assertEquals("key", expense.getType());
    }



    @Test
    public void setDateSentTest() {
        expense.setDateSent(LocalDate.of(2020,11,24));
        assertEquals(LocalDate.of(2020, 11, 24), expense.getDateSent());
    }

    @Test
    public void equalsTestEqual() {
        Expense expense2 = new Expense( "This is a description",
                2.09,"This is a type",
                LocalDate.of(2005, 12, 1),
                Currency.getInstance("BAM"));
        assertEquals(expense, expense2);
    }
    @Test
    public void equalsTestNotNull() {
        assertNotNull(expense);
    }
    @Test
    public void equalsTestNotEqual() {
        Expense expense2 = new Expense( "This is a description",
                2.09,"This is a type",
                LocalDate.of(2005, 11, 1),
                Currency.getInstance("BAM"));
        assertNotEquals(expense, expense2);
    }

    @Test
    public void hashCodeTestEqual() {
        Expense expense2 = new Expense( "This is a description",
                2.09,"This is a type",
                LocalDate.of(2005, 12, 1),
                Currency.getInstance("BAM"));
        assertEquals(expense.hashCode(), expense2.hashCode());
    }
    @Test
    public void hashCodeTestNotEqual() {
        Expense expense2 = new Expense( "This is a description",
                2.09,"This is a type",
                LocalDate.of(2005, 11, 2),
                Currency.getInstance("BAM"));
        assertNotEquals(expense.hashCode(), expense2.hashCode());
    }

    @Test
    void toStringTest(){
        String s = "Expense{ex_id=null, event_id=null, description='This is a description', " +
            "value=2.09, type='This is a type', dateSent=2005-12-01}";
        assertEquals(s, expense.toString());
    }

}
