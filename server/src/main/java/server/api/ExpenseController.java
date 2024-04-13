package server.api;

import commons.Expense;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.database.DebtRepository;
import server.database.ExpenseRepository;
import java.util.List;

@Controller
@RequestMapping("/expense")
public class ExpenseController {

    private final ExpenseRepository db;

    private final DebtRepository debtsDB;


    public ExpenseController(ExpenseRepository db, DebtRepository debtsDB){
        this.db=db;
        this.debtsDB=debtsDB;
    }

    /**
     * find all expenses
     * @return List<Expense>
     */
    @GetMapping(path = { "", "/" })
    @ResponseBody
    public List<Expense> getAllExpenses() {
        return db.findAll();
    }

    /**
     * Get request of the expense
     * @param expense_id id of the request
     * @return ResponseEntity<Expense> - answer of the request
     */
    @GetMapping(path = {"/{id}" })
    @ResponseBody
    public ResponseEntity<Expense> getExpenseByID(@PathVariable("id") long expense_id){
        if(expense_id < 0)
            return ResponseEntity.badRequest().build();
        if(!db.existsById(expense_id))
            return ResponseEntity.notFound().build();
        System.out.println(expense_id);
        Expense expense =db.findById(expense_id).get();
        return ResponseEntity.ok(expense);
    }

    /**
     * Post request of the expense
     * @param expense - expense
     * @return ResponseEntity<Expense> - Response
     */
    @PostMapping(path = { "", "/" })
    public ResponseEntity<Expense> createExpense(@RequestBody Expense expense) {
        if (expense == null) {
            return ResponseEntity.badRequest().build();
        }
        Expense createdExpense = db.save(expense);
        return ResponseEntity.ok(createdExpense);
    }

    /**
     * Update request of the expense class
     * @param expense - the expense
     * @param expense_id - the id of the expense
     * @return ResponseEntity<Expense> - response of the method
     */
    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@RequestBody Expense expense,
                                                 @PathVariable("id") long expense_id) {
        if(expense == null) {
            return ResponseEntity.badRequest().build();
        }
        if(expense_id < 0) {
            return ResponseEntity.badRequest().build();
        }
        if (!db.existsById(expense_id)){
            return ResponseEntity.notFound().build();
        }
        Expense existingExpense = db.findById(expense_id).get();
        existingExpense.setDescription(expense.getDescription());
        existingExpense.setAmount(expense.getAmount());
        existingExpense.setType(expense.getType());
        existingExpense.setDateSent(expense.getDateSent());
//        existingExpense.setCurrency(expense.getCurrency());
        existingExpense.setEvent(expense.getEvent());
        Expense updatedExpenseEntity = db.save(existingExpense);
        return ResponseEntity.ok(updatedExpenseEntity);
    }

    /**
     * Delete request expense
     * @param expense_id - id of the expense
     * @return ResponseEntity<Expense> - response  of the method
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Expense> deleteExpense(@PathVariable("id") long expense_id) {
        if(expense_id < 0) {
            return ResponseEntity.badRequest().build();
        }
        if(!db.existsById(expense_id)) {
            return ResponseEntity.notFound().build();
        }
        db.deleteById(expense_id);
        return ResponseEntity.noContent().build();
    }

}
