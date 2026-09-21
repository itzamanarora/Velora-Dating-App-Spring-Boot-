package com.aman.Velora.auth_service.service.impl;

import com.aman.Velora.auth_service.dto.request.LoginRequestDTO;
import com.aman.Velora.auth_service.dto.request.SignupRequestDTO;
import com.aman.Velora.auth_service.dto.response.AuthResponseDTO;
import com.aman.Velora.auth_service.dto.response.SignupResponseDTO;
import com.aman.Velora.auth_service.exception.EmailNotVerifiedException;
import com.aman.Velora.auth_service.models.OtpPurpose;
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

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

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
            OtpServiceImpl otpService
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.otpService = otpService;
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

        log.info("User Logged in: {}", user.getEmail());

        return AuthResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken("in-progress")
                .tokenType("bearer")
                .expiresIn(1231231)
                .build();
    }

}
