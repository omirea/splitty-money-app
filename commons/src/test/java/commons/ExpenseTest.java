package commons;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseTest {


    @Test
    public void getDescriptionTest() {
        Expense expense = new Expense("This is a description",
                2.09,"This is a type",
                LocalDate.of(2005, 12, 1),
                Currency.getInstance("BAM"));
        assertEquals("This is a description", expense.getName());
    }
    @Test
    public void getValueTest() {
        Expense expense = new Expense( "This is a description",
                2.09,"This is a type",
                LocalDate.of(2005, 12, 1),
                Currency.getInstance("BAM"));
        assertEquals(2.09, expense.getAmount());
    }
    @Test
    public void getTypeTest() {
        Expense expense = new Expense( "This is a description",
                2.09,"This is a type",
                LocalDate.of(2005, 12, 1),
                Currency.getInstance("BAM"));
        assertEquals("This is a type", expense.getType());
    }


    @Test
    public void getDateSent() {
        Expense expense = new Expense( "This is a description",
                2.09,"This is a type",
                LocalDate.of(2005, 12, 1),
                Currency.getInstance("BAM"));
        assertEquals(LocalDate.of(2005, 12, 1), expense.getDateSent());
    }

    @Test
    public void getCurrencyTest() {
        Expense expense = new Expense( "This is a description",
                2.09,"This is a type",
                LocalDate.of(2005, 12, 1),
                Currency.getInstance("BAM"));
        assertEquals(Currency.getInstance("BAM"), expense.getCurrency());
    }


    @Test
    public void setDescriptionTest() {
        Expense expense = new Expense( "This is a description",
                2.09,"This is a type",
                LocalDate.of(2005, 12, 1),
                Currency.getInstance("BAM"));
        expense.setName("abcdefg");
        assertEquals("abcdefg", expense.getName());
    }

    @Test
    public void setValueTest() {
        Expense expense = new Expense( "This is a description",
                2.09,"This is a type",
                LocalDate.of(2005, 12, 1),
                Currency.getInstance("BAM"));
        expense.setAmount(3.04);
        assertEquals(3.04, expense.getAmount());
    }

    @Test
    public void setTypeTest() {
        Expense expense = new Expense( "This is a description",
                2.09,"This is a type",
                LocalDate.of(2005, 12, 1),
                Currency.getInstance("BAM"));
        expense.setType("key");
        assertEquals("key", expense.getType());
    }



    @Test
    public void setDateSentTest() {
        Expense expense = new Expense( "This is a description",
                2.09,"This is a type",
                LocalDate.of(2005, 12, 1),
                Currency.getInstance("BAM"));
        expense.setDateSent(LocalDate.of(2020,11,24));
        assertEquals(LocalDate.of(2020, 11, 24), expense.getDateSent());
    }

    @Test
    public void setCurrencyTest() {
        Expense expense = new Expense( "This is a description",
                2.09,"This is a type",
                LocalDate.of(2005, 12, 1),
                Currency.getInstance("BAM"));
        expense.setCurrency(Currency.getInstance("USD"));
        assertEquals(Currency.getInstance("USD"), expense.getCurrency());
    }

    @Test
    public void equalsTestEqual() {
        Expense expense = new Expense( "This is a description",
                2.09,"This is a type",
                LocalDate.of(2005, 12, 1),
                Currency.getInstance("BAM"));
        Expense expense2 = new Expense( "This is a description",
                2.09,"This is a type",
                LocalDate.of(2005, 12, 1),
                Currency.getInstance("BAM"));
        assertEquals(expense, expense2);
    }
    @Test
    public void equalsTestNotNull() {
        Expense expense = new Expense( "This is a description",
                2.09,"This is a type",
                LocalDate.of(2005, 12, 1),
                Currency.getInstance("BAM"));
        assertNotNull(expense);
    }
    @Test
    public void equalsTestNotEqual() {
        Expense expense = new Expense( "This is a description",
                2.09,"This is a type",
                LocalDate.of(2005, 12, 1),
                Currency.getInstance("BAM"));
        Expense expense2 = new Expense( "This is a description",
                2.09,"This is a type",
                LocalDate.of(2005, 11, 1),
                Currency.getInstance("BAM"));
        assertNotEquals(expense, expense2);
    }

    @Test
    public void hashCodeTestEqual() {
        Expense expense = new Expense( "This is a description",
                2.09,"This is a type",
                LocalDate.of(2005, 12, 1),
                Currency.getInstance("BAM"));
        Expense expense2 = new Expense( "This is a description",
                2.09,"This is a type",
                LocalDate.of(2005, 12, 1),
                Currency.getInstance("BAM"));
        assertEquals(expense.hashCode(), expense2.hashCode());
    }
    @Test
    public void hashCodeTestNotEqual() {
        Expense expense = new Expense( "This is a description",
                2.09,"This is a type",
                LocalDate.of(2005, 12, 2),
                Currency.getInstance("BAM"));
        Expense expense2 = new Expense( "This is a description",
                2.09,"This is a type",
                LocalDate.of(2005, 11, 2),
                Currency.getInstance("BAM"));
        assertNotEquals(expense.hashCode(), expense2.hashCode());
    }

}
