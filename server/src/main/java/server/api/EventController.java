package server.api;

import commons.Event;
import commons.Expense;
import commons.Participant;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.EventRepository;
import server.database.ExpenseRepository;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/")
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


    @PutMapping("/event/{invitationID}")
    public ResponseEntity<Event> updateEvent(@RequestBody Event event, @PathVariable("invitationID") String event_id) {
        if(event == null) {
            return ResponseEntity.badRequest().build();
        }
        if (!db.existsById(event_id)){
            return ResponseEntity.notFound().build();
        }

        Event existingEvent = db.findById(event_id).get();
        existingEvent.setParticipants(event.getParticipants());
        existingEvent.setExpenses(event.getExpenses());
        existingEvent.setName(event.getName());
        Event updatedEventEntity = db.save(existingEvent);
        return ResponseEntity.ok(updatedEventEntity);
    }

    @DeleteMapping("/event/{invitationID}")
    public ResponseEntity<Event> deleteEvent(@PathVariable("invitationID") String event_id) {
        db.deleteById(event_id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/event")
    @ResponseBody
    public ResponseEntity<Event> getEventById(@RequestParam("invitationID") String id){
        if(!db.existsById(id))
            return ResponseEntity.notFound().build();
        System.out.println(id);
        Event event=db.findById(id).get();
        return ResponseEntity.ok(event);
    }

    @PostMapping()
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        if (event == null) {
            return ResponseEntity.badRequest().build();
        }
        Event createdEvent = db.save(event);
        return ResponseEntity.ok(createdEvent);
    }

    /**
     * Adds a user to an event.
     *
     * @param invitation_id the id of the event
     * @param participant the participant to add
     * @return the updated event
     */
    @PutMapping("/{invitation_id}/users")
    public ResponseEntity<Event> addUser(@PathVariable("invitation_id") String invitation_id, @RequestBody Participant participant) {
        // TODO should this be a put or a post?
        if (!db.existsById(invitation_id)) {
            return ResponseEntity.badRequest().build();
        }

        Event event = db.findById(invitation_id).get();
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
     * @param invitation_id id of event from which to remove user
     * @param email email of user to remove
     * @return successful operation indicator
     */
    @DeleteMapping("/{invitation_id}/users/{email}")
    public ResponseEntity<Event> removeUserFromEvent(
            @PathVariable("invitation_id") String invitation_id, @PathVariable("email") String email) {
        if (invitation_id == null || isNullOrEmpty(invitation_id) || isNullOrEmpty(email)) {
            return ResponseEntity.badRequest().build();
        }
        if (!db.existsById(invitation_id)) {
            return ResponseEntity.notFound().build();
        }
        Event event = db.getReferenceById(invitation_id);
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
     *
     * @param invitation_id
     * @param expense
     * @return
     */
    @PostMapping("/{}/expenses")
    public ResponseEntity<Expense> addExpenseToEvent(
            @PathVariable("invitation_id") String invitation_id,
            @RequestBody Expense expense) {
        if (!db.existsById(invitation_id) || expense.getValue() < 0
                || isNullOrEmpty(expense.getDescription())) {
            return ResponseEntity.badRequest().build();
        }

        expense.setDateSent(java.time.LocalDate.now());

        Event event = db.findById(invitation_id).get();
        event.addExpense(expense);
        Expense saved = exRepo.save(expense);
        return ResponseEntity.ok(saved);
    }


}
