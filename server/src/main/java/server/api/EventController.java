package server.api;

import commons.Event;
import commons.Expense;
import commons.Participant;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.EventRepository;
import server.database.ExpenseRepository;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/event")
public class EventController {
    private final EventRepository db;
    private ExpenseRepository exRepo;

    public EventController(EventRepository db, ExpenseRepository exRepo){
        this.db=db;
        this.exRepo=exRepo;
    }

    /**
     * Get all events.
     *
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
        if (!db.existsById(id)){
            return ResponseEntity.notFound().build();
        }

        Event existingEvent = db.findById(id).get();
        existingEvent.setParticipants(event.getParticipants());
        existingEvent.setExpenses(event.getExpenses());
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
        Optional<Event> tempEvent = db.findOne(Example.of(e, ExampleMatcher.matchingAny()));
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

    /**
     * Adds a user to an event.
     *
     * @param id the id of the event
     * @param participant the participant to add
     * @return the updated event
     */
    @PutMapping("/{id}/users")
    public ResponseEntity<Event> addUser(@PathVariable("id") long id,
                                         @RequestBody Participant participant) {
        // TODO should this be a put or a post?
        if (!db.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }

        Event event = db.findById(id).get();
        event.addParticipant(participant);
        try {
            db.save(event);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(event);
    }

    /**
     * Removes a user from an event.
     *
     * @param id id of event from which to remove user
     * @param email email of user to remove
     * @return successful operation indicator
     */
    @DeleteMapping("/{id}/users/{email}")
    public ResponseEntity<Event> removeUserFromEvent(
            @PathVariable("id") long id,
            @PathVariable("email") String email) {
        if (id <= 0 || isNullOrEmpty(email)) {
            return ResponseEntity.badRequest().build();
        }
        if (!db.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Event event = db.getReferenceById(id);
        Optional<Participant> toRemove = event.getParticipants()
                .stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst();
        if (toRemove.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        event.removeParticipant(toRemove.get());
        db.save(event);
        return ResponseEntity.ok(event);
    }

    /**
     * Checks if a string is null or empty.
     *
     * @param s the string s
     * @return true if the string is null or empty
     */
    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    /**
     * adding an expense to event
     * @param id event id
     * @param expense expense to add
     * @return response entity
     */
    @PostMapping("/{id}/expenses")
    public ResponseEntity<Expense> addExpenseToEvent(
            @PathVariable("id") long id,
            @RequestBody Expense expense) {
        if (!db.existsById(id) || isNullOrEmpty(expense.getDescription())) {
            return ResponseEntity.badRequest().build();
        }

        expense.setDateSent(java.time.LocalDate.now());

        Event event = db.findById(id).get();
        event.addExpense(expense);
        Expense saved = exRepo.save(expense);
        return ResponseEntity.ok(saved);
    }


}
