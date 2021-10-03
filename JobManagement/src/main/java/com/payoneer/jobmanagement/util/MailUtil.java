package com.payoneer.jobmanagement.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;


@Component
public class MailUtil {

    @Bean
    public JavaMailSender getJavaMailSender()
    {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(25);

        mailSender.setUsername("connectrmgroup@gmail.com");
        mailSender.setPassword("shobanaps");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    @Autowired
    private JavaMailSender mailSender ;



    public String sendEmail(String to,String body){
        String msg = "";
        SimpleMailMessage message = new SimpleMailMessage();
        try {
            message.setTo(to);
            message.setText(body);
            message.setSubject("Credit card due alert");
            message.setFrom("rmfuelsttm@gmail.com");
            mailSender.send(message);
            msg = "Mail triggered to" + to;
        }catch (Exception e){
            msg = e.getMessage();
        }
        return msg;
    }
}
