package server.api;

import java.util.List;
import java.util.Random;

import commons.Event;
import commons.Participant;
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

    @GetMapping("/event/{id}")
    @ResponseBody
    public String id(@PathVariable("id") Long id){
        if(!db.existsById(id)){
            var event=new Event();
            event.setName("name");
            db.save(event);
        }

        return "Hello " + id + "!";
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

}
