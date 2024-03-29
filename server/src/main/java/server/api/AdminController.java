package server.api;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.PasswordGenerationService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    PasswordGenerationService passwordGenerationService;

    /**
     * constructor for the admin controller
     * @param passwordGenerationService service to inject
     */
    public AdminController(PasswordGenerationService passwordGenerationService) {
        this.passwordGenerationService = passwordGenerationService;
    }

    /**
     * compares the input to the server password
     * @param password - input password
     * @return Response entity with true if they are the same, false otherwise.
     */
    @GetMapping("/{password}")
    public ResponseEntity<Boolean> checkPassword(@PathVariable("password") String password) {
        return ResponseEntity.ok(passwordGenerationService.checkPassword(password));
    }

    @GetMapping("/")
    public ResponseEntity<Boolean> generatePassword() {
        passwordGenerationService.generatePassword();
        return ResponseEntity.ok(true);
    }
}
