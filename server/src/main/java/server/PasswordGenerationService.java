package server;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PasswordGenerationService {

    private String password;

    /**
     * Source: <a href="https://www.baeldung.com/java-random-string">link</a>
     * generates a new password and outputs it in the server.
     * The previous password is overwritten, rendering it useless.
     * Password is an alphanumerical 10 letter string consisting both lower and higher case letters.
     */
    public void generatePassword() {
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
    }

    /**
     * Checks if the input password is the same as the server password.
     * generates a new password if incorrect.
     * @param input - password to compare to
     * @return true if they are the same, false otherwise.
     */
    public boolean checkPassword(String input) {
        boolean b = input.equals(password);
        if (!b) generatePassword();
        return b;
    }
}
