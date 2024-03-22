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
    public void getParticipantByIdNotExistTest(){
        ResponseEntity<Object> responseEntity=participantController.getParticipantByID((long)-1);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }
    @Test
    public void postParticipantTest(){
        Participant participant=new Participant();
        when(participantRepository.save(participant)).thenReturn(participant);
        ResponseEntity<Participant> responseEntity=participantController.createParticipant(participant);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(participant, responseEntity.getBody());
    }

    @Test
    public void postNullParticipantTest(){
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
    public void deleteExpenseTest() {
        long participantId = 1;
        ResponseEntity<Participant> responseEntity = participantController.deleteParticipant(participantId);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(participantRepository, times(1)).deleteById(participantId);
    }
}
