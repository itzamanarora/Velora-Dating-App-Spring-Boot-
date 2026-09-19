package com.aman.Velora.auth_service.mapper;

import com.aman.Velora.auth_service.models.RefreshToken;

import java.time.Instant;
import java.util.UUID;

public class AuthDTOMapper {

    public static RefreshToken mapToRefreshToken(UUID userId, String token, Instant expiresAt) {
        return RefreshToken.builder()
                .userId(userId)
                .token(token)
                .expiresAt(expiresAt)
                .build();
    }
}
