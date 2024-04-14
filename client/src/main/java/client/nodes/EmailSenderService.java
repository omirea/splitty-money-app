package client.nodes;

import jakarta.mail.MessagingException;
import jakarta.mail.NoSuchProviderException;
import jakarta.mail.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;


@Service
@PropertySource("/application.properties")
public class EmailSenderService {

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Value("${server.port}")
    private int port;

    public void sendEmail(String to, String subject, String body) throws MessagingException {;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
        System.out.println("mail sent!");
        Session session = mailSender.getSession();
        session.getTransport().close();
        System.out.println("port is " + port);
        port=8082;
    }
}
