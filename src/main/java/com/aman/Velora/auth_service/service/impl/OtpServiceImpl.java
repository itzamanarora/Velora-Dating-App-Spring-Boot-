package com.aman.Velora.auth_service.service.impl;

import com.aman.Velora.auth_service.exception.InvalidOtpException;
import com.aman.Velora.auth_service.models.OtpPurpose;
import com.aman.Velora.auth_service.models.OtpVerification;
import com.aman.Velora.auth_service.repository.OtpVerificationRepository;
import com.aman.Velora.auth_service.service.OtpService;
import com.aman.Velora.user_service.models.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private static final int OTP_LENGTH = 6;
    private static final int EXPIRY_MINUTES = 10;
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final OtpVerificationRepository otpVerificationRepository;
    @Value("${MAIL_FROM}")
    private String MAIL_FROM;

    @Transactional
    @Override
    public void generateAndSendOtp(User user, OtpPurpose purpose) {
        log.info("Generating OTP for user: {} with purpose: {}", user.getEmail(), purpose);
        String otpCode = generateSecureOtp();

        OtpVerification otpVerification = OtpVerification.builder()
                .user(user)
                .otpCode(otpCode)
                .purpose(purpose)
                .expiresAt(Instant.now().plus(EXPIRY_MINUTES, ChronoUnit.MINUTES))
                .build();

        otpVerificationRepository.save(otpVerification);
        sendOtpEmail(user.getEmail(), otpCode, purpose);
        log.info("OTP generated and sent to user: {} with purpose: {}", user.getEmail(), purpose);
    }

    @Override
    public String generateSecureOtp() {
        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    @Override
    public void sendOtpEmail(String toEmail, String otpCode, OtpPurpose purpose) {
        try {
            Context context = new Context();
            context.setVariable("title", purpose == OtpPurpose.EMAIL_VERIFICATION ? "Verify your email" : "Reset your password");
            context.setVariable("message", "Use the code below to verify your Velora account.");
            context.setVariable("otpCode", otpCode);
            context.setVariable("expiryMinutes", EXPIRY_MINUTES);

            String htmlBody = templateEngine.process("otp-email", context);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(MAIL_FROM);
            helper.setTo(toEmail);
            helper.setSubject(purpose == OtpPurpose.EMAIL_VERIFICATION ? "Verify you velora Account" : "Reset your velora password");
            helper.setText(htmlBody, true); // true = isHtml

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    @Override
    @Transactional
    public void verifyOtp(String email, String otpCode, OtpPurpose purpose) {
        OtpVerification otpVerification = otpVerificationRepository.
                findTopByUser_EmailAndPurposeAndIsUsedFalseOrderByCreatedAtDesc(
                        email, purpose
                ).orElseThrow(() -> new InvalidOtpException("No active OTP found"));

        if (otpVerification.getExpiresAt().isBefore(Instant.now()))
            throw new InvalidOtpException("OTP expired.");

        if (otpVerification.getAttemptCount() >= 3)
            throw new InvalidOtpException("Maximum attempt exceeded.");

        if (!otpVerification.getOtpCode().equals(otpCode)) {
            otpVerification.setAttemptCount((short) (otpVerification.getAttemptCount() + 1));
            otpVerificationRepository.save(otpVerification);
            throw new InvalidOtpException("Incorrect Otp!");
        }
        otpVerification.setUsed(true);
        log.info("OTP is verified: {}", otpVerification.getId());
        otpVerificationRepository.save(otpVerification);
    }
}