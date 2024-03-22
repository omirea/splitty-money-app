package server.api;

import commons.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import server.database.EventRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ExtendWith(MockitoExtension.class)
public class EventControllerTest {

    private TestEventRepository repo;
    private TestExpenseRepository exRepo;

    @Mock
    private EventRepository eventRepository;


    @InjectMocks
    private EventController eventController;


    @Test
    public void testGetEventByID() {
        String event_id = "ab123";

        Event event = new Event();
        when(eventRepository.existsById(event_id)).thenReturn(true);
        when(eventRepository.findById(event_id)).thenReturn(java.util.Optional.of(event));

        ResponseEntity<Event> responseEntity = eventController.getEventById(event_id);

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
        String eID = "ab123";

        Event existingEvent = new Event(); // create an existing Event object with appropriate data
        Event updatedEvent = new Event(); // create an updated Event object with appropriate data

        when(eventRepository.existsById(eID)).thenReturn(true);
        when(eventRepository.findById(eID)).thenReturn(java.util.Optional.of(existingEvent));
        when(eventRepository.save(existingEvent)).thenReturn(updatedEvent);

        ResponseEntity<Event> responseEntity = eventController.updateEvent(updatedEvent, eID);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedEvent, responseEntity.getBody());
    }

    @Test
    public void testDeleteEvent() {
        String eID = "ab123";

        ResponseEntity<Event> responseEntity = eventController.deleteEvent(eID);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(eventRepository, times(1)).deleteById(eID);
    }

    @Test
    public void cannotAddNullEvent() {
        var actual = eventController.createEvent(null);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void getAllEvents() {
        repo = new TestEventRepository();
        eventController = new EventController(repo, exRepo);

        List<Event> list = new ArrayList<>();

        Event event1 = new Event("a");
        Event event2 = new Event("b");

        list.add(event1);
        list.add(event2);

        eventController.createEvent(event1);
        eventController.createEvent(event2);

        List<Event> response = eventController.getAll();

        assertEquals(response, list);
    }




    }


