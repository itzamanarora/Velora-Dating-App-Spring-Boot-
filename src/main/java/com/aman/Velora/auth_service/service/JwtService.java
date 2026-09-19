package com.aman.Velora.auth_service.service;

import com.aman.Velora.user_service.models.User;

import java.util.UUID;

public interface JwtService {
    String generateToken(User user);

    boolean validateAccessToken(String token);

    UUID extractUserId(String token);
}
