package server;

import commons.Participant;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.database.ParticipantRepository;

@Controller
@RequestMapping("/")
public class ParticipantController {

    private ParticipantRepository db;

    public ParticipantController(ParticipantRepository db){
        this.db=db;
    }

    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "Hello participant!";
    }

    @GetMapping("/participant/{id}")
    @ResponseBody
    public String id(@PathVariable("id") Long id){
        if(!db.existsById(id)){
            var user=new Participant();
            user.setName("name");
            db.save(user);
        }

        return "Hello " + id + "!";
    }

    @GetMapping("/participant")
    @ResponseBody
    public ResponseEntity<Participant> getParticipantById(@RequestParam("id") long participant_id){
        if(participant_id<0)
            return ResponseEntity.badRequest().build();
        if(!db.existsById(participant_id))
            return ResponseEntity.notFound().build();
        System.out.println(participant_id);
        Participant participant=db.findById(participant_id).get();
        return ResponseEntity.ok(participant);
    }

//    @PostMapping(path = "/participant",
//            consumes = MediaType.APPLICATION_JSON_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<ParticipantDB> create(@RequestBody ParticipantDB newUser) {
//
//        ParticipantDB user = new ParticipantDB();
//        return new ResponseEntity<>(user, HttpStatus.CREATED);
//    }

}
