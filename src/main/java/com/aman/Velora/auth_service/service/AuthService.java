package com.aman.Velora.auth_service.service;

import com.aman.Velora.auth_service.dto.request.LoginRequestDTO;
import com.aman.Velora.auth_service.dto.request.SignupRequestDTO;
import com.aman.Velora.auth_service.dto.response.AuthResponseDTO;
import com.aman.Velora.user_service.dto.user.UserResponseDTO;

public interface AuthService {
    UserResponseDTO signup(SignupRequestDTO signupRequestDTO);

    AuthResponseDTO login(LoginRequestDTO loginRequestDTO);
}
