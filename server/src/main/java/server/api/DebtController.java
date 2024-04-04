package server.api;

import commons.Debt;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.database.DebtRepository;

import java.util.List;

@Controller
@RequestMapping("/debt")
public class DebtController {

    private final DebtRepository db;

    public DebtController(DebtRepository db) {
        this.db = db;
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
        if (id < 0 || !db.existsById(id)){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(db.findById(id).get());
    }

    /**
     * add debt to db
     * @param debt debt to be added to db
     * @return response status of addition
     */
    @PostMapping(path = { "", "/" })
    public ResponseEntity<Debt> addDebt(@RequestBody Debt debt) {

        if (debt.getAmount() == 0 || debt.getHasToPay() == null || debt.getWhoPaid() == null
            || debt.getExpense() == null) {
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
        if (debt == null) {
            return ResponseEntity.badRequest().build();
        }
        if (!db.existsById(id)){
            return ResponseEntity.notFound().build();
        }

        Debt existingDebt = db.findById(id).get();
        existingDebt.setSettled(debt.isSettled());
        existingDebt.setExpense(debt.getExpense());
        existingDebt.setHasToPay(debt.getHasToPay());
        existingDebt.setWhoPaid(debt.getWhoPaid());
        existingDebt.setAmount(debt.getAmount());

        Debt updatedDebt = db.save(existingDebt);
        return ResponseEntity.ok(updatedDebt);
    }

}
