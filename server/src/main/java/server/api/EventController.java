package server.api;

import commons.Event;
import commons.Expense;
import commons.Participant;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import server.database.EventRepository;
import server.database.ExpenseRepository;
import server.database.ParticipantRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

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
        this.expenseDB=expenseDB;
    }

//    @PostMapping("/json/import")
//    public ResponseEntity<Event> importEmployees(Event) throws Exception {
//        try {
//            String jsonContent = new String(file.getBytes());
//            ObjectMapper om = new ObjectMapper();
//            Event eventImport = om.readValue(jsonContent, new TypeReference<>() {
//            });
//            db.save(eventImport);
//            return ResponseEntity.ok(eventImport);
//        } catch (Exception e) {
//            throw new Exception("Importing Event JSON Failed, Due to : " + e.getMessage());
//        }
//    }

    /**
     * Get all events.
     * @return all events
     */
    @GetMapping(path = { "", "/" })
    public List<Event> getAll() {
        return db.findAll();
    }

    private Map<Object, Consumer<Event>> listeners = new HashMap<>();
    @GetMapping("/updates")
    public DeferredResult<ResponseEntity<Event>> getUpdates() {

        var noContent = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        var res = new DeferredResult<ResponseEntity<Event>>(5000L, noContent);

        var key = new Object();
        listeners.put(key, q -> {
            try {
                res.setResult(ResponseEntity.ok(q));
            } catch(Exception e) {
                // some error handling, don't know what we want
                e.printStackTrace();
            }
        });
        res.onCompletion(() -> {
            listeners.remove(key);
        });
        return res;
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


    @PostMapping(path = { "", "/" })
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        if (event == null) {
            return ResponseEntity.badRequest().build();
        }
        System.out.println(event);
        Event createdEvent = db.save(event);
        CompletableFuture<Void> notifyListenersFuture = CompletableFuture.runAsync(() -> {
            try {
                listeners.forEach((k, l) -> l.accept(event));
            } catch(Exception e) {
                // some error handling, don't know what we want
                e.printStackTrace();
            }
        });
        notifyListenersFuture.exceptionally(ex -> {
            // some error handling/ don't know what we want
            ex.printStackTrace();
            return null;

        });
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
    public ResponseEntity<List<Expense>> getExpenseByInvitationId(
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
