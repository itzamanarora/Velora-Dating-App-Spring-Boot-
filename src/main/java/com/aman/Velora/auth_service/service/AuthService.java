package com.aman.Velora.auth_service.service;

import com.aman.Velora.auth_service.dto.request.*;
import com.aman.Velora.auth_service.dto.response.*;

public interface AuthService {
    SignupResponseDTO signup(SignupRequestDTO signupRequestDTO);

    AuthResponseDTO login(LoginRequestDTO loginRequestDTO);

    VerifyOTPResponseDTO verifyEmailOTP(VerifyOTPRequestDTO verifyOTPRequestDTO);

    ForgotPasswordResponseDTO forgotPassword(ForgotPasswordRequestDTO forgotPasswordRequestDTO);

    ForgotPasswordResponseDTO resetPassword(ResetPasswordRequestDTO resetPasswordRequestDTO);

    ResendOTPResponseDTO resendOtp(ResendOTPRequestDTO resendOTPRequestDTO);

    RefreshTokenResponseDTO refreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO);
}
