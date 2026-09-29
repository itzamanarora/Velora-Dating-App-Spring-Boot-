package com.aman.Velora.profiles_service.dto.response;

import com.aman.Velora.profiles_service.models.Gender;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileResponseDTO {
    private UUID id;
    private UUID userId;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private Gender gender;
    private String bio;
    private Gender preferredGender;
    private String profilePictureUrl;
    private Instant createdAt;
}
