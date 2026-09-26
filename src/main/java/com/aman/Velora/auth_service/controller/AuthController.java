package com.aman.Velora.auth_service.controller;

import com.aman.Velora.auth_service.dto.request.*;
import com.aman.Velora.auth_service.dto.response.*;
import com.aman.Velora.auth_service.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth APIs Endpoint", description = "Signup, login, OTP verification and password reset")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(
            summary = "Register a new user",
            description = "Creates an unverified account and sends an OTP to the given email for verification."
    )
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDTO> signup(@Valid @RequestBody SignupRequestDTO signupRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.signup(signupRequestDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(authService.login(loginRequestDTO));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<VerifyOTPResponseDTO> verifyOtp(@Valid @RequestBody VerifyOTPRequestDTO verifyOTPRequestDTO) {
        return ResponseEntity.ok(authService.verifyEmailOTP(verifyOTPRequestDTO));
    }

    @PostMapping("/forget-passwod")
    public ResponseEntity<ForgotPasswordResponseDTO> forgetPassword(@Valid @RequestBody ForgotPasswordRequestDTO forgotPasswordRequestDTO) {
        return ResponseEntity.ok(authService.forgotPassword(forgotPasswordRequestDTO));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ForgotPasswordResponseDTO> resetPassword(@Valid @RequestBody ResetPasswordRequestDTO resetPasswordRequestDTO) {
        return ResponseEntity.ok(authService.resetPassword(resetPasswordRequestDTO));
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<ResendOTPResponseDTO> resentOtp(@Valid @RequestBody ResendOTPRequestDTO resendOTPRequestDTO) {
        return ResponseEntity.ok(authService.resendOtp(resendOTPRequestDTO));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponseDTO> refreshToken(@Valid @RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) {
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequestDTO));
    }
}
