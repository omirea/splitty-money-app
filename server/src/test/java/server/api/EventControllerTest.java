package server.api;

import commons.Event;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import server.database.EventRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventControllerTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventController eventController;

    @Test
    public void testGetEventByID() {
        String event_id = "ab123";
        long eventId=Long.valueOf(event_id);

        Event event = new Event();
        when(eventRepository.existsById(eventId)).thenReturn(true);
        when(eventRepository.findById(eventId)).thenReturn(java.util.Optional.of(event));

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
        long eventId=Long.valueOf(eID);
        Event existingEvent = new Event(); // create an existing Event object with appropriate data
        Event updatedEvent = new Event(); // create an updated Event object with appropriate data

        when(eventRepository.existsById(eventId)).thenReturn(true);
        when(eventRepository.findById(eventId)).thenReturn(java.util.Optional.of(existingEvent));
        when(eventRepository.save(existingEvent)).thenReturn(updatedEvent);

        ResponseEntity<Event> responseEntity = eventController.updateEvent(updatedEvent, eID);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedEvent, responseEntity.getBody());
    }

    @Test
    public void testDeleteEvent() {
        String eID = "ab123";
        long eventId=Long.valueOf(eID);

        ResponseEntity<Event> responseEntity = eventController.deleteEvent(eID);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(eventRepository, times(1)).deleteById(eventId);
    }

}
