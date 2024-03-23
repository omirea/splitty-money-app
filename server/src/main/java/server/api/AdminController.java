package server.api;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.PasswordGenerationService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    PasswordGenerationService passwordGenerationService;

    public AdminController(PasswordGenerationService passwordGenerationService) {
        this.passwordGenerationService = passwordGenerationService;
    }

    @GetMapping("/{password}")
    public ResponseEntity<Boolean> checkPassword(@PathVariable("password") String password) {
        return ResponseEntity.ok(passwordGenerationService.checkPassword(password));
    }

    @GetMapping("/")
    public void requestPassword() {
        passwordGenerationService.generatePassword();
    }

}
