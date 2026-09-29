package com.aman.Velora.profiles_service.mapper;

import com.aman.Velora.profiles_service.dto.request.ProfileRequestDTO;
import com.aman.Velora.profiles_service.dto.response.ProfileResponseDTO;
import com.aman.Velora.profiles_service.models.Gender;
import com.aman.Velora.profiles_service.models.Profile;

import java.time.LocalDate;

public class ProfileDTOMapper {

    public static ProfileResponseDTO mapToProfileResponseDTO(Profile profile) {
        return ProfileResponseDTO.builder()
                .id(profile.getId())
                .userId(profile.getUserId())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .dateOfBirth(profile.getDateOfBirth().toString())
                .gender(profile.getGender())
                .profilePictureUrl(profile.getProfilePictureUrl())
                .preferredGender(profile.getPreferredGender())
                .bio(profile.getBio())
                .createdAt(profile.getCreatedAt())
                .build();
    }

    public static Profile mapToProfile(ProfileRequestDTO profileRequestDTO) {
        return Profile.builder()
                .firstName(profileRequestDTO.getFirstName())
                .lastName(profileRequestDTO.getLastName())
                .dateOfBirth(LocalDate.parse(profileRequestDTO.getDateOfBirth()))
                .gender(Gender.valueOf(profileRequestDTO.getGender()))
                .profilePictureUrl(profileRequestDTO.getProfilePictureUrl())
                .preferredGender(Gender.valueOf(profileRequestDTO.getPreferredGender()))
                .bio(profileRequestDTO.getBio())
                .build();
    }
}
