package com.aman.Velora.profiles_service.service;

import com.aman.Velora.profiles_service.dto.request.ProfileRequestDTO;
import com.aman.Velora.profiles_service.dto.response.ProfileResponseDTO;
import com.aman.Velora.user_service.dto.page.PageResponseDTO;

import java.util.UUID;

public interface ProfileService {

    ProfileResponseDTO getProfileByUserId(UUID userId);

    ProfileResponseDTO createProfile(UUID userId, ProfileRequestDTO profileRequestDTO);

    PageResponseDTO<ProfileResponseDTO> getAllProfiles(UUID userId, int page, int pageSize, String sortBy, String sortDir);
}
