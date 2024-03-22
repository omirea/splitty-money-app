package client.utils;

import commons.Event;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import org.glassfish.jersey.client.ClientConfig;

import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class EventServerUtils {

    private static final String SERVER = "http://localhost:8080/";

    /**
     *
     * @return a list of events
     */
    public List<Event> getAllEvents(){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/event")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<Event>>() {});
    }

    /**
     * method to get a specific Event based on invitationID
     * @param invitationID id of the Event requested
     * @return the requested Event
     */
    public Event getEventById(String invitationID){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/event/" + invitationID)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<Event>() {});
    }

    /**
     * method to delete a specific Event  from the database
     * @param invitationID the id of the Event  to be deleted
     * @return the deleted Event
     */
    public Event deleteEvent (String invitationID){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/event/" + invitationID)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .delete(new GenericType<Event>() {});
    }

    /**
     * method to create a new Event  in the database
     * @param event  Event  to be added to the database
     * @return the created Event
     */
    public Event createEvent(Event event ){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/event")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(event , APPLICATION_JSON),
                        Event.class);
    }

    /**
     * method to update an Event  from the database
     * @param event Event  to be updated in the database
     * @return the updated Event
     */
    public Event updateEvent(Event event , String invitationID){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/event/" + invitationID)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(event, APPLICATION_JSON),
                        Event.class);
    }
}
