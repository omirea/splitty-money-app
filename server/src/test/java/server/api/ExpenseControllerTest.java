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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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
        Expense expense = new Expense();

        when(expenseRepository.existsById(expenseId)).thenReturn(true);
        when(expenseRepository.findById(expenseId)).thenReturn(java.util.Optional.of(expense));

        ResponseEntity<Expense> responseEntity = expenseController.getExpenseByID(expenseId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expense, responseEntity.getBody());
    }

    @Test
    public void testGetExpenseByIDNegative() {
        long id=-1;
        ResponseEntity<Expense> responseEntity = expenseController.getExpenseByID(id);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    public void testGetExpenseByIDNotFound() {
        long id = 150;
        when(expenseRepository.existsById(id)).thenReturn(false);
        ResponseEntity<Expense> responseEntity = expenseController.getExpenseByID(id);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    public void testGetAllExpenses() {
        List<Expense> dummy = new ArrayList<>();
        when(expenseRepository.findAll()).thenReturn(dummy);
        List<Expense> expenseList = expenseController.getAllExpenses();
        assertEquals(expenseList, dummy);
    }

    @Test
    public void testCreateExpense() {
        Expense expense = new Expense();
        when(expenseRepository.save(expense)).thenReturn(expense);
        ResponseEntity<Expense> responseEntity = expenseController.createExpense(expense);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expense, responseEntity.getBody());
    }

    @Test
    public void testCreateExpenseNull() {
        ResponseEntity<Expense> responseEntity = expenseController.createExpense(null);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    public void testUpdateExpense() {
        long expenseId = 1;
        Expense existingExpense = new Expense();
        Expense updatedExpense = new Expense();

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
    public void testUpdateExpenseNull() {
        long id = 100;
        ResponseEntity<Expense> responseEntity = expenseController.updateExpense(null, id);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    public void testUpdateExpenseNotFound() {
        long id = 150;
        Expense expense = new Expense();
        when(expenseRepository.existsById(id)).thenReturn(false);
        ResponseEntity<Expense> responseEntity = expenseController.updateExpense(expense, id);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    public void testUpdateExpenseNegative() {
        long id = -1;
        Expense expense = new Expense();
        ResponseEntity<Expense> responseEntity = expenseController.updateExpense(expense, id);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    public void testDeleteExpense() {
        long expenseId = 1;

        when(expenseRepository.existsById(expenseId)).thenReturn(true);
        ResponseEntity<Expense> responseEntity = expenseController.deleteExpense(expenseId);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(expenseRepository, times(1)).deleteById(expenseId);
    }

    @Test
    public void testDeleteExpenseNotFound() {
        long id = 150;
        when(expenseRepository.existsById(id)).thenReturn(false);
        ResponseEntity<Expense> responseEntity = expenseController.deleteExpense(id);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    public void testDeleteExpenseNegative() {
        long id = -1;
        ResponseEntity<Expense> responseEntity = expenseController.deleteExpense(id);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }
}