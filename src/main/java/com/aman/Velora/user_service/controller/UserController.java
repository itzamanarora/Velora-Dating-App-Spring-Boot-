package com.aman.Velora.user_service.controller;

import com.aman.Velora.user_service.dto.page.PageResponseDTO;
import com.aman.Velora.user_service.dto.user.UserResponseDTO;
import com.aman.Velora.user_service.service.user.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "User APIs Endpoint")
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<PageResponseDTO<UserResponseDTO>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(required = false) String search
    ) {
        return ResponseEntity.ok(userService.getAllUsers(page, pageSize, sortBy, search));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable UUID userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }
}
