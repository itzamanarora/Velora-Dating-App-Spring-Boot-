package com.aman.Velora.user_service.dto.user;

import com.aman.Velora.user_service.dto.role.RoleResponseDTO;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

    private UUID id;
    private String email;
    private RoleResponseDTO role;
    private Instant createdAt;
}
