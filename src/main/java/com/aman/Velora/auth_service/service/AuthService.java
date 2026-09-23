package com.aman.Velora.auth_service.service;

import com.aman.Velora.auth_service.dto.request.*;
import com.aman.Velora.auth_service.dto.response.AuthResponseDTO;
import com.aman.Velora.auth_service.dto.response.ForgotPasswordResponseDTO;
import com.aman.Velora.auth_service.dto.response.SignupResponseDTO;
import com.aman.Velora.auth_service.dto.response.VerifyOTPResponseDTO;

public interface AuthService {
    SignupResponseDTO signup(SignupRequestDTO signupRequestDTO);

    AuthResponseDTO login(LoginRequestDTO loginRequestDTO);

    VerifyOTPResponseDTO verifyEmailOTP(VerifyOTPRequestDTO verifyOTPRequestDTO);

    ForgotPasswordResponseDTO forgotPassword(ForgotPasswordRequestDTO forgotPasswordRequestDTO);

    ForgotPasswordResponseDTO resetPassword(ResetPasswordRequestDTO resetPasswordRequestDTO);
}
