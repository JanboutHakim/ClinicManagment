package com.example.DocLib.services.implementation;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public void sendOtp(String to, String name, String otp) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        // Prepare context with variables
        Context context = new Context();
        context.setVariable("username", name);
        context.setVariable("otp", otp);

        // Load and process the HTML template
        String htmlContent = templateEngine.process("otp-email.html", context);

        helper.setTo(to);
        helper.setSubject("Email Verification OTP");
        helper.setText(htmlContent, true); // true = HTML

        mailSender.send(message);
    }
}
