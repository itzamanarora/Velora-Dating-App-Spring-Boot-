package com.aman.Velora.user.dto.role;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleResponseDTO {
    private UUID id;
    private String name;
    private String displayName;
    private Instant createdAt;
    private Instant updatedAt;
}
