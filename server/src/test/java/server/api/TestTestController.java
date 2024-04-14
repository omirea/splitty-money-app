package server.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TestTestController {

    @InjectMocks
    private TestController testController;

    @Test
    public void testConnection() {
        // Call the testConnection method
        ResponseEntity<Boolean> responseEntity = testController.testConnection();

        // Verify the status code is OK
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Verify the body contains true
        assertEquals(true, responseEntity.getBody());
    }
}
