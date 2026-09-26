package com.aman.Velora.auth_service.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshTokenResponseDTO {
    private String accessToken;
    private String refreshToken;
    private long expiresIn;
}
