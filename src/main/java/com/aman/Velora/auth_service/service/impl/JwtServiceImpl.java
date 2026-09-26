package com.aman.Velora.auth_service.service.impl;

import com.aman.Velora.auth_service.models.RefreshToken;
import com.aman.Velora.auth_service.repository.RefreshTokenRepository;
import com.aman.Velora.auth_service.service.JwtService;
import com.aman.Velora.user_service.models.User;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtServiceImpl implements JwtService {

    private final SecretKey secretKey;
    private final RefreshTokenRepository refreshTokenRepository;
    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiation;

    public JwtServiceImpl(SecretKey secretKey,
                          RefreshTokenRepository refreshTokenRepository) {
        this.secretKey = secretKey;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public String generateToken(User user) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("role", user.getRole().getDisplayName())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(accessTokenExpiation)))
                .signWith(secretKey)
                .compact();
    }

    @Override
    public boolean validateAccessToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public UUID extractUserId(String token) {
        String userId = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
        return UUID.fromString(userId);
    }

    @Override
    public long getExpirationTime() {
        return accessTokenExpiation / 1000; // Convert milliseconds to seconds
    }

    @Override
    public RefreshToken generateRefreshToken(User user) {
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiresAt(Instant.now().plusSeconds(7 * 24 * 60 * 60)) // 7 days
                .build();
        return refreshTokenRepository.save(refreshToken);
    }
}
