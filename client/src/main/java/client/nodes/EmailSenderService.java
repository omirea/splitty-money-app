package client.nodes;

import jakarta.mail.MessagingException;
import jakarta.mail.NoSuchProviderException;
import jakarta.mail.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;


@Service
@EnableAsync
@PropertySource("/application.properties")
public class EmailSenderService {

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Value("${server.port}")
    private int port;

    @Async
    public void sendEmail(String to, String subject, String body) throws MessagingException {;
        SimpleMailMessage message = new SimpleMailMessage();
        System.out.println(mailSender.getSession().getTransport());
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
        System.out.println("mail sent!");
        mailSender.getSession().getTransport("smtp").close();
    }
}
