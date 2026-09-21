package com.aman.Velora.auth_service.dto.request;

import com.aman.Velora.auth_service.models.OtpPurpose;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerifyOTPRequestDTO {

    @Email(message = "Enter a valid email.")
    @NotBlank(message = "Email is required.")
    @Size(max = 255, message = "Email cannot exceed 255 characters")
    private String email;

    @NotBlank(message = "OTP is required.")
    @Size(min = 6, max = 6, message = "OTP must be 6 digits.")
    private String otp;

    @NotBlank(message = "purpose is required.")
    private OtpPurpose purpose;
}
