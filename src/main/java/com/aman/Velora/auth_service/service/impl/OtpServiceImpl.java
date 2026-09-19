package com.aman.Velora.auth_service.service.impl;

import com.aman.Velora.auth_service.service.OtpService;
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
public class OtpServiceImpl implements OtpService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Override
    public void sendTestEmail(String toEmail) {
        try {
            Context context = new Context();
            context.setVariable("title", "Verify your email");
            context.setVariable("message", "Use the code below to verify your Velora account.");
            context.setVariable("otpCode", "482913");
            context.setVariable("expiryMinutes", 10);

            String htmlBody = templateEngine.process("otp-email", context);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(toEmail);
            helper.setSubject("Verify your Velora account");
            helper.setText(htmlBody, true); // true = isHtml

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}