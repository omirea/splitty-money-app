package server.api;

import commons.Participant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import server.database.ParticipantRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParticipantControllerTest {

    @Mock
    private ParticipantRepository participantRepository;

    @InjectMocks
    private ParticipantController participantController;

    @Test
    public void getParticipantByIdTest(){
        long participantId=1;
        Participant participant=new Participant();

        when(participantRepository.existsById(participantId)).thenReturn(true);
        when(participantRepository.findById(participantId)).thenReturn(Optional.of(participant));

        ResponseEntity<Object> responseEntity=participantController.getParticipantByID(participantId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(participant, responseEntity.getBody());
    }

    @Test
    public void getParticipantByIdNegativeID(){
        ResponseEntity<Object> responseEntity=participantController.getParticipantByID((long)-1);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    public void postParticipantTest(){ //add to db
        Participant participant=new Participant();
        when(participantRepository.save(participant)).thenReturn(participant);
        ResponseEntity<Participant> responseEntity=participantController.createParticipant(participant);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(participant, responseEntity.getBody());
    }

    @Test
    public void postNullParticipantTest(){ //test to add to db
        ResponseEntity<Participant> responseEntity=participantController.createParticipant(null);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    public void updateParticipantTest(){
        long participantId=1;
        Participant existentParticipant=new Participant();
        Participant updatedParticipant=new Participant();

        when(participantRepository.existsById(participantId)).thenReturn(true);
        when(participantRepository.findById(participantId))
                .thenReturn(Optional.of(existentParticipant));
        when(participantRepository.save(existentParticipant)).thenReturn(updatedParticipant);

        ResponseEntity<Participant> responseEntity=participantController
                .updateParticipant(updatedParticipant, participantId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedParticipant, responseEntity.getBody());
    }

    @Test
    public void testUpdateParticipantNull() {
        long id = 100;
        ResponseEntity<Participant> responseEntity = participantController
            .updateParticipant(null, id);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    public void testUpdateParticipantNotFound() {
        long id = 150;
        Participant participant = new Participant();
        when(participantRepository.existsById(id)).thenReturn(false);
        ResponseEntity<Participant> responseEntity = participantController
            .updateParticipant(participant, id);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    public void testUpdateParticipantNegativeID() {
        long id = -1;
        Participant participant = new Participant();
        ResponseEntity<Participant> responseEntity = participantController
            .updateParticipant(participant, id);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    public void deleteParticipantTest() {
        long participantId = 1;
        when(participantRepository.existsById(participantId)).thenReturn(true);
        ResponseEntity<Participant> responseEntity = participantController.deleteParticipant(participantId);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(participantRepository, times(1)).deleteById(participantId);
    }

    @Test
    public void testDeleteParticipantNotFound() {
        long id = 150;
        when(participantRepository.existsById(id)).thenReturn(false);
        ResponseEntity<Participant> responseEntity = participantController.deleteParticipant(id);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    public void testDeleteParticipantNegativeID() {
        long id = -1;
        ResponseEntity<Participant> responseEntity = participantController.deleteParticipant(id);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }



    @Test
    public void testGetAllParticipants() {
        List<Participant> dummy = new ArrayList<>();
        when(participantRepository.findAll()).thenReturn(dummy);
        List<Participant> listOfParticipants = participantController.getAllParticipants();
        assertEquals(listOfParticipants, dummy);
    }
}
