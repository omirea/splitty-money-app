package server.api;

import commons.Debt;
import commons.Expense;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.DebtRepository;
import server.database.ExpenseRepository;
import java.util.List;

@RestController
@RequestMapping("/debt")
public class DebtController {

    private final DebtRepository db;

    private final ExpenseRepository expenseDB;

    public DebtController(DebtRepository db, ExpenseRepository expenseDB) {
        this.db = db;
        this.expenseDB=expenseDB;
    }

    /**
     * get all debts from the db
     * @return all debts in the db
     */
    @GetMapping(path = { "", "/" })
    @ResponseBody
    public List<Debt> getAllDebts() {
        return db.findAll();
    }

    /**
     * get a specific debt from db
     * @param id id of requested debt
     * @return requested debt or error
     */
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Debt> getDebtById(@PathVariable("id") Long id){
        if (id < 0){
            return ResponseEntity.badRequest().build();
        }
        if(!db.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(db.findById(id).get());
    }


    /**
     * add debt to db
     * @param debt debt to be added to db
     * @return response status of addition
     */
    @PostMapping(path = { "", "/" })
    public ResponseEntity<Debt> createDebt(@RequestBody Debt debt) {

        if (debt.getAmount() == 0 || debt.getFrom() == null || debt.getTo() == null){
            //|| debt.getExpense() == null) {
            return ResponseEntity.badRequest().build();
        }
        Debt saved = db.save(debt);
        return ResponseEntity.ok(saved);
    }

    /**
     * delete a debt from db
     * @param id of the debt
     * @return ResponseEntity<Debt> status of deletion
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Debt> deleteDebt(@PathVariable("id") long id) {
        if(id < 0) {
            return ResponseEntity.badRequest().build();
        }
        if(!db.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        db.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * update a debt in the db
     * @param debt updated version of debt
     * @param id id of the debt to be updated
     * @return updated debt, or error
     */
    @PutMapping("/{id}")
    public ResponseEntity<Debt> updateDebt
            (@RequestBody Debt debt, @PathVariable("id") long id) {
        if (debt == null || id < 0) {
            return ResponseEntity.badRequest().build();
        }
        if (!db.existsById(id)){
            return ResponseEntity.notFound().build();
        }

        Debt existingDebt = db.findById(id).get();
        existingDebt.setSettled(debt.isSettled());
        existingDebt.setExpense(debt.getExpense());
        existingDebt.setFrom(debt.getFrom());
        existingDebt.setTo(debt.getTo());
        existingDebt.setAmount(debt.getAmount());

        Debt updatedDebt = db.save(existingDebt);
        return ResponseEntity.ok(updatedDebt);
    }

    @GetMapping("/expense/{expenseID}")
    @ResponseBody
    public ResponseEntity<List<Debt>> getDebtsByExpenseId(
            @PathVariable("expenseID") Long id) {

        Expense expense=new Expense();
        expense.setId(id);

        Debt debt = new Debt();
        debt.setExpense(expense);
        List<Debt> debts = db.findAll(
                Example.of(debt, ExampleMatcher.matchingAll()));
        System.out.println(debt);

        return ResponseEntity.ok(debts);
    }

}
