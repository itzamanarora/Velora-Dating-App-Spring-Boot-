package com.aman.Velora.auth_service.service.impl;

import com.aman.Velora.auth_service.dto.request.*;
import com.aman.Velora.auth_service.dto.response.*;
import com.aman.Velora.auth_service.exception.EmailNotVerifiedException;
import com.aman.Velora.auth_service.exception.RefreshTokenExpiredException;
import com.aman.Velora.auth_service.exception.RefreshTokenNotFoundException;
import com.aman.Velora.auth_service.models.OtpPurpose;
import com.aman.Velora.auth_service.models.RefreshToken;
import com.aman.Velora.auth_service.repository.OtpVerificationRepository;
import com.aman.Velora.auth_service.repository.RefreshTokenRepository;
import com.aman.Velora.auth_service.service.AuthService;
import com.aman.Velora.auth_service.service.JwtService;
import com.aman.Velora.auth_service.service.OtpService;
import com.aman.Velora.auth_service.exception.InvalidCredentialsException;
import com.aman.Velora.user_service.exception.role.RoleNotFoundException;
import com.aman.Velora.user_service.exception.user.UserAlreadyExistsException;
import com.aman.Velora.user_service.models.Role;
import com.aman.Velora.user_service.models.User;
import com.aman.Velora.user_service.repository.RoleRepository;
import com.aman.Velora.user_service.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final OtpVerificationRepository otpVerificationRepository;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final OtpService otpService;

    public AuthServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            OtpServiceImpl otpService,
            OtpVerificationRepository otpVerificationRepository,
            RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.otpService = otpService;
        this.otpVerificationRepository = otpVerificationRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }


    @Override
    @Transactional
    public SignupResponseDTO signup(SignupRequestDTO signupRequestDTO) {
        String email = signupRequestDTO.getEmail().trim();

        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException("Email already registered.");
        }

        Role defaultRole = roleRepository.findByNameIgnoreCaseAndIsActiveTrue("USER").orElseThrow(() ->
                new RoleNotFoundException("Role not found")
        );

        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(signupRequestDTO.getPassword()))
                .role(defaultRole)
                .build();

        User savedUser = userRepository.save(user);

        otpService.generateAndSendOtp(savedUser, OtpPurpose.EMAIL_VERIFICATION);

        log.info("User signed up successfully with email: {}", savedUser.getEmail());

        return SignupResponseDTO.builder()
                .message("Signup Successful! Please verify you email using the otp sent.")
                .email(savedUser.getEmail())
                .build();
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findByEmail(loginRequestDTO.getEmail().trim())
                .orElseThrow(InvalidCredentialsException::new);

        boolean isPasswordValid = passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword());

        if (!isPasswordValid) throw new InvalidCredentialsException();
        if (!user.isEmailVerified()) throw new EmailNotVerifiedException("Please verify your email before logging in.");

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user).getToken();

        log.info("User Logged in: {}", user.getEmail());

        return AuthResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("bearer")
                .expiresIn(jwtService.getExpirationTime())
                .build();
    }

    @Override
    public VerifyOTPResponseDTO verifyEmailOTP(VerifyOTPRequestDTO verifyOTPRequestDTO) {
        otpService.verifyOtp(
                verifyOTPRequestDTO.getEmail().trim(),
                verifyOTPRequestDTO.getOtp(),
                OtpPurpose.EMAIL_VERIFICATION
        );

        User user = userRepository.findByEmail(verifyOTPRequestDTO.getEmail().trim())
                .orElseThrow(InvalidCredentialsException::new);
        user.setEmailVerified(true);
        userRepository.save(user);

        log.info("Email verified successfully for: {}", user.getEmail());

        return VerifyOTPResponseDTO.builder()
                .message("Email verified successfully. You can now log in.")
                .build();
    }

    @Override
    public ForgotPasswordResponseDTO forgotPassword(ForgotPasswordRequestDTO forgotPasswordRequestDTO) {
        userRepository.findByEmail(forgotPasswordRequestDTO.getEmail().trim())
                .ifPresent(user -> {
                    otpService.generateAndSendOtp(user, OtpPurpose.PASSWORD_RESET);
                    log.info("Otp has been sent successfully on email: {}", user.getEmail());
                });

        return ForgotPasswordResponseDTO.builder()
                .message("If an account exists with this email, a password reset OTP has been sent.")
                .build();
    }

    @Override
    @Transactional
    public ForgotPasswordResponseDTO resetPassword(ResetPasswordRequestDTO resetPasswordRequestDTO) {
        otpService.verifyOtp(
                resetPasswordRequestDTO.getEmail().trim(),
                resetPasswordRequestDTO.getOtp(),
                OtpPurpose.PASSWORD_RESET
        );

        User user = userRepository.findByEmail(resetPasswordRequestDTO.getEmail().trim())
                .orElseThrow(InvalidCredentialsException::new);

        user.setPassword(passwordEncoder.encode(resetPasswordRequestDTO.getNewPassword()));
        userRepository.save(user);

        log.info("Password has been updated successfully on email: {}", user.getEmail());

        return ForgotPasswordResponseDTO.builder()
                .message("Password reset successfully. You can now log in with your new password.")
                .build();
    }

    @Override
    public ResendOTPResponseDTO resendOtp(ResendOTPRequestDTO resendOTPRequestDTO) {
        User user = userRepository.findByEmail(resendOTPRequestDTO.getEmail().trim())
                .orElseThrow(InvalidCredentialsException::new);

        otpService.generateAndSendOtp(user, OtpPurpose.EMAIL_VERIFICATION);

        return ResendOTPResponseDTO.builder()
                .message("OTP resent successfully.")
                .build();
    }

    @Override
    public RefreshTokenResponseDTO refreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenRequestDTO.getRefreshToken())
                .orElseThrow(() ->
                        new RefreshTokenNotFoundException("Refresh token not found. Please log in again.")
                );

        if (refreshToken.getExpiresAt().isBefore(Instant.now()))
            throw new RefreshTokenExpiredException("Refresh token is invalid or expired. Please log in again.");

        if (refreshToken.isRevoked())
            throw new RefreshTokenExpiredException("Refresh token is invalid or expired. Please log in again.");

        User user = refreshToken.getUser();

        String accessToken = jwtService.generateToken(user);

        return RefreshTokenResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .expiresIn(jwtService.getExpirationTime())
                .build();
    }

}
