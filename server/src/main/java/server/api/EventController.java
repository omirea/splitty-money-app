package server.api;

import commons.Event;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.EventRepository;

@RestController
@RequestMapping("/event")
public class EventController {
    private EventRepository db;

    public EventController(EventRepository db){
        this.db=db;
    }

    @PutMapping("/{invitationID}")
    public ResponseEntity<Event> updateEvent(@RequestBody Event event,
                                             @PathVariable("invitationID") String event_id) {
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

    @DeleteMapping("/{invitationID}")
    public ResponseEntity<Event> deleteEvent(@PathVariable("invitationID") String event_id) {
        db.deleteById(event_id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = { "", "/" })
    @ResponseBody
    public ResponseEntity<Event> getEventById(@RequestParam("invitationID") String id){
        if(!db.existsById(id))
            return ResponseEntity.notFound().build();
        System.out.println(id);
        Event event=db.findById(id).get();
        return ResponseEntity.ok(event);
    }

    @PostMapping(path = { "", "/" })
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        if (event == null) {
            return ResponseEntity.badRequest().build();
        }
        Event createdEvent = db.save(event);
        return ResponseEntity.ok(createdEvent);
    }


}
