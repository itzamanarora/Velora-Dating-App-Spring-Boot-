package com.aman.Velora.auth_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshTokenRequestDTO {

    @NotBlank(message = "Refresh token is required.")
    private String refreshToken;
}
