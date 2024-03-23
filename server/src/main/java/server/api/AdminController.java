package server.api;

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
    public boolean checkPassword(@PathVariable("password") String password) {

        return passwordGenerationService.checkPassword(password);
    }

    @GetMapping("/")
    public void requestPassword() {
        passwordGenerationService.generatePassword();
    }

}
