package com.aman.Velora.auth_service.security.impl;

import com.aman.Velora.auth_service.dto.request.LoginRequestDTO;
import com.aman.Velora.auth_service.dto.request.SignupRequestDTO;
import com.aman.Velora.auth_service.dto.response.AuthResponseDTO;
import com.aman.Velora.auth_service.security.AuthService;
import com.aman.Velora.user_service.dto.user.UserResponseDTO;
import com.aman.Velora.auth_service.exception.auth.InvalidCredentialsException;
import com.aman.Velora.user_service.exception.role.RoleNotFoundException;
import com.aman.Velora.user_service.exception.user.UserAlreadyExistsException;
import com.aman.Velora.user_service.mapper.UserDTOMapper;
import com.aman.Velora.user_service.models.Role;
import com.aman.Velora.user_service.models.User;
import com.aman.Velora.user_service.repository.RoleRepository;
import com.aman.Velora.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponseDTO signup(SignupRequestDTO signupRequestDTO) {
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
        log.info("User signed up successfully with email: {}", savedUser.getEmail());

        return UserDTOMapper.mapToUserResponse(savedUser);
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findByEmail(loginRequestDTO.getEmail().trim())
                .orElseThrow(InvalidCredentialsException::new);

        boolean passwordHash = passwordEncoder.matches(user.getPassword(), loginRequestDTO.getPassword());

        if (!passwordHash) throw new InvalidCredentialsException("Incorrect password.");

        log.info("User Logged in: {}", user.getEmail());

        return null;
    }


}
