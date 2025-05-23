package com.angus.pethomeadoptionbackend.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;


    public void sendPlainText(Collection<String> receivers, String subject, String content) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("anguschp@gmail.com");
        message.setSubject(subject);
        message.setText(content);

        mailSender.send(message);
    }
}
