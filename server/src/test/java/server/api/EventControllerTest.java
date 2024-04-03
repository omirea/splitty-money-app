package server.api;

import commons.Event;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import server.database.EventRepository;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ExtendWith(MockitoExtension.class)
public class EventControllerTest {

//    private TestEventRepository repo;
//    private TestExpenseRepository exRepo;

    @Mock
    private EventRepository eventRepository;


    @InjectMocks
    private EventController eventController;


    @Test
    public void testGetEventByID() {
        String event_id = "123";

        Event event = new Event();
        event.setInvitationID(event_id);
        when(eventRepository.findOne(Example.of(event, ExampleMatcher.matchingAll()))).thenReturn(Optional.of(event));

        ResponseEntity<Event> responseEntity = eventController.getEventByInvitationId(event_id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(event, responseEntity.getBody());

    }

    @Test
    public void testCreateEvent() {
        Event event = new Event(); // create an Event object with appropriate data

        when(eventRepository.save(event)).thenReturn(event);

        ResponseEntity<Event> responseEntity = eventController.createEvent(event);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(event, responseEntity.getBody());
    }
    @Test
    public void testUpdateEvent() {
        long eID = 123;

        Event existingEvent = new Event(); // create an existing Event object with appropriate data
        Event updatedEvent = new Event(); // create an updated Event object with appropriate data

        when(eventRepository.findById(eID)).thenReturn(java.util.Optional.of(existingEvent));
        when(eventRepository.save(existingEvent)).thenReturn(updatedEvent);

        ResponseEntity<Event> responseEntity = eventController.updateEvent(updatedEvent, eID);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedEvent, responseEntity.getBody());
    }

    @Test
    public void testDeleteEvent() {
        long eID = 123;

        ResponseEntity<Event> responseEntity = eventController.deleteEvent(eID);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(eventRepository, times(1)).deleteById(eID);
    }

    @Test
    public void cannotAddNullEvent() {
        var actual = eventController.createEvent(null);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }


//    @Test
//    public void getAllEvents() {
//        repo = new TestEventRepository();
//        eventController = new EventController(repo, exRepo);
//
//        List<Event> list = new ArrayList<>();
//
//        Event event1 = new Event("a");
//        Event event2 = new Event("b");
//
//        list.add(event1);
//        list.add(event2);
//
//        eventController.createEvent(event1);
//        eventController.createEvent(event2);
//
//        List<Event> response = eventController.getAll();
//
//        assertEquals(response, list);
//    }
}


