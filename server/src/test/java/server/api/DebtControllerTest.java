package server.api;

import commons.Debt;
import commons.Event;
import commons.Participant;
import commons.Expense;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.mockito.junit.jupiter.MockitoExtension;
import server.database.DebtRepository;
import server.database.EventRepository;
import server.database.ExpenseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DebtControllerTest {

    @Mock
    private DebtRepository debtRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private DebtController debtController;


    @Test
    public void testGetDebtByID() {
        long debtId = 1;
        Debt debt = new Debt();

        when(debtRepository.existsById(debtId)).thenReturn(true);
        when(debtRepository.findById(debtId)).thenReturn(java.util.Optional.of(debt));

        ResponseEntity<Debt> responseEntity = debtController.getDebtById(debtId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(debt, responseEntity.getBody());
    }

    @Test
    public void testGetDebtByIDNegative() {
        ResponseEntity<Debt> responseEntity = debtController.getDebtById(-1L);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    public void testGetDebtByIDNotFound() {
        long id = 150L;
        when(debtRepository.existsById(id)).thenReturn(false);
        ResponseEntity<Debt> responseEntity = debtController.getDebtById(id);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }
    @Test
    public void testGetAllDebts() {
        List<Debt> dummy = new ArrayList<>();
        when(debtRepository.findAll()).thenReturn(dummy);
        List<Debt> debtList = debtController.getAllDebts();
        assertEquals(debtList, dummy);
    }

    @Test
    public void testCreateDebt() {
        Debt debt = new Debt(new Event(), new Expense(), new Participant(), new Participant(),20.00);
        when(debtRepository.save(debt)).thenReturn(debt);
        ResponseEntity<Debt> responseEntity = debtController.createDebt(debt);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(debt, responseEntity.getBody());
    }

    @Test
    public void testCreateDebtAmountZero() {
        Debt debt = new Debt(new Event(), new Expense(), new Participant(), new Participant(),0.00);
        ResponseEntity<Debt> responseEntity = debtController.createDebt(debt);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

//    @Test
//    public void testCreateDebtExpenseNull() {
//        Debt debt = new Debt(null ,new Participant(), new Participant(),20.00);
//        ResponseEntity<Debt> responseEntity = debtController.createDebt(debt);
//        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
//    }

    @Test
    public void testCreateDebtGetFromNull() {
        Debt debt = new Debt(new Event(), new Expense(), null, new Participant(), 20.00);
        ResponseEntity<Debt> responseEntity = debtController.createDebt(debt);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testCreateDebtGetToNull() {
        Debt debt = new Debt(new Event(), new Expense(), new Participant(), null, 20.00);
        ResponseEntity<Debt> responseEntity = debtController.createDebt(debt);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateDebt() {
        long debtId = 1;
        Debt existingDebt = new Debt();
        Debt updatedDebt = new Debt();

        when(debtRepository.existsById(debtId)).thenReturn(true);
        when(debtRepository.findById(debtId))
                .thenReturn(java.util.Optional.of(existingDebt));
        when(debtRepository.save(existingDebt)).thenReturn(updatedDebt);

        ResponseEntity<Debt> responseEntity = debtController
                .updateDebt(updatedDebt, debtId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedDebt, responseEntity.getBody());
    }

    @Test
    public void testUpdateDebtNull() {
        long id = 100;
        ResponseEntity<Debt> responseEntity = debtController.updateDebt(null, id);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    public void testUpdateDebtNotFound() {
        long id = 150;
        Debt debt = new Debt();
        when(debtRepository.existsById(id)).thenReturn(false);
        ResponseEntity<Debt> responseEntity = debtController.updateDebt(debt, id);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    public void testUpdateDebtNegative() {
        long id = -1;
        Debt debt = new Debt();
        ResponseEntity<Debt> responseEntity = debtController.updateDebt(debt, id);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    public void testDeleteDebt() {
        long debtId = 1;
        when(debtRepository.existsById(debtId)).thenReturn(true);
        ResponseEntity<Debt> responseEntity = debtController.deleteDebt(debtId);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(debtRepository, times(1)).deleteById(debtId);
    }

    @Test
    public void testDeleteDebtNotFound() {
        long id = 150;
        when(debtRepository.existsById(id)).thenReturn(false);
        ResponseEntity<Debt> responseEntity = debtController.deleteDebt(id);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }
    @Test
    public void testDeleteDebtNegative() {
        long id = -1;
        ResponseEntity<Debt> responseEntity = debtController.deleteDebt(id);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    public void testGetDebtByExistingInvitationId() {
        String invitationID = "inviteId";
        Event event = new Event();
        event.setInvitationID(invitationID);
        List<Debt> debts = new ArrayList<>();
        debts.add(new Debt());

        when(eventRepository.findOne(any())).thenReturn(java.util.Optional.of(event));
        when(debtRepository.findAll((Example<Debt>) any())).thenReturn(debts);

        ResponseEntity<List<Debt>> response = debtController.getDebtByInvitationId(invitationID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(debts, response.getBody());
    }

    @Test
    public void testNotGetDebtByExistingInvitationId() {
        String invitationID = "inviteID";

        when(eventRepository.findOne(any())).thenReturn(Optional.empty());

        ResponseEntity<List<Debt>> response = debtController.getDebtByInvitationId(invitationID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void notGetDebtByExistingExpenseTest() {
        long expenseId = 1;
        Expense expense = new Expense();
        expense.setID(expenseId);

        List<Debt> debts = new ArrayList<>();
        debts.add(new Debt());

        when(expenseRepository.findById(expenseId)).thenReturn(Optional.of(expense));
        when(debtRepository.findAll((Example<Debt>) any())).thenReturn(debts);

        ResponseEntity<List<Debt>> response = debtController.getDebtByExpense(expenseId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(debts, response.getBody());
    }

    @Test
    public void notGetDebtByExpenseTest() {
        Long expenseId = 1L;

        when(expenseRepository.findById(expenseId)).thenReturn(Optional.empty());

        ResponseEntity<List<Debt>> response = debtController.getDebtByExpense(expenseId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
