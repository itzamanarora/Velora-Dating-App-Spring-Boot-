package com.aman.Velora.auth_service.controller;

import com.aman.Velora.auth_service.dto.request.LoginRequestDTO;
import com.aman.Velora.auth_service.dto.request.SignupRequestDTO;
import com.aman.Velora.auth_service.dto.response.AuthResponseDTO;
import com.aman.Velora.auth_service.security.AuthService;
import com.aman.Velora.user_service.dto.user.UserResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth APIs Endpoint")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDTO> signup(@Valid @RequestBody SignupRequestDTO signupRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.signup(signupRequestDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(authService.login(loginRequestDTO));
    }
}
