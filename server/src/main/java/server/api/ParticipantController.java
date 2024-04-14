package server.api;

import commons.Debt;
import commons.Expense;
import commons.Participant;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import server.database.DebtRepository;
import server.database.ExpenseRepository;
import server.database.ParticipantRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/participant")
public class ParticipantController {

    private final ParticipantRepository db;
    private final DebtRepository debtDB;
    private final ExpenseRepository expenseDB;

    public ParticipantController(ParticipantRepository db, DebtRepository debtCtrl,
                                 ExpenseRepository expenseDB){
        this.db=db;
        this.debtDB = debtCtrl;
        this.expenseDB = expenseDB;
    }

    @GetMapping(path = { "", "/" })
    @ResponseBody
    public List<Participant> getAllParticipants() {
        return db.findAll();
    }

    /**
     * websocket for participants
     * @param participant
     */
    @MessageMapping("/participants") // /app/participants
    @SendTo("/topic/participants")
    public Participant addParticipant(Participant participant){
        createParticipant(participant);
        return participant;
    }

    /**
     * websocket for participants
     * @param participant
     */
    @MessageMapping("/remParticipants") // /app/remParticipants
    @SendTo("/topic/participants")
    public Participant deleteParticipant(Participant participant){
        deleteParticipant(participant.getId());
        return participant;
    }


    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Object> getParticipantByID(@PathVariable("id") Long id){
        if(!db.existsById(id)){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(db.findById(id).get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Participant> deleteParticipant(@PathVariable("id") Long id) {

        if(id < 0) {
            return ResponseEntity.badRequest().build();
        }
        if(!db.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        //delete the person
        db.deleteById(id);

        //update expenses
        List<Expense> expenses = expenseDB.findAll();
        List<Debt> debts = debtDB.findAll();
        for (Expense e : expenses) {
            List<Debt> expDebts = debts.stream()
                .filter(d -> d.getExpense().equals(e))
                .toList();
            System.out.println(expDebts);
            Double total = expDebts.stream()
                .mapToDouble(Debt::getAmount)
                .sum();
            if (e.getAmount() <= total) continue;
            if (e.getAmount() > total) {
                e.setAmount(total);
                expenseDB.save(e);
            }
            if (total == 0) {
                expenseDB.deleteById(e.getId());
            }
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = { "", "/" })
    public ResponseEntity<Participant> createParticipant(@RequestBody Participant participant) {
        if (participant == null) {
            return ResponseEntity.badRequest().build();
        }
        Participant createParticipant = db.save(participant);
        return ResponseEntity.ok(createParticipant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Participant> updateParticipant
        (@RequestBody Participant participant, @PathVariable("id") Long id) {
        if(participant == null) {
            return ResponseEntity.badRequest().build();
        }
        if(id < 0) {
            return ResponseEntity.badRequest().build();
        }
        if (!db.existsById(id)){
            return ResponseEntity.notFound().build();
        }

        Participant existingParticipant = db.findById(id).get();
        existingParticipant.setName(participant.getName());
        existingParticipant.setEmail(participant.getEmail());
        existingParticipant.setIBAN(participant.getIBAN());
        existingParticipant.setBIC(participant.getBIC());
        Participant updatedParticipant = db.save(existingParticipant);
        return ResponseEntity.ok(updatedParticipant);
    }

}
