package com.stefanini.librarybackend.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class EmailSenderService {

    private final Environment environment;
    @Autowired
    private JavaMailSender mailSender;

    public EmailSenderService(Environment environment) {
        this.environment = environment;
    }

    public void sendMail(String to, String email, String subject) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(Objects.requireNonNull(environment.getProperty("SPRING_MAIL_USERNAME")));
        message.setTo(to);
        message.setText(email);
        message.setSubject(subject);


        mailSender.send(message);
        log.info("Mail sent to " + to);
    }
}
