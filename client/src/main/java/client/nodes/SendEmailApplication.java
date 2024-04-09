package client.nodes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SendEmailApplication implements CommandLineRunner {

    @Autowired
    private EmailSenderService emailSenderService;

    public static void main(String[] args) {
        SpringApplication.run(SendEmailApplication.class, args);
    }

    @Override
    public void run(String[] args) throws Exception {
        // Sending email
        String to = args[0];
        String subject = "Splitty Invite Code ";
        String body = "Thank you for using Splitty!\n\n" +
                "This is your invitation code: " + args[1];
        emailSenderService.sendEmail(to, subject, body);
    }
}