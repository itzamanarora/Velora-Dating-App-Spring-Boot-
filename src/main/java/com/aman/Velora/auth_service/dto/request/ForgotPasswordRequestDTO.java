package com.aman.Velora.auth_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ForgotPasswordRequestDTO {

    @Email(message = "Enter a valid email.")
    @NotBlank(message = "Email is required.")
    private String email;

}
