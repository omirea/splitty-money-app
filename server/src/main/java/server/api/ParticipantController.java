package server.api;

import commons.Event;
import commons.Expense;
import commons.Participant;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.ParticipantRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/participant")
public class ParticipantController {

    private final ParticipantRepository db;

    public ParticipantController(ParticipantRepository db){
        this.db=db;
    }

    @GetMapping(path = { "", "/" })
    @ResponseBody
    public List<Participant> getAllParticipants() {
        return db.findAll();
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

    @GetMapping("/{name}")
    @ResponseBody
    public ResponseEntity<Participant> getParticipantByInvitationId(
            @PathVariable("name") String invitationID){
        Event e = new Event();
        e.setInvitationID(invitationID);
        Optional<Event> tempEvent = db.findOne(Example.of(e, ExampleMatcher.matchingAll()));
        if(tempEvent.isPresent()){
            Event event = tempEvent.get();
            String invID = event.getInvitationID();
            String name = event.getName();
            List<Expense> exs = getExpenseByInvitationId(invID).getBody();
            List<Participant> prs = getParticipantsByInvitationId(invID).getBody();
            Event newE = new Event(name, invID, exs, prs);
            newE.setCreateDate(event.getCreateDate());
            newE.setLastModified(event.getLastModified());
            newE.setID(event.getID());
            return ResponseEntity.ok(newE);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
