package com.aman.Velora.auth_service.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseDTO {

    private String accessToken;

    private String refreshToken;

    private String tokenType;

    private long expiresIn;
}
