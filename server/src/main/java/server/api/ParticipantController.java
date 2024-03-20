package server.api;

import commons.Participant;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.database.ParticipantRepository;

import java.util.List;

@Controller
@RequestMapping("/participant")
public class ParticipantController {

    private ParticipantRepository db;

    public ParticipantController(ParticipantRepository db){
        this.db=db;
    }

    @GetMapping("/participant")
    @ResponseBody
    public List<Participant> getAllParticipants() {
        return db.findAll();
    }

    @GetMapping("/participant/{id}")
    @ResponseBody
    public ResponseEntity<Object> findParticipantByID(@PathVariable("id") Long id){
        if(!db.existsById(id)){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(db.findById(id).get());
    }

    @DeleteMapping("/participant/{id}")
    public ResponseEntity<Participant> deleteParticipant(@PathVariable("id") Long id) {
        db.deleteById(id);
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

    @PutMapping("/participant/{id}")
    public ResponseEntity<Participant> updateParticipant
        (@RequestBody Participant participant, @PathVariable("id") long id) {
        if(participant == null) {
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
