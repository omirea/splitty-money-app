package server;


import commons.Expense;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.database.ExpenseRepository;

@Controller
@RequestMapping("/")
public class ExpenseController {

    private ExpenseRepository db;

    public ExpenseController(ExpenseRepository db){
        this.db=db;
    }

    /**
     * Get request of the expense
     * @param expense_id id of the request
     * @return ResponseEntity<Expense> - answer of the request
     */
    @GetMapping("/expense")
    @ResponseBody
    public ResponseEntity<Expense> getExpenseByID(@RequestParam("id") long expense_id){
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
     * @return esponseEntity<Expense> - Response
     */
    @PostMapping()
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
    @PutMapping("/expense/{id}")
    public ResponseEntity<Expense> updateExpense(@RequestBody Expense expense, @PathVariable("id") long expense_id) {
        if(expense == null) {
            return ResponseEntity.badRequest().build();
        }
        if (!db.existsById(expense_id)){
            return ResponseEntity.notFound().build();
        }

        Expense existingExpense = db.findById(expense_id).get();
        existingExpense.setDescription(expense.getDescription());
        existingExpense.setValue(expense.getValue());
        existingExpense.setType(expense.getType());
        existingExpense.setDateSent(expense.getDateSent());
        existingExpense.setCurrency(expense.getCurrency());
        Expense updatedExpenseEntity = db.save(existingExpense);
        return ResponseEntity.ok(updatedExpenseEntity);
    }

    /**
     * Delete request expense
     * @param expense_id - id of the expense
     * @return ResponseEntity<Expense> - response  of the method
     */
    @DeleteMapping("/expense/{id}")
    public ResponseEntity<Expense> deleteExpense(@PathVariable("id") long expense_id) {
        db.deleteById(expense_id);
        return ResponseEntity.noContent().build();
    }
}
