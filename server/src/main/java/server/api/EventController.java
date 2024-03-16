package server.api;

import commons.Event;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.EventRepository;

@RestController
@RequestMapping("/")
public class EventController {
    private EventRepository db;

    public EventController(EventRepository db){
        this.db=db;
    }

    @PutMapping("/event/{invitationID}")
    public ResponseEntity<Event> updateEvent(@RequestBody Event event, @PathVariable("invitationID") String event_id) {
        long id=Long.parseLong(event_id);
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

    @DeleteMapping("/event/{invitationID}")
    public ResponseEntity<Event> deleteEvent(@PathVariable("invitationID") String event_id) {
        long id=Long.parseLong(event_id);
        db.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/event")
    @ResponseBody
    public ResponseEntity<Event> getEventById(@RequestParam("invitationID") String event_id){
        long id=Long.parseLong(event_id);
        if(id<0)
            return ResponseEntity.badRequest().build();
        if(!db.existsById(id))
            return ResponseEntity.notFound().build();
        System.out.println(event_id);
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


}
