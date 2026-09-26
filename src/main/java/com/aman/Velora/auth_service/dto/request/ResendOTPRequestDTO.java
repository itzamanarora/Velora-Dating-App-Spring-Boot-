package com.aman.Velora.auth_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResendOTPRequestDTO {

    @Email(message = "Email is required.")
    @NotBlank(message = "Enter a valid email.")
    private String email;
}
