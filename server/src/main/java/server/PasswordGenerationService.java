package server;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PasswordGenerationService {

    private String password;

    //Source: https://www.baeldung.com/java-random-string
    public String generatePassword() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        password = random.ints(leftLimit, rightLimit + 1)
            .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
            .limit(targetStringLength)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();

        System.out.println("Password: " + password);
        return password;
    }

    public boolean checkPassword(String input) {
        boolean b = input.equals(password);
        if (!b) generatePassword();
        return b;
    }
}
