package com.liquibase.demo.services;

import com.liquibase.demo.model.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@AllArgsConstructor
public class WelcomeEmailMessage {

    private final JavaMailSender mailSender;

    private final SpringTemplateEngine templateEngine;

    public void sendWelcomeEmail(User user) {
        Context context = new Context();
        context.setVariable("name", user.getFirstName());
        context.setVariable("email", user.getEmail());
        String htmlBody = templateEngine.process("Welcome", context);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(user.getEmail());
            helper.setSubject("Welcome to Instagram!");
            helper.setText(htmlBody, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
