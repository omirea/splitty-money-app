package server.api;

import commons.Event;
import commons.Expense;
import commons.Participant;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.EventRepository;
import server.database.ExpenseRepository;
import server.database.ParticipantRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/event")
public class EventController {
    private final EventRepository db;
    private final ParticipantRepository participantDB;
    private final ExpenseRepository expenseDB;

    public EventController(EventRepository db, ParticipantRepository participantDB,
                           ExpenseRepository expenseDB){
        this.db=db;
        this.participantDB = participantDB;
        this.expenseDB = expenseDB;
    }

    /**
     * Get all events.
     * @return all events
     */
    @GetMapping(path = { "", "/" })
    public List<Event> getAll() {
        return db.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@RequestBody Event event,
                                             @PathVariable("id") long id) {
        if(event == null) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Event> tempEvent = db.findById(id);
        if (tempEvent.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Event existingEvent = tempEvent.get();
        existingEvent.setName(event.getName());
        Event updatedEventEntity = db.save(existingEvent);
        return ResponseEntity.ok(updatedEventEntity);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Event> deleteEvent(@PathVariable("id") long id) {
        db.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{invitationID}")
    @ResponseBody
    public ResponseEntity<Event> getEventByInvitationId(
        @PathVariable("invitationID") String invitationID){
        Event e = new Event();
        e.setInvitationID(invitationID);
        Optional<Event> tempEvent = db.findOne(Example.of(e, ExampleMatcher.matchingAll()));
        return tempEvent.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping(path = { "", "/" })
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        if (event == null) {
            return ResponseEntity.badRequest().build();
        }
        System.out.println(event);
        Event createdEvent = db.save(event);
        return ResponseEntity.ok(createdEvent);
    }

    @GetMapping("/{invitationID}/participant")
    @ResponseBody
    public ResponseEntity<List<Participant>> getParticipantsByInvitationId(
            @PathVariable("invitationID") String invitationID) {

        Event e = new Event();
        e.setInvitationID(invitationID);
        Optional<Event> tempEvent = db.findOne(Example.of(e, ExampleMatcher.matchingAll()));
        if (tempEvent.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Participant p = new Participant();
        p.setEvent(tempEvent.get());
        List<Participant> participants = participantDB.findAll(
                Example.of(p, ExampleMatcher.matchingAll()));
        //System.out.println(participants);

        return ResponseEntity.ok(participants);
    }

    @GetMapping("/{invitationID}/expense")
    @ResponseBody
    public ResponseEntity<List<Expense>> getExpensesByInvitationId(
            @PathVariable("invitationID") String invitationID) {

        Event e = new Event();
        e.setInvitationID(invitationID);
        Optional<Event> tempEvent = db.findOne(Example.of(e, ExampleMatcher.matchingAll()));
        if (tempEvent.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Expense expense = new Expense();
        expense.setEvent(tempEvent.get());
        List<Expense> expenses = expenseDB.findAll(
                Example.of(expense, ExampleMatcher.matchingAll()));
        System.out.println(expenses);

        return ResponseEntity.ok(expenses);
    }

}
