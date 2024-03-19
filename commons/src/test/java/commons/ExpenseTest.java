package commons;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseTest {

//    @Test
//    public void getWhoPaidTest() {
//        Participant p1 = new Participant("a","b","c","d");
//        Participant p2 = new Participant("e","g","f","h");
//        Participant p3 = new Participant("this","or","that","right");
//        List<Participant> participants = Arrays.asList(p2, p3);
//
//        Expense expense = new Expense(p1, "This is a description",
//                2.09,"This is a type", participants,
//                LocalDate.of(2005, 12, 1),
//                Currency.getInstance("BAM"));
//        assertEquals(p1,expense.getWhoPaid());
//    }

    @Test
    public void getDescriptionTest() {
        Expense expense = new Expense("This is a description",
                2.09,"This is a type",
                LocalDate.of(2005, 12, 1),
                Currency.getInstance("BAM"));
        assertEquals("This is a description", expense.getDescription());
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
//    @Test
//    public void getParticipantsTest() {
//        Participant p1 = new Participant("a","b","c","d");
//        Participant p2 = new Participant("e","g","f","h");
//        Participant p3 = new Participant("this","or","that","right");
//        List<Participant> participants = Arrays.asList(p2, p3);
//
//        Expense expense = new Expense(p1, "This is a description",
//                2.09,"This is a type", participants,
//                LocalDate.of(2005, 12, 1),
//                Currency.getInstance("BAM"));
//        assertEquals(participants, expense.getParticipants());
//    }

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

//    @Test
//    public void setWhoPaidTest() {
//        Participant p1 = new Participant("a","b","c","d");
//        Participant p2 = new Participant("e","g","f","h");
//        Participant p3 = new Participant("this","or","that","right");
//        List<Participant> participants = Arrays.asList(p2, p3);
//
//        Expense expense = new Expense(p1, "This is a description",
//                2.09,"This is a type", participants,
//                LocalDate.of(2005, 12, 1),
//                Currency.getInstance("BAM"));
//        expense.setWhoPaid(p2);
//        assertEquals(p2,expense.getWhoPaid());
//    }

    @Test
    public void setDescriptionTest() {
        Expense expense = new Expense( "This is a description",
                2.09,"This is a type",
                LocalDate.of(2005, 12, 1),
                Currency.getInstance("BAM"));
        expense.setDescription("abcdefg");
        assertEquals("abcdefg", expense.getDescription());
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

//    @Test
//    public void setParticipantsTest() {
//        Participant p1 = new Participant("a","b","c","d");
//        Participant p2 = new Participant("e","g","f","h");
//        Participant p3 = new Participant("this","or","that","right");
//        List<Participant> participants = Arrays.asList(p2, p3);
//        List<Participant> participants2 = Arrays.asList(p1, p3);
//
//        Expense expense = new Expense(p1, "This is a description",
//                2.09,"This is a type", participants,
//                LocalDate.of(2005, 12, 1),
//                Currency.getInstance("BAM"));
//        expense.setParticipants(participants2);
//        assertEquals(participants2, expense.getParticipants());
//    }

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
        Participant p1 = new Participant("a","b","c","d");
        Participant p2 = new Participant("e","g","f","h");
        Participant p3 = new Participant("this","or","that","right");
        List<Participant> participants = Arrays.asList(p2, p3);

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
//    @Test
//    public void addParticipantTest() {
//        Participant p1 = new Participant("a","b","c","d");
//        Participant p2 = new Participant("e","g","f","h");
//        Participant p3 = new Participant("this","or","that","right");
//        List<Participant> participants = new ArrayList<>(List.of(p2));
//        List<Participant> participants2 = Arrays.asList(p2, p3);
//
//        Expense expense = new Expense(p1, "This is a description",
//                2.09,"This is a type", participants,
//                LocalDate.of(2005, 12, 1),
//                Currency.getInstance("BAM"));
//
//        expense.addParticipant(p3);
//        assertEquals(expense.getParticipants(), participants2);
//    }

//    @Test
//    public void removeParticipantTest() {
//        Expense expense = new Expense( "This is a description",
//                2.09,"This is a type",
//                LocalDate.of(2005, 12, 1),
//                Currency.getInstance("BAM"));
//
//        expense.removeParticipant(p3);
//        assertEquals(expense.getParticipants(), participants);
//    }
}
