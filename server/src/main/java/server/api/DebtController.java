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


    @GetMapping("/debt")
    @ResponseBody
    public List<Debt> getAll() {
        return db.findAll();
    }

    @GetMapping("/debt/{id}")
    @ResponseBody
    public ResponseEntity<Debt> getById(@PathVariable("id") Long id){
        if (id < 0 || !db.existsById(id)){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(db.findById(id).get());
    }

    @PostMapping("/debt")
    public ResponseEntity<Debt> add(@RequestBody Debt debt) {

        if (debt.getAmount() == 0 || debt.getFrom() == null || debt.getTo() == null
            || debt.getExpense() == null) {
            return ResponseEntity.badRequest().build();
        }
        Debt saved = db.save(debt);
        return ResponseEntity.ok(saved);
    }

    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

}
