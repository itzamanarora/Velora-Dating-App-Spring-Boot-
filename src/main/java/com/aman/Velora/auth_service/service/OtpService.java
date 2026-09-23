package com.aman.Velora.auth_service.service;

import com.aman.Velora.auth_service.dto.request.VerifyOTPRequestDTO;
import com.aman.Velora.auth_service.models.OtpPurpose;
import com.aman.Velora.user_service.models.User;

public interface OtpService {

    void generateAndSendOtp(User user, OtpPurpose purpose);

    String generateSecureOtp();

    void sendOtpEmail(String toEmail, String otpCode, OtpPurpose purpose);

    void verifyOtp(String email, String otpCode, OtpPurpose purpose);
}
