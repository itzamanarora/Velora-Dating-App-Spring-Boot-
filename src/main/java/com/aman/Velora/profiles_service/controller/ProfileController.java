package com.aman.Velora.profiles_service.controller;

import com.aman.Velora.auth_service.service.JwtService;
import com.aman.Velora.profiles_service.dto.request.ProfileRequestDTO;
import com.aman.Velora.profiles_service.dto.response.ProfileResponseDTO;
import com.aman.Velora.profiles_service.service.ProfileService;
import com.aman.Velora.user_service.dto.page.PageResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Profile Controller", description = "Controller for managing user profiles")
@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileController {

    private final ProfileService profileService;
    private final JwtService jwtService;

    public ProfileController(ProfileService profileService, JwtService jwtService) {
        this.profileService = profileService;
        this.jwtService = jwtService;
    }

    @PostMapping("/me")
    public ResponseEntity<ProfileResponseDTO> createProfile(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody ProfileRequestDTO profileRequestDTO
    ) {
        String token = authorizationHeader.replace("Bearer ", "");
        UUID userId = jwtService.extractUserId(token);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(profileService.createProfile(userId, profileRequestDTO));
    }

    @GetMapping("/me")
    public ResponseEntity<ProfileResponseDTO> getProfileByUserId(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        UUID userId = jwtService.extractUserId(token);
        return ResponseEntity.ok(profileService.getProfileByUserId(userId));
    }

    @GetMapping
    public ResponseEntity<PageResponseDTO<ProfileResponseDTO>> getAllProfiles(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "lastActiveAt") String sortBy,
            @RequestParam(required = false) String search
    ) {
        String token = authorizationHeader.replace("Bearer ", "");
        UUID userId = jwtService.extractUserId(token);
        return ResponseEntity.ok(profileService.getAllProfiles(userId, page, pageSize, sortBy, search));
    }
}
