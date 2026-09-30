package com.aman.Velora.swipe_service.controller;

import com.aman.Velora.auth_service.service.JwtService;
import com.aman.Velora.swipe_service.dto.request.SwipeRequestDTO;
import com.aman.Velora.swipe_service.dto.response.SwipeResponseDTO;
import com.aman.Velora.swipe_service.service.SwipeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Swipe Controller")
@RestController
@RequestMapping("/api/v1/swipe")
public class SwipeController {

    private final SwipeService swipeService;
    private final JwtService jwtService;

    public SwipeController(SwipeService swipeService, JwtService jwtService) {
        this.swipeService = swipeService;
        this.jwtService = jwtService;
    }

    @PostMapping
    public ResponseEntity<SwipeResponseDTO> swipe(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody SwipeRequestDTO swipeRequestDTO
    ) {
        String token = authorizationHeader.replace("Bearer ", "");
        UUID userId = jwtService.extractUserId(token);
        return ResponseEntity.status(HttpStatus.CREATED).body(swipeService.swipe(userId, swipeRequestDTO));
    }
}
