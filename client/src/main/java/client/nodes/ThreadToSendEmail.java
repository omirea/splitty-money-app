package client.nodes;

import org.springframework.mail.SimpleMailMessage;

public class ThreadToSendEmail implements Runnable {
    @Override
    public void run() {
        System.out.println("in thread to send email");
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo("2");
//        message.setSubject(subject);
//        message.setText(body);
//        mailSender.send(message);
    }
}
