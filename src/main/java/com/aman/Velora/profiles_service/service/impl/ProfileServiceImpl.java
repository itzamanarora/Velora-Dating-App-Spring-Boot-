package com.aman.Velora.profiles_service.service.impl;

import com.aman.Velora.profiles_service.dto.request.ProfileRequestDTO;
import com.aman.Velora.profiles_service.dto.response.ProfileResponseDTO;
import com.aman.Velora.profiles_service.exception.ProfileNotFoundException;
import com.aman.Velora.profiles_service.mapper.ProfileDTOMapper;
import com.aman.Velora.profiles_service.models.Gender;
import com.aman.Velora.profiles_service.models.Profile;
import com.aman.Velora.profiles_service.repository.ProfileRepository;
import com.aman.Velora.profiles_service.service.ProfileService;
import com.aman.Velora.user_service.dto.response.PageResponseDTO;
import com.aman.Velora.user_service.mapper.PageDTOMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@Slf4j
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public ProfileResponseDTO getProfileByUserId(UUID userId) {
        log.info("Fetching profile for userId: {}", userId);

        Profile profile = profileRepository.findByUserId(userId).orElseThrow(() ->
                new ProfileNotFoundException("Profile not found for userId: " + userId));

        log.info("Profile found: {}", profile.getFirstName() + " " + profile.getLastName());
        return ProfileDTOMapper.mapToProfileResponseDTO(profile);
    }

    @Override
    public ProfileResponseDTO createProfile(UUID userId, ProfileRequestDTO profileRequestDTO) {
        log.info("Creating profile for userId: {}", userId);

        Profile profile = ProfileDTOMapper.mapToProfile(profileRequestDTO);
        profile.setUserId(userId);
        profile.setLastActiveAt(Instant.now());
        Profile savedProfile = profileRepository.save(profile);

        log.info("Profile created: {}", savedProfile.getFirstName() + " " + savedProfile.getLastName());

        return ProfileDTOMapper.mapToProfileResponseDTO(savedProfile);
    }

    @Override
    public PageResponseDTO<ProfileResponseDTO> getAllProfiles(UUID userId, Gender genderPreferred, int page, int pageSize, String sortBy, String sortDir) {
        log.info("Fetching all profiles for userId: {} with preferred gender: {}", userId, genderPreferred);

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sortBy).descending());

        Page<ProfileResponseDTO> profilePage =
                profileRepository.
                        findAllByUserIdNotAndGender(userId, genderPreferred, pageable)
                        .map(ProfileDTOMapper::mapToProfileResponseDTO);

        return PageDTOMapper.mapToPageResponse(profilePage);
    }
}
