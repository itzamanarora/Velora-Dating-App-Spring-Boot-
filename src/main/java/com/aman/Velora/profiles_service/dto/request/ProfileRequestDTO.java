package com.aman.Velora.profiles_service.dto.request;

import com.aman.Velora.profiles_service.models.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileRequestDTO {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Date of birth is required")
    private String dateOfBirth;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotBlank(message = "Profile picture URL is required")
    @Size(max = 2048, message = "Profile picture URL must not exceed 2048 characters")
    private String profilePictureUrl;
}
