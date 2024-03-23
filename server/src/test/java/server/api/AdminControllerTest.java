package server.api;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import server.PasswordGenerationService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {
    @Mock
    private PasswordGenerationService passwordGenerationService;

    @InjectMocks
    private AdminController adminController;

    @Test
    void testCheckValidPassword() {
        String password = "validPassword";
        when(passwordGenerationService.checkPassword(password)).thenReturn(true);

        ResponseEntity<Boolean> response = adminController.checkPassword(password);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Boolean.TRUE, response.getBody());
    }

    @Test
    void testCheckInvalidPassword() {
        String password = "invalidPassword";
        when(passwordGenerationService.checkPassword(password)).thenReturn(false);

        ResponseEntity<Boolean> response = adminController.checkPassword(password);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotEquals(Boolean.TRUE, response.getBody());
    }

    @Test
    void testRequestPassword() {
        adminController.requestPassword();

        verify(passwordGenerationService, times(1)).generatePassword();
    }
}
