package commons;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseTest {

    @Test
    public void getWhoPaidTest() {
        Participant p1 = new Participant("a","b","c","d");
        Participant p2 = new Participant("e","g","f","h");
        Participant p3 = new Participant("this","or","that","right");
        List<Participant> participants = Arrays.asList(p2, p3);

        Expense expense = new Expense(p1, "This is a description",
                2.09,"This is a type", participants,
                LocalDate.of(2005, 12, 1),
                Currency.getInstance("BAM"));
        assertEquals(p1,expense.getWhoPaid());
    }

    @Test
    public void getDescriptionTest() {
        Participant p1 = new Participant("a","b","c","d");
        Participant p2 = new Participant("e","g","f","h");
        Participant p3 = new Participant("this","or","that","right");
        List<Participant> participants = Arrays.asList(p2, p3);

        Expense expense = new Expense(p1, "This is a description",
                2.09,"This is a type", participants,
                LocalDate.of(2005, 12, 1),
                Currency.getInstance("BAM"));
        assertEquals("This is a description", expense.getDescription());
    }
    @Test
    public void getValueTest() {
        Participant p1 = new Participant("a","b","c","d");
        Participant p2 = new Participant("e","g","f","h");
        Participant p3 = new Participant("this","or","that","right");
        List<Participant> participants = Arrays.asList(p2, p3);

        Expense expense = new Expense(p1, "This is a description",
                2.09,"This is a type", participants,
                LocalDate.of(2005, 12, 1),
                Currency.getInstance("BAM"));
        assertEquals(2.09, expense.getValue());
    }
    @Test
    public void getTypeTest() {
        Participant p1 = new Participant("a","b","c","d");
        Participant p2 = new Participant("e","g","f","h");
        Participant p3 = new Participant("this","or","that","right");
        List<Participant> participants = Arrays.asList(p2, p3);

        Expense expense = new Expense(p1, "This is a description",
                2.09,"This is a type", participants,
                LocalDate.of(2005, 12, 1),
                Currency.getInstance("BAM"));
        assertEquals("This is a type", expense.getType());
    }
    @Test
    public void getParticipantsTest() {
        Participant p1 = new Participant("a","b","c","d");
        Participant p2 = new Participant("e","g","f","h");
        Participant p3 = new Participant("this","or","that","right");
        List<Participant> participants = Arrays.asList(p2, p3);

        Expense expense = new Expense(p1, "This is a description",
                2.09,"This is a type", participants,
                LocalDate.of(2005, 12, 1),
                Currency.getInstance("BAM"));
        assertEquals(participants, expense.getParticipants());
    }

    @Test
    public void getDateSent() {
        Participant p1 = new Participant("a","b","c","d");
        Participant p2 = new Participant("e","g","f","h");
        Participant p3 = new Participant("this","or","that","right");
        List<Participant> participants = Arrays.asList(p2, p3);

        Expense expense = new Expense(p1, "This is a description",
                2.09,"This is a type", participants,
                LocalDate.of(2005, 12, 1),
                Currency.getInstance("BAM"));
        assertEquals(LocalDate.of(2005, 12, 1), expense.getDateSent());
    }

    @Test
    public void getCurrencyTest() {
        Participant p1 = new Participant("a","b","c","d");
        Participant p2 = new Participant("e","g","f","h");
        Participant p3 = new Participant("this","or","that","right");
        List<Participant> participants = Arrays.asList(p2, p3);

        Expense expense = new Expense(p1, "This is a description",
                2.09,"This is a type", participants,
                LocalDate.of(2005, 12, 1),
                Currency.getInstance("BAM"));
        assertEquals(Currency.getInstance("BAM"), expense.getCurrency());
    }

    @Test
    public void setWhoPaidTest() {
        Participant p1 = new Participant("a","b","c","d");
        Participant p2 = new Participant("e","g","f","h");
        Participant p3 = new Participant("this","or","that","right");
        List<Participant> participants = Arrays.asList(p2, p3);

        Expense expense = new Expense(p1, "This is a description",
                2.09,"This is a type", participants,
                LocalDate.of(2005, 12, 1),
                Currency.getInstance("BAM"));
        expense.setWhoPaid(p2);
        assertEquals(p2,expense.getWhoPaid());
    }

    @Test
    public void setDescriptionTest() {
        Participant p1 = new Participant("a","b","c","d");
        Participant p2 = new Participant("e","g","f","h");
        Participant p3 = new Participant("this","or","that","right");
        List<Participant> participants = Arrays.asList(p2, p3);

        Expense expense = new Expense(p1, "This is a description",
                2.09,"This is a type", participants,
                LocalDate.of(2005, 12, 1),
                Currency.getInstance("BAM"));
        expense.setDescription("abcdefg");
        assertEquals("abcdefg", expense.getDescription());
    }

    @Test
    public void setValueTest() {
        Participant p1 = new Participant("a","b","c","d");
        Participant p2 = new Participant("e","g","f","h");
        Participant p3 = new Participant("this","or","that","right");
        List<Participant> participants = Arrays.asList(p2, p3);

        Expense expense = new Expense(p1, "This is a description",
                2.09,"This is a type", participants,
                LocalDate.of(2005, 12, 1),
                Currency.getInstance("BAM"));
        expense.setValue(3.04);
        assertEquals(3.04, expense.getValue());
    }

    @Test
    public void setTypeTest() {
        Participant p1 = new Participant("a","b","c","d");
        Participant p2 = new Participant("e","g","f","h");
        Participant p3 = new Participant("this","or","that","right");
        List<Participant> participants = Arrays.asList(p2, p3);

        Expense expense = new Expense(p1, "This is a description",
                2.09,"This is a type", participants,
                LocalDate.of(2005, 12, 1),
                Currency.getInstance("BAM"));
        expense.setType("key");
        assertEquals("key", expense.getType());
    }

    @Test
    public void setParticipantsTest() {
        Participant p1 = new Participant("a","b","c","d");
        Participant p2 = new Participant("e","g","f","h");
        Participant p3 = new Participant("this","or","that","right");
        List<Participant> participants = Arrays.asList(p2, p3);
        List<Participant> participants2 = Arrays.asList(p1, p3);

        Expense expense = new Expense(p1, "This is a description",
                2.09,"This is a type", participants,
                LocalDate.of(2005, 12, 1),
                Currency.getInstance("BAM"));
        expense.setParticipants(participants2);
        assertEquals(participants2, expense.getParticipants());
    }

    @Test
    public void setDateSentTest() {
        Participant p1 = new Participant("a","b","c","d");
        Participant p2 = new Participant("e","g","f","h");
        Participant p3 = new Participant("this","or","that","right");
        List<Participant> participants = Arrays.asList(p2, p3);

        Expense expense = new Expense(p1, "This is a description",
                2.09,"This is a type", participants,
                LocalDate.of(2005, 12, 1),
                Currency.getInstance("BAM"));
        expense.setDateSent(LocalDate.of(2020,11,24));
        assertEquals(LocalDate.of(2020, 11, 24), expense.getDateSent());
    }

    @Test
    public void setCurrencyTest() {
        Participant p1 = new Participant("a","b","c","d");
        Participant p2 = new Participant("e","g","f","h");
        Participant p3 = new Participant("this","or","that","right");
        List<Participant> participants = Arrays.asList(p2, p3);

        Expense expense = new Expense(p1, "This is a description",
                2.09,"This is a type", participants,
                LocalDate.of(2005, 12, 1),
                Currency.getInstance("BAM"));
        expense.setCurrency(Currency.getInstance("USD"));
        assertEquals(Currency.getInstance("USD"), expense.getCurrency());
    }

    @Test
    public void equalsTestEqual() {
        Participant p1 = new Participant("a","b","c","d");
        Participant p2 = new Participant("e","g","f","h");
        Participant p3 = new Participant("this","or","that","right");
        List<Participant> participants = Arrays.asList(p2, p3);

        Expense expense = new Expense(p1, "This is a description",
                2.09,"This is a type", participants,
                LocalDate.of(2005, 12, 1),
                Currency.getInstance("BAM"));
        Expense expense2 = new Expense(p1, "This is a description",
                2.09,"This is a type", participants,
                LocalDate.of(2005, 12, 1),
                Currency.getInstance("BAM"));
        assertEquals(expense, expense2);
    }
    public void equalsTestNotNull() {
        Participant p1 = new Participant("a","b","c","d");
        Participant p2 = new Participant("e","g","f","h");
        Participant p3 = new Participant("this","or","that","right");
        List<Participant> participants = Arrays.asList(p2, p3);

        Expense expense = new Expense(p1, "This is a description",
                2.09,"This is a type", participants,
                LocalDate.of(2005, 12, 1),
                Currency.getInstance("BAM"));
        assertNotNull(expense);
    }
    public void equalsTestNotEqual() {
        Participant p1 = new Participant("a","b","c","d");
        Participant p2 = new Participant("e","g","f","h");
        Participant p3 = new Participant("this","or","that","right");
        List<Participant> participants = Arrays.asList(p2, p3);

        Expense expense = new Expense(p1, "This is a description",
                2.09,"This is a type", participants,
                LocalDate.of(2005, 12, 1),
                Currency.getInstance("BAM"));
        Expense expense2 = new Expense(p1, "This is a description",
                2.09,"This is a type", participants,
                LocalDate.of(2005, 11, 1),
                Currency.getInstance("BAM"));
        assertNotEquals(expense, expense2);
    }

    public void hashCodeTestEqual() {
        Participant p1 = new Participant("a","b","c","d");
        Participant p2 = new Participant("e","g","f","h");
        Participant p3 = new Participant("this","or","that","right");
        List<Participant> participants = Arrays.asList(p2, p3);

        Expense expense = new Expense(p1, "This is a description",
                2.09,"This is a type", participants,
                LocalDate.of(2005, 12, 1),
                Currency.getInstance("BAM"));
        Expense expense2 = new Expense(p1, "This is a description",
                2.09,"This is a type", participants,
                LocalDate.of(2005, 12, 1),
                Currency.getInstance("BAM"));
        assertEquals(expense.hashCode(), expense2.hashCode());
    }
    public void hashCodeTestNotEqual() {
        Participant p1 = new Participant("a","b","c","d");
        Participant p2 = new Participant("e","g","f","h");
        Participant p3 = new Participant("this","or","that","right");
        List<Participant> participants = Arrays.asList(p2, p3);

        Expense expense = new Expense(p1, "This is a description",
                2.09,"This is a type", participants,
                LocalDate.of(2005, 12, 2),
                Currency.getInstance("BAM"));
        Expense expense2 = new Expense(p1, "This is a description",
                2.09,"This is a type", participants,
                LocalDate.of(2005, 11, 2),
                Currency.getInstance("BAM"));
        assertNotEquals(expense.hashCode(), expense2.hashCode());
    }
    @Test
    public void addParticipantTest() {
        Participant p1 = new Participant("a","b","c","d");
        Participant p2 = new Participant("e","g","f","h");
        Participant p3 = new Participant("this","or","that","right");
        List<Participant> participants = new ArrayList<>(List.of(p2));
        List<Participant> participants2 = Arrays.asList(p2, p3);

        Expense expense = new Expense(p1, "This is a description",
                2.09,"This is a type", participants,
                LocalDate.of(2005, 12, 1),
                Currency.getInstance("BAM"));

        expense.addParticipant(p3);
        assertEquals(expense.getParticipants(), participants2);
    }

    @Test
    public void removeParticipantTest() {
        Participant p1 = new Participant("a","b","c","d");
        Participant p2 = new Participant("e","g","f","h");
        Participant p3 = new Participant("this","or","that","right");
        List<Participant> participants = new ArrayList<>(List.of(p2));
        List<Participant> participants2 = new ArrayList<>(Arrays.asList(p2, p3));

        Expense expense = new Expense(p1, "This is a description",
                2.09,"This is a type", participants2,
                LocalDate.of(2005, 12, 1),
                Currency.getInstance("BAM"));

        expense.removeParticipant(p3);
        assertEquals(expense.getParticipants(), participants2);
    }
}
