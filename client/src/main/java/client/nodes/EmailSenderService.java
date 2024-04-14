package client.nodes;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import javax.inject.Inject;

@Service
@PropertySource("/application.properties")
public class EmailSenderService {

    private final JavaMailSenderImpl mailSender;

    @Inject
    public EmailSenderService(JavaMailSenderImpl mailSender){
        this.mailSender=mailSender;
    }

    @Value("${server.port}")
    private int port;

    public void sendEmail(String to, String subject, String body) throws MessagingException {;
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
        System.out.println("mail sent!");
//        Properties prop=new Properties();
//        String fileName="src/main/resources/application.properties";
//        try(FileInputStream fileInputStream=new FileInputStream(fileName)){
//            prop.load(fileInputStream);
//        }catch(IOException e){
//            e.printStackTrace();
//        }
//        prop.setProperty("server.port", String.valueOf(port+1));
//        try(OutputStream outputStream=new FileOutputStream(fileName)){
//            prop.store(outputStream, "Updated port");
//        }catch (IOException e){
//            throw new RuntimeException(e);
//        }
//        mailSender.getSession().getTransport("smtp").close();
    }
}
