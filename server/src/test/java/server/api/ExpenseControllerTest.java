package server.api;

import commons.Expense;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import server.database.ExpenseRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExpenseControllerTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseController expenseController;

    @Test
    public void testGetExpenseByID() {
        long expenseId = 1;
        Expense expense = new Expense(); // create an Expense object with appropriate data

        when(expenseRepository.existsById(expenseId)).thenReturn(true);
        when(expenseRepository.findById(expenseId)).thenReturn(java.util.Optional.of(expense));

        ResponseEntity<Expense> responseEntity = expenseController.getExpenseByID(expenseId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expense, responseEntity.getBody());
    }

    @Test
    public void testCreateExpense() {
        Expense expense = new Expense(); // create an Expense object with appropriate data

        when(expenseRepository.save(expense)).thenReturn(expense);

        ResponseEntity<Expense> responseEntity = expenseController.createExpense(expense);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expense, responseEntity.getBody());
    }

    @Test
    public void testUpdateExpense() {
        long expenseId = 1;
        Expense existingExpense = new Expense();
        // create an existing Expense object// with appropriate data
        Expense updatedExpense = new Expense();
        // create an updated Expense object with appropriate data

        when(expenseRepository.existsById(expenseId)).thenReturn(true);
        when(expenseRepository.findById(expenseId))
            .thenReturn(java.util.Optional.of(existingExpense));
        when(expenseRepository.save(existingExpense)).thenReturn(updatedExpense);

        ResponseEntity<Expense> responseEntity = expenseController
            .updateExpense(updatedExpense, expenseId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedExpense, responseEntity.getBody());
    }

    @Test
    public void testDeleteExpense() {
        long expenseId = 1;

        ResponseEntity<Expense> responseEntity = expenseController.deleteExpense(expenseId);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(expenseRepository, times(1)).deleteById(expenseId);
    }
}