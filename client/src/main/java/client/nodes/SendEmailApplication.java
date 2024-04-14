package client.nodes;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication

public class SendEmailApplication {

    @Autowired
    private EmailSenderService emailSenderService;

    private static String to;

    private static String body;

    public static void main(String[] args) {
        to = args[0];
        body = "Thank you for using Splitty!\n\n" +
                "This is your invitation code: " + args[1];
        SpringApplication.run(SendEmailApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void sendEmail() throws MessagingException {
        String subject = "Splitty Invite Code ";
        emailSenderService.sendEmail(to, subject, body);
        System.out.println("mail sent");
    }
}
